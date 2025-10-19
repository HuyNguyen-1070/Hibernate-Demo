package com.example.service;

import com.example.entity.Language;
import com.example.entity.ProductCategory;
import com.example.entity.ProductCategoryId;
import com.example.entity.ProductCategoryTranslation;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

public class CategoryService {

    /**
     * 1. Tìm tất cả danh mục (Category) với bản dịch theo languageId cụ thể.
     */
    public List<ProductCategoryTranslation> findAllCategories(String languageId) {
        List<ProductCategoryTranslation> translations = Collections.emptyList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL: Lấy các bản dịch danh mục khớp với mã ngôn ngữ và sắp xếp theo ID
            // Không cần Transaction cho truy vấn SELECT đơn giản
            String hql = "FROM ProductCategoryTranslation pct WHERE pct.id.languageId = :langId ORDER BY pct.id.productCategoryId";
            Query<ProductCategoryTranslation> query = session.createQuery(hql, ProductCategoryTranslation.class);
            query.setParameter("langId", languageId);

            translations = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return translations;
    }

    /**
     * 2. Tạo một ProductCategory mới và trả về ID được tạo.
     */
    public int createCategory(boolean canBeShipped) {
        Transaction transaction = null;
        int generatedId = -1;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProductCategory category = new ProductCategory();
            category.setCanBeShipped(canBeShipped);

            // Lưu và lấy ID được tạo
            session.persist(category);
            generatedId = category.getProductCategoryId();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return generatedId;
    }

    /**
     * 3. Cập nhật hoặc thêm mới bản dịch (Translation) cho một Category (Upsert).
     */
    public void upsertCategoryTranslation(int categoryId, String langId, String name) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProductCategoryId pId = new ProductCategoryId(categoryId, langId);

            ProductCategoryTranslation translation = session.get(ProductCategoryTranslation.class, pId);

            if (translation == null) {
                translation = new ProductCategoryTranslation();
                translation.setId(pId);

                // Lấy Category và Language Entity từ DB để thiết lập mối quan hệ
                ProductCategory category = session.get(ProductCategory.class, categoryId);
                Language language = session.get(Language.class, langId);

                translation.setCategory(category);
                translation.setLanguage(language);
            }

            translation.setCategoryName(name);

            session.merge(translation); // Sử dụng merge cho thao tác upsert

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * 4. Xóa một danh mục (và tất cả bản dịch liên quan)
     */
    public void deleteCategory(int categoryId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Xóa tất cả bản dịch trước bằng HQL (tránh lỗi khóa ngoại)
            session.createQuery("DELETE FROM ProductCategoryTranslation WHERE id.productCategoryId = :catId")
                    .setParameter("catId", categoryId)
                    .executeUpdate();

            // Tải và xóa Entity ProductCategory chính
            ProductCategory category = session.get(ProductCategory.class, categoryId);

            if (category != null) {
                session.delete(category);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
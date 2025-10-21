package com.example.service;

import com.example.entity.*;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ProductService {

    /**
     * Lấy danh sách sản phẩm theo ID ngôn ngữ và tùy chọn lọc theo ID danh mục.
     */
    public List<ProductTranslation> findAllProducts(String languageId, Integer productCategoryId) {
        List<ProductTranslation> translations = Collections.emptyList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM ProductTranslation pt WHERE pt.id.languageId = :langId";

            if (productCategoryId != null && productCategoryId > 0) {
                hql += " AND pt.product.productCategory.productCategoryId = :catId";
            }

            hql += " ORDER BY pt.product.productId";

            Query<ProductTranslation> query = session.createQuery(hql, ProductTranslation.class);
            query.setParameter("langId", languageId);

            if (productCategoryId != null && productCategoryId > 0) {
                query.setParameter("catId", productCategoryId);
            }

            translations = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return translations;
    }

    /**
     * Lấy bản dịch sản phẩm chi tiết theo ID sản phẩm và ID ngôn ngữ.
     */
    public ProductTranslation findProductTranslation(int productId, String languageId) {
        ProductTranslation translation = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT pt FROM ProductTranslation pt "
                    + "LEFT JOIN FETCH pt.product p "
                    + "LEFT JOIN FETCH p.productCategory c "
                    + "LEFT JOIN FETCH c.translations ct "
                    + "WHERE pt.id.productId = :pId AND pt.id.languageId = :langId";
            Query<ProductTranslation> query = session.createQuery(hql, ProductTranslation.class);
            query.setParameter("pId", productId);
            query.setParameter("langId", languageId);

            translation = query.uniqueResult();

            // THÊM: Gán tên danh mục theo ngôn ngữ vào transient field
            if (translation != null && translation.getProduct() != null
                    && translation.getProduct().getProductCategory() != null) {

                ProductCategory category = translation.getProduct().getProductCategory();
                String categoryName = getCategoryNameByLanguage(category, languageId);
                category.setCurrentLanguageName(categoryName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return translation;
    }
    /**
     * Lấy tên danh mục theo ngôn ngữ
     */
    public String getCategoryNameByLanguage(ProductCategory category, String languageId) {
        if (category == null || category.getTranslations() == null) {
            return "N/A";
        }

        // Tìm bản dịch theo ngôn ngữ
        for (ProductCategoryTranslation translation : category.getTranslations()) {
            if (translation.getId().getLanguageId().equals(languageId)) {
                return translation.getCategoryName();
            }
        }

        // Fallback: trả về bản dịch đầu tiên nếu không tìm thấy
        if (!category.getTranslations().isEmpty()) {
            return category.getTranslations().iterator().next().getCategoryName();
        }

        return "N/A";
    }
    /**
     * Lấy thông tin lõi của sản phẩm.
     */
    public Product findProductCore(int productId) {
        Product product = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            product = session.get(Product.class, productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    /**
     * Thêm mới một sản phẩm (core Product)
     */
    public int createProduct(BigDecimal price, BigDecimal weight, int categoryId) {
        Transaction transaction = null;
        int newProductId = -1;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProductCategory category = session.get(ProductCategory.class, categoryId);
            Product product = new Product();

            product.setPrice(price);
            product.setWeight(weight.doubleValue());
            product.setProductCategory(category);

            session.persist(product);
            session.flush(); // Đảm bảo ID được tạo

            newProductId = product.getProductId();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return newProductId;
    }

    /**
     * Cập nhật thông tin core của Product (Giá, Khối lượng, Danh mục)
     */
    public void updateProductCore(int productId, BigDecimal price, BigDecimal weight, int categoryId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Product product = session.get(Product.class, productId);

            if (product != null) {
                product.setPrice(price);
                // ĐÃ CHỈNH SỬA: Chuyển BigDecimal sang double cho trường Weight
                product.setWeight(weight.doubleValue());

                ProductCategory category = session.get(ProductCategory.class, categoryId);
                // FIX: Sử dụng setProductCategory thay vì setCategory
                product.setProductCategory(category);

                session.merge(product);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Cập nhật hoặc thêm mới bản dịch (Translation) cho một Product (Upsert).
     */
    public void upsertProductTranslation(int productId, String langId, String name, String description) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProductTranslationId pId = new ProductTranslationId(productId, langId);
            ProductTranslation translation = session.get(ProductTranslation.class, pId);

            if (translation == null) {
                translation = new ProductTranslation();
                translation.setId(pId);

                Product product = session.get(Product.class, productId);
                Language language = session.get(Language.class, langId);

                translation.setProduct(product);
                translation.setLanguage(language);
            }

            translation.setProductName(name);
            translation.setProductDescription(description);

            session.merge(translation);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Xóa sản phẩm, bao gồm tất cả các bản dịch liên quan.
     */
    public void deleteProduct(int productId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // 1. Xóa tất cả bản dịch (ProductTranslation)
            session.createQuery("DELETE FROM ProductTranslation WHERE id.productId = :pid")
                    .setParameter("pid", productId)
                    .executeUpdate();

            // 2. Tải và xóa Entity Product chính
            Product product = session.get(Product.class, productId);

            if (product != null) {
                session.delete(product);
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
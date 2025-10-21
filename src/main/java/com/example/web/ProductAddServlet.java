package com.example.web;

import com.example.entity.ProductCategoryTranslation;
import com.example.service.CategoryService;
import com.example.service.ProductService;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/products/add")
public class ProductAddServlet extends HttpServlet {

    private ProductService productService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String langCode = LangUtil.resolveLang(req);
        String languageId = langCode.toUpperCase();
        req.setAttribute("lang", langCode);

        // Lấy danh sách danh mục để hiển thị trong dropdown
        List<ProductCategoryTranslation> categories = categoryService.findAllCategories(languageId);
        req.setAttribute("categories", categories);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/product-add.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String langCode = LangUtil.resolveLang(req);

        try {
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            BigDecimal weight = new BigDecimal(req.getParameter("weight"));
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));

            // 1️⃣ Tạo sản phẩm core
            int newProductId = productService.createProduct(price, weight, categoryId);

            // 2️⃣ Lấy dữ liệu bản dịch
            String name_vi = req.getParameter("name_vi");
            String desc_vi = req.getParameter("desc_vi");
            String name_en = req.getParameter("name_en");
            String desc_en = req.getParameter("desc_en");
            String name_fr = req.getParameter("name_fr");
            String desc_fr = req.getParameter("desc_fr");

            // 3️⃣ Lưu các bản dịch (nếu có nhập)
            if (name_vi != null && !name_vi.trim().isEmpty())
                productService.upsertProductTranslation(newProductId, "VI", name_vi, desc_vi);

            if (name_en != null && !name_en.trim().isEmpty())
                productService.upsertProductTranslation(newProductId, "EN", name_en, desc_en);

            if (name_fr != null && !name_fr.trim().isEmpty())
                productService.upsertProductTranslation(newProductId, "FR", name_fr, desc_fr);

            // 4️⃣ Chuyển hướng về danh sách
            resp.sendRedirect(req.getContextPath() + "/home?lang=" + langCode);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi khi thêm sản phẩm: " + e.getMessage());
            doGet(req, resp);
        }
    }
}

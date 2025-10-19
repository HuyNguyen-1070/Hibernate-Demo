package com.example.web;

import com.example.entity.ProductCategoryTranslation;
import com.example.entity.ProductTranslation;
import com.example.entity.Product;
import com.example.service.ProductService;
import com.example.service.CategoryService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(urlPatterns={"/products/edit"})
public class ProductEditServlet extends HttpServlet {

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

        // THÊM: Set UTF-8 encoding
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String langCode = LangUtil.resolveLang(req);
        String languageId = langCode.toUpperCase();
        req.setAttribute("lang", langCode);

        // 1. Lấy ID sản phẩm
        int id = 0;
        try { id = Integer.parseInt(req.getParameter("id")); } catch (Exception ignore) {}

        // 2. Lấy thông tin lõi (Product core) và bản dịch hiện tại
        Product coreProduct = productService.findProductCore(id);
        ProductTranslation viTranslation = productService.findProductTranslation(id, "VI");
        ProductTranslation enTranslation = productService.findProductTranslation(id, "EN");
        ProductTranslation frTranslation = productService.findProductTranslation(id, "FR");

        // 3. Lấy tất cả danh mục
        List<ProductCategoryTranslation> categories = categoryService.findAllCategories(languageId);

        // Đặt thuộc tính cho view
        req.setAttribute("coreProduct", coreProduct);
        req.setAttribute("vi", viTranslation);
        req.setAttribute("en", enTranslation);
        req.setAttribute("fr", frTranslation);
        req.setAttribute("categories", categories);

        // 4. Forward
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/product-edit.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // THÊM: Set UTF-8 encoding TRƯỚC KHI lấy parameters
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Lấy lại ngôn ngữ để chuyển hướng
        String langCode = LangUtil.resolveLang(req);
        req.setAttribute("lang", langCode);

        // 1. Lấy dữ liệu Core Product (Giá, Khối lượng, Danh mục)
        int id = Integer.parseInt(req.getParameter("id"));

        BigDecimal price = new BigDecimal(req.getParameter("price"));
        BigDecimal weight = new BigDecimal(req.getParameter("weight"));
        int categoryId = Integer.parseInt(req.getParameter("categoryId"));

        // 2. Lấy dữ liệu Dịch thuật (VI, EN, FR) - ĐÃ ĐƯỢC MÃ HÓA ĐÚNG
        String name_vi = req.getParameter("name_vi");
        String desc_vi = req.getParameter("desc_vi");
        String name_en = req.getParameter("name_en");
        String desc_en = req.getParameter("desc_en");
        String name_fr = req.getParameter("name_fr");
        String desc_fr = req.getParameter("desc_fr");

        // DEBUG: In ra để kiểm tra encoding
        System.out.println("Name VI: " + name_vi);
        System.out.println("Desc VI: " + desc_vi);

        // 3. Cập nhật Core Product
        productService.updateProductCore(id, price, weight, categoryId);

        // 4. Cập nhật các bản dịch
        if (name_vi != null && !name_vi.trim().isEmpty())
            productService.upsertProductTranslation(id, "VI", name_vi, desc_vi);
        if (name_en != null && !name_en.trim().isEmpty())
            productService.upsertProductTranslation(id, "EN", name_en, desc_en);
        if (name_fr != null && !name_fr.trim().isEmpty())
            productService.upsertProductTranslation(id, "FR", name_fr, desc_fr);

        // 5. Chuyển hướng về trang chi tiết sản phẩm vừa sửa
        resp.sendRedirect(req.getContextPath() + "/products/detail?id=" + id + "&lang=" + langCode);
    }
}
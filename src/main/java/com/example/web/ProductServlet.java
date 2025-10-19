package com.example.web;

import com.example.entity.ProductCategoryTranslation;
import com.example.entity.ProductTranslation;
import com.example.service.CategoryService;
import com.example.service.ProductService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class ProductServlet extends HttpServlet {

    private ProductService productService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        categoryService = new CategoryService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Xác định ngôn ngữ
        String langCode = LangUtil.resolveLang(request);
        String languageId = langCode.toUpperCase();

        request.setAttribute("lang", langCode);

        // 2. Lấy danh sách Categories để hiển thị sidebar
        List<ProductCategoryTranslation> categories = categoryService.findAllCategories(languageId);
        request.setAttribute("categories", categories);

        // 3. Xử lý tham số lọc Category (cat)
        Integer activeCatId = null;
        try {
            String catParam = request.getParameter("cat");
            if (catParam != null && !catParam.isEmpty()) {
                activeCatId = Integer.valueOf(catParam);
            }
        } catch (Exception ignore) {}

        request.setAttribute("activeCatId", activeCatId);

        // 4. Lấy danh sách sản phẩm
        List<ProductTranslation> products = productService.findAllProducts(languageId, activeCatId);
        request.setAttribute("products", products); // Đã sửa tên attribute từ 'productList' thành 'products'

        // 5. Forward request đến JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
        dispatcher.forward(request, response);
    }
}
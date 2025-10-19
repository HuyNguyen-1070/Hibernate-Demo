package com.example.web;

import com.example.entity.ProductCategoryTranslation;
import com.example.service.CategoryService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {

    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        categoryService = new CategoryService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Xác định ngôn ngữ
        String langCode = LangUtil.resolveLang(request);
        String languageId = langCode.toUpperCase();

        request.setAttribute("lang", langCode);

        // 2. Gọi Service để lấy danh sách danh mục
        List<ProductCategoryTranslation> categories = categoryService.findAllCategories(languageId);

        // 3. Đặt danh sách vào request scope
        request.setAttribute("categories", categories);

        // 4. Forward request đến JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/categories.jsp");
        dispatcher.forward(request, response);
    }
}
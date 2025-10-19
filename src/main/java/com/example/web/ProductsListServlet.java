package com.example.web;

import com.example.entity.ProductTranslation;
import com.example.service.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet hiển thị toàn bộ danh sách sản phẩm (products.jsp)
 */
@WebServlet("/products")
public class ProductsListServlet extends HttpServlet {

    private ProductService productService;

    public void init() {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String langCode = LangUtil.resolveLang(request);
        String languageId = langCode.toUpperCase();
        request.setAttribute("lang", langCode);

        List<ProductTranslation> productList = productService.findAllProducts(languageId, null);
        request.setAttribute("products", productList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/products.jsp");
        dispatcher.forward(request, response);
    }
}
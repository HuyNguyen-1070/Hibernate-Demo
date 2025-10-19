package com.example.web;

import com.example.entity.ProductTranslation;
import com.example.service.ProductService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/products/detail"})
public class ProductDetailServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String langCode = LangUtil.resolveLang(req);
        String languageId = langCode.toUpperCase();
        req.setAttribute("lang", langCode);

        // 1. Lấy ID sản phẩm từ tham số
        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing product ID.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID format.");
            return;
        }

        // 2. Gọi Service để lấy thông tin chi tiết sản phẩm
        ProductTranslation p = productService.findProductTranslation(id, languageId);

        if (p == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found.");
            return;
        }

        // 3. Đặt sản phẩm vào request scope
        req.setAttribute("p", p);

        // 4. Forward tới view chi tiết
        req.getRequestDispatcher("/WEB-INF/views/product-detail.jsp").forward(req, resp);
    }
}
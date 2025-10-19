package com.example.web;

import com.example.service.ProductService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet(urlPatterns={"/products/delete"})
public class ProductDeleteServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String lang = LangUtil.resolveLang(req);
        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            // Bỏ qua nếu ID không hợp lệ
        }

        if (id > 0) {
            // Gọi Service để thực hiện thao tác xóa
            productService.deleteProduct(id);
        }

        // Chuyển hướng về trang chủ /home (danh sách sản phẩm)
        resp.sendRedirect(req.getContextPath() + "/home?lang=" + lang);
    }
}
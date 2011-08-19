//package com.example;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.example.business.ProductService;
//import com.example.model.Product;
//
//@WebServlet(urlPatterns={"/products"})
//public class ProductServlet extends HttpServlet {
//
//    @EJB
//    private ProductService productService;
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Preprocess request: load list of products for display in JSP.
//        List<Product> products = productService.list();
//        request.setAttribute("products", products);
//        request.getRequestDispatcher("/WEB-INF/products.jsp").forward(request, response);
//    }
//
//}
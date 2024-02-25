package com.mziuri;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mziuri.storage.StorageReader;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {
    ProductDao productDao = new ProductDao();

    @Override
    public void init() {
        StorageReader.getInstance().readAndAddProducts(JDBCConnector.getInstance().getEntityManager());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");

        PrintWriter printWriter = response.getWriter();
        printWriter.println(new ObjectMapper().writeValueAsString(productDao.getProducts()));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantityToPurchase = Integer.parseInt(request.getParameter("quantity"));
        System.out.println(productId);
        System.out.println("Aaaaaaaa" + quantityToPurchase);

        productDao.purchaseProduct(productId, quantityToPurchase);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Purchase successful");
    }
}
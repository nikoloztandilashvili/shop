package com.mziuri;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

@WebServlet("/update-products")
public class UpdateProductsServlet extends HttpServlet {
    private static final String JSON_FILE_PATH = "src/main/resources/storage.json";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Product[] updatedProducts = objectMapper.readValue(request.getReader(), Product[].class);
        for (int i =0; i<updatedProducts.length; i++){
            System.out.println(updatedProducts[i]);
        }
        objectMapper.writeValue(new File(JSON_FILE_PATH), updatedProducts);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Products updated successfully");
    }
}


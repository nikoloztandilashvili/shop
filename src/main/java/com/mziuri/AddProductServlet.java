package com.mziuri;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/add-product")
public class AddProductServlet extends HttpServlet {
    private static final String JSON_FILE_PATH = "src/main/resources/storage.json";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Product[] newProducts = objectMapper.readValue(request.getReader(), Product[].class);


        List<Product> products = objectMapper.readValue(new File(JSON_FILE_PATH), new TypeReference<List<Product>>() {});
        products.addAll(Arrays.asList(newProducts));
        objectMapper.writeValue(new File(JSON_FILE_PATH), products);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Products added successfully");
    }
}


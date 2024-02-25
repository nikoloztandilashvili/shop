package com.mziuri.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mziuri.Product;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.io.File;

@RequiredArgsConstructor
public class StorageReader {
    private static final String STORAGE_FILE_PATH = "src/main/resources/storage.json";

    private static StorageReader instance;

    public static synchronized StorageReader getInstance() {
        if (instance == null) {
            instance = new StorageReader();
        }
        return instance;
    }

    public void readAndAddProducts(EntityManager entityManager) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(STORAGE_FILE_PATH);

            StorageConfig shop = objectMapper.readValue(file, StorageConfig.class);

            entityManager.getTransaction().begin();

            for (Product product : shop.getProducts()) {
                entityManager.merge(product);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
}

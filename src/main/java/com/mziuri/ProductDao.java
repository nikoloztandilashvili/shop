package com.mziuri;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private CriteriaQuery<Product> select;
    private TypedQuery<Product> productTypedQuery;
    private final JDBCConnector jdbcConnector = JDBCConnector.getInstance();


    public List<Product> getProducts() {
        return jdbcConnector.getEntityManager()
                .createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();
    }


    public List<GetProductResponse> getProductResponse() {
        select = jdbcConnector.getCriteriaQuery().select(jdbcConnector.getProductsRoot().get("name").get("quantity"));
        productTypedQuery = jdbcConnector.getEntityManager().createQuery(select);

        List<Product> products = productTypedQuery.getResultList();
        List<GetProductResponse> getProductResponseList = new ArrayList<>();

        products.forEach(product -> getProductResponseList.add(new GetProductResponse(product.getProd_name(), product.getProd_amount())));

        return getProductResponseList;
    }


    public void purchaseProduct(int productId, int quantityToPurchase) {
        EntityManager entityManager = jdbcConnector.getEntityManager();

        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("UPDATE Product p SET p.quantity = p.quantity - ?1 WHERE p.id = ?2");
            query.setParameter(1, quantityToPurchase);
            query.setParameter(2, productId);
            query.executeUpdate();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Failed to purchase product", e);
        } finally {
            entityManager.close();
        }
    }



    public Product getProductById(int id) {
        return null;
    }


    public void addNewProduct(Product product) {

    }
}

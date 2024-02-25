package com.mziuri;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String prod_name;

    @Column(name = "price")
    private float prod_price;

    @Column(name = "quantity")
    private int prod_amount;



    public int getId() {
        return id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public float getProd_price() {
        return prod_price;
    }

    public int getProd_amount() {
        return prod_amount;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public void setProd_price(float prod_price) {
        this.prod_price = prod_price;
    }

    public void setProd_amount(int prod_amount) {
        this.prod_amount = prod_amount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + prod_name + '\'' +
                ", price=" + prod_price +
                ", quantity=" + prod_amount +
                '}';
    }
}


package com.example.myWebApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
@Entity
public class Product {
    @Id
    private int prodID;
    private String prodName;

    public Product() {
    }

    private int price;

    public void setProdID(int prodID) {
        this.prodID = prodID;
    }


    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Product(int prodID, String prodName, int price) {
        this.prodID = prodID;
        this.prodName = prodName;
        this.price = price;
    }

    @Override
    public String toString(){
        return "Product{"+
                "prodID="+prodID+
                ",prodName="+prodName+
                ",price="+price+
                '}';
    }
}

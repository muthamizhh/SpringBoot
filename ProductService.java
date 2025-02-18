package com.example.myWebApp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.example.myWebApp.model.Product;
import com.example.myWebApp.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ProductService {
    @Autowired
    ProductRepo repo;

//    List<Product> products = new ArrayList<>(Arrays.asList(
//            new Product(1,"macBook Pro",110000),
//            new Product(2,"HP Probook",50000),
//            new Product(3,"Water Bottle",100)
//    ));
//    public List<Product> getProducts(){
//        return products;
//    }
//
//    public Product getProductById(int prodID) {
//        return products.stream()
//                .filter(p -> p.getProdID() == prodID)
//                .findFirst().orElse(new Product(100,"Product not found",0));
//    }
//
//    public void addProduct( Product prod){
//        System.out.println(prod);
//        products.add(prod);
//    }
//
//    public void updateProduct(Product prod) {
//        for(int i=0;i<products.size();i++){
//            if (products.get(i).getProdID()==prod.getProdID()){
//                products.set(i,prod);
//            }
//        }
//    }
//
//    public void deleteProduct(int prodID) {
//        for (int i=0;i<products.size();i++){
//            if(products.get(i).getProdID()==prodID){
//                products.remove(i);
//            }
//        }
//    }
    public List<Product> getProducts(){
        return repo.findAll();
    }

    public Product getProductById(int prodID) {
        return repo.findById(prodID).orElse(new Product(404,"Element Not found",500));
    }

    public void addProduct( Product prod){
        repo.save(prod);
    }

    public void updateProduct(Product prod) {
        repo.save(prod);
    }

    public void deleteProduct(int prodID) {
        repo.deleteById(prodID);
    }
}

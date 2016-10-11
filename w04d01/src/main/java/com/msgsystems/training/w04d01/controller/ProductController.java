package com.msgsystems.training.w04d01.controller;

import com.msgsystems.training.w04d01.data.model.Product;
import com.msgsystems.training.w04d01.service.ProductService;

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public String readProduct(int id) {
        try {
            return productService.readProduct(id).toString();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public void saveProduct(final Product product) {
        try {
            productService.saveProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.msgsystems.training.w04d04.repository;

import com.msgsystems.training.w04d04.model.Product;

import java.util.Arrays;
import java.util.List;

public class ProductRepository {

    public Product get(int id) {
        return new Product(id, "Dell XPS 9370");
    }

    public List<Product> getProducts() {
        return Arrays.asList(new Product(1, "Asus"), new Product(1, "Dell"));
    }

    public void saveProduct(final Product product) {
    }
}

package com.msgsystems.training.w04d01.service;

import com.msgsystems.training.w04d01.data.model.Product;
import com.msgsystems.training.w04d01.data.repository.ProductRepository;

@SuppressWarnings("unused")
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProduct(final Product product) {
        productRepository.saveProduct(product);
    }

    public Product getProduct(final int id) {
        return productRepository.getProduct(id);
    }

    public void updateProduct(final Product product) {
        productRepository.updateProduct(product);
    }

    public void deleteProduct(final Product product) {
        productRepository.deleteProduct(product);
    }
}

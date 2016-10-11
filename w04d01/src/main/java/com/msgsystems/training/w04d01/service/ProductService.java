package com.msgsystems.training.w04d01.service;

import com.msgsystems.training.w04d01.data.model.Product;
import com.msgsystems.training.w04d01.data.repository.ProductRepository;

import javax.persistence.EntityNotFoundException;

@SuppressWarnings("unused")
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /** CRUD operations */

    public void saveProduct(final Product product) throws Exception {
        // perform very complex logic before saving the product...
        productRepository.saveProduct(product);
    }

    public Product readProduct(final int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Please provide a positive ID");
        }

        Product byId = productRepository.getProduct(id);
        // do complex processing on the obtained product, before returning it to the controller

        /* if (Java8)
        return Optional.ofNullable(byId)
                       .orElseThrow(() -> new EntityNotFoundException("No product with the id " + id));
        */

        if (byId != null) {
            return byId;
        } else {
            throw new EntityNotFoundException("There is no product with the id " + id);
        }
    }

    public void updateProduct(final Product product) throws Exception {
        productRepository.updateProduct(product);
    }

    public void deleteProduct(final Product product) throws Exception {
        productRepository.deleteProduct(product);
    }
}

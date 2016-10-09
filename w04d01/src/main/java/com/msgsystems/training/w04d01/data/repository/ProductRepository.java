package com.msgsystems.training.w04d01.data.repository;

import com.msgsystems.training.w04d01.data.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ProductRepository {

    private final EntityManager entityManager;

    public ProductRepository(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Product getProduct(final int id) {
        return entityManager.find(Product.class, id);
    }

    public void saveProduct(final Product product) {
        final EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        try {
            entityManager.persist(product);
            entityManager.flush();

            transaction.commit();
        } catch (final Exception ex) {
            rollbackTransaction(transaction);
        }
    }

    public void updateProduct(final Product product) {
        final EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        try {
            entityManager.merge(product);
            entityManager.flush();

            transaction.commit();
        } catch (final Exception ex) {
            rollbackTransaction(transaction);
        }
    }

    public void deleteProduct(final Product product) {
        final EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        try {
            entityManager.remove(product);
            entityManager.flush();

            transaction.commit();
        } catch (final Exception ex) {
            rollbackTransaction(transaction);
        }
    }

    private void rollbackTransaction(final EntityTransaction transaction) {
        if (transaction.isActive()) {
            transaction.rollback();
        }
    }
}

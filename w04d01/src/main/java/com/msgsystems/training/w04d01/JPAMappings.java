package com.msgsystems.training.w04d01;

import com.msgsystems.training.w04d01.data.repository.ProductRepository;
import com.msgsystems.training.w04d01.service.ProductService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class JPAMappings {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private static ProductRepository productRepository;
    private static ProductService productService;

    static {
        // disable Hibernate's logging messages
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        entityManagerFactory = Persistence.createEntityManagerFactory("msg_training");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public static void main(final String[] args) {
        productRepository = new ProductRepository(entityManager);
        productService = new ProductService(productRepository);

        System.out.println("The connection was properly established");

        closeEntityManagerObjects();
    }

    private static void closeEntityManagerObjects() {
        Optional.ofNullable(entityManager).ifPresent(EntityManager::close);
        Optional.ofNullable(entityManagerFactory).ifPresent(EntityManagerFactory::close);
    }
}

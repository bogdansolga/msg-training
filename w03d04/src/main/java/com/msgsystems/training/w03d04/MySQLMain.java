package com.msgsystems.training.w03d04;

import com.msgsystems.training.w03d04.repository.ProductRepository;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLMain {

    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (final SQLException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static void main(final String[] args) {
        final ProductRepository productRepository = new ProductRepository();
        System.out.println(productRepository.getCount());
    }
}

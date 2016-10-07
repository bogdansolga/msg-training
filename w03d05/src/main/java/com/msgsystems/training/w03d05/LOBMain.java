package com.msgsystems.training.w03d05;

import com.msgsystems.training.w03d05.repository.ProductRepository;
import com.msgsystems.training.w03d05.service.ProductService;

import java.sql.DriverManager;
import java.sql.SQLException;

public class LOBMain {

    private static final String IMAGE_FILE = "minimalism.jpg";
    private static final String DESCRIPTION_FILE = "spring-boot.xml";

    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (final SQLException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static void main(final String[] args) {
        final ProductService productService = new ProductService(new ProductRepository());

        final int imageId = productService.saveImage(IMAGE_FILE);
        final int descriptionId = productService.saveDescription(DESCRIPTION_FILE);

        productService.loadImage(imageId, "loaded.jpg");
        productService.loadDescription(descriptionId, "loaded.xml");
    }
}

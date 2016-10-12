package com.msgsystems.training.w04d03.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductService {

    private static final Logger LOGGER = LogManager.getLogger();

    public void displayProduct() {
        LOGGER.trace("Displaying an interesting product");
    }
}

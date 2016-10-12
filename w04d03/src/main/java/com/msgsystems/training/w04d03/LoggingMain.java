package com.msgsystems.training.w04d03;

import com.msgsystems.training.w04d03.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingMain {

    private final static Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        LOGGER.info("Hello, Log4J 2!");
        LOGGER.debug("A message with parameters {}", 15);

        LOGGER.warn("A possible problem - {} and {}", "first", "second");
        LOGGER.error("A big problem");

        LOGGER.trace("A trace message");

        //LOGGER.fatal("A fatal error / exception", new Exception("it's raining"));

        ProductService productService = new ProductService();
        productService.displayProduct();
    }
}

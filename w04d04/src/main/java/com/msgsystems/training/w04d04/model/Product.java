package com.msgsystems.training.w04d04.model;

import java.io.Serializable;

public class Product implements Serializable {

    private final int id;
    private final String name;

    public Product(final int id, final String name) {
        this.id = id; this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

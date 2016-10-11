package com.msgsystems.training.w04d01.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(
        name = "Product",
        schema = "msg_training"
)
@NamedQuery(
        name = "Product.bySection",
        query = "SELECT product " +
                "FROM Product product " +
                "WHERE product.storeSection.store.id = :storeId"
)
public class Product implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = true, nullable = false, insertable = true, updatable = false, length = 50)
    private String name;

    @ManyToOne(targetEntity = StoreSection.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "sectionId")
    private StoreSection storeSection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StoreSection getStoreSection() {
        return storeSection;
    }

    public void setStoreSection(StoreSection storeSection) {
        this.storeSection = storeSection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id &&
                Objects.equals(name, product.name) &&
                Objects.equals(storeSection, product.storeSection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, storeSection);
    }

    @Override
    public String toString() {
        return id + ", " + name;
    }
}

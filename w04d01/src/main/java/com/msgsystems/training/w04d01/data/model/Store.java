package com.msgsystems.training.w04d01.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Store implements Serializable {

    @Id
    private int id;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "StoreManager",
            joinColumns = {
                // navigating from the 'StoreManager' to the 'Store'
                @JoinColumn(name = "store_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    // navigating from the 'StoreManager' to the 'Manager'
                @JoinColumn(name = "manager_id", referencedColumnName = "id")
            }
    )
    private Set<Manager> storeManagers;

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

    public Set<Manager> getStoreManagers() {
        return storeManagers;
    }

    public void setStoreManagers(Set<Manager> storeManagers) {
        this.storeManagers = storeManagers;
    }
}

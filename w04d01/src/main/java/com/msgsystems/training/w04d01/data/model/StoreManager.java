package com.msgsystems.training.w04d01.data.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "StoreManager")
public class StoreManager implements Serializable {

    @EmbeddedId
    private StoreManagerPK storeManagerPK;

    public StoreManagerPK getStoreManagerPK() {
        return storeManagerPK;
    }

    public void setStoreManagerPK(StoreManagerPK storeManagerPK) {
        this.storeManagerPK = storeManagerPK;
    }
}

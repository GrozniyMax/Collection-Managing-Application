package com.example.demo.CollectionWrappers;

import com.example.demo.CommonClasses.Entities.Flat;

import java.util.Objects;

public class CollectionItem {
    Flat object;
    String ownerLogin;

    public CollectionItem(Flat object, String ownerLogin) {
        this.object = object;
        this.ownerLogin = ownerLogin;
    }

    public Flat getItem() {
        return object;
    }

    public void setItem(Flat object) {
        this.object = object;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionItem that = (CollectionItem) o;
        return Objects.equals(object, that.object) && Objects.equals(ownerLogin, that.ownerLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, ownerLogin);
    }
}

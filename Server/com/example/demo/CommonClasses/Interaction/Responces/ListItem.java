package com.example.demo.CommonClasses.Interaction.Responces;

import com.example.demo.CommonClasses.Entities.Flat;

import java.io.Serializable;

public class ListItem implements Serializable {
    private Flat value;
    private String owner;

    public ListItem(String owner, Flat value) {
        this.owner = owner;
        this.value = value;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Flat getValue() {
        return value;
    }

    public void setValue(Flat value) {
        this.value = value;
    }
}

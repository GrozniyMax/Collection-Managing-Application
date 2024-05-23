package com.example.demo.CommonClasses.Interaction.Responces;

import com.example.demo.CommonClasses.Entities.Flat;

import java.io.Serializable;
import java.util.List;

public record RefreshResponce(List<Flat> objects) implements Serializable {
    //contains object to insert into table
}

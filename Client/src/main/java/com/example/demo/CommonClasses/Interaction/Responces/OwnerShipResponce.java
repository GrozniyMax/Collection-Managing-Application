package com.example.demo.CommonClasses.Interaction.Responces;

import com.example.demo.CommonClasses.Entities.Flat;

import java.io.Serializable;
import java.util.List;

public record OwnerShipResponce(List<ListItem> objects) implements Serializable {
}

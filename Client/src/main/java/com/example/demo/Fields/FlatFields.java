package com.example.demo.Fields;

import com.example.demo.Proxies.FlatProxy;

import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;

public enum FlatFields implements Field {

    NONE("NONE",
            FlatProxy::getId),
    ID("ID",
            FlatProxy::getId),
    NAME("Name",
            FlatProxy::getName),
    COORDINATES("Coordinates",
            FlatProxy::getCoordinates),
    CREATION_DATE("Creation date",
            FlatProxy::getCreationDate),
    AREA("Area",
            FlatProxy::getArea),
    NUMBER_OF_ROOMS("Number of rooms",
            FlatProxy::getNumberOfRooms),
    FURNISH("Furnish",
            FlatProxy::getFurnishProxy),
    VIEW("View",
            FlatProxy::getViewProxy),
    TRANSPORT("Transport",
            FlatProxy::getTransportProxy),
    HOUSE("House",
            FlatProxy::getHouse),;

    private String localName;
    private Function<FlatProxy, Comparable> function;


    FlatFields(String localName, Function<FlatProxy, Comparable> function) {
        this.localName = localName;
        this.function = function;
    }

    public static void localize(){
        ResourceBundle r = Field.loadResourceBundle();
        for(FlatFields field : FlatFields.values()){
            field.localName = Objects.requireNonNull(r.getString(field.name()),"NULL RESOURCE");
        }
        System.out.println("localized fields!");
    }

    public static FlatFields[] getSimpleField(){
        return Arrays.stream(FlatFields.values()).filter((f) -> !(f.equals(COORDINATES)||f.equals(HOUSE))).toArray(FlatFields[]::new);
    }

    public static FlatFields valueOfLocalName(String localName){
        for(FlatFields field : FlatFields.values()){
            if(field.localName.equals(localName)){
                return field;
            }
        }
        throw new IllegalArgumentException("No such field: " + localName+ "or it is complex");
    }


    @Override
    public Function<FlatProxy, Comparable> getFieldValueFunction() {
        return Objects.requireNonNull(this.function,"FlatField");
    }

    @Override
    public String toLocalString() {
        return this.localName;
    }


    @Override
    public String toString() {
        return toLocalString();
    }
}

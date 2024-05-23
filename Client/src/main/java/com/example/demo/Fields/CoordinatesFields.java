package com.example.demo.Fields;

import com.example.demo.Proxies.FlatProxy;

import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;

public enum CoordinatesFields implements Field {
    COODINATE_X("X",
            flatProxy -> flatProxy.getCoordinates().getX()),
    COODINATE_Y("Y",
            flatProxy -> flatProxy.getCoordinates().getY()),;

    private String localName;
    private Function<FlatProxy, Comparable> function;


    CoordinatesFields(String localName, Function<FlatProxy, Comparable> function) {
        this.localName = localName;
        this.function = function;
    }

    public static void localize(){
        ResourceBundle r = Field.loadResourceBundle();
        for(CoordinatesFields field : CoordinatesFields.values()){
            field.localName = r.getString("Coordinates."+field.name());
        }
    }

    public static CoordinatesFields valueOfLocalName(String localName){
        for(CoordinatesFields field : CoordinatesFields.values()){
            if(field.localName.equals(localName)){
                return field;
            }
        }
        throw new IllegalArgumentException("No such field: " + localName);
    }

    @Override
    public Function<FlatProxy, Comparable> getFieldValueFunction() {
        return Objects.requireNonNull(this.function,"Coordinates field");
    }

    @Override
    public String toLocalString() {
        return FlatFields.COORDINATES.toLocalString()+this.localName;
    }


    @Override
    public String toString() {
        return toLocalString();
    }
}

package com.example.demo.Fields;

import com.example.demo.Proxies.FlatProxy;

import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;

public enum HouseFields implements Field{
    HOUSE_NAME("name",
            flatProxy -> flatProxy.getHouse().getName()),
    HOUSE_YEAR("year",
            flatProxy -> flatProxy.getHouse().getYear()),
    HOUSE_NUMBER_OF_FLOORS("number of floors",
            flatProxy -> Integer.valueOf(flatProxy.getHouse().getNumberOfFloors()));

    private String localName;
    private Function<FlatProxy, Comparable> function;


    HouseFields(String localName, Function<FlatProxy, Comparable> function) {
        this.localName = localName;
        this.function = function;
    }

    public static void localize(){
        ResourceBundle r = Field.loadResourceBundle();
        for(HouseFields field : HouseFields.values()){
            field.localName = r.getString("House."+field.name());
        }
    }

    public static HouseFields valueOfLocalName(String localName){
        for(HouseFields field : HouseFields.values()){
            if(field.localName.equals(localName)){
                return field;
            }
        }
        throw new IllegalArgumentException("No such field: " + localName);
    }


    @Override
    public Function<FlatProxy, Comparable> getFieldValueFunction() {
        return Objects.requireNonNull(this.function,"House field");
    }

    @Override
    public String toLocalString() {
        return FlatFields.HOUSE.toLocalString()+localName;
    }


    @Override
    public String toString() {
        return toLocalString();
    }
}

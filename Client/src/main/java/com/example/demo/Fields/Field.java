package com.example.demo.Fields;

import com.example.demo.Managers.SessionContext;
import com.example.demo.Proxies.FlatProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Function;

public interface Field {

    public Function<FlatProxy,Comparable> getFieldValueFunction();

    public String toLocalString();

    public static Field valueOfLocalString(String localString) {
        try {
            return FlatFields.valueOfLocalName(localString);
        }catch (IllegalArgumentException e){
            try {
                return CoordinatesFields.valueOfLocalName(localString);
            }catch (IllegalArgumentException e1){
                try {
                    return HouseFields.valueOfLocalName(localString);
                }catch (IllegalArgumentException e2){
                    throw new IllegalArgumentException("No such field: " + localString);
                }
            }
        }
    }

    public static Field[] getAllFields() {
        ArrayList<Field> allFields = new ArrayList<>(30);
        allFields.addAll(Arrays.stream(FlatFields.getSimpleField()).toList());
        allFields.addAll(Arrays.stream(CoordinatesFields.values()).toList());
        allFields.addAll(Arrays.stream(HouseFields.values()).toList());
        return allFields.toArray(Field[]::new);
    }

    public static ResourceBundle loadResourceBundle() {
        return ResourceBundle.getBundle(Field.class.getCanonicalName(), SessionContext.getLanguage());
    }




}

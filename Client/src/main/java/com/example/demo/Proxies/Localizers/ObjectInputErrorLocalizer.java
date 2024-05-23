package com.example.demo.Proxies.Localizers;

import com.example.demo.Fields.CoordinatesFields;
import com.example.demo.Fields.Field;
import com.example.demo.Fields.FlatFields;
import com.example.demo.Fields.HouseFields;
import javafx.scene.control.Alert;

public class ObjectInputErrorLocalizer {

    private static String invalidFieldMSG;

    //ObjectInputMessages
    private static String wrongNameMSG;
    private static String wrongViewMSG;
    private static String wrongAreaMSG;
    private static String wrongTransportMSG;
    private static String wrongNumberOfRoomsMSG;
    private static String wrongFurnishMSG;
    private static String wrongCoordinatesMSG;
    private static String wrongCoordinatesXMSG;
    private static String wrongCoordinatesYMSG;
    private static String wrongHouseMSG;
    private static String wrongHouseNameMSG;
    private static String wrongHouseYearMSG;
    private static String wrongHouseNumberOfFloorsMSG;



    static {
        localize();
    }

    public static void localize() {
        MainLocalizer.localize(ObjectInputErrorLocalizer.class);
    }

    public static String getLocalizerErrorMSG(Field field){
        if (field.getClass().equals(FlatFields.class)){
            switch ((FlatFields) field){
                case NAME -> {
                    return wrongNameMSG;
                }
                case AREA -> {
                    return wrongAreaMSG;
                }
                case TRANSPORT -> {
                    return wrongTransportMSG;
                }
                case VIEW -> {
                    return wrongViewMSG;
                }
                case NUMBER_OF_ROOMS ->
                {
                    return wrongNumberOfRoomsMSG;
                }
                case FURNISH -> {
                    return wrongFurnishMSG;
                }
                case COORDINATES -> {
                    return wrongCoordinatesMSG;
                }
                case HOUSE ->
                {
                    return wrongHouseMSG;
                }
                default ->throw new IllegalArgumentException("No MSG for field "+((FlatFields) field).name());
            }
        } else if (field.getClass().equals(CoordinatesFields.class)) {
            switch ((CoordinatesFields) field){
                case COODINATE_X -> {
                    return wrongCoordinatesXMSG;
                }
                case COODINATE_Y -> {
                    return wrongCoordinatesYMSG;
                }
                default ->throw new IllegalArgumentException("No MSG for field "+((CoordinatesFields) field).name());
            }
        } else if (field.getClass().equals(HouseFields.class)) {
            switch ((HouseFields) field){
                case HOUSE_NAME -> {
                    return wrongHouseNameMSG;
                }
                case HOUSE_YEAR ->
                {
                    return wrongHouseYearMSG;
                }
                case HOUSE_NUMBER_OF_FLOORS ->
                {
                    return wrongHouseNumberOfFloorsMSG;
                }
                default -> throw new IllegalArgumentException("No MSG for field "+((HouseFields) field).name());
            }
        }else {
            throw new IllegalArgumentException("No field class for this field");
        }
    }

    public static void showAlertForInvalidField(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(invalidFieldMSG);
        alert.showAndWait();
    }



}

package com.example.demo.GUI.Update;

import com.example.demo.CommonClasses.Entities.Coordinates;
import com.example.demo.CommonClasses.Entities.House;
import com.example.demo.Fields.CoordinatesFields;
import com.example.demo.Fields.FlatFields;
import com.example.demo.Fields.HouseFields;
import com.example.demo.GUI.BaseController;
import com.example.demo.GUI.Commands.ComandsWindowController;
import com.example.demo.Managers.CommandContext;
import com.example.demo.Proxies.FlatProxy;
import com.example.demo.Proxies.FurnishProxy;
import com.example.demo.Proxies.Localizers.ObjectInputErrorLocalizer;
import com.example.demo.Proxies.TransportProxy;
import com.example.demo.Proxies.ViewProxy;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.synedra.validatorfx.Validator;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class UpdateWindowController extends BaseController implements Initializable {

    @FXML
    private Label header;

    @FXML
    private TextField nameTextField;

    @FXML
    private ChoiceBox<String> view;

    @FXML
    private TextField areaTextField;

    @FXML
    private ChoiceBox<String> transport;

    @FXML
    private TextField numberOfRoomsTextField;

    @FXML
    private ChoiceBox<String> furnish;

    @FXML
    private Label coordinatesLabel;

    @FXML
    private TextField xTextField;

    @FXML
    private TextField yTextField;

    @FXML
    private Label houseLabel;

    @FXML
    private TextField houseNameTextField;

    @FXML
    private TextField houseYearTextField;

    @FXML
    private TextField houseNumberOfFloorsTextField;

    @FXML
    private Button saveButton;

    private Validator validator = new Validator();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localize(resourceBundle);
        header.setText(header.getText()+(Long) CommandContext.instance().getArgument());



        //Initialize choiceBoxes
        view.getItems().clear();
        view.getItems().addAll(Arrays.stream(ViewProxy.values()).map(ViewProxy::toString).toList());

        furnish.getItems().clear();
        furnish.getItems().addAll(Arrays.stream(FurnishProxy.values()).map(FurnishProxy::toString).toList());

        transport.getItems().clear();
        transport.getItems().addAll(Arrays.stream(TransportProxy.values()).map(TransportProxy::toString).toList());

        //Initialize validators

        //Name validation
        validator.createCheck().dependsOn("text",nameTextField.textProperty())
                .withMethod((context -> {
                    if (validateName(context.get("text"))) {
                        context.error(
                                ObjectInputErrorLocalizer.getLocalizerErrorMSG(FlatFields.NAME));
                    }
                })).decorates(nameTextField).immediate();

        //Area validation
        validator.createCheck().dependsOn("text",areaTextField.textProperty())
                .withMethod((context -> {
                    if (validateArea(context.get("text"))) {
                        context.error(
                                ObjectInputErrorLocalizer.getLocalizerErrorMSG(FlatFields.AREA)
                        );
                    }
                })).decorates(areaTextField).immediate();

        //Number of rooms
        validator.createCheck().dependsOn("text",numberOfRoomsTextField.textProperty())
                .withMethod((context -> {
                    if (validateNumberOfRooms(context.get("text"))) {
                        context.error(
                                ObjectInputErrorLocalizer.getLocalizerErrorMSG(FlatFields.NUMBER_OF_ROOMS)
                        );
                    }
                })).decorates(numberOfRoomsTextField).immediate();

        //X
        validator.createCheck().dependsOn("text",xTextField.textProperty())
                .withMethod((context -> {
                    if (validateCoordinatesX(context.get("text"))) {
                        context.error(
                                ObjectInputErrorLocalizer.getLocalizerErrorMSG(CoordinatesFields.COODINATE_X)
                        );
                    }
                })).decorates(xTextField).immediate();

        //Y
        validator.createCheck().dependsOn("text",yTextField.textProperty())
                .withMethod((context -> {
                    if (validateCoordinatesY(context.get("text"))) {
                        context.error(
                                ObjectInputErrorLocalizer.getLocalizerErrorMSG(CoordinatesFields.COODINATE_Y)
                        );
                    }
                })).decorates(yTextField).immediate();

        //House Name
        validator.createCheck().dependsOn("text",houseNameTextField.textProperty())
                .withMethod((context -> {
                    if (validateHouseName(context.get("text"))) {
                        context.error(
                                ObjectInputErrorLocalizer.getLocalizerErrorMSG(HouseFields.HOUSE_NAME)
                        );
                    }
                })).decorates(houseNameTextField).immediate();
        //House Year
        validator.createCheck().dependsOn("text",houseYearTextField.textProperty())
                .withMethod((context -> {
                    if (validateHouseDigitals(context.get("text"))) {
                        context.error(
                                ObjectInputErrorLocalizer.getLocalizerErrorMSG(HouseFields.HOUSE_YEAR)
                        );
                    }
                })).decorates(houseYearTextField).immediate();

        //House number of floors
        validator.createCheck().dependsOn("text",houseNumberOfFloorsTextField.textProperty())
                .withMethod((context -> {
                    if (validateHouseDigitals(context.get("text"))) {
                        context.error(
                                ObjectInputErrorLocalizer.getLocalizerErrorMSG(HouseFields.HOUSE_NUMBER_OF_FLOORS)
                        );
                    }
                })).decorates(houseNumberOfFloorsTextField).immediate();

    }


    public void onSaveButtonClick(){
        if (validator.containsErrors()){
            ObjectInputErrorLocalizer.showAlertForInvalidField();
        }
        try {
            FlatProxy proxy = new FlatProxy();
            proxy.setName(nameTextField.getText());
            proxy.setArea(Integer.parseInt(areaTextField.getText()));

            proxy.setFurnishProxy(FurnishProxy.valueOfLocalString(furnish.getValue()));
            proxy.setViewProxy(ViewProxy.valueOfLocalString(view.getValue()));
            proxy.setTransportProxy(TransportProxy.valueOfLocalString(transport.getValue()));
            proxy.setNumberOfRooms(Long.parseLong(numberOfRoomsTextField.getText()));

            Coordinates c = new Coordinates();
            c.setX(Integer.parseInt(xTextField.getText()));
            c.setY(Float.parseFloat(yTextField.getText()));
            proxy.setCoordinates(c);

            House h = new House();
            h.setName(nameTextField.getText());
            h.setYear(Integer.parseInt(yTextField.getText()));
            h.setNumberOfFloors(Integer.parseInt(numberOfRoomsTextField.getText()));
            proxy.setHouse(h);

            CommandContext.instance().setInputObject(proxy.getOrigin());
            ComandsWindowController.basicCommandExecution("update");
        } catch (IllegalArgumentException e) {
            ObjectInputErrorLocalizer.showAlertForInvalidField();
        }


    }



    //VALIDATORS
    //FOR ALL VALIDATORS TRUE IS INCORRECT
    private static boolean validateName(String text){
        if (text==null) return true;
        return text.isBlank()||text.isEmpty();
    }

    private static boolean validateArea(String text){
        if (text==null) return true;
        try {
            Integer value = Integer.parseInt(text.trim());
            return ((value==null)||(value<0)||(value>745));
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private static boolean validateNumberOfRooms(String text){
        if (text==null) return true;
        try {
            Long value = Long.parseLong(text.trim());
            return value<0;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private static boolean validateCoordinatesX(String text){
        if (text==null) return true;
        try {
            Integer value = Integer.parseInt(text.trim());

            return value>606;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private static boolean validateCoordinatesY(String text){
        if (text==null) return true;
        try {
            Float value = Float.parseFloat(text.trim());
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private static boolean validateHouseName(String text){
        if (text==null) return true;
        return text.isBlank()||text.isEmpty();
    }

    private static boolean validateHouseDigitals(String text){
        if (text==null) return true;
        try {
            Integer value = Integer.parseInt(text.trim());
            return value<0;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private static boolean validateEnums(String text){
        if (text==null) return true;
        return text.isBlank()||text.isEmpty();
    }
}

package com.example.demo.GUI.Commands;

import com.example.demo.CommonClasses.Exceptions.FunctionFailedException;
import com.example.demo.CommonClasses.Interaction.Responces.ServerResponse;
import com.example.demo.GUI.BaseController;
import com.example.demo.GUI.ObjectInput.ObjectInputWindow;
import com.example.demo.Managers.CommandContext;
import com.example.demo.Managers.SessionContext;
import com.example.demo.Managers.UDPManager;
import com.example.demo.Managers.UTF8Control;
import com.example.demo.Proxies.FurnishProxy;
import com.example.demo.Proxies.ViewProxy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ComandsWindowController extends BaseController implements Initializable {

    @FXML
    private Label header;

    @FXML
    private Button historyButton;

    @FXML
    private Button removeByIdButton;

    @FXML
    private TextField idTextField;

    @FXML
    private Button helpButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button removeAllByViewButton;

    @FXML
    private Button removeFirstButton;

    @FXML
    private Button removeLowerButton;

    @FXML
    private Button objectInsertButton;

    @FXML
    private Button groupByCreationDateButton;

    @FXML
    private Button counterGreaterButton;


    @FXML
    private ChoiceBox<FurnishProxy> furnishChoiceBox;

    @FXML
    private ChoiceBox<ViewProxy> viewChoiceBox;


    private static String correctAllertHeading;
    private static String incorrectAllertHeading;
    private static String correctExecution;
    private static String incorrectExecution;
    private static String incorrectGetting;
    private static String invalidArgument;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localize(resourceBundle);

        //localize viewChoiceBox
        viewChoiceBox.getItems().clear();
        viewChoiceBox.getItems().addAll(ViewProxy.values());

        //localize viewChoiceBox
        furnishChoiceBox.getItems().clear();
        furnishChoiceBox.getItems().addAll(FurnishProxy.values());

        correctAllertHeading = resourceBundle.getString("correctAllertHeading");
        incorrectAllertHeading = resourceBundle.getString("incorrectAllertHeading");
        correctExecution = resourceBundle.getString("correctExecution");
        incorrectExecution = resourceBundle.getString("incorrectExecution");
        incorrectGetting = resourceBundle.getString("incorrectGetting");
        invalidArgument = resourceBundle.getString("invalidArgument");


    }


    public static void basicCommandExecution(String comandName){
        CommandContext.instance().setCommandName(comandName);
        UDPManager.sendCommandRequest();

        StringBuilder builder = new StringBuilder();

        Alert alert;
        try {
            ServerResponse response = UDPManager.getServerResponce();
            if (response.status()){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(correctExecution);
                response.output().forEach((o)->builder.append(o).append("\n"));
            }else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(incorrectExecution);
                builder.append(response.exception().getClass()).append("\n");
                builder.append(response.exception().getMessage()).append("\n");
            }
        } catch (FunctionFailedException e) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(incorrectExecution);
            builder.append("Unable to read object").append("\n");
        }

        alert.setContentText(builder.toString());
        alert.showAndWait();
    }
    private void basicInvalidArgumentAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(invalidArgument);
        alert.showAndWait();
    }

    public void historyButtonAction(ActionEvent actionEvent) {
        basicCommandExecution("history");
    }

    public void removeByIdButtonButtonAction(ActionEvent actionEvent) {
        try {
            Long id = Long.parseLong(idTextField.getText());
            if (id<0) throw new IllegalArgumentException();
            CommandContext.instance().setArgument(id);
            basicCommandExecution("remove_by_id");
        } catch (IllegalArgumentException e) {
            basicInvalidArgumentAlert();
        }
    }

    public void helpButtonAction(ActionEvent actionEvent) {
        var resourceBundle = ResourceBundle.getBundle("com.example.demo.GUI.Commands.Help", SessionContext.getLanguage());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(correctExecution);
        StringBuilder builder = new StringBuilder();
        resourceBundle.keySet().stream().forEach((key)->builder.append(key).append(" : ").append(resourceBundle.getString(key)).append("\n"));
        alert.setContentText(builder.toString());
        alert.showAndWait();
    }

    public void infoButtonAction(ActionEvent actionEvent) {
        basicCommandExecution("info");
    }

    public void removeAllByViewButtonAction(ActionEvent actionEvent) {
        try {
            CommandContext.instance().setArgument(viewChoiceBox.getValue().getOrigin());
            basicCommandExecution("remove_all_by_view");
        }catch (IllegalArgumentException|NullPointerException e){
            basicInvalidArgumentAlert();
        }
    }

    public void removeFirstButtonAction(ActionEvent actionEvent) {
        System.out.println(CommandContext.instance().getFirstRow());
        CommandContext.instance().setArgument(CommandContext.instance().getFirstRow());
        basicCommandExecution("remove_by_id");
    }

    public void removeLowerButtonAction(ActionEvent actionEvent) {
        if (!CommandContext.instance().isObjectSet()) basicInvalidArgumentAlert();
        basicCommandExecution("remove_lower");
    }

    public void objectInsertButtonAction(ActionEvent actionEvent) {
        Stage nextStage = new Stage();
        try {
            var nextWindow = new ObjectInputWindow(nextStage);
            nextWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void groupByCreationDateButtonAction(ActionEvent actionEvent) {
        basicCommandExecution("group_counting_by_creation_date");
    }

    public void counterGreaterButtonAction(ActionEvent actionEvent) {
        try {
            FurnishProxy proxy = furnishChoiceBox.getValue();
            CommandContext.instance().setArgument(furnishChoiceBox.getValue().getOrigin());
            basicCommandExecution("count_greater_than_furnish");
        } catch (IllegalArgumentException|NullPointerException e) {
            throw new RuntimeException(e);
        }
    }



    //TODO add buttons handling
    //TODO for command help localize it here
    //TODO testCommands

}

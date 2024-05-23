package com.example.demo.GUI;

import com.example.demo.Managers.SessionContext;
import com.example.demo.Managers.UTF8Control;
import javafx.scene.control.*;

import java.util.Arrays;
import java.util.ResourceBundle;

public abstract class BaseController {


    /**
     * Localizes ONLY Button, Label, TextField
     * @param bundle localization bundle
     */
    protected final void localize(ResourceBundle bundle){
        //TODO add ChoiceBox localization
        Arrays.stream(this.getClass().getDeclaredFields()).forEach((field -> {
            try {
                field.setAccessible(true);
                switch (field.getType().getSimpleName()){
                    case "Button":{
                        localizeItem((Button) field.get(this),field.getName(),bundle);
                        break;
                    }
                    case "Label":{
                        localizeItem((Label) field.get(this),field.getName(),bundle);
                        break;
                    }
                    case "TextField":{
                        localizeItem((TextField) field.get(this),field.getName(),bundle);
                        break;
                    }
                }

                field.setAccessible(false);
            } catch (Exception e) {
                StringBuilder builder = new StringBuilder();
                builder.append("Error in localize method. ");
                builder.append("Error in field: ").append(field.getType()+" ").append(field.getName()).append(" of Class").append(this.getClass().getName()).append("\n");
                builder.append("Error message: ").append(e.getMessage());
                throw new RuntimeException(builder.toString());
            }
        }));
    }

    private void localizeItem(Button button,String buttonName,ResourceBundle bundle){
        button.setText(bundle.getString(buttonName));
    }

    private void localizeItem(Label label,String labelName,ResourceBundle bundle){
        label.setText(bundle.getString(labelName));
    }

    private void localizeItem(TextField textField, String textFieldName, ResourceBundle bundle){
        textField.setPromptText(bundle.getString(textFieldName));
    }

    protected void basicFailedConnection(){
        var res = ResourceBundle.getBundle("com.example.demo.GUI.BadConnection", SessionContext.getLanguage());

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(res.getString("alertHeader"));
        alert.setContentText(res.getString("alertContent"));
        alert.showAndWait();
    }




}

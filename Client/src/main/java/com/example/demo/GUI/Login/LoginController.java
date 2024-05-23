package com.example.demo.GUI.Login;

import com.example.demo.CommonClasses.Exceptions.FunctionFailedException;
import com.example.demo.GUI.BaseController;
import com.example.demo.Managers.SessionContext;
import com.example.demo.GUI.CollectionWindow.CollectionWindow;
import com.example.demo.Managers.UDPManager;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

import java.io.IOException;
import java.net.URL;

import javafx.util.Duration;
import java.util.ResourceBundle;

public class LoginController extends BaseController implements Initializable {

    private enum Mode{
        REGISTERING,
        LOGGING;
        public Mode changeMode(){
            if (this.equals(REGISTERING)) return LOGGING;
            return REGISTERING;
        }
    }

    private Mode currentMode = Mode.LOGGING;

    @FXML
    private Button confirmButton;

    @FXML
    private Button changeButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label noAccountLabel;

    @FXML
    private AnchorPane fillPane;

    @FXML
    private AnchorPane switchPane;

    @FXML
    private Label fillPageHeader;

    private String registerConfirmButtonText;
    private String loginConfirmButtonText;


    private String registerChangeButtonText;
    private String loginChangeButtonText;

    private String registerNoAccountLabelText;
    private String loginNoAccountLabelText;

    private String loginHeaderText;
    private String registerHeaderText;

    private String wrongUsernameMsg;
    private String wrongPasswordMsg;
    private String usedLoginMSG;

    private String invalidFieldsMsg;

    private Validator validator = new Validator();

    private TranslateTransition fillPaneToRight;
    private TranslateTransition fillPaneToLeft;

    private TranslateTransition switchPaneToRight;
    private TranslateTransition switchPaneToLeft;

    private Duration animationDuration = Duration.seconds(2);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        registerChangeButtonText=resourceBundle.getString("register.confirmButton");
        registerConfirmButtonText=resourceBundle.getString("register.changeButton");
        registerNoAccountLabelText=resourceBundle.getString("register.noAccountLabel");
        registerHeaderText=resourceBundle.getString("register.fillPageHeader");

        usedLoginMSG = resourceBundle.getString("usedLoginMSG");


        loginChangeButtonText=resourceBundle.getString("login.confirmButton");
        loginNoAccountLabelText=resourceBundle.getString("login.noAccountLabel");
        loginConfirmButtonText=resourceBundle.getString("login.changeButton");
        loginHeaderText=resourceBundle.getString("login.fillPageHeader");


        fillPaneToRight = new TranslateTransition(animationDuration, fillPane);
        fillPaneToRight.setByX(300);
        fillPaneToRight.setOnFinished((actionEvent -> fillPane.setVisible(true)));

        fillPaneToLeft = new TranslateTransition(animationDuration, fillPane);
        fillPaneToLeft.setByX(-300);
        fillPaneToLeft.setOnFinished((actionEvent -> fillPane.setVisible(true)));

        switchPaneToRight = new TranslateTransition(animationDuration, switchPane);
        switchPaneToRight.setByX(300);

        switchPaneToLeft = new TranslateTransition(animationDuration, switchPane);
        switchPaneToLeft.setByX(-300);


        localize(resourceBundle);
        passwordField.setPromptText(resourceBundle.getString("passwordField"));
        wrongUsernameMsg = resourceBundle.getString("wrongUsernameMsg");
        wrongPasswordMsg = resourceBundle.getString("wrongPasswordMsg");
        invalidFieldsMsg = resourceBundle.getString("invalidFieldsMsg");

        validator.createCheck().dependsOn("text",usernameField.textProperty())
                .withMethod((context -> {
                    String value = context.get("text");
                    if ((value==null)||(value.isEmpty())||(value.isBlank())||
                            value.chars().anyMatch(Character::isWhitespace))
                        context.error(wrongUsernameMsg);
                })).decorates(usernameField).immediate();

        validator.createCheck().dependsOn("text",passwordField.textProperty())
                .withMethod((context -> {
                    String value = context.get("text");
                    if ((value==null)||(value.isEmpty())||(value.isBlank())||
                            value.chars().anyMatch(Character::isWhitespace))
                        context.error(wrongPasswordMsg);
                })).decorates(passwordField).immediate();
    }

    @FXML
    protected void enableAnimation() {
        fillPane.setVisible(false);
        switch (currentMode){
            case REGISTERING->{
                //REGISTER TO LOGGING
                confirmButton.setText(loginConfirmButtonText);
                changeButton.setText(loginConfirmButtonText);
                noAccountLabel.setText(loginNoAccountLabelText);
                fillPageHeader.setText(loginHeaderText);

                fillPaneToLeft.play();
                switchPaneToRight.play();
                break;
            }
            case LOGGING->{
                //LOGGING TO REGISTER

                confirmButton.setText(registerConfirmButtonText);
                changeButton.setText(registerConfirmButtonText);
                noAccountLabel.setText(registerNoAccountLabelText);
                fillPageHeader.setText(registerHeaderText);

                fillPaneToRight.play();
                switchPaneToLeft.play();
            }
        }
        currentMode = currentMode.changeMode();
    }

    @FXML
    protected void login()  {
        String login = usernameField.getText();
        String password = passwordField.getText();

        SessionContext.setCredentials(login,password);
        SessionContext.toAutentificationRequest();

        if (validator.containsErrors()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(invalidFieldsMsg);
            alert.showAndWait();
            return;
        }

        boolean result = false;

        try {
            switch (currentMode){
                case REGISTERING->{
                    result = UDPManager.getRegistrationResponce();
                    if (!result){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText(usedLoginMSG);
                        alert.showAndWait();
                        return;
                    }
                }
                case LOGGING->{
                    result = UDPManager.getAutentificationResponce();
                    if (!result){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText(invalidFieldsMsg);
                        alert.showAndWait();
                        return;
                    }
                }
            }
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();

            try {
                CollectionWindow nextWindow = new CollectionWindow(stage);
                nextWindow.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FunctionFailedException|IOException e) {
            basicFailedConnection();
        }


    }
}
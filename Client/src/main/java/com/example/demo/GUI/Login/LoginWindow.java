package com.example.demo.GUI.Login;

import com.example.demo.GUI.BaseWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginWindow extends BaseWindow {


    public LoginWindow(Stage stage) throws IOException {
        super(stage);

        URL fxml = getClass().getResource("loginWindow.fxml");

        var localization = this.loadResourceBundle();
        FXMLLoader fxmlLoader = new FXMLLoader(fxml,localization);
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setScene(scene);
//        stage.getScene().getStylesheets().add(this.getClass().getResource("/ccs/LoginWindow.css").toExternalForm());
    }


}
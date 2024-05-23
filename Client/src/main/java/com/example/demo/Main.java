package com.example.demo;

import com.example.demo.GUI.Login.LoginWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        //TODO some pre-register steps maybe123
        //TODO write help resource bundle
        LoginWindow loginWindow = new LoginWindow(stage);
        loginWindow.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

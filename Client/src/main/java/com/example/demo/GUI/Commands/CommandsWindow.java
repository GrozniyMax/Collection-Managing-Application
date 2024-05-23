package com.example.demo.GUI.Commands;

import com.example.demo.GUI.BaseWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CommandsWindow extends BaseWindow {
    public CommandsWindow(Stage stage) throws IOException {
        super(stage);
        URL fxml = getClass().getResource("commandsWindow.fxml");

        var localization = this.loadResourceBundle();
        FXMLLoader fxmlLoader = new FXMLLoader(fxml,localization);
        Scene scene = new Scene(fxmlLoader.load(), 600, 800);
        stage.setScene(scene);
    }
}

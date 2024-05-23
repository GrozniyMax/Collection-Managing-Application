package com.example.demo.GUI.ObjectInput;

import com.example.demo.GUI.BaseWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ObjectInputWindow extends BaseWindow {

    public ObjectInputWindow(Stage stage) throws IOException {
        super(stage);
        URL fxml = getClass().getResource("objectInputWindow.fxml");

        var localization = loadResourceBundle();

        FXMLLoader fxmlLoader = new FXMLLoader(fxml, localization);
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setScene(scene);
    }
}

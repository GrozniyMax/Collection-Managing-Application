package com.example.demo.GUI.Update;

import com.example.demo.GUI.BaseWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class UpdateWindow extends BaseWindow {
    public UpdateWindow(Stage stage)  throws IOException {
        super(stage);
        URL fxml = getClass().getResource("updateWindow.fxml");

        var localization = loadResourceBundle();

        FXMLLoader fxmlLoader = new FXMLLoader(fxml, localization);
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setScene(scene);
    }
}

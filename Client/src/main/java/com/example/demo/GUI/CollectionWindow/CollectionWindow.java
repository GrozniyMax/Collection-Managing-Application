package com.example.demo.GUI.CollectionWindow;

import com.example.demo.GUI.BaseWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CollectionWindow extends BaseWindow {

    public CollectionWindow(Stage stage) throws IOException {
        super(stage);
        URL fxml = getClass().getResource("collectionWindow.fxml");

        var r = loadResourceBundle();
        FXMLLoader fxmlLoader = new FXMLLoader(fxml,r);
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setScene(scene);
    }
}

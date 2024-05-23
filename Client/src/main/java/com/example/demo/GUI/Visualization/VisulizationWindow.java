package com.example.demo.GUI.Visualization;

import com.example.demo.GUI.BaseWindow;
import com.example.demo.Proxies.FlatTableAdapter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

public class VisulizationWindow extends BaseWindow {
    public VisulizationWindow(Stage stage, LinkedList<FlatTableAdapter> masterData)  throws IOException {
        super(stage);

        URL fxml = getClass().getResource("visualizationWindow.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxml);
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setScene(scene);


    }
}

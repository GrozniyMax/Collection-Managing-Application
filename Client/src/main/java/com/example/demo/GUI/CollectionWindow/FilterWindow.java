package com.example.demo.GUI.CollectionWindow;

import com.example.demo.GUI.BaseWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class FilterWindow extends BaseWindow {

    public FilterWindow(Stage stage,CollecntionWindowController olderController) throws IOException {
        super(stage);
        URL fxml = getClass().getResource("filterWindow.fxml");

        var r = loadResourceBundle();
        FXMLLoader fxmlLoader = new FXMLLoader(fxml,r);
        fxmlLoader.setController(olderController.new FilterWindowController());
        Scene scene = new Scene(fxmlLoader.load(), 400, 230);
        stage.setScene(scene);
    }
}

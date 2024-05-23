package com.example.demo.GUI.ScriptWindow;

import com.example.demo.GUI.BaseController;
import com.example.demo.GUI.BaseWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ScriptWindow extends BaseWindow {

    public ScriptWindow(Stage stage) throws IOException {
        super(stage);
        URL fxml = getClass().getResource("scriptWindow.fxml");
        var localization = this.loadResourceBundle();
        FXMLLoader fxmlLoader = new FXMLLoader(fxml,localization);
        Scene scene = new Scene(fxmlLoader.load(), 400, 200);
        stage.setScene(scene);
    }
}

package com.example.demo.GUI.ScriptWindow;

import com.example.demo.GUI.BaseController;
import com.example.demo.ScriptExecution.ScriptExecutor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ScriptWindowController extends BaseController implements Initializable {


    @FXML
    private Button execute;

    @FXML
    private Button fileChoose;

    @FXML
    private Label prefix;

    @FXML
    private Label filepath;

    @FXML
    private File chosenFile;

    private String failed;

    private String success;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localize(resourceBundle);
        failed=resourceBundle.getString("failed");
        success=resourceBundle.getString("success");

    }

    public void onExecuteClick(){
        ScriptExecutor scriptExecutor = new ScriptExecutor();
        if (scriptExecutor.execute(filepath.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(success);
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(failed);
            alert.showAndWait();
        };
    }

    public void onFileChooseClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        chosenFile = fileChooser.showOpenDialog(execute.getScene().getWindow());
        String[] path = chosenFile.getAbsolutePath().split("/");
        filepath.setText(path[path.length-1]);
    }
}

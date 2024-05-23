package com.example.demo.GUI;

import com.example.demo.Managers.SessionContext;
import com.example.demo.Managers.UTF8Control;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.ResourceBundle;

public abstract class BaseWindow {
    protected Stage stage;

    public BaseWindow(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        stage.setResizable(false);
        stage.show();
    }

    protected final ResourceBundle loadResourceBundle() {
//        System.out.println(this.getClass().getCanonicalName());

        return ResourceBundle.getBundle(this.getClass().getCanonicalName(), SessionContext.getLanguage());
    }

}

module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.datatransfer;
    requires java.sql;


    //Exports
    opens com.example.demo.GUI.ScriptWindow to javafx.fxml;
    exports com.example.demo.GUI.ScriptWindow;

    opens com.example.demo.GUI.Visualization to javafx.fxml;
    exports com.example.demo.GUI.Visualization;

    opens com.example.demo.GUI.Update to javafx.fxml;
    exports com.example.demo.GUI.Update;

    opens com.example.demo.Proxies to javafx.fxml;
    exports com.example.demo.Proxies;

    opens com.example.demo.GUI.ObjectInput to javafx.fxml;
    exports com.example.demo.GUI.ObjectInput;

    opens com.example.demo.GUI.Commands to javafx.fxml;
    exports com.example.demo.GUI.Commands;

    opens com.example.demo.GUI.Login to javafx.fxml;
    exports com.example.demo.GUI.Login;

    opens com.example.demo.GUI.CollectionWindow to javafx.fxml;
    exports com.example.demo.GUI.CollectionWindow;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;

    exports com.example.demo.CommonClasses.Interaction.Requests;
    opens com.example.demo.CommonClasses.Interaction.Requests to javafx.fxml;

    exports com.example.demo.Managers;
    opens com.example.demo.Managers to javafx.fxml;

}
package com.example.demo.GUI.CollectionWindow;

import com.example.demo.CommonClasses.Entities.Coordinates;
import com.example.demo.CommonClasses.Entities.House;
import com.example.demo.CommonClasses.Exceptions.FunctionFailedException;
import com.example.demo.Fields.CoordinatesFields;
import com.example.demo.Fields.Field;
import com.example.demo.Fields.FlatFields;
import com.example.demo.Fields.HouseFields;
import com.example.demo.GUI.BaseController;
import com.example.demo.GUI.Commands.CommandsWindow;
import com.example.demo.GUI.ObjectInput.ObjectInputWindow;
import com.example.demo.GUI.ScriptWindow.ScriptWindow;
import com.example.demo.GUI.Update.UpdateWindow;
import com.example.demo.GUI.Visualization.VisulizationWindow;
import com.example.demo.Managers.CommandContext;
import com.example.demo.Managers.SessionContext;
import com.example.demo.Managers.UDPManager;
import com.example.demo.Managers.UTF8Control;
import com.example.demo.Proxies.*;
import com.example.demo.Proxies.Localizers.MainLocalizer;
import com.example.demo.Proxies.Localizers.ObjectInputErrorLocalizer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Predicate;

public class CollecntionWindowController extends BaseController implements Initializable {


    @FXML
    private Label label;

    @FXML
    private Button addButton;

    @FXML
    private Button cleanButton;

    @FXML
    private Button comandsButton;

    @FXML
    private Button visualizationButton;

    @FXML
    private Button filterButton;

    @FXML
    private ChoiceBox<Field> sortChoiceBox;

    @FXML
    private TableView<FlatTableAdapter> table;

    //Columns

    @FXML
    private TableColumn<FlatTableAdapter, Long> IDColumn;

    @FXML
    private TableColumn<FlatTableAdapter, String> nameColumn;

    @FXML
    private TableColumn<FlatTableAdapter, ZonedDateTime> creationDateColumn;

    @FXML
    private TableColumn<FlatTableAdapter, Coordinates> coordinatesColumn;

    @FXML
    private TableColumn<FlatTableAdapter, Integer> areaColumn;

    @FXML
    private TableColumn<FlatTableAdapter, Long> numberOfRoomsColumng;

    @FXML
    private TableColumn<FlatTableAdapter, String> furnishColumn;

    @FXML
    private TableColumn<FlatTableAdapter, String> viewColumn;

    @FXML
    private TableColumn<FlatTableAdapter, String> transportColumn;

    @FXML
    private TableColumn<FlatTableAdapter, House> houseColumn;

    @FXML
    private ChoiceBox<Locale> languageChoiceBox;

    private LinkedList<FlatTableAdapter> masterData;

    private ObservableList<FlatTableAdapter> preparedDate;

    private Predicate<FlatProxy> filterPredicate = (flatProxy -> false);

    private Comparator<FlatProxy> sortFunction = new Comparator<>() {
        @Override
        public int compare(FlatProxy o1, FlatProxy o2) {
            return 0;
        }
    };

    private String permissionDeniedMSG;
    private static String failFunctionMSG;


    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadLocalisation(resourceBundle);
        refresh();

        timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
            // Update the list
            refresh();
            prepareData();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        languageChoiceBox.getItems().addAll(
                Locale.US,
                new Locale("el", "GR"),
                new Locale("es", "PR"),
                new Locale("sk", "SK"),
                new Locale("ru", "RU")
        );
        languageChoiceBox.setValue(Locale.US);
        sortChoiceBox.setValue(FlatFields.NONE);
    }

    private void loadLocalisation(ResourceBundle resourceBundle) {

        localize(resourceBundle);
        table.setItems(preparedDate);

        permissionDeniedMSG = resourceBundle.getString("permissionDeniedMsg");
        failFunctionMSG = resourceBundle.getString("failFunctionMsg");

        //State sortChoiceBox values
        sortChoiceBox.getItems().clear();
        sortChoiceBox.getItems().addAll(Field.getAllFields());

        //Set Name and propertyFactory
        IDColumn.setText(FlatFields.ID.toLocalString());
        IDColumn.setCellValueFactory(new PropertyValueFactory<FlatTableAdapter, Long>("id"));

        nameColumn.setText(FlatFields.NAME.toLocalString());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        creationDateColumn.setText(FlatFields.CREATION_DATE.toLocalString());
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

        coordinatesColumn.setText(FlatFields.COORDINATES.toLocalString());
        coordinatesColumn.setCellValueFactory(new PropertyValueFactory<>("coordinates"));

        areaColumn.setText(FlatFields.AREA.toLocalString());
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));

        numberOfRoomsColumng.setText(FlatFields.NUMBER_OF_ROOMS.toLocalString());
        numberOfRoomsColumng.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));

        furnishColumn.setText(FlatFields.FURNISH.toLocalString());
        furnishColumn.setCellValueFactory(new PropertyValueFactory<>("furnish"));


        viewColumn.setText(FlatFields.VIEW.toLocalString());
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("view"));


        transportColumn.setText(FlatFields.TRANSPORT.toLocalString());
        transportColumn.setCellValueFactory(new PropertyValueFactory<>("transport"));

        houseColumn.setText(FlatFields.HOUSE.toLocalString());
        houseColumn.setCellValueFactory(new PropertyValueFactory<>("house"));


        //TODO execute script
        //TODO большой скрипт
        //TODO команды работали
        //TODO naming

    }

    public void onCommandClick(ActionEvent actionEvent) {
        Stage nextStage = new Stage();
        nextStage.initOwner(addButton.getScene().getWindow());
        nextStage.initModality(Modality.WINDOW_MODAL);
        try {
            CommandContext.instance().setFirstRow(table.getItems().get(0).getId());
            var nextWindow = new CommandsWindow(nextStage);
            nextWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void onNewObjectClick(ActionEvent actionEvent) {
        Stage nextStage = new Stage();
        nextStage.initOwner(addButton.getScene().getWindow());
        nextStage.initModality(Modality.WINDOW_MODAL);
        try {
            var nextWindow = new ObjectInputWindow(nextStage);
            nextWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onScriptExecuteButtonClick(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.initOwner(addButton.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        try {
            var nextWindow = new ScriptWindow(stage);
            nextWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sort(ActionEvent actionEvent) {
        Field field = sortChoiceBox.getValue();
        field = Objects.requireNonNullElse(field, FlatFields.NONE);
        Field finalField = field;
        sortFunction = new Comparator<FlatProxy>() {
            @Override
            public int compare(FlatProxy o1, FlatProxy o2) {
                return finalField.getFieldValueFunction().apply(o1).compareTo(
                        finalField.getFieldValueFunction().apply(o2));
            }
        };
        refresh();
        prepareData();
    }

    public void onFilterClick(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.initOwner(addButton.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        try {
            FilterWindow nextWindow = new FilterWindow(stage, this);
            nextWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void refresh() {
        List<FlatProxy> responce = UDPManager.getRefreshResponce();
        masterData = responce.stream().map(FlatTableAdapter::valueOf)
                .collect(LinkedList<FlatTableAdapter>::new, LinkedList<FlatTableAdapter>::add,
                        LinkedList<FlatTableAdapter>::addAll);
        preparedDate = FXCollections.observableArrayList(masterData);
    }

    private void prepareData() {
        preparedDate = FXCollections.observableArrayList(masterData.stream().map(FlatTableAdapter::toFlatProxy)
                .filter(Predicate.not(filterPredicate)).sorted(sortFunction).map(FlatTableAdapter::valueOf).toList());
        table.setItems(preparedDate);
    }

    public void onMouseClick(MouseEvent event) {
        Long id = table.getSelectionModel().getSelectedItem().getId();
        try {
            if (UDPManager.getCommandSenderInstance().getCheckIDResponce(id)) {
                CommandContext.instance().setArgument(id);
                Stage nextStage = new Stage();
                nextStage.initOwner(addButton.getScene().getWindow());
                nextStage.initModality(Modality.WINDOW_MODAL);
                try {
                    var nextWindow = new UpdateWindow(nextStage);
                    nextWindow.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(permissionDeniedMSG);
                alert.showAndWait();
            }
        } catch (FunctionFailedException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(permissionDeniedMSG);
            alert.showAndWait();
        }

    }

    public static void basePermissionDenied() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(failFunctionMSG);
        alert.showAndWait();
    }


    public void onVisualizationButtonClick() {
        Stage nextStage = new Stage();
        try {
            var nextWindow = new VisulizationWindow(nextStage, masterData);
            nextWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onLocalizeSet() {
        Locale locale = languageChoiceBox.getValue();
        SessionContext.setLanguage(locale);
        //Proxies relocalizetion
        FurnishProxy.localize();
        TransportProxy.localize();
        ViewProxy.localize();
        ObjectInputErrorLocalizer.localize();

        //Fields localization
        FlatFields.localize();
        HouseFields.localize();
        CoordinatesFields.localize();
        var r = ResourceBundle.getBundle("com.example.demo.GUI.CollectionWindow.CollectionWindow", locale);
        loadLocalisation(r);

    }


    public class FilterWindowController extends BaseController implements Initializable {

        //TODO localize filter
        private enum Mode {
            ENUM,
            TEXT_FIELD;
        }

        private Mode current = Mode.TEXT_FIELD;


        @FXML
        private Label header;

        @FXML
        private ChoiceBox<Field> filterColumnChoiceBox;

        @FXML
        private ChoiceBox<Character> sortTypeChoiceBox;

        @FXML
        private AnchorPane root;

        @FXML
        private Button filterButton;

        private TextField textInput = new TextField();

        private ChoiceBox<String> enumInput = new ChoiceBox<>();

        private String invalidArgument;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            localize(resourceBundle);
            invalidArgument = resourceBundle.getString("invalidArgument");

            filterColumnChoiceBox.getItems().clear();
            filterColumnChoiceBox.getItems().addAll(Field.getAllFields());
            sortTypeChoiceBox.getItems().addAll(
                    '>', '<', '='
            );

            filterColumnChoiceBox.setOnAction((this::onColumnSelected));
            filterButton.setOnAction((event -> onFilterClick()));


        }

        public void onColumnSelected(ActionEvent actionEvent) {
            Field value = filterColumnChoiceBox.getValue();
            if (value.equals(FlatFields.VIEW)) {
                current = Mode.ENUM;
                enumInput = new ChoiceBox<>();
                enumInput.getItems().addAll(Arrays.stream(ViewProxy.values()).map((ViewProxy::toString)).toList());
                setBasicSettings(enumInput);
                root.getChildren().add(enumInput);
            } else if (value.equals(FlatFields.TRANSPORT)) {
                current = Mode.ENUM;
                enumInput = new ChoiceBox<>();
                enumInput.getItems().addAll(Arrays.stream(TransportProxy.values()).map((TransportProxy::toString)).toList());
                setBasicSettings(enumInput);
                root.getChildren().add(enumInput);
            } else if (value.equals(FlatFields.FURNISH)) {
                current = Mode.ENUM;
                enumInput = new ChoiceBox<>();
                enumInput.getItems().addAll(Arrays.stream(FurnishProxy.values()).map((FurnishProxy::toString)).toList());
                setBasicSettings(enumInput);
                root.getChildren().add(enumInput);
            } else if (value.equals(FlatFields.NONE)) {
                return;
            } else {
                current = Mode.TEXT_FIELD;
                textInput = new TextField();
                textInput.setPromptText(value.toLocalString());
                setBasicSettings(textInput);
                root.getChildren().add(textInput);
            }

        }

        private void setBasicSettings(Control inputValue) {
            inputValue.setPrefHeight(35.0);
            inputValue.setPrefWidth(133);
            inputValue.setLayoutX(253);
            inputValue.setLayoutY(97);
        }


        public void onFilterClick() {
            Field field = filterColumnChoiceBox.getValue();
            Predicate<FlatProxy> comparisonPredicate;
            int comparisonType = 0;
            try {
                comparisonType = getComparisonType();
            } catch (Exception e) {
                //do nothing
            }
            int finalComparisonType = comparisonType;
            try {
                if (field.equals(FlatFields.NONE)) {
                    comparisonPredicate = (FlatProxy flatProxy) -> false;
                } else if (field.equals(FlatFields.ID)) {
                    Long comparisoID = Long.parseLong(getComparisonString());
                    comparisonPredicate = (FlatProxy flatProxy) -> flatProxy.getId().compareTo(comparisoID) == finalComparisonType;
                } else if (field.equals(FlatFields.NAME)) {
                    String compareName = getComparisonString();
                    comparisonPredicate = (FlatProxy flatProxy) -> flatProxy.getName().compareTo(compareName) == finalComparisonType;
                } else if (field.equals(FlatFields.CREATION_DATE)) {
                    ZonedDateTime compareDate = ZonedDateTime.parse(getComparisonString());
                    comparisonPredicate = (FlatProxy flatProxy) -> flatProxy.getCreationDate().compareTo(compareDate) == finalComparisonType;
                } else if (field.equals(CoordinatesFields.COODINATE_X)) {
                    Integer x = Integer.parseInt(getComparisonString());
                    comparisonPredicate = (FlatProxy flatProxy) -> flatProxy.getCoordinates().getX().compareTo(x) == finalComparisonType;
                } else if (field.equals(CoordinatesFields.COODINATE_Y)) {
                    Float y = Float.parseFloat(getComparisonString());
                    comparisonPredicate = (FlatProxy flatProxy) -> flatProxy.getCoordinates().getY().compareTo(y) == finalComparisonType;
                } else if (field.equals(FlatFields.VIEW)) {
                    ViewProxy viewProxy = (ViewProxy) ViewProxy.valueOfLocalString(getComparisonString());
                    comparisonPredicate = flatProxy -> ((ViewProxy) flatProxy.getViewProxy()).compareTo(viewProxy) == finalComparisonType;
                } else if (field.equals(FlatFields.TRANSPORT)) {
                    TransportProxy transport = (TransportProxy) TransportProxy.valueOfLocalString(getComparisonString());
                    comparisonPredicate = flatProxy -> ((TransportProxy) flatProxy.getTransportProxy()).compareTo(transport) == finalComparisonType;
                } else if (field.equals(FlatFields.FURNISH)) {
                    FurnishProxy furnishProxy = (FurnishProxy) FurnishProxy.valueOfLocalString(getComparisonString());
                    comparisonPredicate = flatProxy -> ((FurnishProxy) flatProxy.getFurnishProxy()).compareTo(furnishProxy) == finalComparisonType;
                } else if (field.equals(FlatFields.AREA)) {
                    Integer area = Integer.parseInt(getComparisonString());
                    comparisonPredicate = flatProxy -> flatProxy.getArea().compareTo(area) == finalComparisonType;
                } else if (field.equals(FlatFields.NUMBER_OF_ROOMS)) {
                    Long numberOfRooms = Long.parseLong(getComparisonString());
                    comparisonPredicate = flatProxy -> flatProxy.getNumberOfRooms().compareTo(numberOfRooms) == finalComparisonType;
                } else if (field.equals(HouseFields.HOUSE_NAME)) {
                    String houseName = getComparisonString();
                    comparisonPredicate = flatProxy -> flatProxy.getHouse().getName().compareTo(houseName) == finalComparisonType;
                } else if (field.equals(HouseFields.HOUSE_YEAR)) {
                    Integer houseYear = Integer.parseInt(getComparisonString());
                    comparisonPredicate = flatProxy -> flatProxy.getHouse().getYear().compareTo(houseYear) == finalComparisonType;
                } else {
                    Integer number = Integer.parseInt(getComparisonString());
                    comparisonPredicate = flatProxy -> Integer.valueOf(flatProxy.getHouse().getNumberOfFloors()).compareTo(number) == finalComparisonType;
                }
                CollecntionWindowController.this.filterPredicate = comparisonPredicate;
                refresh();
                prepareData();
                ((Stage) root.getScene().getWindow()).close();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(invalidArgument);
                alert.showAndWait();
                CollecntionWindowController.this.filterPredicate = (FlatProxy flatProxy) -> false;
            }

        }

        public int getComparisonType() {
            switch (sortTypeChoiceBox.getValue()) {
                case '<':
                    return -1;
                case '>':
                    return 1;
                default:
                    return 0;
            }
        }

        public String getComparisonString() {
            if (current.equals(Mode.ENUM)) {
                return enumInput.getValue();
            } else {
                return textInput.getText();
            }

        }
    }
}

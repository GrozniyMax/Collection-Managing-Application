package com.example.demo.GUI.Visualization;

import com.example.demo.CommonClasses.Exceptions.FunctionFailedException;
import com.example.demo.CommonClasses.Interaction.Requests.LoadCollectionWithOwnerShip;
import com.example.demo.CommonClasses.Interaction.Responces.OwnerShipResponce;
import com.example.demo.CommonClasses.Utils.Pair;
import com.example.demo.GUI.BaseController;
import com.example.demo.GUI.Commands.ComandsWindowController;
import com.example.demo.GUI.Update.UpdateWindow;
import com.example.demo.Managers.CommandContext;
import com.example.demo.Managers.UDPManager;
import com.example.demo.Proxies.FlatVisualizationAdapter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.*;


public class VisualizationWindowController extends BaseController implements Initializable {
    //TODO add user validation

    private List<FlatVisualizationAdapter> masterData;

    @FXML
    private Canvas canvas;

    @FXML
    private Pane root;

    @FXML
    private Canvas background;

    private Timeline timeline;


    public void loadCollactionWithOwners() {
        UDPManager.sendAbstractRequest(new LoadCollectionWithOwnerShip());
        try {
            masterData = ((OwnerShipResponce) UDPManager.getAbstractServerResponce()).objects().stream().map(FlatVisualizationAdapter::valueOf).toList();
        } catch (FunctionFailedException e) {
            //TODO localize
            ComandsWindowController.basicCommandExecution("something went wrong");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FlatVisualizationAdapter.setBounds(new Pair<>(20, 100),
                new Pair<>(0, canvas.getWidth()),
                new Pair<>(0, canvas.getHeight()));

        loadCollactionWithOwners();

        drawMesh();
        drawFlats();

        timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
            // Update the list
            loadCollactionWithOwners();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //TODO add event listen1er
        canvas.setOnMouseMoved(this::onMouseMoved);
        canvas.setOnMouseClicked(mouseEvent -> flatClicked(mouseEvent.getX(), mouseEvent.getY()));
    }

    public void reDraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawFlats();
    }

    private void drawMesh() {
        GraphicsContext gc = background.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        double cellSize = 20.0;
        double width = canvas.getWidth(), height = canvas.getHeight();
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());

        for (double x = cellSize; x < width; x += cellSize) {
            gc.strokeLine(x, 0, x, height);
        }
        for (double y = cellSize; y < height; y += cellSize) {
            gc.strokeLine(0, height - y, width, height - y);
        }
    }

    public void drawFlats() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (FlatVisualizationAdapter item : masterData) {
            gc.setFill(item.getColor());
            gc.fillRect(item.getX(), item.getY(), item.getSize(), item.getSize());
        }
    }


    public void onMouseMoved(MouseEvent event) {
        Double mouseX = event.getX();
        Double mouseY = event.getY();

        Double newX, newY;
        Double velocity;
        for (FlatVisualizationAdapter item : masterData) {

            Double dx = item.getX() - mouseX;
            Double dy = item.getY() - mouseY;

            velocity = getVelocity(dx, dy);

            newX = item.getX() + dx * velocity;
            newY = item.getY() + dy * velocity;

            newX = Double.min(canvas.getWidth() - item.getSize(), newX);
            newY = Double.min(canvas.getHeight() - item.getSize(), newY);

            newX = Double.max(item.getSize(), newX);
            newY = Double.max(item.getSize(), newY);

            item.setX(newX);
            item.setY(newY);

        }
        reDraw();


    }

    private Double getVelocity(Double dx, Double dy) {
        double distance = Math.sqrt(dx * dx + dy * dy);
        return 1 / distance;
    }

    private void flatClicked(double x, double y) {
        FlatVisualizationAdapter flatClicked = null;
        for (FlatVisualizationAdapter item : masterData) {
            double canvasX = item.getCenter().getKey().doubleValue();
            double canvasY = item.getCenter().getValue().doubleValue();
            double size = item.getSize();
            double distance = Math.sqrt(Math.pow(x - canvasX, 2) + Math.pow(y - canvasY, 2));

            if (distance <= size) {
                flatClicked = item;
                break;
            }
        }
        if (flatClicked != null) {

            CommandContext.instance().setArgument(flatClicked.getId());
            Stage nextStage = new Stage();
            try {
                var nextWindow = new UpdateWindow(nextStage);
                nextWindow.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("MISSED");
        }

    }

    private Image getIcon() {
        return new Image(Objects.requireNonNull(getClass().getResource("icon.png")).toExternalForm());
    }


    private Number scale(Number x, Number max, Number min, Number max2) {
        double relative = (x.doubleValue() - min.doubleValue()) / (max.doubleValue() - min.doubleValue());
        return max2.doubleValue() * relative;
    }
}

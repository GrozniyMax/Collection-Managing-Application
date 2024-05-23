package com.example.demo.Proxies;

import com.example.demo.CommonClasses.Entities.Flat;
import com.example.demo.CommonClasses.Interaction.Responces.ListItem;
import com.example.demo.CommonClasses.Utils.Pair;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class FlatVisualizationAdapter {

    private static HashMap<String,Color> userToColor = new HashMap<>();

    private static Double minSize = 0.;
    private static Double maxSize = 745D;

    private static Double minX = 0D;
    private static Double maxX = 606D;

    private static Double minY = 0D;
    private static Double maxY = 700D;

    private static Pair<Number,Number> sizeNewBounds;
    private static Pair<Number,Number> xNewBounds;
    private static Pair<Number,Number> yNewBounds;




    private final Long id;
    private Double x;
    private Double y;
    private Double size;
    private Color color;
    private final String login;

    private FlatVisualizationAdapter(ListItem flat) {
        this.id = flat.getValue().getId();
        x = flat.getValue().getCoordinates().getX().doubleValue();
        y = flat.getValue().getCoordinates().getY().doubleValue();
        size = flat.getValue().getArea().doubleValue();

        login = flat.getOwner();

        if (!(userToColor.keySet().contains(login))){
            userToColor.put(flat.getOwner(),getRandomColor(0));
        }
        color = userToColor.get(flat.getOwner());
        scale();
    }


    private void scale(){
        x = getScaledX(xNewBounds.getValue(),xNewBounds.getKey());
        y = getScaledY(yNewBounds.getValue(),yNewBounds.getKey());
        size = getScaledSize(sizeNewBounds.getValue(),sizeNewBounds.getKey());
    }

    public Long getId() {
        return id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getLogin() {
        return login;
    }

    public Double getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    /**
     *
     * @return < x, y >
     */
    public Pair<Number,Number> getCenter(){
        return new Pair<>(x+size/2,y+size/2);

    }

    public Double getScaledSize(Number newMax, Number newMin) {
        Double relative = (double) (this.size - 0)/(maxSize-minSize);
        return newMin.doubleValue() + (newMax.doubleValue() - newMin.doubleValue())*relative;
    }

    public Double getScaledX(Number newMax, Number newMin) {
        Double relative = (double) (this.size - minX)/(maxX-minX);
        return newMin.doubleValue() + (newMax.doubleValue() - newMin.doubleValue())*relative;
    }

    public Double getScaledY(Number newMax, Number newMin) {
        Double relative = (double) (this.size - minY)/(maxY-minY);
        return newMin.doubleValue() + (newMax.doubleValue() - newMin.doubleValue())*relative;
    }

    public static Color getRandomColor(int addition){
        Random random = new Random();

            // Генерируем три случайных числа для RGB кода
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
            // Форматируем RGB код в HEX формат
        return Color.web(String.format("#%02x%02x%02x", (red+addition)%256, (green+addition)%256, (blue+addition)%256));

    }

    public static FlatVisualizationAdapter valueOf(ListItem flat){
        return new FlatVisualizationAdapter(flat);
    }

    /**
     * Set bounds for size,X,Y scaling
     * @param newSize < min, max >
     * @param newX < min, max >
     * @param newY < min, max >
     */
    public static void setBounds(Pair<Number,Number> newSize,Pair<Number,Number> newX,Pair<Number,Number> newY){
        sizeNewBounds = newSize;
        xNewBounds = newX;
        yNewBounds = newY;

    }
}

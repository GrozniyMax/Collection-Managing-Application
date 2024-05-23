package com.example.demo.Proxies;

import com.example.demo.CommonClasses.Entities.*;
import javafx.beans.property.*;

import java.time.ZonedDateTime;

public class FlatTableAdapter {
    private SimpleLongProperty id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private SimpleStringProperty name;//Поле не может быть null, Строка не может быть пустой

    private SimpleObjectProperty<Coordinates> coordinates;

    private SimpleObjectProperty<ZonedDateTime> creationDate;

    private SimpleIntegerProperty area;

    private SimpleLongProperty numberOfRooms;

    private SimpleObjectProperty<FurnishProxy> furnish;

    private SimpleObjectProperty<ViewProxy> view;

    private SimpleObjectProperty<TransportProxy> transport;

    private SimpleObjectProperty<House> house;

    public FlatTableAdapter() {
        this.id = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.coordinates = new SimpleObjectProperty<>();
        this.creationDate = new SimpleObjectProperty<>();
        this.area = new SimpleIntegerProperty();
        this.numberOfRooms = new SimpleLongProperty();
        this.furnish = new SimpleObjectProperty<>();
        this.view = new SimpleObjectProperty<>();
        this.transport = new SimpleObjectProperty<>();
        this.house = new SimpleObjectProperty<>();
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Coordinates getCoordinates() {
        return coordinates.get();
    }

    public SimpleObjectProperty<Coordinates> coordinatesProperty() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates.set(coordinates);
    }

    public ZonedDateTime getCreationDate() {
        return creationDate.get();
    }

    public SimpleObjectProperty<ZonedDateTime> creationDateProperty() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate.set(creationDate);
    }

    public int getArea() {
        return area.get();
    }

    public SimpleIntegerProperty areaProperty() {
        return area;
    }

    public void setArea(int area) {
        this.area.set(area);
    }

    public FurnishProxy getFurnish() {
        return furnish.getValue();
    }

    public SimpleObjectProperty<FurnishProxy> furnishProperty() {
        return furnish;
    }

    public void setFurnish(FurnishProxy furnish) {
        this.furnish.set(furnish);
    }

    public long getNumberOfRooms() {
        return numberOfRooms.get();
    }

    public SimpleLongProperty numberOfRoomsProperty() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(long numberOfRooms) {
        this.numberOfRooms.set(numberOfRooms);
    }

    public ViewProxy getView() {
        return view.get();
    }

    public SimpleObjectProperty<ViewProxy> viewProperty() {
        return view;
    }

    public void setView(ViewProxy view) {
        this.view.set(view);
    }

    public TransportProxy getTransport() {
        return transport.get();
    }

    public SimpleObjectProperty<TransportProxy> transportProperty() {
        return transport;
    }

    public void setTransport(TransportProxy transport) {
        this.transport.set(transport);
    }

    public House getHouse() {
        return house.get();
    }

    public SimpleObjectProperty<House> houseProperty() {
        return house;
    }

    public void setHouse(House house) {
        this.house.set(house);
    }

    public static FlatTableAdapter valueOf(FlatProxy flatProxy){
        FlatTableAdapter flatTableAdapter = new FlatTableAdapter();
        flatTableAdapter.setId(flatProxy.getId());
        flatTableAdapter.setName(flatProxy.getName());
        flatTableAdapter.setCreationDate(flatProxy.getCreationDate());
        flatTableAdapter.setCoordinates(flatProxy.getCoordinates());
        flatTableAdapter.setView((ViewProxy) flatProxy.getViewProxy());
        flatTableAdapter.setTransport((TransportProxy) flatProxy.getTransportProxy());
        flatTableAdapter.setFurnish((FurnishProxy) flatProxy.getFurnishProxy());
        flatTableAdapter.setArea(flatProxy.getArea());
        flatTableAdapter.setNumberOfRooms(flatProxy.getNumberOfRooms());
        flatTableAdapter.setHouse(flatProxy.getHouse());
        return flatTableAdapter;
    }

    public FlatProxy toFlatProxy(){
        FlatProxy flatProxy = new FlatProxy();
        flatProxy.setId(getId());
        flatProxy.setName(getName());
        flatProxy.setCoordinates(getCoordinates());
        flatProxy.setArea(getArea());
        flatProxy.setNumberOfRooms(getNumberOfRooms());
        flatProxy.setHouse(getHouse());
        flatProxy.setFurnishProxy(furnish.get());
        flatProxy.setViewProxy(view.get());
        flatProxy.setTransportProxy(transport.get());
        return flatProxy;
    }

}

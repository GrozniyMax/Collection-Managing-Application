package com.example.demo.Proxies;

import com.example.demo.CommonClasses.Entities.*;
import com.example.demo.CommonClasses.Utils.IDManager;

import java.time.ZonedDateTime;

public class FlatProxy implements BasicProxy<Flat>,Comparable<FlatProxy>{
    private static IDManager ID_MANAGER = new IDManager();

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer area; //Максимальное значение поля: 745, Значение поля должно быть больше 0
    private Long numberOfRooms; //Значение поля должно быть больше 0
    private FurnishProxy furnish; //Поле может быть null
    private ViewProxy viewProxy; //Поле не может быть null
    private TransportProxy transport; //Поле не может быть null
    private House house; //Поле не может быть null
    {
        //блок инициализации объекта
        this.id = ID_MANAGER.getID();
        this.creationDate =  ZonedDateTime.now();
    }

    /**
     * Пустой конструктор
     */
    public FlatProxy() {
    }

    /**
     * Устанавливает значение поля id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Устанавливает значение поля name
     * @param name
     * @throws IllegalArgumentException - если значение поля name некорректно
     */
    public void setName(String name) throws IllegalArgumentException {
        if ((name==null)||name.equals("")) throw new IllegalArgumentException("Имя не должно быть пустым");
        this.name = name;
    }

    /**
     * Устанавливает значение поля creationDate
     * @param creationDate
     * @throws IllegalArgumentException - если значение поля creationDate некорректно
     */
    public void setCreationDate(ZonedDateTime creationDate)throws IllegalArgumentException {
        if (creationDate==null) throw new IllegalArgumentException("Дата не должна быть пустая");
        this.creationDate = creationDate;
    }

    /**
     * Устанавливает значение поля coordinates
     * @param coordinates
     * @throws IllegalArgumentException - если значение поля coordinates некорректно
     */
    public void setCoordinates(Coordinates coordinates) throws IllegalArgumentException {
        if (coordinates==null) throw new IllegalArgumentException("Координаты не должны быть пустыми");
        this.coordinates = coordinates;
    }
    /**
     * Устанавливает значение поля area
     * @param area
     * @throws IllegalArgumentException - если значение поля area некорректно
     */
    public void setArea(Integer area) throws IllegalArgumentException {
        if ((area==null)||(area<0)||(area>745)) throw new IllegalArgumentException("Некорректное значение Flat.area");
        this.area = area;
    }
    /**
     * Устанавливает значение поля numberOfRooms
     * @param numberOfRooms
     * @throws IllegalArgumentException - если значение поля numberOfRooms некорректно
     */
    public void setNumberOfRooms(Long numberOfRooms) throws IllegalArgumentException {
        if ((numberOfRooms==null)||(numberOfRooms<0)) throw new IllegalArgumentException("Некорректное значение Flat.numberOfRooms");
        this.numberOfRooms = numberOfRooms;
    }
    /**
     * Устанавливает значение поля furnish
     * @param furnish
     * @throws IllegalArgumentException - если значение поля furnish некорректно
     */
    public void setFurnishProxy(FurnishProxy furnish) throws IllegalArgumentException {
        if (furnish==null) throw new IllegalArgumentException("Поле не должно быть пустым");
        this.furnish = furnish;
    }
    /**
     * Устанавливает значение поля ViewProxy
     * @param ViewProxy
     * @throws IllegalArgumentException - если значение поля ViewProxy некорректно
     */
    public void setViewProxy(ViewProxy ViewProxy) throws IllegalArgumentException {
        if (ViewProxy ==null) throw new IllegalArgumentException("Поле не должно быть пустым");
        this.viewProxy = ViewProxy;
    }
    /**
     * Устанавливает значение поля transport
     * @param transport
     * @throws IllegalArgumentException - если значение поля transport некорректно
     */
    public void setTransportProxy(TransportProxy transport) throws IllegalArgumentException {
        if (transport==null) throw new IllegalArgumentException("Поле не должно быть пустым");
        this.transport = transport;
    }
    /**
     * Устанавливает значение поля house
     * @param house
     * @throws IllegalArgumentException - если значение поля house некорректно
     */
    public void setHouse(House house) throws IllegalArgumentException {
        if (house==null) throw new IllegalArgumentException("Поле не должно быть пустым");
        this.house = house;
    }



    /**
     * Возращает id объекта
     * @return - id объекта
     */
    public Long getId() {
        return id;
    }

    /**
     * Возвращает creationDate объекта
     * @return creationDate объекта
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Возвращает furnish объекта
     * @return furnish объекта
     */
    public FurnishProxy getFurnishProxy() {
        return furnish;
    }

    /**
     * Возвращает ViewProxy объекта
     * @return ViewProxy объекта
     */
    public ViewProxy getViewProxy() {
        return viewProxy;
    }
    /**
     * Возвращает name объекта
     * @return name объекта
     */
    public String getName() {
        return name;
    }
    /**
     * Возвращает coordinates объекта
     * @return coordinates объекта
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
    /**
     * Возвращает area объекта
     * @return area объекта
     */
    public Integer getArea() {
        return area;
    }
    /**
     * Возвращает numberOfRooms объекта
     * @return numberOfRooms объекта
     */
    public Long getNumberOfRooms() {
        return numberOfRooms;
    }
    /**
     * Возвращает transport объекта
     * @return transport объекта
     */
    public TransportProxy getTransportProxy() {
        return transport;
    }
    /**
     * Возвращает house объекта
     * @return house объекта
     */
    public House getHouse() {
        return house;
    }

    /**
     * Возвращает строковое представление объекта Flat
     * @return строковое представление объекта Flat
     */
    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.toString() +
                ", creationDate=" + creationDate.toString() +
                ", area=" + area +
                ", numberOfRooms=" + numberOfRooms +
                ", furnish=" + furnish.toString() +
                ", ViewProxy=" + viewProxy.toString() +
                ", transport=" + transport.toString() +
                ", house=" + house.toString() +
                '}';
    }


    @Override
    public Flat getOrigin() {
        Flat flat = new Flat();
        flat.setId(id);
        flat.setName(name);
        flat.setCreationDate(creationDate);
        flat.setCoordinates(coordinates);
        flat.setArea(area);
        flat.setNumberOfRooms(numberOfRooms);
        flat.setView(viewProxy.getOrigin());
        flat.setFurnish(furnish.getOrigin());
        flat.setTransport(transport.getOrigin());
        flat.setHouse(house);
        return flat;
    }


    public static FlatProxy valueOf(Flat value) {
        FlatProxy flatProxy = new FlatProxy();
        flatProxy.setId(value.getId());
        flatProxy.setName(value.getName());
        flatProxy.setCoordinates(value.getCoordinates().clone());
        flatProxy.setArea(value.getArea());
        flatProxy.setNumberOfRooms(value.getNumberOfRooms());
        flatProxy.setViewProxy(ViewProxy.valueOf(value.getView()));
        flatProxy.setTransportProxy(TransportProxy.valueOf(value.getTransport()));
        flatProxy.setFurnishProxy(FurnishProxy.valueOf(value.getFurnish()));
        flatProxy.setHouse(value.getHouse().clone());
        return flatProxy;
    }



    public int compareTo(FlatProxy o) {
        return this.id.compareTo(o.id);
    }
}

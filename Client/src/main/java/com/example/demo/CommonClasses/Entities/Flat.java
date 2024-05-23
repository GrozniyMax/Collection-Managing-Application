package com.example.demo.CommonClasses.Entities;



import com.example.demo.CommonClasses.Utils.IDManager;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Класс для хранения объекта типа Flat
 */
public class Flat implements Comparable<Flat>, Serializable {

    private static IDManager ID_MANAGER = new IDManager();

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer area; //Максимальное значение поля: 745, Значение поля должно быть больше 0
    private Long numberOfRooms; //Значение поля должно быть больше 0
    private Furnish furnish; //Поле может быть null
    private View view; //Поле не может быть null
    private Transport transport; //Поле не может быть null
    private House house; //Поле не может быть null
    {
        //блок инициализации объекта
        this.id = ID_MANAGER.getID();
        this.creationDate =  ZonedDateTime.now();
    }

    /**
     * Пустой конструктор
     */
    public Flat() {
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
    public void setFurnish(Furnish furnish) throws IllegalArgumentException {
        if (furnish==null) throw new IllegalArgumentException("Поле не должно быть пустым");
        this.furnish = furnish;
    }
    /**
     * Устанавливает значение поля view
     * @param view
     * @throws IllegalArgumentException - если значение поля view некорректно
     */
    public void setView(View view) throws IllegalArgumentException {
        if (view==null) throw new IllegalArgumentException("Поле не должно быть пустым");
        this.view = view;
    }
    /**
     * Устанавливает значение поля transport
     * @param transport
     * @throws IllegalArgumentException - если значение поля transport некорректно
     */
    public void setTransport(Transport transport) throws IllegalArgumentException {
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
     * Возвращает итог сравнения объектов
     * @return резуьтат сравнения
     */
    @Override
    public int compareTo(Flat o) {
        if (o==null) return 1;
        return area.compareTo(o.area);
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
    public Furnish getFurnish() {
        return furnish;
    }

    /**
     * Возвращает view объекта
     * @return view объекта
     */
    public View getView() {
        return view;
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
    public Transport getTransport() {
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
                ", view=" + view.toString() +
                ", transport=" + transport.toString() +
                ", house=" + house.toString() +
                '}';
    }



    public void changeID(){
        this.id = ID_MANAGER.getID();
    }

    public static void setIdManager(IDManager manager) {
//TODO setting ID manager last ID;
    }
}
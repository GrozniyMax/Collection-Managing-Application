package com.example.demo.CommonClasses.Entities;

import java.io.Serializable;

/**
 * Класс для хранения объекта типа House
 */
public class House implements Serializable,Comparable<House>{

    private String name; //Поле может быть null
    private Integer year; //Поле не может быть null, Значение поля должно быть больше 0
    private int numberOfFloors; //Значение поля должно быть больше 0
    /**
     * Устанавливает значение поля name
     * @param name - значение поля name
     * @throws IllegalArgumentException - если значение поля name некорректно
     */
    public void setName(String name) throws IllegalArgumentException {
        if ((name==null)||(name.equals(""))) throw new IllegalArgumentException("Некорректное значение House.name. Оно не должно быть пустым");
        this.name = name;
    }
    /**
     * Устанавливает значение поля year
     * @param year - значение поля year
     * @throws IllegalArgumentException - если значение поля year некорректно
     */
    public void setYear(Integer year) throws IllegalArgumentException {
        if ((year==null)||(year<0)) throw new IllegalArgumentException("Некорректное значение House.year");
        this.year = year;
    }
    /**
     * Устанавливает значение поля numberOfFloors
     * @param numberOfFloors - значение поля numberOfFloors
     * @throws IllegalArgumentException - если значение поля numberOfFloors некорректно
     */
    public void setNumberOfFloors(int numberOfFloors) throws IllegalArgumentException {
        if (numberOfFloors < 0) throw new IllegalArgumentException("Некорректное значение House.numberOfFloors");
        this.numberOfFloors = numberOfFloors;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * Возвращает строковое представление объекта House
     * @return строковое представление объекта House
     */
    @Override
    public String toString() {
        return "name='" + name + '\'' +
                "\nyear=" + year +
                "\nnumberOfFloors=" + numberOfFloors;
    }


    public House clone() {
        House house = new House();
        house.setName(name);
        house.setYear(year);
        house.setNumberOfFloors(numberOfFloors);
        return house;
    }

    @Override
    public int compareTo(House o) {
        return this.year.compareTo(o.year);
    }
}

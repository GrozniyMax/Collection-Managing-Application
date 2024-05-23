package com.example.demo.CommonClasses.Entities;

import java.io.Serializable;

/**
 * Класс для хранения объекта типа Coordinates
 */
public class Coordinates implements Serializable,Comparable<Coordinates>{

    /**
     * Поле x - координата x
     */
    private Integer x; //Максимальное значение поля: 606, Поле не может быть null
    /**
     * Поле y - координата y
     */
    private Float y; //Поле не может быть null

    public Coordinates() {
    }
    /**
     * Устанавливает значение поля x
     * @param x - значение поля x
     * @throws IllegalArgumentException - если значение поля x некорректно
     */
    public void setX(Integer x) throws IllegalArgumentException {
        if ((x==null)||(x>606)) throw new IllegalArgumentException("Некорректное значение Coordinates.x");
        this.x = x;
    }
    /**
     * Устанавливает значение поля y
     * @param y - значение поля y
     * @throws IllegalArgumentException - если значение поля y некорректно
     */
    public void setY(Float y) throws IllegalArgumentException {
        if (y==null) throw new IllegalArgumentException("Некорректное значение Coordinates.y");
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    /**
     * Возвращает строковое представление объекта Coordinates
     * @return строковое представление объекта Coordinates
     */
    @Override
    public String toString() {
        return "x=" + x.toString() +
                "\ny=" + y.toString();
    }

    public Coordinates clone() {
        Coordinates coordinates = new Coordinates();
        coordinates.setX(x);
        coordinates.setY(y);
        return coordinates;
    }

    @Override
    public int compareTo(Coordinates o) {
        return o.getX().compareTo(this.getX());
    }
}

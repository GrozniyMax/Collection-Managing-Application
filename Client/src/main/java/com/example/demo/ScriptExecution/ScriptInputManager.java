package com.example.demo.ScriptExecution;


import com.example.demo.CommonClasses.Entities.*;
import com.example.demo.CommonClasses.Exceptions.EndOfStreamException;
import com.example.demo.CommonClasses.Exceptions.InvalidInputException;

import java.io.InputStream;

/**
 * Класс для управления вводом из скрипта
 * Реализует интерфейс InputManager
 */
public class ScriptInputManager extends BaseInputManager implements InputManager{

    /**
     * Конструктор
     * @param readFrom - поток ввода
     */
    public ScriptInputManager(InputStream readFrom) {
        super(readFrom);
    }


    /**
     * Читает координаты из консоли
     * @return Coordinates - прочтенные координаты

     */
    @Override
    public Coordinates readCoordinates() throws InvalidInputException {
        try {
            String[] values = this.readLine().strip().split(" +");
            if (!(values.length ==2)) throw new IllegalArgumentException(" Необходимо вводить ровно 2 коодинаты");
            Coordinates coordinates = new Coordinates();
            coordinates.setX(Integer.parseInt(values[0]));
            coordinates.setY(Float.parseFloat(values[1]));
            return coordinates;
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    /**
     * Читает Furnish из консоли
     * @return Furnish - прочтенный Furnish
     * @throws EndOfStreamException если конец потока
     */
    public Furnish readFurnish() throws InvalidInputException{
        try {
            return Furnish.valueOf(this.readLine().strip().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }


    /**
     * Читает View из консоли
     * @return View - прочтенный View
     * @throws InvalidInputException если некорректный ввод
     */
    public View readView() throws InvalidInputException{
        try {
            return View.valueOf(this.readLine().strip().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    /**
     * Читает House из консоли
     * @return House - прочтенный House
     * @throws InvalidInputException если некорректный ввод
     */
    public House readHouse()throws InvalidInputException{
        try {
            House object = new House();
            object.setName(this.readLine());
            object.setYear(this.readInt());
            object.setNumberOfFloors(this.readInt());
            return object;
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    /**
     * Читает Transport из консоли
     * @return прочитанный Transport
     * @throws InvalidInputException если некорректный ввод
     */
    public Transport readTransport() throws InvalidInputException{
        try {
            return Transport.valueOf(this.readLine().strip().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    /**
     * Читает Flat из консоли
     * @return прочитанный Flat
     * @throws InvalidInputException если некорректный ввод
     */
    public Flat readFlat() throws InvalidInputException{
        try {
            Flat object = new Flat();
            object.setName(this.readLine());
            object.setCoordinates(this.readCoordinates());
            object.setArea(this.readInt());
            object.setNumberOfRooms(this.readLong());
            object.setFurnish(this.readFurnish());
            object.setView(this.readView());
            object.setTransport(this.readTransport());
            object.setHouse(this.readHouse());
            return object;
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(e.getMessage());
        } catch (EndOfStreamException e){
            throw new InvalidInputException("Ввод неокончен");
        }
    }

}

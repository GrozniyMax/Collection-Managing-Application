package com.example.demo.ScriptExecution;


import com.example.demo.CommonClasses.Entities.*;

/**
 * Интерфейс для управления вводом
 */
public interface InputManager {

    /**
     * Читает строку из консоли
     * @return строка
     */
    String readLine();

    default String readLine(String prefix){
        System.out.print(prefix);
        return readLine();
    }

    /**
     * Читает координаты из консоли
     * @return координаты
     */
    Coordinates readCoordinates();


    /**
     * Читает Furnish из консоли
     * @return прочитанный Furnish
     */
    Furnish readFurnish();

    /**
     * Читает View из консоли
     * @return прочитанный View
     */
     View readView();

    /**
     * Читает Transport из консоли
     * @return прочитанный Transport
     */

     Transport readTransport();

    /**
     * Читает House из консоли
     * @return прочитанный House
     */
    House readHouse();

    /**
     * Читает Flat из консоли
     * @return прочитанный Flat
     */
     Flat readFlat();

}

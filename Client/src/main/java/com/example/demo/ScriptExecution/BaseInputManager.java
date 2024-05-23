package com.example.demo.ScriptExecution;


import com.example.demo.CommonClasses.Exceptions.EndOfStreamException;
import com.example.demo.CommonClasses.Exceptions.InvalidInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Базовый класс для управления вводом/выводом
 */
public abstract class BaseInputManager {

    protected BufferedReader reader;

    /**
     * Конструктор
     * @param readFrom - поток ввода
     */
    protected BaseInputManager(InputStream readFrom) {
        this.reader = new BufferedReader(new InputStreamReader(readFrom));
    }


    /**
     * Читает строку из консоли
     * @return строка
     * @throws EndOfStreamException если конец потока
     */
    public String readLine() throws EndOfStreamException {
        try {
            String line = reader.readLine();
            return line;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения вводимых данных, это критическая ошибка");
        }
    }
    /**
     * Читает строку из консоли добавляя префикс
     * @param prefix префикс
     * @return строка
     * @throws EndOfStreamException если конец потока
     */
    public String readLine(String prefix) throws EndOfStreamException {
        System.out.print(prefix);
        return this.readLine();
    }

    /**
     * Читает целое число из консоли
     * @return Integer - прочтенное число
     * @throws InvalidInputException если некорректный ввод
     */
    public Integer readInt() throws InvalidInputException {
        try {
            return Integer.parseInt(this.readLine().strip());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Некорректный ввод целого числа формата Integer");
        }
    }
    /**
     * Читает целое число из консоли добавляя префикс
     * @param prefix - префикс
     * @return Integer - прочтенное число
     * @throws InvalidInputException если некорректный ввод
     */
    public Integer readInt(String prefix) throws InvalidInputException {
        System.out.print(prefix);
        return this.readInt();
    }

    /**
     * Читает длинное число из консоли
     * @return Long - прочтенное число
     * @throws InvalidInputException если некорректный ввод
     */
    public Long readLong() throws InvalidInputException {
        try {
            return Long.parseLong(this.readLine().strip());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Неккорректно введено число формата Long");
        }
    }
    /**
     * Читает длинное число из консоли добавляя префикс
     * @param prefix - префикс
     * @return Long - прочтенное число
     * @throws InvalidInputException если некорректный ввод
     */
    public Long readLong(String prefix) throws InvalidInputException {
        System.out.print(prefix);
        return this.readLong();
    }
    /**
     * Читает дробное число из консоли
     * @return Float - прочтенное число
     * @throws InvalidInputException если некорректный ввод
     */
    public Float readFloat() throws InvalidInputException {
        try {
            return Float.parseFloat(this.readLine().strip());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Неккорректно введено число формата Float");
        }
    }
    /**
     * Читает дробное число из консоли добавляя префикс
     * @param prefix - префикс
     * @return Float - прочтенное число
     * @throws InvalidInputException если некорректный ввод
     */
    public Float readFloat(String prefix) throws InvalidInputException {
        System.out.print(prefix);
        return readFloat();
    }
}

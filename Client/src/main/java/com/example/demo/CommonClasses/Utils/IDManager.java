package com.example.demo.CommonClasses.Utils;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Класс, представляющий менеджер идентификаторов
 */
public class IDManager {
    private Long lastId = 0L;



    public IDManager(Long lastId) {
        this.lastId = lastId;

    }

    public IDManager() {
        lastId = 0L;
    }

    /**
     * Метод для получения нового идентификатора
     * @return - новый идентификатор
     */
    public  Long getID(){

        return lastId++;


    }


}

package com.example.demo.CollectionWrappers;

import com.example.demo.CommonClasses.Entities.Flat;
import com.example.demo.CommonClasses.Utils.IDManager;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Класс коллекции
 */
public class MyCollection {
    private LinkedList<CollectionItem> list;
    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock readLock = lock.readLock();
    Lock writeLock = lock.writeLock();


    /**
     * Конструктор
     * @param list - список
     */
    public MyCollection(LinkedList<CollectionItem> list,long lastVal) {
        this.list = list;
        Flat.setIdManager(new IDManager(lastVal));
    }

    /**
     * Метод для получения списка
     * @return
     */
    public LinkedList<CollectionItem> getList() {
        return list;
    }

    /**
     * Метод для установки списка
     * @param list - список
     */
    public void setList(LinkedList<CollectionItem> list) {
        writeLock.lock();
        this.list = list;
        writeLock.unlock();
    }
}

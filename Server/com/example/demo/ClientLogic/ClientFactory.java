package com.example.demo.ClientLogic;

import com.example.demo.ClientLogic.ClientClasses.Client;

import com.example.demo.Managers.CommandManager;

import java.net.SocketAddress;

/**
 * Фабрика клиентов
 */
public class ClientFactory {

    /**
     * "Базовый" менеджер команд
     */
    CommandManager manager;

    /**
     * Конструктор
     * @param manager - менеджер команд
     */
    public ClientFactory(CommandManager manager) {
        this.manager = manager;
    }

    /**
     * Метод для создания клиента
     * @param address - адрес
     * @return - клиент
     */
    public Client createClient(SocketAddress address){
        return new Client(manager.inheritate(),address);

    }
}

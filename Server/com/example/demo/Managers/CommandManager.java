package com.example.demo.Managers;

import com.example.demo.CollectionWrappers.CollectionManager;


import com.example.demo.Commands.Command;
import com.example.demo.Commands.Parametres.ParametresBundle;

import com.example.demo.CommonClasses.Exceptions.FunctionFailedException;
import com.example.demo.CommonClasses.Interaction.Requests.CommandRequest;
import com.example.demo.CommonClasses.Interaction.Responces.ServerResponse;
import com.example.demo.Exceptions.ExitCommandException;
import com.example.demo.Main.Main;
import com.example.demo.Commands.CommonComands.*;


import java.util.*;

import java.util.logging.Logger;

/**
 * Класс, управляющий командами
 */
public class CommandManager {
    CollectionManager collectionManager;
    static final Logger logger = Main.logger;


    private Map<String, Command> commands = new HashMap<>();

    {
        //TODO clear commands list
        register(new Add());
        register(new Clear());
        register(new CountGreaterThanFurish());

        register(new GroupCountingByCreationDate());

        register(new History());
        register(new Info());
        register(new RemoveAllByView());
        register(new RemoveById());

        register(new RemoveLower());

        register(new Update());
    }

    /**
     * Добавляет команду в список
     * @param newCommand - новая команда
     */
    public void register(Command newCommand){
        commands.put(newCommand.getName(),newCommand);
    }


    /**
     * Конструктор
     * @param collectionManager - менеджер коллекции
     */
    public CommandManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public CommandManager inheritate(){
        return new CommandManager(collectionManager);
    }

    /**
     * Конструктор
     * @param collectionManager - менеджер коллекции
     * @param commands - список команд
     */
    public CommandManager(CollectionManager collectionManager, Map<String, Command> commands) {
        this.collectionManager = collectionManager;
        this.commands = commands;
    }




    /**
     * Выполняет команду
     * @param request - запрос
     * @return ответ
     * @throws ExitCommandException
     */
    public ServerResponse handle(CommandRequest request) throws ExitCommandException {
        Command currentCommand = commands.get(request.getComandName());
        collectionManager.addHistory(currentCommand.getName());
        //Creating arguments bundle
        ParametresBundle parametres=null;
        switch (currentCommand.getRequiredParametres()){
            case COLLECTION_MANAGER -> parametres = new ParametresBundle(collectionManager);
            case NONE -> parametres = new ParametresBundle();
            case COMMAND_LIST -> parametres = new ParametresBundle(this.commands.values());
            case COLLECTION_MANAGER_AND_USER_DATA -> parametres = new ParametresBundle(collectionManager, request);
        }

        ServerResponse serverResponse = null;
        try {
            serverResponse = new ServerResponse(currentCommand.execute(parametres, request.getLocale()));
        } catch (FunctionFailedException e) {
            serverResponse = new ServerResponse(e);
        }
        return serverResponse;
    }

    /**
     * Возвращает менеджер коллекции
     * @return менеджер коллекции
     */
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}

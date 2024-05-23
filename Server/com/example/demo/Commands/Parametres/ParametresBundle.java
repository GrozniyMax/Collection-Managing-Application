package com.example.demo.Commands.Parametres;

import com.example.demo.CollectionWrappers.CollectionManager;
import com.example.demo.Commands.Command;

import com.example.demo.CommonClasses.Interaction.Requests.CommandRequest;


import java.util.Collection;

/**
 * Класс для передачи параметров в команды
 */
public record ParametresBundle(CollectionManager collectionManager, CommandRequest data, Collection<Command> commands) {

    public ParametresBundle(Collection<Command> commands) {
        this(null, null, commands);
    }

    public ParametresBundle(CollectionManager collectionManager) {
        this(collectionManager, null, null);
    }

    public ParametresBundle(CollectionManager collectionManager, CommandRequest date) {
        this(collectionManager, date, null);
    }

    public ParametresBundle() {
        this(null,null,null);
    }

}

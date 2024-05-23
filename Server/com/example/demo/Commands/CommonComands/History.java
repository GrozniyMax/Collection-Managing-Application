package com.example.demo.Commands.CommonComands;

import com.example.demo.Commands.BaseCommand;
import com.example.demo.Commands.Command;
import com.example.demo.Commands.Parametres.ParametresBundle;
import com.example.demo.Commands.Parametres.ServerParams;


import java.util.LinkedList;
import java.util.Locale;


public class History extends BaseCommand {
    /**
     * @see BaseCommand
     */
    public History() {
        super("history");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COLLECTION_MANAGER;
    }


    @Override
    public LinkedList<String> execute(ParametresBundle parametresBundle, Locale authData) {
        return new LinkedList<>(parametresBundle.collectionManager().getHistory());
    }


}

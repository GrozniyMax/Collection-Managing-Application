package com.example.demo.Commands.CommonComands;


import com.example.demo.CollectionWrappers.CollectionItem;
import com.example.demo.CollectionWrappers.MyCollection;
import com.example.demo.Commands.BaseCommand;
import com.example.demo.Commands.Parametres.ParametresBundle;
import com.example.demo.Commands.Parametres.ServerParams;


import com.example.demo.CommonClasses.Entities.Flat;
import com.example.demo.CommonClasses.Exceptions.FunctionFailedException;

import com.example.demo.DataBase.DBController;
import com.example.demo.Main.Main;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;


public class Add extends BaseCommand {
    Logger logger = Main.logger;

    /**
     * @see BaseCommand
     */
    public Add() {
        super("add");
    }



    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COLLECTION_MANAGER_AND_USER_DATA;
    }


    public List<String> execute(ParametresBundle parametresBundle, Locale locale) throws IllegalArgumentException, FunctionFailedException {

        List<CollectionItem> collection = parametresBundle.collectionManager().getList();
        MyCollection c = parametresBundle.collectionManager().getCollection();
        Flat object = parametresBundle.data().getInputObject();
        String login = parametresBundle.data().getCredentials().getLogin();
        object.changeID();
        var item = new CollectionItem(object, login);
        try {
            DBController.getInstance().insertFlat(item);
            parametresBundle.collectionManager().getList().add(item);
            return new LinkedList<>();
        } catch (SQLException e) {
            logger.warning("SQL mistake:" + e.getMessage());
            throw new FunctionFailedException("Some SQL error");
        }

    }

}

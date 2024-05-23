package com.example.demo.Commands.CommonComands;

import com.example.demo.CollectionWrappers.MyCollection;
import com.example.demo.Commands.BaseCommand;
import com.example.demo.Commands.Command;
import com.example.demo.Commands.Parametres.ParametresBundle;
import com.example.demo.Commands.Parametres.ServerParams;

import com.example.demo.CommonClasses.Entities.Flat;

import com.example.demo.DataBase.DBController;
import com.example.demo.Main.Main;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RemoveLower extends BaseCommand {
    Logger logger = Main.logger;

    /**
     * @see BaseCommand
     */
    public RemoveLower() {
        super("remove_lover");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COLLECTION_MANAGER_AND_USER_DATA;
    }


    @Override
    public LinkedList<String> execute(ParametresBundle parametresBundle, Locale locale) {
        Flat compareObject = parametresBundle.data().getInputObject();
        MyCollection collection = parametresBundle.collectionManager().getCollection();
        String login = parametresBundle.data().getCredentials().getLogin();
        collection.setList(
                collection.getList().stream().filter((o)->{
                    if (!o.getOwnerLogin().equals(login)) return true;
                    if (!(o.getItem().compareTo(compareObject) != -1)) return true;
                    try {
                        DBController.getInstance().removeByID(o.getItem().getId());
                        return false;
                    } catch (SQLException e) {
                        return true;
                    }}).collect(Collectors.toCollection(LinkedList::new))
        );
        return new LinkedList<>();
    }


}

package com.example.demo.Commands.CommonComands;

import com.example.demo.CollectionWrappers.CollectionItem;

import com.example.demo.Commands.BaseCommand;
import com.example.demo.Commands.Command;
import com.example.demo.Commands.Parametres.ParametresBundle;
import com.example.demo.Commands.Parametres.ServerParams;

import com.example.demo.CommonClasses.Exceptions.FunctionFailedException;

import com.example.demo.DataBase.DBController;
import com.example.demo.Exceptions.ExitCommandException;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


public class Clear extends BaseCommand {
    /**
     * @see BaseCommand
     */
    public Clear() {
        super("clear");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COLLECTION_MANAGER_AND_USER_DATA;
    }

    @Override
    public List<String> execute(ParametresBundle arguments, Locale language) throws FunctionFailedException, ExitCommandException, FunctionFailedException {
        String login = arguments.data().getCredentials().getLogin();
        List<CollectionItem> collection = arguments.collectionManager().getList();

        collection.stream().forEach((object)->{
            try {
                if (object.getOwnerLogin().equals(login)){
                    DBController.getInstance().removeByID(object.getItem().getId());
                }
            } catch (SQLException e) {
                //do nothing;
            }
        });
        collection.removeIf((o)->o.getOwnerLogin().equals(login));
        return new LinkedList<>();
    }


}

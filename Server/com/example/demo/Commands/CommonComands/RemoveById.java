package com.example.demo.Commands.CommonComands;

import com.example.demo.CollectionWrappers.MyCollection;
import com.example.demo.Commands.BaseCommand;
import com.example.demo.Commands.Command;
import com.example.demo.Commands.Parametres.ParametresBundle;
import com.example.demo.Commands.Parametres.ServerParams;

import com.example.demo.CommonClasses.Exceptions.FunctionFailedException;

import com.example.demo.DataBase.DBController;
import com.example.demo.Main.Main;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class RemoveById extends BaseCommand {
    Logger logger = Main.logger;

    /**
     * @see BaseCommand
     */
    public RemoveById() {
        super("remove_by_id");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COLLECTION_MANAGER_AND_USER_DATA;
    }


    @Override
    public List<String> execute(ParametresBundle parametresBundle, Locale locale) throws FunctionFailedException {
        String login = parametresBundle.data().getCredentials().getLogin();
        try {
            Long id = BaseCommand.<Long>getTypedFunctionArgument(parametresBundle);
            if ((id<0)||(id>parametresBundle.collectionManager().getList().size())) throw new IndexOutOfBoundsException();
            MyCollection collection = parametresBundle.collectionManager().getCollection();
            Set<Long> usedIDs = collection.getList().stream().map((obj)->obj.getItem().getId()).distinct().collect(Collectors.toSet());
            if (!usedIDs.contains(id)) throw new FunctionFailedException("Нет элемента с таким id");
            collection.setList(
                    collection.getList().stream().filter((obj)->{
                        if (!(obj.getItem().getId().equals(id))) return true;
                        if (!obj.getOwnerLogin().equals(login)) {
                            logger.info("Permission denied");
                        }
                        try {
                            DBController.getInstance().removeByID(id);
                            return false;
                        } catch (SQLException e) {
                            return true;
                        }
                    }).collect(Collectors.toCollection(LinkedList::new))
            );
        } catch (IndexOutOfBoundsException e) {
            throw new FunctionFailedException("id больше чем количество элементов массива");
        }
        return new LinkedList<>();
    }


}

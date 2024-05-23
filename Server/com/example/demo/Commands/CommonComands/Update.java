package com.example.demo.Commands.CommonComands;

import com.example.demo.CollectionWrappers.CollectionItem;
import com.example.demo.CollectionWrappers.MyCollection;
import com.example.demo.Commands.BaseCommand;
import com.example.demo.Commands.Command;
import com.example.demo.Commands.Parametres.ParametresBundle;
import com.example.demo.Commands.Parametres.ServerParams;


import com.example.demo.CommonClasses.Entities.Flat;
import com.example.demo.CommonClasses.Exceptions.FunctionFailedException;

import com.example.demo.DataBase.DBController;
import com.example.demo.Main.Main;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Update extends BaseCommand {
    Logger logger = Main.logger;

    /**
     * @see BaseCommand
     */
    public Update() {
        super("update");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COLLECTION_MANAGER_AND_USER_DATA;
    }


    @Override
    public LinkedList<String> execute(ParametresBundle parametresBundle, Locale locale) throws FunctionFailedException {
        var resourses = ResourceBundle.getBundle("Commands",locale);
        try {
            String login = parametresBundle.data().getCredentials().getLogin();
            MyCollection collection = parametresBundle.collectionManager().getCollection();

            Long id = BaseCommand.<Long>getTypedFunctionArgument(parametresBundle);
            if ((id<0)||(id>parametresBundle.collectionManager().getList().size())) throw new IndexOutOfBoundsException();

            if (!(collection.getList().stream().map((o)->o.getItem().getId()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).containsKey(id))){
                throw new IllegalArgumentException("Элемента с таким id нет в коллекции");
            }
            Flat object = parametresBundle.data().getInputObject();
            object.setId(id);
            CollectionItem item = new CollectionItem(object,login);

            if (!collection.getList().get(id.intValue()).getOwnerLogin().equals(login)){
                logger.info("Permission denied");
                throw new FunctionFailedException(resourses.getString("permission_denied"));
            }else {
                try {
                    DBController.getInstance().updateItemByID(id,item);
                    parametresBundle.collectionManager().getCollection().setList(DBController.getInstance().loadCollection().getList());
                } catch (SQLException e) {
                    logger.info("Can not update object");

                }
            }
            return new LinkedList<String>();
        } catch ( NumberFormatException|IndexOutOfBoundsException e){
            throw new FunctionFailedException(resourses.getString("InvalidID"));
        }

    }


}

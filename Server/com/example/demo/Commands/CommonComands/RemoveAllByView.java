package com.example.demo.Commands.CommonComands;

import com.example.demo.CollectionWrappers.CollectionItem;
import com.example.demo.CollectionWrappers.MyCollection;
import com.example.demo.Commands.BaseCommand;
import com.example.demo.Commands.Command;
import com.example.demo.Commands.Parametres.ParametresBundle;
import com.example.demo.Commands.Parametres.ServerParams;



import com.example.demo.CommonClasses.Entities.View;

import com.example.demo.DataBase.DBController;
import com.example.demo.Main.Main;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RemoveAllByView extends BaseCommand {
    Logger logger = Main.logger;

    /**
     * @see BaseCommand
     */
    public RemoveAllByView() {
        super("remove_all_by_view");
    }

    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COLLECTION_MANAGER_AND_USER_DATA;
    }


    @Override
    public List<String> execute(ParametresBundle parametresBundle, Locale locale) {
        String login = parametresBundle.data().getCredentials().getLogin();
        View view = BaseCommand.<View>getTypedFunctionArgument(parametresBundle);
        MyCollection collection = parametresBundle.collectionManager().getCollection();
        LinkedList<CollectionItem> newCollection = collection.getList().stream().filter((o)->{
            if (login.equals(o.getOwnerLogin())) return true;
            if (!o.getItem().getView().equals(view)) {
                logger.info("Permission denied");
                return true;
            }
            try {
                DBController.getInstance().removeByID(o.getItem().getId());
                return false;
            } catch (SQLException e) {
                return true;
            }
        }).collect(Collectors.toCollection(LinkedList::new));

        collection.setList(newCollection);
        return new LinkedList<>();
    }

}

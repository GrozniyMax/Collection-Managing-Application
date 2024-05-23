package com.example.demo.Commands.CommonComands;

import com.example.demo.CollectionWrappers.MyCollection;
import com.example.demo.Commands.BaseCommand;
import com.example.demo.Commands.Command;
import com.example.demo.Commands.Parametres.ParametresBundle;
import com.example.demo.Commands.Parametres.ServerParams;
import com.example.demo.CommonClasses.Exceptions.FunctionFailedException;


import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Info extends BaseCommand {
    /**
     * @see BaseCommand
     */
    public Info() {
        super("info");
    }


    /**
     * @see Command#getRequiredParametres()
     */
    @Override
    public ServerParams getRequiredParametres() {
        return ServerParams.COLLECTION_MANAGER;
    }


    @Override
    public LinkedList<String> execute(ParametresBundle parametresBundle, Locale locale) throws FunctionFailedException {
        try {
            MyCollection collection = parametresBundle.collectionManager().getCollection();
            var resourses = ResourceBundle.getBundle("Commands",locale);

//            String type = resourses.getString("Info[0]");
//            String description = resourses.getString("Info[1]");

            String type = new String(resourses.getString("Info[0]").getBytes("ISO-8859-1"), "UTF-8");
            String description = new String(resourses.getString("Info[1]").getBytes("ISO-8859-1"), "UTF-8");

            return Stream.of(
                    type+parametresBundle.collectionManager().getList().getClass().getSimpleName(),
                    description+ parametresBundle.collectionManager().getList().size()).collect(Collectors.toCollection(LinkedList::new));
        } catch (Exception e) {
            throw new FunctionFailedException();
        }
    }

}

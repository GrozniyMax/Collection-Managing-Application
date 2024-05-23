package com.example.demo.Commands.CommonComands;

import com.example.demo.CollectionWrappers.MyCollection;
import com.example.demo.Commands.BaseCommand;
import com.example.demo.Commands.Command;
import com.example.demo.Commands.Parametres.ParametresBundle;
import com.example.demo.Commands.Parametres.ServerParams;
import com.example.demo.CommonClasses.Entities.Furnish;
import com.example.demo.CommonClasses.Exceptions.FunctionFailedException;


import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;


public class CountGreaterThanFurish extends BaseCommand {
    /**
     * @see BaseCommand
     */
    public CountGreaterThanFurish() {
        super("count_greater_than_furnish");
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
        try {
            //TODO debug because of resourceBundle
            var resourses = ResourceBundle.getBundle("Commands",locale);
            String openStatement = new String(resourses.getString("CountGreaterThanFurish").getBytes("ISO-8859-1"), "UTF-8");
//            String openStatement = resourses.getString("CountGreaterThanFurish");
            MyCollection collection = parametresBundle.collectionManager().getCollection();
            Furnish compareFurish = (Furnish) parametresBundle.data().getArgument();
            LinkedList<String> result = new LinkedList<>();
            result.add(openStatement+collection.getList().stream().filter((f)->f.getItem().getFurnish().compareTo(compareFurish)==1).count());
            return result;
        } catch (Exception e) {
            throw new FunctionFailedException("Не получилось достать аргумент");
        }
    }


}

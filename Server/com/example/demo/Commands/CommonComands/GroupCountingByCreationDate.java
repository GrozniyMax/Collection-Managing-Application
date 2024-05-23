package com.example.demo.Commands.CommonComands;

import com.example.demo.CollectionWrappers.MyCollection;
import com.example.demo.Commands.BaseCommand;
import com.example.demo.Commands.Command;
import com.example.demo.Commands.Parametres.ParametresBundle;
import com.example.demo.Commands.Parametres.ServerParams;
import com.example.demo.CommonClasses.Exceptions.FunctionFailedException;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GroupCountingByCreationDate extends BaseCommand {
    /**
     * @see BaseCommand
     */
    public GroupCountingByCreationDate() {
        super("group_counting_by_creation_date");
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
//        String openStatement = resourses.getString("GroupCountingByCreationDate");
            String openStatement = new String(resourses.getString("GroupCountingByCreationDate").getBytes("ISO-8859-1"), "UTF-8");


            return collection.getList().stream().map((f)->f.getItem().getCreationDate())
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet().stream()
                    .map((o)->openStatement+o.getKey().toString()+": "+o.getValue())
                    .collect(Collectors.toCollection(LinkedList::new));
        } catch (UnsupportedEncodingException e) {
            throw new FunctionFailedException();
        }


    }


}

package com.example.demo.Commands;


import com.example.demo.Commands.Parametres.ParametresBundle;
import com.example.demo.Commands.Parametres.ServerParams;
import com.example.demo.CommonClasses.Exceptions.FunctionFailedException;

import com.example.demo.Exceptions.ExitCommandException;


import java.util.List;
import java.util.Locale;

public interface Command {
    /**
     * Получить имя команды
     * @return имя команды
     */
     String getName();


     ServerParams getRequiredParametres();

    /**
     * Выполнить команду
     *
     * @param arguments - аргументы команды
     * @return строки которые нужно вывести пользователю
     * @throws FunctionFailedException если команда завершилась неудачей
     * @throws ExitCommandException    если команда завершилась выходом из программы
     */
     List<String> execute(ParametresBundle arguments, Locale language) throws  ExitCommandException, FunctionFailedException;


}

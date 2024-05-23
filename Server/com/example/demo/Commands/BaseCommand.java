package com.example.demo.Commands;

import com.example.demo.Commands.Parametres.ParametresBundle;

/**
 * Клаcc реализующий базовую команду
 */
public abstract class BaseCommand implements Command {



    /**
     * Имя команды
     */
    protected final String name;



    /**
     * Конструктор
     * @param name - имя
     */
    protected BaseCommand(String name) {
        this.name = name;
    }


    /**
     * Получить имя команды
     * @return - имя команды
     */
    public String getName() {
        return name;
    }

    /**
     * Получить аргумент в необходимом типе
     * @return - описание команды
     */
    public static <T> T getTypedFunctionArgument(ParametresBundle parametresBundle){
        return (T) parametresBundle.data().getArgument();
    }
}

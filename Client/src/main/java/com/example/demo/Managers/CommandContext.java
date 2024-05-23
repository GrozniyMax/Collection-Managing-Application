package com.example.demo.Managers;

import com.example.demo.CommonClasses.Entities.Flat;
import com.example.demo.CommonClasses.Interaction.Requests.CommandRequest;

public class CommandContext {

    private Long firstRowObjectID;

    private static CommandContext instance = new CommandContext();

    public static CommandContext instance(){
        return instance;
    }

    private Object argument;

    private Flat inputObject;

    private String commandName;

    private boolean isArgumentSet=false;
    private boolean isObjectSet=false;
    private boolean isNameSet=false;

    public Object getArgument() {
        return this.argument;
    }

    public void setArgument(Object argument) {
        isArgumentSet=true;
        instance.argument = argument;
    }

    public Flat getInputObject() {
        return this.inputObject;
    }

    public void setInputObject(Flat inputObject) {
        this.inputObject = inputObject;
        isObjectSet=true;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public void setCommandName(String commandName) {
        isNameSet=true;
        this.commandName = commandName;
    }

    public boolean isArgumentSet() {
        return isArgumentSet;
    }

    public boolean isObjectSet() {
        return isObjectSet;
    }

    public boolean isNameSet() {
        return isNameSet;
    }

    public Long getFirstRow() {
        return firstRowObjectID;
    }

    public void setFirstRow(Long firstRow) {
        this.firstRowObjectID = firstRow;
    }

    public CommandRequest toRequest(){
        isArgumentSet=false;
        isNameSet=false;
        isObjectSet=false;
        return new CommandRequest(commandName,argument,inputObject, SessionContext.getLanguage());
    }
}

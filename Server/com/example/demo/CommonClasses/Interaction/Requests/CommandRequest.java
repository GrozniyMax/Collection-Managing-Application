package com.example.demo.CommonClasses.Interaction.Requests;

import com.example.demo.CommonClasses.Entities.Flat;

import java.util.Locale;

public class CommandRequest extends AutentificationRequest{
    private Object argument;
    private Flat inputObject;
    private String comandName;
    private Locale locale;

    public CommandRequest(String comandName,Object argument, Flat inputObject,Locale locale) {
        super();
        this.comandName = comandName;
        this.argument = argument;
        this.inputObject = inputObject;
        this.locale = locale;
    }

    public Object getArgument() {
        return argument;
    }

    public Flat getInputObject() {
        return inputObject;
    }

    public String getComandName() {
        return comandName;
    }

    public Locale getLocale() {
        return locale;
    }
}

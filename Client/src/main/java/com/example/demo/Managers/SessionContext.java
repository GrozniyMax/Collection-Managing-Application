package com.example.demo.Managers;

import com.example.demo.CommonClasses.Interaction.Requests.AutentificationRequest;
import com.example.demo.CommonClasses.Interaction.Requests.CredentialsData;

import java.util.Locale;

public class SessionContext {

    private static boolean CredentialsSet;

    public static SessionContext instance = new SessionContext();

    public static SessionContext getInstance() {
        return instance;
    }

    private CredentialsData credentialsData;

    private Locale language=Locale.US;


    private SessionContext(CredentialsData credentialsData) {
        this.credentialsData = credentialsData;
        instance=this;
    }

    private SessionContext() {
    }

    private void setCredentialsData(String username, String password) {
        this.credentialsData = new CredentialsData(username,password);
    }


    public static void setCredentials(String username, String password){
        instance.setCredentialsData(username,password);
    }

    public static boolean isReady(){
        return CredentialsSet;
    }

    private CredentialsData getCredentialsData() {
        return credentialsData;
    }

    public static CredentialsData getCredentials() {
        return instance.credentialsData;
    }

    public static Locale getLanguage(){
        return instance.language;
    }

    public static void setLanguage(Locale language) {
        instance.language = language;
    }

    public static AutentificationRequest toAutentificationRequest(){
        return new AutentificationRequest(getCredentials());
    }
}

package com.example.demo.CommonClasses.Interaction.Requests;


public class AutentificationRequest implements Request{

    public final long serialVersionUID = 42L;

    static CredentialsData basicCredentials;

    protected CredentialsData credentials;

    public AutentificationRequest(CredentialsData credentials) {
        this.credentials = credentials;
        basicCredentials = credentials;
    }
    public AutentificationRequest() {
        this.credentials = basicCredentials;
    }

    public CredentialsData getCredentials() {
        return credentials;
    }

    @Override
    public boolean isCorrect() {
        return credentials.isCorrect();
    }

    public void setCredentials(CredentialsData credentials) {
        this.credentials = credentials;
    }

    public void setBasicCredentials(CredentialsData basicCredentials) {
        AutentificationRequest.basicCredentials = basicCredentials;
    }
}

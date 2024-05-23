package com.example.demo.CommonClasses.Interaction.Requests;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CredentialsData implements Serializable {

    private String login;
    private byte[] password;

    public CredentialsData() {
    }

    public CredentialsData(String login, byte[] password) {
        this.login = login;
        this.password = password;
    }

    public CredentialsData(String login,String password){
        try {
            this.login=login;
            MessageDigest ms = MessageDigest.getInstance("SHA-384");
            this.password = ms.digest(password.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException|UnsupportedEncodingException e) {
            System.out.println("Ошибка которую невозможно исправить.");
            System.exit(-1);
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public boolean isCorrect(){
        return login != null && password != null;
    }
}

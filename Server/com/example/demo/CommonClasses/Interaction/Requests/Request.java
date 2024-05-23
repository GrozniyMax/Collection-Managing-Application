package com.example.demo.CommonClasses.Interaction.Requests;

import java.io.Serializable;

public interface Request extends Serializable {

    public CredentialsData getCredentials();

    public boolean isCorrect();
}

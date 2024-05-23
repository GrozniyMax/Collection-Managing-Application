package com.example.demo.CommonClasses.Interaction.Responces;

import java.io.Serializable;

public record AutentificationResponce(boolean status) implements Serializable {
    //true - succces
    //false - not
}

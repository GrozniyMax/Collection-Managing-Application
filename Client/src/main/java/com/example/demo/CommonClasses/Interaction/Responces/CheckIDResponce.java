package com.example.demo.CommonClasses.Interaction.Responces;

import java.io.Serializable;

public record CheckIDResponce(boolean result) implements Serializable {
    //true - object belongs to author of ValidIDRequest
    //false - not
}

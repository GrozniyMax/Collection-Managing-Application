package com.example.demo.CommonClasses.Interaction.Responces;

import java.io.Serializable;
import java.util.List;

/**
 * Класс, представляющий ответ сервера на команду
 */
public record ServerResponse(boolean status,
                             List<String> output,
                             Throwable exception) implements Serializable {

    /**
     * Конструктор для создания успешного ответа
     * @param output - список строк, которые будут отправлены клиенту
     */
    public ServerResponse(List<String> output) {
        this(true, output, null);
    }


    /**
     * Конструктор для создания ответа с ошибкой
     * @param exception - исключение, которое произошло
     */
    public ServerResponse(Throwable exception) {
        this(false, null, exception);
    }
}

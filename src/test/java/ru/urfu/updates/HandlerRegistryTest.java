package ru.urfu.updates;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестирование работы регистра обработчиков. Поиск обработчиков и их вызов
 */
public class HandlerRegistryTest {

    private final HandlerRegistry handlerRegistry = new HandlerRegistry();

    private static final String NOT_FOUND_HANDLER_MESSAGE = "No action!";

    /**
     * Проверяем, что будет вызван обработчик и вернется корректный ответ
     */
    @Test
    public void shouldFoundHandler() {
        String message = "Привет!";
        String receivedMessage = handlerRegistry.handle(message);
        assertEquals("Ваше сообщение: Привет!", receivedMessage);
    }
}

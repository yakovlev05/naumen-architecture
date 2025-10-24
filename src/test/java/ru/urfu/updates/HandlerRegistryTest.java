package ru.urfu.updates;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        assertNotEquals(NOT_FOUND_HANDLER_MESSAGE, receivedMessage,
                "Обработчик должен быть найден и должен вернуться корректный ответ");
    }

    /**
     * Проверяем, что обработчик не будет найден. Должна вернуться заглушка
     */
    @Test
    public void shouldNotFoundHandler() {
        String messageFromUser = "";
        String receivedMessage = handlerRegistry.handle(messageFromUser);
        assertEquals(NOT_FOUND_HANDLER_MESSAGE, receivedMessage,
                "Обработчик не должен был найден, должно вернуться сообщение-заглушка");
    }
}

package ru.urfu.updates.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестируем работу обработчика {@link WrapperHandler}
 */
public class WrapperHandlerTest {

    private final Handler handler = new WrapperHandler();

    /**
     * Обработчик должен корректно обработать сообщение и вернуть ответ
     */
    @Test
    public void shouldReturnWrappedMessage() {
        String message = "hello";
        String receivedMessage = handler.handle(message);
        assertEquals("Ваше сообщение: hello", receivedMessage,
                "Сообщение должно быть обернуто в 'Ваше сообщение: <>' и возвращено обратно");
    }

}

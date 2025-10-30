package ru.urfu.updates;

import ru.urfu.updates.handler.Handler;
import ru.urfu.updates.handler.WrapperHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * Регистр обработчиков для сообщений от ботов
 */
public class HandlerRegistry {

    private static final String PLACEHOLDER = "No action!";

    private final List<Handler> handlers = new LinkedList<>();

    /**
     * Регистрация обработчиков. При равных условиях, обработчик выше имеет выше приоритет
     */
    public HandlerRegistry() {
        register(new WrapperHandler());
    }

    /**
     * Обработать сообщение пользователя и получить ответ
     */
    public String handle(String messageFromUser) {
        for (Handler handler : handlers) {
            if (handler.canHandle(messageFromUser)) {
                return handler.handle(messageFromUser);
            }
        }

        return PLACEHOLDER;
    }

    /**
     * Регистрация одного обработчика
     */
    private void register(Handler handler) {
        handlers.add(handler);
    }
}

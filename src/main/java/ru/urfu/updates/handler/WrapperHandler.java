package ru.urfu.updates.handler;

/**
 * Обработчик, реализующий логику ответа по шаблону: 'Ваше сообщение: <>'
 */
public class WrapperHandler implements Handler {

    private static final String TEMPLATE = "Ваше сообщение: %s";

    @Override
    public boolean canHandle(String message) {
        return true;
    }

    @Override
    public String handle(String message) {
        return TEMPLATE.formatted(message);
    }
}

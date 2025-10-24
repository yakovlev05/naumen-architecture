package ru.urfu.updates.handler;

/**
 * Обработчик, реализующий логику ответа по шаблону: 'Ваше сообщение: <>'
 */
public class WrapperHandler implements Handler {

    private static final String TEMPLATE = "Ваше сообщение: %s";

    /**
     * Обрабатываем только не пустые сообщения
     */
    @Override
    public boolean canHandle(String message) {
        return message != null && !message.isBlank();
    }

    /**
     * Возвращаем сообщение пользователя, обернутое в шаблон
     */
    @Override
    public String handle(String message) {
        return TEMPLATE.formatted(message);
    }
}

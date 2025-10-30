package ru.urfu.updates.handler;

/**
 * Обрабатывает сообщение и возвращает сообщение-ответ
 */
public interface Handler {

    /**
     * Проверяет, может ли обработать переданное сообщение
     * @param message сообщение от пользователя
     * @return true - может обработать, false - не может
     */
    boolean canHandle(String message);

    /**
     * Обрабатывает сообщение и возвращает ответ
     * @param message сообщение от пользователя
     * @return результат обработки (ответ от бота)
     */
    String handle(String message);
}

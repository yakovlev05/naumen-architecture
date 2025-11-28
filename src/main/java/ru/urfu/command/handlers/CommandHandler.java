package ru.urfu.command.handlers;

/**
 * Обработчик команды
 */
public interface CommandHandler {

    /**
     * Обработка команды
     */
    void handle();

    /**
     * Получить имя команды, которую обрабатывает данный обработчик
     *
     * @return имя команды
     */
    String getCommandName();
}

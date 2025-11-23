package ru.urfu.command;

import org.springframework.stereotype.Service;
import ru.urfu.command.handlers.CommandHandler;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс для регистрации обработчиков команд и их вызова
 */
@Service
public class CommandHandlerRegistry {

    private final Map<String, CommandHandler> handlers;

    public CommandHandlerRegistry(List<CommandHandler> handlers) {
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(
                        CommandHandler::getCommandName,
                        handler -> handler
                ));
    }

    public void handle(String command) {
        CommandHandler handler = Optional.ofNullable(handlers.get(command))
                .orElseThrow(() -> new CommandNotFound("Неизвестная команда: " + command));

        handler.handle();
    }
}

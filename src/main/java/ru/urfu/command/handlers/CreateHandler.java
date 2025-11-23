package ru.urfu.command.handlers;

import org.springframework.stereotype.Component;
import ru.urfu.document.DocumentService;

import java.util.Scanner;

/**
 * Обработчик команды create
 */
@Component
public class CreateHandler implements CommandHandler {

    private final Scanner scanner;
    private final DocumentService documentService;

    public CreateHandler(Scanner scanner, DocumentService documentService) {
        this.scanner = scanner;
        this.documentService = documentService;
    }

    @Override
    public void handle() {
        System.out.print("Введите имя документа: ");
        String name = scanner.nextLine().trim();

        System.out.println("Введите содержимое документа (пустая строка — завершить ввод):");

        StringBuilder content = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            content.append(line).append(System.lineSeparator());
        }

        documentService.createDocument(name, content.toString());

        System.out.println("Документ создан и сохранён в памяти.");
    }

    @Override
    public String getCommandName() {
        return "create";
    }
}

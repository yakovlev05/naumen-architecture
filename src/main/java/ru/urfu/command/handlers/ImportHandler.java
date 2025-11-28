package ru.urfu.command.handlers;

import org.springframework.stereotype.Component;
import ru.urfu.document.DocumentService;

import java.io.IOException;
import java.util.Scanner;

/**
 * Обработчик команды import
 */
@Component
public class ImportHandler implements CommandHandler {

    private final Scanner scanner;
    private final DocumentService documentService;

    public ImportHandler(Scanner scanner, DocumentService documentService) {
        this.scanner = scanner;
        this.documentService = documentService;
    }

    @Override
    public void handle() {
        System.out.print("Введите путь к txt файлу: ");
        String path = scanner.nextLine();

        try {
            documentService.importTxt(path);
        } catch (IOException e) {
            System.out.println("Ошибка импорта: " + e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "import";
    }

}

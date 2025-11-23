package ru.urfu.command.handlers;

import org.springframework.stereotype.Component;
import ru.urfu.document.DocumentService;
import ru.urfu.document.MyDocument;

import java.util.List;
import java.util.Scanner;

/**
 * Обработчик команды list
 */
@Component
public class ListHandler implements CommandHandler {

    private final Scanner scanner;
    private final DocumentService documentService;

    public ListHandler(Scanner scanner, DocumentService documentService) {
        this.scanner = scanner;
        this.documentService = documentService;
    }

    @Override
    public void handle() {
        List<MyDocument> myDocuments = documentService.list();
        if (myDocuments.isEmpty()) {
            System.out.println("Документов нет");
            return;
        }
        int i = 0;
        for (MyDocument doc : myDocuments) {
            System.out.println(i + ": " + doc.name());
            i++;
        }
    }

    @Override
    public String getCommandName() {
        return "list";
    }
}

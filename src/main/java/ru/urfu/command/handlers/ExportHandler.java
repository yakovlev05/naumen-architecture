package ru.urfu.command.handlers;

import org.springframework.stereotype.Component;
import ru.urfu.document.DocumentService;
import ru.urfu.document.MyDocument;
import ru.urfu.exporter.DocumentExporter;
import ru.urfu.exporter.DocumentExporterFactory;
import ru.urfu.exporter.ExporterNotFound;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

/**
 * Обработчик команды export
 */
@Component
public class ExportHandler implements CommandHandler {

    private static final Path OUTPUT_DIR = Path.of(System.getProperty("user.home"),
            "IdeaProjects/naumen-architecture");

    private final Scanner scanner;
    private final DocumentService documentService;
    private final DocumentExporterFactory documentExporterFactory;

    public ExportHandler(
            Scanner scanner,
            DocumentService documentService,
            DocumentExporterFactory documentExporterFactory
    ) {
        this.scanner = scanner;
        this.documentService = documentService;
        this.documentExporterFactory = documentExporterFactory;
    }

    @Override
    public void handle() {
        System.out.print("Введите номер документа: ");
        int index = Integer.parseInt(scanner.nextLine());

        Optional<MyDocument> documentOptional = documentService.getDocument(index);
        if (documentOptional.isEmpty()) {
            System.out.println("Нет документа с таким номером.");
            return;
        }

        MyDocument myDocument = documentOptional.get();

        System.out.print("Введите формат (txt/pdf): ");
        String format = scanner.nextLine().trim().toLowerCase();

        Path outputPath = buildPath(myDocument.name(), format);
        if (outputPath == null) {
            return;
        }

        doExport(format, outputPath, myDocument);
    }

    private Path buildPath(String fileName, String format) {
        try {
            Files.createDirectories(OUTPUT_DIR);
            return OUTPUT_DIR.resolve(fileName + "." + format);
        } catch (IOException e) {
            System.out.println("Ошибка создания директории: " + e);
            return null;
        }
    }

    private void doExport(String format, Path outputPath, MyDocument myDocument) {
        try {
            DocumentExporter exporter = documentExporterFactory.getExporter(format);
            exporter.export(myDocument, outputPath.toString());
            System.out.println("Экспорт выполнен: " + outputPath);
        } catch (ExporterNotFound e) {
            System.out.println("Неверный формат");
        }
    }

    @Override
    public String getCommandName() {
        return "export";
    }
}

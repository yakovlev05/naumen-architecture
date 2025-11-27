package ru.urfu.command.handlers;

import org.springframework.stereotype.Component;
import ru.urfu.document.DocumentService;
import ru.urfu.document.MyDocument;
import ru.urfu.exporter.DocumentExporter;
import ru.urfu.exporter.DocumentExporterRegistry;
import ru.urfu.exporter.ExporterNotFound;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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
    private final DocumentExporterRegistry documentExporterRegistry;
    private final List<DocumentExporter> exporters;

    public ExportHandler(
            Scanner scanner,
            DocumentService documentService,
            DocumentExporterRegistry documentExporterRegistry,
            List<DocumentExporter> exporters
    ) {
        this.scanner = scanner;
        this.documentService = documentService;
        this.documentExporterRegistry = documentExporterRegistry;
        this.exporters = exporters;
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

        System.out.print(buildMessage());
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
            DocumentExporter exporter = documentExporterRegistry.getExporter(format);
            exporter.export(myDocument, outputPath.toString());
            System.out.println("Экспорт выполнен: " + outputPath);
        } catch (ExporterNotFound e) {
            System.out.println("Неверный формат");
        }
    }

    private List<String> getSupportedFormats() {
        return exporters.stream()
                .map(DocumentExporter::getFileFormat)
                .toList();
    }

    private String buildMessage() {
        return "Введите формат (%s):".formatted(String.join("/", getSupportedFormats()));
    }

    @Override
    public String getCommandName() {
        return "export";
    }
}

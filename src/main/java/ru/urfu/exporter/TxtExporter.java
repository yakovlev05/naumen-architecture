package ru.urfu.exporter;

import org.springframework.stereotype.Component;
import ru.urfu.document.MyDocument;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Экспортирует документ в .txt
 */
@Component
public class TxtExporter implements DocumentExporter {

    @Override
    public void export(MyDocument myDocument, String pathToSave) {
        try {
            Files.writeString(Path.of(pathToSave), myDocument.content());
        } catch (IOException e) {
            System.out.println("Ошибка при экспорте документа в txt: " + e.getMessage());
        }
    }

    @Override
    public String getFileFormat() {
        return "txt";
    }

}

package ru.urfu.exporter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import ru.urfu.document.MyDocument;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Экспортирует документ в pdf
 */
@Component
public class PdfExporter implements DocumentExporter {

    @Override
    public void export(MyDocument myDocument, String pathToSave) {
        try {
            try (FileOutputStream outputStream = new FileOutputStream(pathToSave)) {
                Document pdf = new Document();
                PdfWriter.getInstance(pdf, outputStream);

                pdf.open();
                pdf.add(new Paragraph(myDocument.content()));
                pdf.close();
            }
        } catch (IOException | DocumentException e) {
            System.out.println("Ошибка при экспорте документа в pdf: " + e.getMessage());
        }
    }

    @Override
    public String getFileFormat() {
        return "pdf";
    }

}

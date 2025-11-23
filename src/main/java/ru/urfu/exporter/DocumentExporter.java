package ru.urfu.exporter;

import ru.urfu.document.MyDocument;

/**
 * Экспортирует документ в определенный формат файла
 */
public interface DocumentExporter {
    /**
     * Экспорт документа с сохранением в файловую систему
     *
     * @param myDocument   объект {@link MyDocument}
     * @param pathToSave путь, куда будет сохранен файл
     */
    void export(MyDocument myDocument, String pathToSave);

    /**
     * Получить формат файла, в который экспортирует данная реализация
     *
     * @return формат файла. Например pdf, pptx, word
     */
    String getFileFormat();

}

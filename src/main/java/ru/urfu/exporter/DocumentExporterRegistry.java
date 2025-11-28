package ru.urfu.exporter;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Хранилище экспортеров для документов. Возвращает нужный экспортер в зависимости от типа документа.
 * Экспортер должен реализовывать интерфейс {@link DocumentExporter}.
 */
@Service
public class DocumentExporterRegistry {

    private final Map<String, DocumentExporter> exporters;

    public DocumentExporterRegistry(List<DocumentExporter> exporters) {
        this.exporters = exporters.stream()
                .collect(Collectors.toMap(
                        DocumentExporter::getFileFormat,
                        Function.identity()
                ));
    }


    /**
     * Получить экспортер по типу документа
     *
     * @param type тип документа
     * @return объект, реализующий интерфейс {@link DocumentExporter}
     */
    public DocumentExporter getExporter(String type) {
        return Optional.ofNullable(exporters.get(type))
                .orElseThrow(() -> new ExporterNotFound("Неизвестный тип экспортера: " + type));
    }

    /**
     * Получить все доступные форматы для экспорта
     *
     * @return список форматов
     */
    public List<String> getFormats() {
        return exporters.keySet().stream().toList();
    }

}

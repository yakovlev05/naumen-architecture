package ru.urfu.exporter;

public class ExporterNotFound extends RuntimeException {
    public ExporterNotFound(String message) {
        super(message);
    }
}

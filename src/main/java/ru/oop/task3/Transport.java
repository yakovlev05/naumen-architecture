package ru.oop.task3;

/**
 * Любой транспорт для пассажирских перевозок
 */
public interface Transport extends Positioned {
    /**
     * Подъехать к точке назначения
     */
    void driveTo(Person person, Position destination);
}

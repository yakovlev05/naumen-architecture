package ru.oop.task2;

/**
 * Любой транспорт для пассажирских перевозок
 */
public interface Transport extends Positioned {

    /**
     * Подъехать к месту назначения
     * @param person человек, который едет
     * @param destination место назначения, куда человек едет
     */
    void driveTo(Person person, Position destination);
}

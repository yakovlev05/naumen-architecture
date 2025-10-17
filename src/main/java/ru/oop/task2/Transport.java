package ru.oop.task2;

/**
 * Любой транспорт для пассажирских перевозок
 */
public interface Transport extends Positioned {

    /**
     * Подъехать к точке назначения как можно ближе
     */
    void goToNearest(Position destination);

    /**
     * Сесть в транспорт
     */
    void getIn(Person person);

    /**
     * Покинуть транспорт
     */
    void getOut(Person person);
}

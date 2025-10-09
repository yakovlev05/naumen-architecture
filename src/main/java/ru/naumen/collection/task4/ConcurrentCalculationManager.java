package ru.naumen.collection.task4;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Класс управления расчётами
 */
public class ConcurrentCalculationManager<T> {

    private final BlockingQueue<Future<T>> results = new LinkedBlockingQueue<>();

    /**
     * Добавить задачу на параллельное вычисление
     */
    public void addTask(Supplier<T> task) {
        try {
            results.put(CompletableFuture.supplyAsync(task));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получить результат вычисления.
     * Возвращает результаты в том порядке, в котором добавлялись задачи.
     */
    public T getResult() {
        try {
            return results.take().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

/**
 * ОБОСНОВАНИЕ:
 *
 * 1. Выбрал коллекцию - LinkedBlockingQueue. Она реализует BlockingQueue.
 *      - Коллекция потокобезопасна, поэтому можно добавлять задачи из разных потоков - put(...)
 *      - Коллекция поддерживает блокировку при получении элемента, если элементов в коллекции нет - take()
 *      - Это очередь. Реализует FIFO - важен такой порядок по условии задачи
 *      - Выбрал именно на основе связанного списка, потому что у нас операции извлечения
 *      из начала и добавления в конец - O(1)
 *
 * 2. Сложность. addTask - O(1), getResult - O(1).
 *
 * 3. Сложность гарантирована, так как связанный список и операции
 *  выполняются с началом и концом
 *
 *
 * addTask: вызываем supplyAsync и полученный CompletableFuture кладем в очередь - O(1).
 * Задача начинает исполняться в ForkJoinPool асинхронно
 *
 * getResult: Получение из начала очереди - O(1), далее блокируемся на ожидание завершения Future
 */

package ru.naumen.collection.task4;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Supplier;

/**
 * Класс управления расчётами
 */
public class ConcurrentCalculationManager<T> {

    private final ConcurrentLinkedDeque<Future<T>> results = new ConcurrentLinkedDeque<>();

    /**
     * Добавить задачу на параллельное вычисление
     */
    public void addTask(Supplier<T> task) {
        var future = new FutureTask<>(task::get);
        results.add(future);
        future.run();
    }

    /**
     * Получить результат вычисления.
     * Возвращает результаты в том порядке, в котором добавлялись задачи.
     */
    public T getResult() {
        try {
            return results.poll().get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

/**
 * ОБОСНОВАНИЕ:
 *
 * Пояснение к реализации: метод addTask() вызывается в задаче в отдельном потоке,
 * поэтому логично в нем и выполнять действие (или требуется использовать свой Executor?).
 * Осталось только решить вопрос с получением результата, то есть дожидаться его получения. Для этого используем
 * future, который имеет реализованный get, блокирующий поток.
 *
 * 1. Использовал коллекцию - ConcurrentLinkedDeque. Это потокобезопасная двунаправленная очередь.
 * Почему очередь - вставка O(1).
 * Есть ещё CopyOnWriteArrayList - но это массивы, вставка O(n)
 * Есть ещё - Collections.synchronizedList() - просто лист с synchronized методами,
 * такой подход (синхронизация) как правило менее эффективен.
 *
 * 2. Конкретно работа с коллекцией - O(1)
 *
 * 3. Сложность добавления в очередь - O(1).
 * !!! Добавляем в конец и получаем так же с конца. Иначе было бы - O(n)
 */

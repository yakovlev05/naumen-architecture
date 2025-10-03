package ru.naumen.collection.task2;

import java.util.*;

/**
 * Дано:
 * <pre>
 * public class User {
 *     private String username;
 *     private String email;
 *     private byte[] passwordHash;
 *     …
 * }
 * </pre>
 * Нужно реализовать метод
 * <pre>
 * public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB);
 * </pre>
 * <p>который возвращает дубликаты пользователей, которые есть в обеих коллекциях.</p>
 * <p>Одинаковыми считаем пользователей, у которых совпадают все 3 поля: username,
 * email, passwordHash. Дубликаты внутри коллекций collA, collB можно не учитывать.</p>
 * <p>Метод должен быть оптимален по производительности.</p>
 * <p>Пользоваться можно только стандартными классами Java SE.
 * Коллекции collA, collB изменять запрещено.</p>
 * <p>
 * См. {@link User}
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Task2 {

    /**
     * Возвращает дубликаты пользователей, которые есть в обеих коллекциях
     */
    public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB) {
        Set<User> setB = new HashSet<>(collB);

        List<User> intersection = new LinkedList<>();
        for (User user : collA) {
            if (setB.contains(user)) {
                intersection.add(user);
            }
        }

        return intersection;
    }
}

/**
 * ОБОСНОВАНИЕ:
 *
 * 1. Одну коллекцию конвертирую в HashSet, другую оставляю как есть. Результат - intersection в LinkedList
 *
 * 2. Сложность - O(n). Перебираем все элементы в множестве.
 *
 * 3.
 *      1 - Добавление в конец intersection - O(1)
 *      2 - Проверка наличия элемента в множестве - O(1)
 *      3 - HashMap может не выдавать O(1), если количество элементов в разы больше чем бакетов.
 *      Но мы в данном случае создаем его через конструктор, а там вместимость уже рассчитывается.
 *      4 - Итерация по коллекции - O(n). Мы итерируемся не по set,
 *      потому что set может при итерации не выдавать O(1), если большая разряженность между хэш кодами ключей.
 *
 */

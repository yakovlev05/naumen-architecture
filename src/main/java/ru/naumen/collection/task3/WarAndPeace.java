package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.*;

/**
 * <p>Написать консольное приложение, которое принимает на вход произвольный текстовый файл в формате txt.
 * Нужно собрать все встречающийся слова и посчитать для каждого из них количество раз, сколько слово встретилось.
 * Морфологию не учитываем.</p>
 * <p>Вывести на экран наиболее используемые (TOP) 10 слов и наименее используемые (LAST) 10 слов</p>
 * <p>Проверить работу на романе Льва Толстого “Война и мир”</p>
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class WarAndPeace {

    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Война и мир.txt");

    public static void main(String[] args) {
        // Итерация быстрее, чем у HashMap, получение элемента - O(1), hashMap и equals у строки уже реализованы
        Map<String, Integer> wordsToCount = new LinkedHashMap<>();


        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> {
                    wordsToCount.put(word, wordsToCount.getOrDefault(word, 0) + 1); // Получение и вставка - O(1)
                });

        // Ограничение capacity до 11
        Queue<Map.Entry<String, Integer>> top10 = new PriorityQueue<>(11, Comparator.comparingInt(Map.Entry::getValue));
        Queue<Map.Entry<String, Integer>> last10 = new PriorityQueue<>(11, Comparator.comparingInt((e) -> -e.getValue()));

        for (Map.Entry<String, Integer> entry : wordsToCount.entrySet()) { // Итерация - O(n), создание entrySet - O(n)
            addOrSkip(top10, entry, true); // Происходит за O(log(10)) - получается константа
            addOrSkip(last10, entry, false); // Происходит за O(log(10)) - получается константа
        }


        dispRes(top10, "TOP 10 наиболее используемых слов:"); // O(10) - не больше 10 элементов
        System.out.println();
        dispRes(last10, "LAST 10 наименее используемых:"); // O(10)
    }

    private static void dispRes(Queue<Map.Entry<String, Integer>> result, String title) {
        List<Map.Entry<String, Integer>> words = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            words.addFirst(result.poll()); // Итерация по 10 элементам - O(10)
        }

        System.out.println(title);
        words.forEach(kv -> System.out.printf("%s - %d раз(а)%n", kv.getKey(), kv.getValue()));
    }

    private static void addOrSkip(Queue<Map.Entry<String, Integer>> queue, Map.Entry<String, Integer> entry, boolean isBiggest) {
        if (queue.size() < 10
            || (isBiggest
                ? entry.getValue() > queue.peek().getValue()
                : entry.getValue() < queue.peek().getValue())
        ) {
            queue.offer(entry); // Мы ограничиваем размер - сложность константа O(log(10))
            if (queue.size() > 10) {
                queue.poll();
            }
        }
    }
}

/**
 * ОБОСНОВАНИЕ:
 * 1. Выбранные коллекции:
 *  -- LinkedHashMap:
 *      - Доступ и вставка - O(1)
 *      - Быстрее итерация, чем у HashMap
 *      - equals и hashcode у строки уже реализован, этим достигается O(1)
 *  -- PriorityQueue:
 *      - Вставка и получение - O(log(n)), в нашем случае сократили до O(log(10))
 *      - Под капотом бинарная куча - первый элемент всегда наименьший (в зависимости от компаратора)
 *
 * 2. Сложность - O(n)
 *
 * 3. - Итерация по все словам - O(n)
 *    - Создание entrySet() - O(n)
 *    - Итерация по entrySet() - O(n)
 *    - Добавление слова в очередь - O(log10)
 *    - Вывод результата - O(10)
 */

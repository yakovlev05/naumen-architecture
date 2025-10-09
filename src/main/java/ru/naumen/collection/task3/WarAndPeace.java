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
        Map<String, Integer> wordsToCount = new HashMap<>();


        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> {
                    wordsToCount.put(word, wordsToCount.getOrDefault(word, 0) + 1);
                });

        Queue<Map.Entry<String, Integer>> top10 = new PriorityQueue<>(Comparator.comparingInt((e) -> -e.getValue()));
        Queue<Map.Entry<String, Integer>> last10 = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        for (Map.Entry<String, Integer> entry : wordsToCount.entrySet()) {
            top10.add(entry);
        }

        for (Map.Entry<String, Integer> entry : wordsToCount.entrySet()) {
            last10.add(entry);
        }

        System.out.println("TOP 10 наиболее используемых слов:");
        for (int i = 0; i < 10; i++){
            System.out.println(top10.poll());
        }

        System.out.println("\nLAST 10 наименее используемых:");
        for (int i = 0; i < 10; i++){
            System.out.println(last10.poll());
        }

    }
}

/**
 * ОБОСНОВАНИЕ:
 *
 * 1.   - Для подсчета частоты выбрал HashMap. HasMap требует определённых equals и hashcode - у строки это
 *        есть по умолчанию. HashMap идеально подходит для подсчета количества слов. Доступ по ключу O(1), мы можем
 *        эффективно обновлять счетчик. O(1) достигается за счет хорошей хэш функции, равномерно раскидывающей ключи
 *        по бакетам.
 *
 *        - Для ТОПов выбрал PriorityQueue. PriorityQueue под капотом - бинарная куча. То есть гарантирует, что корень
 *        наименьший элемент (от определенного компаратора зависит). Вставка и получение элемента - log(n)
 *
 * 2. Сложность - O(n + log k), где n - число слов в тексте, а k - колво уникальных слов
 *
 * 3. - Итерация по всем словам - O(n)
 *    - Операции put и get для словаря - O(1)
 *    - Прохождение по wordsToCount для заполнения очередей - O(n)
 *    - Операции add и poll для PriorityQueue - O(log n)
 *
 *
 *   С предыдущего решения:
 *   - Улучшил память, избавился от объекта WordCount
 *   - Разделили операции поиска топов и количества. Теперь сначала просто количество считаем и используем для этого
 *   самую подходящую коллекцию, а потом строим топ. Раньше пытался сразу все за один проход сделать
 */

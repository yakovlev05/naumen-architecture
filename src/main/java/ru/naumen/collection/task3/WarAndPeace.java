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
        SortedSet<WordCount> words = new TreeSet<>();
        Map<String, WordCount> wordToCount = new HashMap<>();


        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> {
                    WordCount currWord = wordToCount.get(word);
                    if (currWord == null) {
                        currWord = new WordCount(word);
                        wordToCount.put(word, currWord);
                    } else {
                        words.remove(currWord);
                        currWord.incCount();
                    }
                    words.add(currWord);
                });


        System.out.println("TOP 10 наиболее используемых слов:");
        words.reversed().stream().limit(10).forEach(System.out::println);

        System.out.println("\nLAST 10 наименее используемых:");
        words.stream().limit(10).forEach(System.out::println);
    }
}

class WordCount implements Comparable<WordCount> {
    private final String word;
    private int count = 1;

    public WordCount(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    public void incCount() {
        count++;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof WordCount wordCount)) return false;

        return word.equals(wordCount.word);
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }

    @Override
    public String toString() {
        return "%s - %d раз(а)".formatted(word, count);
    }

    @Override
    public int compareTo(WordCount o) {
        return Integer.compare(count, o.count);
    }
}

/**
 * ОБОСНОВАНИЕ:
 *
 * 1. Создаю две коллекции - HashMap и TreeSet.
 *      - Почему TreeSet? Хочу быстро получать элементы в отсортированном виде.
 *      TreeSet под капотом представляет собой бинарное дерево, операции вставки, удаления - O(log n).
 *      В конце работы алгоритма без сортировки уже получаем ответ.
 *      - Почему HashMap? Для подсчета слов необходимо обновлять счетчик в объекте WordCount. То есть нужно
 *      получать как то объект. Эту проблему и решает HashMap. Получение и вставка - O(1)
 *      !!! Важно заметить, что при больших объемах данных (превышающее число бакетов) сложность O(1)
 *      может не гарантироваться.
 *
 * 2. Сложность алгоритма - O(n * log k), где n - число символов в тексте,
 * k - число слов в множестве (то есть уникальных)
 *
 * 3. Прочие моменты.
 *      - Операции с HashMap: получение и вставка - O(1). Про негарантированную сложность писал выше
 *      - Операции с SortedSet: удаление и добавление - O(log m). Может возникнуть вопрос зачем удалять,
 *      а потом вставлять? SortedSet - дерево, местоположение элемента высчитывается при вставке.
 *      Чтобы найти место для нового элемента, в худшем случае необходимо пройти всю высоту дерева - log m
 *      - Финальный ответ. Ответ уже отсортирован в SortedSet. Читаем сначала с конца, а потом с начала.
 *      Метод reversed() - O(1), он ничего не делает, кроме как "переворачивает" дерево, меняет его представление.
 */

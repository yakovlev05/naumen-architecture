package example.bot;

import java.util.ArrayList;
import java.util.List;

/**
 * Фейк бот для тестирования. Хранит все отправленные сообщения.
 */
public class FakeBot implements Bot {

    /**
     * Список отправленных сообщений
     */
    private final List<String> messages = new ArrayList<>();

    @Override
    public void sendMessage(Long chatId, String message) {
        messages.add(message);
    }

    /**
     * Получить отправленное сообщение по индексу
     */
    public String getMessage(int index) {
        return messages.get(index);
    }

}

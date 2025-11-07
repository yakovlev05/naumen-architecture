package example.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестируем работу бота - {@link BotLogic}. А именно команды <b>/test, /notify, /repeat</b>
 */
public class BotLogicTest {

    /**
     * Инстанс {@link FakeBot}. Нужен для создания {@link BotLogic}.
     * Один экземпляр на весь тест, т.к. не хранит состояния.
     */
    private final FakeBot fakeBot = new FakeBot();

    /**
     * Инстанс {@link BotLogic}. Тестируемый класс.
     * Один экземпляр на весь тест, т.к. не хранит состояния.
     */
    private final BotLogic botLogic = new BotLogic(fakeBot);

    /**
     * Экземпляр пользователя {@link User}. Создается при каждом тесте, т.к. хранит состояние
     */
    private User user;

    /**
     * Инициализация теста.
     */
    @BeforeEach
    public void setUp() {
        this.user = new User(525L);
    }

    /**
     * Тестируем /test. Проверяем корректное изменение состояния пользователя
     * и реакцию на неверные ответы. В конце теста состояние пользователя должно поменять на начальное.
     */
    @Test
    public void testCommandTestIncorrectAnswers() {
        botLogic.processCommand(user, "/test");
        Assertions.assertEquals(State.TEST, user.getState());

        Assertions.assertEquals("Вычислите степень: 10^2", fakeBot.getMessage(0));
        botLogic.processCommand(user, "1");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 100", fakeBot.getMessage(1));

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", fakeBot.getMessage(2));
        botLogic.processCommand(user, "111111");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 6", fakeBot.getMessage(3));

        Assertions.assertEquals(State.INIT, user.getState());
    }

    /**
     * Тестируем /test при правильных ответах
     */
    @Test
    public void testCommandTestCorrectAnswers() {
        botLogic.processCommand(user, "/test");

        Assertions.assertEquals("Вычислите степень: 10^2", fakeBot.getMessage(0));
        botLogic.processCommand(user, "100");
        Assertions.assertEquals("Правильный ответ!", fakeBot.getMessage(1));

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", fakeBot.getMessage(2));
        botLogic.processCommand(user, "6");
        Assertions.assertEquals("Правильный ответ!", fakeBot.getMessage(3));
    }

    /**
     * Тестируем /notify с задержкой 1 секунда. Так же проверяем корректное состояние у пользователя
     */
    @Test
    public void testCommandNotifyWithDelayOneSecond() throws InterruptedException {
        botLogic.processCommand(user, "/notify");
        Assertions.assertEquals(State.SET_NOTIFY_TEXT, user.getState());

        botLogic.processCommand(user, "hello");
        Assertions.assertEquals(State.SET_NOTIFY_DELAY, user.getState());
        botLogic.processCommand(user, "1");
        Assertions.assertEquals(State.INIT, user.getState());

        Thread.sleep(1015);

        Assertions.assertEquals("Сработало напоминание: 'hello'", fakeBot.getMessage(3));
    }

    /**
     * Тестируем /notify с задержкой в виде отрицательного числа
     */
    @Test
    public void testCommandNotifyNegativeDelay() throws InterruptedException {
        botLogic.processCommand(user, "/notify");
        botLogic.processCommand(user, "negative");

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> botLogic.processCommand(user, "-1"));

        Assertions.assertEquals("Negative delay.", exception.getMessage());
    }

    /**
     * Тестируем /notify с задержкой != число
     */
    @Test
    public void testCommandNotifyWithStringDelay() throws InterruptedException {
        botLogic.processCommand(user, "/notify");
        botLogic.processCommand(user, "hello");
        botLogic.processCommand(user, "delay");

        Assertions.assertEquals("Пожалуйста, введите целое число", fakeBot.getMessage(2));
    }

    /**
     * Тестируем /repeat, если пользователь не давал неверных ответов на вопросы
     */
    @Test
    public void testCommandRepeatWithEmptyWrongAnswers() {
        botLogic.processCommand(user, "/repeat");
        Assertions.assertEquals("Нет вопросов для повторения", fakeBot.getMessage(0));
    }

    /**
     * Тестируем /repeat, если есть неверные ответы. После верного ответа, вопрос удаляется из списка
     */
    @Test
    public void testCommandRepeatWithWrongAnswersAndRemoveFromList() {
        botLogic.processCommand(user, "/test");
        botLogic.processCommand(user, "incorrect answer");

        botLogic.processCommand(user, "/repeat");
        Assertions.assertEquals("Вычислите степень: 10^2", fakeBot.getMessage(3));

        botLogic.processCommand(user, "100");
        Assertions.assertEquals("Правильный ответ!", fakeBot.getMessage(4));
        Assertions.assertEquals("Тест завершен", fakeBot.getMessage(5));

        botLogic.processCommand(user, "/repeat");
        Assertions.assertEquals("Нет вопросов для повторения", fakeBot.getMessage(6));
    }

    /**
     * Тестируем /repeat, если есть неверные ответы. После неверного ответа, вопрос НЕ удаляется из списка
     */
    @Test
    public void testCommandRepeatWithWrongAnswersAndNotRemoveFromList() {
        botLogic.processCommand(user, "/test");
        botLogic.processCommand(user, "incorrect answer");
        botLogic.processCommand(user, "/repeat");
        botLogic.processCommand(user, "10011111");

        Assertions.assertEquals("Вы ошиблись, верный ответ: 100", fakeBot.getMessage(4));
        Assertions.assertEquals("Тест завершен", fakeBot.getMessage(5));

        botLogic.processCommand(user, "/repeat");
        Assertions.assertEquals("Вычислите степень: 10^2", fakeBot.getMessage(6));
    }
}

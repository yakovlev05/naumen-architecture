package example.note;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестирование логики работы {@link NoteLogic}
 */
public class NoteLogicTest {
    /**
     * {@link NoteLogic} - создается каждый раз в тесте
     */
    private NoteLogic noteLogic;

    /**
     * Создание нового инстанса {@link NoteLogic} перед каждым тестовым методом
     */
    @BeforeEach
    public void setUp() {
        this.noteLogic = new NoteLogic();
    }

    /**
     * Проверяем добавление заметки и получение
     */
    @Test
    public void testAddAndGetNotes() {
        String resultOfAdd = noteLogic.handleMessage("/add my note");
        Assertions.assertEquals("Note added!", resultOfAdd);

        String resultOfNotes = noteLogic.handleMessage("/notes");
        Assertions.assertEquals("""
                        Your notes:
                        1. my note
                        """,
                resultOfNotes
        );
    }

    /**
     * Тестируем редактирование заметок
     */
    @Test
    public void testEditNotes() {
        noteLogic.handleMessage("/add first");
        noteLogic.handleMessage("/add second");

        String resultOfAdd = noteLogic.handleMessage("/edit 1 new value");
        Assertions.assertEquals("Note edited!", resultOfAdd);

        String resultOfNotes = noteLogic.handleMessage("/notes");
        Assertions.assertEquals("""
                        Your notes:
                        1. new value
                        2. second
                        """,
                resultOfNotes
        );
    }

    /**
     * Тестируем удаление заметок
     */
    @Test
    public void testDeleteNote() {
        noteLogic.handleMessage("/add new note");
        noteLogic.handleMessage("/add the best note");

        String resultOfDelete = noteLogic.handleMessage("/del 1");
        Assertions.assertEquals("Note deleted!", resultOfDelete);

        String resultOfNotes = noteLogic.handleMessage("/notes");
        Assertions.assertEquals("""
                        Your notes:
                        2. the best note
                        """,
                resultOfNotes
        );
    }

}

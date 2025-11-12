package example.container;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестирование работы {@link Container}
 */
public class ContainerTest {

    /**
     * {@link Container} - создается каждый раз при тесте
     */
    private Container container;

    /**
     * Создание нового инстанса {@link Container} перед каждым тестовым методом
     */
    @BeforeEach
    public void setUp() {
        this.container = new Container();
    }

    /**
     * Тестируем добавление элементов,
     * получение по индексу и получение размера контейнера
     */
    @Test
    public void testAddSizeAndGetByIndex() {
        Item item1 = new Item(1);
        Item item2 = new Item(2);

        boolean addResponse1 = container.add(item1);
        Assertions.assertTrue(addResponse1);
        boolean addResponse2 = container.add(item2);
        Assertions.assertTrue(addResponse2);

        int sizeResponse = container.size();
        Assertions.assertEquals(2, sizeResponse);

        Assertions.assertEquals(
                container.get(0),
                item1
        );
        Assertions.assertEquals(
                container.get(1),
                item2
        );
    }

    /**
     * Тестируем удаление элемента и проверку через contains
     */
    @Test
    public void testRemoveAndContains() {
        Item item1 = new Item(11);
        Item item2 = new Item(22);

        container.add(item1);
        container.add(item2);

        boolean removeResponse = container.remove(item1);
        Assertions.assertTrue(removeResponse);

        Assertions.assertFalse(container.contains(item1));
    }

}

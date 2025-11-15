package shopping;

import customer.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import product.Product;
import product.ProductDao;

/**
 * Тестируем работу {@link ShoppingService}
 */
@ExtendWith(MockitoExtension.class)
public class ShoppingServiceTest {

    /**
     * Создаем на каждый тест нового покупателя. Junit5 - пересоздает инстанс класса нка каждый тест
     */
    private final Customer customer = new Customer(777L, "79022645252");

    /**
     * Создаем на каждый тест корзину. Junit5 - пересоздает инстанс класса на каждый тест
     */
    private final Cart cart = new Cart(customer);

    /**
     * Объект для тестирования
     */
    private final ShoppingService shoppingService;

    /**
     * Мок объект, инжектится в shoppingService
     */
    private final ProductDao productDao;

    /**
     * Инициализация мок объекта и тестируемого сервиса
     *
     * @param productDao мок объект {@link ProductDao}
     */
    public ShoppingServiceTest(@Mock ProductDao productDao) {
        this.shoppingService = new ShoppingServiceImpl(productDao);
        this.productDao = productDao;
    }

    /**
     * Тестируем получение корзины пользователь.
     * Тест падает, так как на каждое получение корзины создается новая корзина
     */
    @Test
    public void testGetCart() {
        Cart cartFirst = shoppingService.getCart(customer);
        Cart cartSecond = shoppingService.getCart(customer);
        Assertions.assertEquals(cartFirst, cartSecond);
    }

    /**
     * Нет смысла тестировать.
     * Метод только проксирует вызов метода из {@link ProductDao}
     */
    @Test
    public void testGetAllProducts() {

    }

    /**
     * Нет смысла тестировать. Метод проксирует вызов метода из {@link ProductDao}
     */
    @Test
    public void testGetProductByName() {

    }

    /**
     * Тестируем покупку с пустой корзиной
     */
    @Test
    public void testBuyWithEmptyCart() throws BuyException {
        Assertions.assertFalse(shoppingService.buy(cart));
    }

    /**
     * Тестируем покупку при условии, что количество доступных товаров меньше, чем в корзине
     */
    @Test
    public void testBuyWithNoProductAvailable() {
        Product product1 = new Product("Хлеб", 5);
        Product product2 = new Product("Сыр", 3);
        cart.add(product1, 4);
        cart.add(product2, 1);
        product1.subtractCount(5);

        BuyException buyException = Assertions.assertThrows(BuyException.class, () -> shoppingService.buy(cart));
        Assertions.assertEquals("В наличии нет необходимого количества товара 'Хлеб'",
                buyException.getMessage());
    }

    /**
     * Успешная покупка.
     * Тест не проходит, потому что корзина не очищается после покупки. (Логическая ошибка)
     */
    @Test
    public void testBuySuccess() throws BuyException {
        Product product1 = new Product("Хлеб", 5);
        Product product2 = new Product("Сыр", 3);
        cart.add(product1, 2);
        cart.add(product2, 1);

        Assertions.assertTrue(shoppingService.buy(cart));
        Assertions.assertEquals(3, product1.getCount());
        Assertions.assertEquals(2, product2.getCount());
        Mockito.verify(productDao, Mockito.times(1)).save(product1);
        Mockito.verify(productDao, Mockito.times(1)).save(product2);
        Assertions.assertEquals(0, cart.getProducts().size());
    }

    /**
     * Тестируем покупку, когда в корзине отрицательное число товаров. Тест падает.
     * (Тест по сути не относится к {@link ShoppingService}, так как это ошибка в корзине.
     * Но из-за неправильной работы корзины нарушается работа ShoppingService, поэтому и оставил этот тест)
     * <p>
     * Ещё можно создать Product с отрицательным числом. Но тут проверки наверное и не нужны (это не ошибка),
     * т.к. Product просто класс-хранилище, он не должен содержать бизнес логики,
     * это забота класса, который работает с ним
     */
    @Test
    public void testBuyWithNegativeCountInCart() throws BuyException {
        Product product1 = new Product("Хлеб", 5);
        Product product2 = new Product("Сыр", 3);
        cart.add(product1, -2);
        cart.add(product2, -1);

        Assertions.assertFalse(shoppingService.buy(cart));
    }

    /**
     * Тестируем buy при условии, что корзина null. Тест падает, т.к. проверки нет.
     * <p>
     * (И нужна ли эта проверка? Как будто это "нецелевое использование сервиса".
     * Оставил тест, но не уверен, что такое тестировать нужно)
     */
    @Test
    public void testBuyWhenCartIsNull() throws BuyException {
        Assertions.assertFalse(shoppingService.buy(null));
    }

    /**
     * Тестируем, когда количество продуктов в корзине равно количеству продуктов в наличии.
     * Тест падает, потому что в корзине стоит проверка при добавлении,
     * что колво объектов должно быть всегда на один больше, чем в наличии (Логическая ошибка)
     */
    @Test
    public void testBuyWhenProductCountEqualsCart() throws BuyException {
        Product product1 = new Product("Хлеб", 5);
        cart.add(product1, 5);

        Assertions.assertTrue(shoppingService.buy(cart));
        Assertions.assertEquals(0, product1.getCount());
        Mockito.verify(productDao, Mockito.times(1)).save(product1);
        Assertions.assertEquals(0, cart.getProducts().size());
    }

}

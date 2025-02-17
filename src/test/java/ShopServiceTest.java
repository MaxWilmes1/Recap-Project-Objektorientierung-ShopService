import IDService.IdService;
import IDService.UUIDService;
import Orders.*;
import Products.Product;
import Products.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {
    private ProductRepo productRepo;
    private OrderRepo orderRepo;
    private IdService idService;
    private ShopService shopService;

    @BeforeEach
    void setup() {
        productRepo = new ProductRepo();
        orderRepo = new OrderMapRepo();
        idService = new UUIDService();
        shopService = new ShopService(productRepo, orderRepo, idService);
    }

    @Test
    void addOrderTest() {
        //GIVEN
        Product product = Product.builder()
                .id("1")
                .name("Apfel")
                .build();
        productRepo.addProduct(product);
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);

        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = Order.builder()
                .products(List.of(product))
                .status(OrderStatus.PROCESSING)
                .build();
        assertEquals(expected.products(), actual.products());
        assertEquals(expected.status(), actual.status());
        assertNotNull(actual.id());
        assertNotNull(actual.timeStamp());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectProductNotAvailableException() {
        //GIVEN
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);
        List<String> productsIds = List.of("1", "2");

        //WHEN

        assertThrows(ProductNotAvailableException.class, () -> shopService.addOrder(productsIds));
    }

    @Test
    void getOrderByStatus_givenOrderInStatusProcessing_thenReturnOrder(){
        //GIVEN
        Product product = Product.builder()
                .id("1")
                .name("Apfel")
                .build();
        Order newOrder = Order.builder()
                .id("1")
                .products(List.of(product))
                .status(OrderStatus.PROCESSING)
                .build();
        orderRepo.addOrder(newOrder);
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);

        //WHEN
        List<Order> actual = shopService.getOrdersByStatus(OrderStatus.PROCESSING);

        //THEN
        Order expectedOrder = Order.builder()
                .id("1")
                .products(List.of(product))
                .status(OrderStatus.PROCESSING)
                .build();
        List<Order> expected = List.of(expectedOrder);
        assertEquals(expected, actual);
    }

    @Test
    void updateOrder_given1OrderInStatusProcessing_thenReturnOrderInStatusInDelivery(){
        //GIVEN
        Product product = Product.builder()
                .id("1")
                .name("Apfel")
                .build();

        Order newOrder = Order.builder()
                .id("1")
                .products(List.of(product))
                .status(OrderStatus.PROCESSING)
                .timeStamp(Instant.now())
                .build();
        orderRepo.addOrder(newOrder);
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);

        //WHEN
        shopService.updateOrder("1", OrderStatus.IN_DELIVERY);
        Order actual = shopService.getOrderRepo().getOrderById("1");

        //THEN
        Order expected = Order.builder()
                .id("1")
                .products(List.of(product))
                .status(OrderStatus.IN_DELIVERY)
                .timeStamp(newOrder.timeStamp())
                .build();

        assertEquals(expected.id(), actual.id());
        assertEquals(expected.products(), actual.products());
        assertEquals(expected.status(), actual.status());
        assertNotEquals(expected.timeStamp(), actual.timeStamp());
    }

    @Test
    void updateOrder_whenOrderDoesNotExist_thenThrowException() {
        // GIVEN
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);

        String invalidOrderId = "999"; // Eine nicht existierende Order-ID

        // WHEN & THEN
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> shopService.updateOrder(invalidOrderId, OrderStatus.COMPLETED));

        // Überprüfen, ob die erwartete Fehlermeldung zurückgegeben wird
        assertEquals("Order with ID " + invalidOrderId + " not found!", exception.getMessage());
    }

    @Test
    void getOldestOrderPerStatus() {
        //GIVEN
        Product product = Product.builder()
                .id("1")
                .name("Apfel")
                .build();

        Order order1 = Order.builder()
                .id("1")
                .products(List.of(product))
                .status(OrderStatus.IN_DELIVERY)
                .timeStamp(Instant.now())
                .build();
        Order order2 = Order.builder()
                .id("2")
                .products(List.of(product))
                .status(OrderStatus.PROCESSING)
                .timeStamp(Instant.now())
                .build();
        Order order3 = Order.builder()
                .id("3")
                .products(List.of(product))
                .status(OrderStatus.PROCESSING)
                .timeStamp(Instant.now())
                .build();
        orderRepo.addOrder(order1);
        orderRepo.addOrder(order2);
        orderRepo.addOrder(order3);

        ShopService shopService = new ShopService(productRepo, orderRepo, idService);

        //WHEN
        Map<String, Order> actual = shopService.getOldestOrderPerStatus();

        //THEN
        Map<String, Order> expected = Map.of("1", order1, "2", order2);
        assertEquals(expected,actual);
    }
}

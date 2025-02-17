import Orders.*;
import Products.Product;
import Products.ProductRepo;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        ProductRepo productRepo = new ProductRepo();
        Product product = Product.builder()
                .id("1")
                .name("Apfel")
                .build();
        productRepo.addProduct(product);
        shopService.setProductRepo(productRepo);

        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = Order.builder()
                .id("1")
                .products(List.of(product))
                .status(OrderStatus.PROCESSING)
                .build();
        assertEquals(expected.products(), actual.products());
        assertNotNull(actual.id());
        assertNotNull(actual.timeStamp());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectProductNotAvailableException() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN

        assertThrows(ProductNotAvailableException.class, () -> shopService.addOrder(productsIds));
    }

    @Test
    void getOrderByStatus_given1OrderInStatusProcessing_thenReturn1Order(){
        //GIVEN
        ShopService shopService = new ShopService();
        OrderRepo orderRepo = new OrderListRepo();
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
        shopService.setOrderRepo(orderRepo);

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
        ShopService shopService = new ShopService();
        OrderRepo orderRepo = new OrderMapRepo();
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
        shopService.setOrderRepo(orderRepo);

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
    void updateOrder_given1OrderInStatusProcessig_thenReturnOrderInStatusInDelivery(){
        //GIVEN
        ShopService shopService = new ShopService();
        OrderRepo orderRepo = new OrderListRepo();
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
        shopService.setOrderRepo(orderRepo);

        //WHEN
        shopService.updateOrder("1", OrderStatus.IN_DELIVERY);
        Order actual = shopService.getOrderRepo().getOrderById("1");

                //THEN
        Order expected = Order.builder()
                .id("1")
                .products(List.of(product))
                .status(OrderStatus.IN_DELIVERY)
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void updateOrder_whenOrderDoesNotExist_thenThrowException() {
        // GIVEN
        ShopService shopService = new ShopService(); // Neues ShopService-Objekt mit leerem orderRepo
        String invalidOrderId = "999"; // Eine nicht existierende Order-ID

        // WHEN & THEN
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> shopService.updateOrder(invalidOrderId, OrderStatus.COMPLETED));

        // Überprüfen, ob die erwartete Fehlermeldung zurückgegeben wird
        assertEquals("Order with ID " + invalidOrderId + " not found!", exception.getMessage());
    }
}

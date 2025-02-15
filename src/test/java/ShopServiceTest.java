import Orders.*;
import Products.Product;
import Products.ProductRepo;
import org.junit.jupiter.api.Test;

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
                .id("-1")
                .products(List.of(product))
                .build();
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void getOrderStatus_given1OrderInStatusInProgress_thenReturn1Order(){
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
                .status(OrderStatus.IN_PROGRESS)
                .build();
        orderRepo.addOrder(newOrder);
        shopService.setOrderRepo(orderRepo);

        //WHEN
        List<Order> actual = shopService.getOrdersByStatus(OrderStatus.IN_PROGRESS);

        //THEN
        Order expectedOrder = Order.builder()
                .id("1")
                .products(List.of(product))
                .status(OrderStatus.IN_PROGRESS)
                .build();
        List<Order> expected = List.of(expectedOrder);
        assertEquals(expected, actual);
    }
}

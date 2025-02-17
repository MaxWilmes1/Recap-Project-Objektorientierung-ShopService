import Products.Product;
import Orders.Order;
import Orders.OrderListRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderListRepoTest {
    private OrderListRepo repo;
    private Product product;
    private Order newOrder;

    @BeforeEach
    void setup(){
        //GIVEN
        repo = new OrderListRepo();

        product = Product.builder()
                .id("1")
                .name("Apfel")
                .build();
        newOrder = Order.builder()
                .id("1")
                .products(List.of(product))
                .build();
        repo.addOrder(newOrder);
    }

    @Test
    void getOrders() {
        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        expected.add(newOrder);

        assertEquals(actual, expected);
    }

    @Test
    void getOrderById() {
        //WHEN
        Order actual = repo.getOrderById("1");

        //THEN
        Product expectedProduct = Product.builder()
                .id("1")
                .name("Apfel")
                .build();
        Order expected = Order.builder()
                .id("1")
                .products(List.of(expectedProduct))
                .build();

        assertEquals(actual, expected);
    }

    @Test
    void addOrder() {
        //WHEN
        Order actual = repo.addOrder(newOrder);

        //THEN
        Product expectedProduct = Product.builder()
                .id("1")
                .name("Apfel")
                .build();
        Order expected = Order.builder()
                .id("1")
                .products(List.of(expectedProduct))
                .build();
        assertEquals(actual, expected);
        assertEquals(repo.getOrderById("1"), expected);
    }

    @Test
    void removeOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        //WHEN
        repo.removeOrder("1");

        //THEN
        assertNull(repo.getOrderById("1"));
    }

}

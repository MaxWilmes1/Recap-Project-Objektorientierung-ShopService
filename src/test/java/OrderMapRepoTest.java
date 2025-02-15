import Orders.Order;
import Orders.OrderMapRepo;
import Products.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        Product product = Product.builder()
                .id("1")
                .name("Apfel")
                .build();
        Order newOrder = Order.builder()
                .id("1")
                .products(List.of(product))
                .build();
        repo.addOrder(newOrder);

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        Product product1 = Product.builder()
                .id("1")
                .name("Apfel")
                .build(); ;
        expected.add(newOrder);

        assertEquals(actual, expected);
    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        Product product = Product.builder()
                .id("1")
                .name("Apfel")
                .build(); ;
        Order newOrder = Order.builder()
                .id("1")
                .products(List.of(product))
                .build();
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.getOrderById("1");

        //THEN
        Product product1 = Product.builder()
                .id("1")
                .name("Apfel")
                .build(); ;
        Order expected = Order.builder()
                .id("1")
                .products(List.of(product))
                .build();

        assertEquals(actual, expected);
    }

    @Test
    void addOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        Product product = Product.builder()
                .id("1")
                .name("Apfel")
                .build(); ;
        Order newOrder = Order.builder()
                .id("1")
                .products(List.of(product))
                .build();

        //WHEN
        Order actual = repo.addOrder(newOrder);

        //THEN
        Product product1 = Product.builder()
                .id("1")
                .name("Apfel")
                .build(); ;
        Order expected = Order.builder()
                .id("1")
                .products(List.of(product))
                .build();
        assertEquals(actual, expected);
        assertEquals(repo.getOrderById("1"), expected);
    }

    @Test
    void removeOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        //WHEN
        repo.removeOrder("1");

        //THEN
        assertNull(repo.getOrderById("1"));
    }
}

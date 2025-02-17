import Orders.Order;
import Orders.OrderMapRepo;
import Orders.OrderRepo;
import Orders.OrderStatus;
import Products.Product;
import Products.ProductRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId)
                    .orElseThrow(() -> new ProductNotAvailableException(productId));
            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        List<Order> ordersByStatus = new ArrayList<>();
        orderRepo.getOrders().stream()
                .filter(order -> order.status() == orderStatus)
                .forEach(ordersByStatus::add);
        return ordersByStatus;
    }
}



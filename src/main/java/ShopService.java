import IDService.IdService;
import IDService.UUIDService;
import Orders.Order;
import Orders.OrderMapRepo;
import Orders.OrderRepo;
import Orders.OrderStatus;
import Products.Product;
import Products.ProductRepo;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final IdService idService;

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId)
                    .orElseThrow(() -> new ProductNotAvailableException(productId));
            products.add(productToOrder);
        }
        String newId = idService.generateId();
        Order newOrder = new Order(newId, products, OrderStatus.PROCESSING, Instant.now());

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        List<Order> ordersByStatus = new ArrayList<>();
        orderRepo.getOrders().stream()
                .filter(order -> order.status() == orderStatus)
                .forEach(ordersByStatus::add);
        return ordersByStatus;
    }

    /*
    Es wird ein Optional erstellt
    Wenn der im Optional eine Order ist, wird der .map Block ausgeführt
    Wenn null im Optional ist, wird der .orElseThrow Block ausgeführt
    .map nimmt das Objekt im Optional (hier die Order) und speichert das Objekt im Parameter der Lambda Funktion
    Der Rückgabewert von return wird zum Rückgabewert der Methode, weswegen dieser auf void gesetzt werden kann
    */
    public void updateOrder(String orderID, OrderStatus orderStatus) {
        Optional.ofNullable(orderRepo.getOrderById(orderID))
                .map(existingOrder -> {
                    Order updatedOrder = existingOrder.withStatus(orderStatus).withTimeStamp(Instant.now());
                    orderRepo.removeOrder(orderID);
                    orderRepo.addOrder(updatedOrder);
                    return updatedOrder;
                })
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + orderID + " not found!"));
    }
}



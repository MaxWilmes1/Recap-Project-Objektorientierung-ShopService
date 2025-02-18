import Orders.OrderMapRepo;
import Orders.OrderRepo;
import Products.Product;
import Products.ProductRepo;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();

        ShopService myShop = new ShopService(productRepo, orderRepo);

        Product apple = Product.builder()
                .id("1")
                .name("apple")
                .build();
        productRepo.addProduct(apple);
        Product banana = Product.builder()
                .id("2")
                .name("banana")
                .build();
        productRepo.addProduct(banana);

        List<String> order1 = List.of("1","2");
        List<String> order2 = List.of("1");
        List<String> order3 = List.of("2");

        myShop.addOrder(order1);
        myShop.addOrder(order2);
        myShop.addOrder(order3);

        System.out.println(myShop);

    }
}

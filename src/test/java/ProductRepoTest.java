import Products.Product;
import Products.ProductRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    @org.junit.jupiter.api.Test
    void getProducts() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        Product product = Product.builder()
                .id("1")
                .name("Apfel")
                .build();
        repo.addProduct(product);

        //WHEN
        List<Product> actual = repo.getProducts();

        //THEN
        List<Product> expected = new ArrayList<>();
        expected.add(product);
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getProductById() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        Product product = Product.builder()
                .id("1")
                .name("Apfel")
                .build();
        repo.addProduct(product);

        //WHEN
        Product actual = repo.getProductById("1");

        //THEN
        Product expected = product;
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void addProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        Product newProduct = new Product("2", "Banane");

        //WHEN
        Product actual = repo.addProduct(newProduct);

        //THEN
        Product expected = new Product("2", "Banane");
        assertEquals(actual, expected);
        assertEquals(repo.getProductById("2"), expected);
    }

    @org.junit.jupiter.api.Test
    void removeProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        repo.removeProduct("1");

        //THEN
        assertNull(repo.getProductById("1"));
    }
}

import Products.Product;
import Products.ProductRepo;
import org.junit.jupiter.api.Test;

import java.util.*;

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
    void getProductById_givenAvailableProduct_thenReturnProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        Optional<Product> product = Optional.of(Product.builder()
                .id("1")
                .name("Apfel")
                .build());
        repo.addProduct(product.get());

        //WHEN
        Optional<Product> actual = repo.getProductById("1");

        //THEN
        Optional<Product> expected = product;
        assertEquals(expected.get(), actual.get());
    }
    @Test
    void getProductById_givenUnavailableProduct_thenReturnEmpty() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        Optional<Product> product = Optional.of(Product.builder()
                .id("1")
                .name("Apfel")
                .build());
        repo.addProduct(product.get());

        //WHEN
        Optional<Product> actual = repo.getProductById("2");

        //THEN
        Optional<Product> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void addProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        Product newProduct = new Product("2", "Banane");

        //WHEN
        Optional<Product> actual = Optional.of( repo.addProduct(newProduct));

        //THEN
        Optional<Product> expected = Optional.of(new Product("2", "Banane"));
        assertEquals(expected.get(), actual.get());
        assertEquals(expected, repo.getProductById("2"));
    }

    @org.junit.jupiter.api.Test
    void removeProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        repo.removeProduct("1");

        //THEN
        Optional<Product> expected = Optional.empty();
        assertEquals(expected, repo.getProductById("1"));
    }
}

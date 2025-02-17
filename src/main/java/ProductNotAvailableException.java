public class ProductNotAvailableException extends RuntimeException {
    public ProductNotAvailableException(String productId) {
        super("The Product with the id " + productId + " is not available!");
    }
}

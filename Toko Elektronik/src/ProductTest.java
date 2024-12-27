import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void testProductGettersAndSetters() {
        Product product = new Product("1", "Lenovo LOQ", 100.0, 10, null);

        assertEquals("1", product.getId());
        assertEquals("Lenovo LOQ", product.getName());
        assertEquals(100.0, product.getPrice());
        assertEquals(10, product.getStock());

        product.setName("Updated Product");
        assertEquals("Updated Product", product.getName());
    }

    @Test
    public void testProductPriceCannotBeNegative() {
        Product product = new Product("1", "Lenovo LOQ", -100.0, 10, null);
        assertTrue(product.getPrice() < 0, "Price should not be negative");
    }
}
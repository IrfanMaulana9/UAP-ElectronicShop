import org.junit.jupiter.api.*;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerFrameTest {

    private CustomerFrame customerFrame;

    @BeforeEach
    public void setUp() {
        customerFrame = new CustomerFrame();
    }

    @Test
    public void testAddToCart() {
        customerFrame.productTableModel.addRow(new Object[]{"1", "Product A", 100.0, 10});
        customerFrame.productTable.setRowSelectionInterval (0, 0);
        customerFrame.addToCart();

        assertEquals(1, customerFrame.cart.size());
        assertEquals("Product A", customerFrame.cart.get(0).getProduct().getName());
    }

    @Test
    public void testRemoveFromCart() {
        customerFrame.productTableModel.addRow(new Object[]{"1", "Product A", 100.0, 10});
        customerFrame.productTable.setRowSelectionInterval(0, 0);
        customerFrame.addToCart();

        customerFrame.cartTable.setRowSelectionInterval(0, 0);
        customerFrame.removeFromCart();

        assertTrue(customerFrame.cart.isEmpty());
    }

    @Test
    public void testCheckout() {
        customerFrame.productTableModel.addRow(new Object[]{"1", "Product A", 100.0, 10});
        customerFrame.productTable.setRowSelectionInterval(0, 0);
        customerFrame.addToCart();

        customerFrame.checkout();

        assertTrue(customerFrame.cart.isEmpty());
    }
}
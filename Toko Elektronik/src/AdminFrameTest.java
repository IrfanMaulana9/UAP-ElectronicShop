import org.junit.jupiter.api.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

public class AdminFrameTest {

    private AdminFrame adminFrame;

    @BeforeEach
    public void setUp() {
        adminFrame = new AdminFrame();
    }

    @Test
    public void testAddProduct() {
        adminFrame.idField.setText("1");
        adminFrame.nameField.setText("Product A");
        adminFrame.priceField.setText("100.0");
        adminFrame.stockField.setText("10");

        adminFrame.addProduct();

        assertEquals(1, adminFrame.products.size());
        assertEquals("Product A", adminFrame.products.get(0).getName());
    }

    @Test
    public void testUpdateProduct() {
        adminFrame.idField.setText("1");
        adminFrame.nameField.setText("Product A");
        adminFrame.priceField.setText("100.0");
        adminFrame.stockField.setText("10");
        adminFrame.addProduct();

        adminFrame.productTable.setRowSelectionInterval(0, 0);
        adminFrame.nameField.setText("Updated Product");
        adminFrame.updateProduct();

        assertEquals("Updated Product", adminFrame.products.get(0).getName());
    }

    @Test
    public void testDeleteProduct() {
        adminFrame.idField.setText("1");
        adminFrame.nameField.setText("Product A");
        adminFrame.priceField.setText("100.0");
        adminFrame.stockField.setText("10");
        adminFrame.addProduct();

        adminFrame.productTable.setRowSelectionInterval(0, 0);
        adminFrame.deleteProduct();

        assertTrue(adminFrame.products.isEmpty());
    }
}
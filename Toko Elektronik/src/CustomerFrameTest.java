import org.junit.jupiter.api.*;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerFrameTest {

    private CustomerFrame customerFrame;

    @BeforeEach
    public void setUp() {
        // Inisialisasi CustomerFrame sebelum setiap tes
        customerFrame = new CustomerFrame();
    }

    @Test
    public void testaddandRemoveFromCart() {
        // Membuat produk baru untuk tes ini
        customerFrame.productTableModel.addRow(new Object[]{"2", "Product B", 500.0, 20});

        // Memilih produk pertama yang akan ditambahkan ke keranjang
        customerFrame.productTable.setRowSelectionInterval(0, 0);
        customerFrame.addToCart();

        // Memastikan produk ada di keranjang sebelum dihapus
        assertEquals(1, customerFrame.cart.size(), "Keranjang harus berisi 1 produk sebelum dihapus.");

        // Memilih produk dari keranjang untuk dihapus
        customerFrame.cartTable.setRowSelectionInterval(0, 0);
        customerFrame.removeFromCart();

        // Memastikan keranjang kosong setelah penghapusan produk
        assertTrue(customerFrame.cart.isEmpty(), "Keranjang harus kosong setelah penghapusan.");
    }

    @Test
    public void testCheckout() {
        // Membuat produk baru untuk tes ini
        customerFrame.productTableModel.addRow(new Object[]{"3", "Product C", 750.0, 15});

        // Memilih produk pertama yang akan ditambahkan ke keranjang
        customerFrame.productTable.setRowSelectionInterval(0, 0);
        customerFrame.addToCart();

        // Memastikan produk ada di keranjang sebelum checkout
        assertEquals(1, customerFrame.cart.size(), "Keranjang harus berisi 1 produk sebelum checkout.");

        // Melakukan checkout
        customerFrame.checkout();

        // Memastikan keranjang kosong setelah checkout
        assertTrue(customerFrame.cart.isEmpty(), "Keranjang harus kosong setelah checkout.");
    }
}
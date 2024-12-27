import org.junit.jupiter.api.*;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

public class AdminFrameTest {

    private AdminFrame adminFrame;

    @BeforeEach
    public void setUp() {
        adminFrame = new AdminFrame();
        adminFrame.products.clear(); // Pastikan daftar produk kosong sebelum tes
    }

    @Test
    public void testAddUpdateDeleteProduct() {
        // Langkah 1: Menambahkan produk baru
        adminFrame.idField.setText("1");
        adminFrame.nameField.setText("Product A");
        adminFrame.priceField.setText("100.0");
        adminFrame.stockField.setText("10");

        // Menambahkan produk
        adminFrame.addProduct();

        // Memeriksa apakah produk pertama berhasil ditambahkan
        assertEquals(1, adminFrame.products.size(), "Jumlah produk harus 1 setelah ditambahkan.");
        assertEquals("Product A", adminFrame.products.get(0).getName(), "Nama produk pertama harus 'Product A'.");

        // Langkah 2: Memperbarui produk yang sudah ditambahkan
        adminFrame.productTable.setRowSelectionInterval(0, 0); // Memilih produk yang baru saja ditambahkan
        adminFrame.nameField.setText("Updated Product");  // Mengubah nama produk
        adminFrame.updateProduct();

        // Memeriksa apakah produk berhasil diperbarui
        assertEquals("Updated Product", adminFrame.products.get(0).getName(), "Nama produk pertama harus 'Updated Product'.");

        // Langkah 3: Menghapus produk yang sudah ditambahkan dan diperbarui
        adminFrame.productTable.setRowSelectionInterval(0, 0);  // Memilih produk yang sudah diperbarui
        adminFrame.deleteProduct();

        // Memeriksa apakah produk telah dihapus
        assertTrue(adminFrame.products.isEmpty(), "Daftar produk harus kosong setelah penghapusan.");
    }
}
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    private static final String TEST_FILE = "test_products.dat";

    @BeforeEach
    public void setUp() {
        // Hapus file test sebelum setiap pengujian
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testSaveAndLoadProducts() throws IOException, ClassNotFoundException {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("1", "Product A", 100.0, 10, null));
        products.add(new Product("2", "Product B", 200.0, 5, null));

        // Simpan produk
        FileHandler.saveProducts(products);

        // Muat produk
        ArrayList<Product> loadedProducts = FileHandler.loadProducts();

        assertEquals(2, loadedProducts.size());
        assertEquals("Product A", loadedProducts.get(0).getName());
        assertEquals("Product B", loadedProducts.get(1).getName());
    }


}

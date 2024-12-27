import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    private static final String PRODUCTS_FILE = "products.dat";

    public static void saveProducts(ArrayList<Product> products) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(PRODUCTS_FILE))) {
            oos.writeObject(products);
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Product> loadProducts() throws IOException, ClassNotFoundException {
        File file = new File(PRODUCTS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            return (ArrayList<Product>) ois.readObject();
        }
    }
}
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

public class CustomerFrame extends JFrame {
    public ArrayList<Product> products;
    public ArrayList<CartItem> cart;
    public DefaultTableModel productTableModel;
    public DefaultTableModel cartTableModel;
    public JTable productTable;
    public JTable cartTable;
    public JLabel totalLabel;
    public JLabel imageLabel; // New image label
    public double totalAmount = 0.0;

    public CustomerFrame() {
        products = loadProducts();
        cart = new ArrayList<>();
        initializeUI();
        updateProductTable();
    }

    public void initializeUI() {
        setTitle("Toko Elektronik - Customer Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700); // Increased window size
        setLocationRelativeTo(null);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu accountMenu = new JMenu("Akun");
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> logout());
        accountMenu.add(logoutItem);
        menuBar.add(accountMenu);
        setJMenuBar(menuBar);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create split pane for product list and details
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(600);

        // Left panel containing product table and image
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));

        // Product Panel
        JPanel productPanel = createProductPanel();

        // Image Panel
        JPanel imagePanel = createImagePanel();

        leftPanel.add(productPanel, BorderLayout.CENTER);
        leftPanel.add(imagePanel, BorderLayout.SOUTH);

        splitPane.setLeftComponent(leftPanel);

        // Cart Panel (Right Side)
        JPanel cartPanel = createCartPanel();
        splitPane.setRightComponent(cartPanel);

        mainPanel.add(splitPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    public JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Gambar Produk"));

        // Initialize image label with fixed size
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(200, 200));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel.add(imageLabel, BorderLayout.CENTER);
        return panel;
    }

    public JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Daftar Produk"));

        // Product Table
        String[] productColumns = {"ID", "Nama", "Harga", "Stok"};
        productTableModel = new DefaultTableModel(productColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(productTableModel);
        JScrollPane productScrollPane = new JScrollPane(productTable);

        // Add selection listener to update image when product is selected
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSelectedProductImage();
            }
        });

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addToCartButton = new JButton("Tambah ke Keranjang");
        addToCartButton.addActionListener(e -> addToCart());
        buttonPanel.add(addToCartButton);

        panel.add(productScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void updateSelectedProductImage() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            String productId = productTableModel.getValueAt(selectedRow, 0).toString();
            Product product = findProductById(productId);
            if (product != null && product.getImage() != null) {
                // Scale the image to fit the label while maintaining aspect ratio
                ImageIcon icon = product.getImage();
                Image image = icon.getImage();
                Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            }
        } else {
            imageLabel.setIcon(null);
            imageLabel.setText("");
        }
    }



    public JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Keranjang Belanja"));
        panel.setPreferredSize(new Dimension(300, 0));

        // Cart Table
        String[] cartColumns = {"Nama", "Harga", "Jumlah", "Total"};
        cartTableModel = new DefaultTableModel(cartColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cartTable = new JTable(cartTableModel);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);

        // Total and Buttons Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Total Label
        totalLabel = new JLabel("Total: Rp 0");
        totalLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        totalLabel.setFont(new Font(totalLabel.getFont().getName(), Font.BOLD, 14));

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton removeButton = new JButton("Hapus");
        JButton checkoutButton = new JButton("Bayar");

        removeButton.addActionListener(e -> removeFromCart());
        checkoutButton.addActionListener(e -> checkout());

        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);

        bottomPanel.add(totalLabel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        panel.add(cartScrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void addToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Pilih produk yang ingin dibeli!");
            return;
        }

        String productId = productTableModel.getValueAt(selectedRow, 0).toString();
        Product product = findProductById(productId);

        if (product != null) {
            if (product.getStock() > 0) {
                // Check if product already in cart
                CartItem existingItem = findCartItemByProduct(product);
                if (existingItem != null) {
                    existingItem.incrementQuantity();
                } else {
                    cart.add(new CartItem(product));
                }

                product.setStock(product.getStock() - 1);
                updateTables();
                updateTotal();
            } else {
                showError("Stok produk habis!");
            }
        }
    }

    public void removeFromCart() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Pilih item yang ingin dihapus dari keranjang!");
            return;
        }

        CartItem item = cart.get(selectedRow);
        Product product = item.getProduct();

        // Return stock
        product.setStock(product.getStock() + item.getQuantity());

        // Remove from cart
        cart.remove(selectedRow);

        updateTables();
        updateTotal();
    }

 public void checkout() {
        if (cart.isEmpty()) {
            showError("Keranjang belanja kosong!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                String.format("Total pembayaran: Rp %.2f\nLanjutkan pembelian?", totalAmount),
                "Konfirmasi Pembelian",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Save updated product stock
            saveProducts(products);

            // Clear cart
            cart.clear();
            updateTables();
            updateTotal();

            showSuccess("Pembelian berhasil!\nTerima kasih telah berbelanja.");
        }
    }

    public void updateTables() {
        updateProductTable();
        updateCartTable();
    }

    public void updateProductTable() {
        productTableModel.setRowCount(0);
        for (Product product : products) {
            productTableModel.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    formatPrice(product.getPrice()),
                    product.getStock()
            });
        }
    }

    public void updateCartTable() {
        cartTableModel.setRowCount(0);
        for (CartItem item : cart) {
            cartTableModel.addRow(new Object[]{
                    item.getProduct().getName(),
                    formatPrice(item.getProduct().getPrice()),
                    item.getQuantity(),
                    formatPrice(item.getTotal())
            });
        }
    }

    public void updateTotal() {
        totalAmount = cart.stream()
                .mapToDouble(CartItem::getTotal)
                .sum();
        totalLabel.setText(String.format("Total: Rp %s", formatPrice(totalAmount)));
    }

    public String formatPrice(double price) {
        return String.format("%,.2f", price);
    }

    public Product findProductById(String id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public CartItem findCartItemByProduct(Product product) {
        return cart.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
    }

    public  ArrayList<Product> loadProducts() {
        try {
            return FileHandler.loadProducts();
        } catch (Exception e) {
            showError("Error loading products: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveProducts(ArrayList<Product> products) {
        try {
            FileHandler.saveProducts(products);
        } catch (Exception e) {
            showError("Error saving products: " + e.getMessage());
        }
    }

    public  void logout() {
        dispose();
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }
}

// Helper class untuk item dalam keranjang
class CartItem {
    public Product product;
    public int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public double getTotal() {
        return product.getPrice() * quantity;
    }
}
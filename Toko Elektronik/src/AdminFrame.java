import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AdminFrame extends JFrame {
    public ArrayList<Product> products;
    public JTable productTable;
    public DefaultTableModel tableModel;
    public JTextField idField, nameField, priceField, stockField;
    public JLabel imageLabel;
    public ImageIcon currentImage;

    public AdminFrame() {
        products = loadProducts(); // Load existing products
        initializeUI();
    }

    public void initializeUI() {
        setTitle("Toko Elektronik - Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu accountMenu = new JMenu("Akun");
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> logout());
        accountMenu.add(logoutItem);
        menuBar.add(accountMenu);
        setJMenuBar(menuBar);

        // Panel Input
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Fields initialization
        idField = new JTextField(20);
        nameField = new JTextField(20);
        priceField = new JTextField(20);
        stockField = new JTextField(20);

        // Labels and Fields
        addFormField(inputPanel, "ID:", idField, gbc, 0);
        addFormField(inputPanel, "Nama:", nameField, gbc, 1);
        addFormField(inputPanel, "Harga:", priceField, gbc, 2);
        addFormField(inputPanel, "Stok:", stockField, gbc, 3);

        // Image Panel
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(150, 150));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton browseButton = new JButton("Pilih Gambar");
        browseButton.addActionListener(e -> browseImage());

        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(new JLabel("Gambar:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(imageLabel, gbc);
        gbc.gridy = 5;
        inputPanel.add(browseButton, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addButton(buttonPanel, "Tambah", e -> addProduct());
        addButton(buttonPanel, "Update", e -> updateProduct());
        addButton(buttonPanel, "Hapus", e -> deleteProduct());
        addButton(buttonPanel, "Clear", e -> clearFields());

        // Table
        String[] columns = {"ID", "Nama", "Harga", "Stok"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);

        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = productTable.getSelectedRow();
                if (row != -1) {
                    selectProduct(row);
                }
            }
        });

        // Layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Tambahkan document listener untuk validasi real-time
        priceField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { validatePrice(); }
            public void insertUpdate(DocumentEvent e) { validatePrice(); }
            public void removeUpdate(DocumentEvent e) { validatePrice(); }
        });

        stockField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { validateStock(); }
            public void insertUpdate(DocumentEvent e) { validateStock(); }
            public void removeUpdate(DocumentEvent e) { validateStock(); }
        });

        add(mainPanel);

        // Load initial data
        updateTable();
    }

    public void validatePrice() {
        try {
            String text = priceField.getText();
            if (!text.isEmpty()) {
                double price = Double.parseDouble(text);
                if (price < 0) {
                    priceField.setForeground(Color.RED);
                    // Opsional: Tampilkan pesan error
                    showError("Harga tidak boleh kurang dari 0!");
                } else {
                    priceField.setForeground(Color.BLACK);
                }
            }
        } catch (NumberFormatException ex) {
            priceField.setForeground(Color.RED);
        }
    }

    public void validateStock() {
        try {
            String text = stockField.getText();
            if (!text.isEmpty()) {
                int stock = Integer.parseInt(text);
                if (stock < 0) {
                    stockField.setForeground(Color.RED);
                    // Opsional: Tampilkan pesan error
                    showError("Stok tidak boleh kurang dari 0!");
                } else {
                    stockField.setForeground(Color.BLACK);
                }
            }
        } catch (NumberFormatException ex) {
            stockField.setForeground(Color.RED);
        }
    }
    public void browseImage() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                currentImage = new ImageIcon(chooser.getSelectedFile().getPath());
                Image scaledImage = currentImage.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } catch (Exception ex) {
                showError("Error loading image: " + ex.getMessage());
            }
        }
    }

    public void addFormField(JPanel panel, String label, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    public void addButton(JPanel panel, String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        panel.add(button);
    }

    public void logout() {
        saveProducts(products);
        dispose();
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
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

    public void addProduct() {
        try {
            validateInput();
            Product product = new Product(
                    idField.getText(),
                    nameField.getText(),
                    Double.parseDouble(priceField.getText()),
                    Integer.parseInt(stockField.getText()),
                    currentImage
            );
            products.add(product);
            saveProducts(products);
            updateTable();
            clearFields();
            showSuccess("Product added successfully!");
        } catch (Exception e) {
            showError("Error adding product: " + e.getMessage());
        }
    }

    public void updateProduct() {
        try {
            validateInput();
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow == -1) {
                throw new Exception("Please select a product to update");
            }

            Product product = products.get(selectedRow);
            product.setName(nameField.getText());
            product.setPrice(Double.parseDouble(priceField.getText()));
            product.setStock(Integer.parseInt(stockField.getText()));
            if (currentImage != null) {
                product.setImage(currentImage);
            }

            saveProducts(products);
            updateTable();
            clearFields();
            showSuccess("Product updated successfully!");
        } catch (Exception e) {
            showError("Error updating product: " + e.getMessage());
        }
    }

    public void deleteProduct() {
        try {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow == -1) {
                throw new Exception("Please select a product to delete");
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this product?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                products.remove(selectedRow);
                saveProducts(products);
                updateTable();
                clearFields();
                showSuccess("Product deleted successfully!");
            }
        } catch (Exception e) {
            showError("Error deleting product: " + e.getMessage());
        }
    }

    public void selectProduct(int row) {
        Product product = products.get(row);
        idField.setText(product.getId());
        nameField.setText(product.getName());
        priceField.setText(String.valueOf(product.getPrice()));
        stockField.setText(String.valueOf(product.getStock()));
        if (product.getImage() != null) {
            currentImage = product.getImage();
            imageLabel.setIcon(currentImage);
        }
    }

    public void validateInput() throws Exception {
        if (idField.getText().trim().isEmpty() ||
                nameField.getText().trim().isEmpty() ||
                priceField.getText().trim().isEmpty() ||
                stockField.getText().trim().isEmpty()) {
            throw new Exception("All fields must be filled");
        }

        try {
            Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            throw new Exception("Price must be a valid number");
        }

        try {
            Integer.parseInt(stockField.getText());
        } catch (NumberFormatException e) {
            throw new Exception("Stock must be a valid integer");
        }
    }

    public void updateTable() {
        tableModel.setRowCount(0);
        for (Product product : products) {
            tableModel.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock()
            });
        }
    }

    public void clearFields() {
        idField.setText("");
        nameField.setText("");
        priceField.setText("");
        stockField.setText("");
        imageLabel.setIcon(null);
        currentImage = null;
        productTable.clearSelection();
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
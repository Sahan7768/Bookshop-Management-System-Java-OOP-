import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookshopSystem extends JFrame {
    // Color scheme - Professional and attractive
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);      // Blue
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94);      // Dark Blue
    private static final Color ACCENT_COLOR = new Color(26, 188, 156);       // Teal
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);      // Green
    private static final Color WARNING_COLOR = new Color(230, 126, 34);      // Orange
    private static final Color DANGER_COLOR = new Color(231, 76, 60);        // Red
    private static final Color LIGHT_BG = new Color(236, 240, 241);          // Light Gray
    private static final Color WHITE = Color.WHITE;
    
    private User currentUser;
    private List<Book> books;
    private List<Transaction> transactions;
    private List<CartItem> cart;
    
    private JTabbedPane tabbedPane;
    private DefaultTableModel booksTableModel;
    private DefaultTableModel cartTableModel;
    private DefaultTableModel transactionsTableModel;
    private DefaultTableModel manageBooksTableModel;
    private JTable booksTable;
    private JTable cartTable;
    private JTable transactionsTable;
    private JTable manageBooksTable;
    private JLabel totalLabel;
    private JLabel userInfoLabel;
    
    public BookshopSystem(User user) {
        this.currentUser = user;
        this.books = FileManager.loadBooks();
        this.transactions = FileManager.loadTransactions();
        this.cart = new ArrayList<>();
        
        setTitle("City Bookshop - Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        
        setVisible(true);
    }
    
    private void initComponents() {
        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_BG);
        
        // Header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(WHITE);
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Add tabs based on user role
        tabbedPane.addTab("📚 Browse Books", createBrowseBooksPanel());
        tabbedPane.addTab("🛒 Shopping Cart", createShoppingCartPanel());
        tabbedPane.addTab("📊 Transaction History", createTransactionHistoryPanel());
        
        // Manager-only tabs
        if (currentUser.hasPermission("ADD")) {
            tabbedPane.addTab("📖 Manage Books", createManageBooksPanel());
            tabbedPane.addTab("👥 Manage Accounts", createManageAccountsPanel());
        }
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_COLOR);
        panel.setPreferredSize(new Dimension(0, 80));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Title
        JLabel titleLabel = new JLabel("City Bookshop");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(WHITE);
        
        // User info
        userInfoLabel = new JLabel("👤 " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
        userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userInfoLabel.setForeground(WHITE);
        
        JButton logoutBtn = createStyledButton("Logout", DANGER_COLOR);
        logoutBtn.addActionListener(e -> logout());
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(userInfoLabel);
        rightPanel.add(logoutBtn);
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createBrowseBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel searchLabel = new JLabel("🔍 Search:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JTextField searchField = new JTextField(20);
        styleTextField(searchField);
        
        JComboBox<String> searchTypeCombo = new JComboBox<>(new String[]{
            "All", "Title", "Author", "Category", "Price Range"
        });
        styleComboBox(searchTypeCombo);
        
        JButton searchBtn = createStyledButton("Search", ACCENT_COLOR);
        JButton clearBtn = createStyledButton("Clear", SECONDARY_COLOR);
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Search by:"));
        searchPanel.add(searchTypeCombo);
        searchPanel.add(searchBtn);
        searchPanel.add(clearBtn);
        
        // Books table
        String[] columns = {"Book ID", "Title", "Author", "Category", "Price", "Stock"};
        booksTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        booksTable = new JTable(booksTableModel);
        styleTable(booksTable);
        refreshBooksTable(books);
        
        JScrollPane scrollPane = new JScrollPane(booksTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        
        // Add to cart panel
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        addPanel.setBackground(WHITE);
        addPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        qtySpinner.setPreferredSize(new Dimension(80, 30));
        
        JButton addToCartBtn = createStyledButton("Add to Cart 🛒", SUCCESS_COLOR);
        
        addPanel.add(qtyLabel);
        addPanel.add(qtySpinner);
        addPanel.add(addToCartBtn);
        
        // Search functionality
        searchBtn.addActionListener(e -> {
            String searchText = searchField.getText().trim().toLowerCase();
            String searchType = (String) searchTypeCombo.getSelectedItem();
            
            if (searchText.isEmpty()) {
                refreshBooksTable(books);
                return;
            }
            
            List<Book> filtered = books.stream().filter(book -> {
                switch (searchType) {
                    case "Title":
                        return book.getTitle().toLowerCase().contains(searchText);
                    case "Author":
                        return book.getAuthor().toLowerCase().contains(searchText);
                    case "Category":
                        return book.getCategory().toLowerCase().contains(searchText);
                    case "Price Range":
                        try {
                            double maxPrice = Double.parseDouble(searchText);
                            return book.getPrice() <= maxPrice;
                        } catch (NumberFormatException ex) {
                            return false;
                        }
                    default:
                        return book.getTitle().toLowerCase().contains(searchText) ||
                               book.getAuthor().toLowerCase().contains(searchText) ||
                               book.getCategory().toLowerCase().contains(searchText);
                }
            }).collect(Collectors.toList());
            
            refreshBooksTable(filtered);
        });
        
        clearBtn.addActionListener(e -> {
            searchField.setText("");
            refreshBooksTable(books);
        });
        
        addToCartBtn.addActionListener(e -> {
            int selectedRow = booksTable.getSelectedRow();
            if (selectedRow >= 0) {
                int modelRow = booksTable.convertRowIndexToModel(selectedRow);
                Book book = getBookFromTable(modelRow);
                int qty = (Integer) qtySpinner.getValue();
                addToCart(book, qty);
                qtySpinner.setValue(1);
            } else {
                showMessage("Please select a book!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(addPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createShoppingCartPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Cart table
        String[] columns = {"Book ID", "Title", "Quantity", "Price", "Total"};
        cartTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cartTable = new JTable(cartTableModel);
        styleTable(cartTable);
        
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        
        // Control panel
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        controlPanel.setBackground(WHITE);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Total panel
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 24));
        totalLabel.setForeground(PRIMARY_COLOR);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        
        JButton removeBtn = createStyledButton("Remove Selected", WARNING_COLOR);
        JButton clearBtn = createStyledButton("Clear Cart", DANGER_COLOR);
        JButton checkoutBtn = createStyledButton("💳 Checkout", SUCCESS_COLOR);
        checkoutBtn.setFont(new Font("Arial", Font.BOLD, 16));
        
        buttonPanel.add(removeBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(checkoutBtn);
        
        controlPanel.add(totalLabel, BorderLayout.NORTH);
        controlPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Button actions
        removeBtn.addActionListener(e -> removeFromCart());
        clearBtn.addActionListener(e -> clearCart());
        checkoutBtn.addActionListener(e -> checkout());
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createTransactionHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("📊 Transaction History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(PRIMARY_COLOR);
        
        JButton refreshBtn = createStyledButton("Refresh", ACCENT_COLOR);
        refreshBtn.addActionListener(e -> refreshTransactionTable());
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createHorizontalStrut(20));
        headerPanel.add(refreshBtn);
        
        // Transactions table
        String[] columns = {"Transaction ID", "Book ID", "Title", "Quantity", "Price", "Total", "Cashier", "Date & Time"};
        transactionsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionsTable = new JTable(transactionsTableModel);
        styleTable(transactionsTable);
        refreshTransactionTable();
        
        JScrollPane scrollPane = new JScrollPane(transactionsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createManageBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBackground(WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                "Book Information",
                0, 0,
                new Font("Arial", Font.BOLD, 16),
                PRIMARY_COLOR
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JTextField idField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JComboBox<String> categoryCombo = new JComboBox<>(new String[]{
            "Fiction", "Non-Fiction", "Science", "Technology", "History", 
            "Biography", "Children", "Romance", "Mystery", "Fantasy"
        });
        categoryCombo.setEditable(true);
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();
        
        styleTextField(idField);
        styleTextField(titleField);
        styleTextField(authorField);
        styleComboBox(categoryCombo);
        styleTextField(priceField);
        styleTextField(quantityField);
        
        inputPanel.add(createLabel("Book ID:"));
        inputPanel.add(idField);
        inputPanel.add(createLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(createLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(createLabel("Category:"));
        inputPanel.add(categoryCombo);
        inputPanel.add(createLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(createLabel("Quantity:"));
        inputPanel.add(quantityField);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        
        JButton addBtn = createStyledButton("➕ Add Book", SUCCESS_COLOR);
        JButton updateBtn = createStyledButton("✏️ Update", ACCENT_COLOR);
        JButton deleteBtn = createStyledButton("🗑️ Delete", DANGER_COLOR);
        JButton clearBtn = createStyledButton("Clear", SECONDARY_COLOR);
        
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(clearBtn);
        
        inputPanel.add(new JLabel());
        inputPanel.add(buttonPanel);
        
        // Table
        String[] columns = {"Book ID", "Title", "Author", "Category", "Price", "Quantity"};
        manageBooksTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        manageBooksTable = new JTable(manageBooksTableModel);
        styleTable(manageBooksTable);
        refreshManageBooksTable();
        
        JScrollPane scrollPane = new JScrollPane(manageBooksTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        
        // Button actions
        addBtn.addActionListener(e -> addBook(idField, titleField, authorField, categoryCombo, priceField, quantityField));
        updateBtn.addActionListener(e -> updateBook(idField, titleField, authorField, categoryCombo, priceField, quantityField));
        deleteBtn.addActionListener(e -> deleteBook());
        clearBtn.addActionListener(e -> {
            idField.setText("");
            titleField.setText("");
            authorField.setText("");
            categoryCombo.setSelectedIndex(0);
            priceField.setText("");
            quantityField.setText("");
        });
        
        // Table selection listener
        manageBooksTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = manageBooksTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Book book = books.get(selectedRow);
                    idField.setText(book.getBookId());
                    titleField.setText(book.getTitle());
                    authorField.setText(book.getAuthor());
                    categoryCombo.setSelectedItem(book.getCategory());
                    priceField.setText(String.valueOf(book.getPrice()));
                    quantityField.setText(String.valueOf(book.getQuantity()));
                }
            }
        });
        
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createManageAccountsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBackground(WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                "Create New Account",
                0, 0,
                new Font("Arial", Font.BOLD, 16),
                PRIMARY_COLOR
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Cashier", "Manager"});
        
        styleTextField(usernameField);
        styleTextField(passwordField);
        styleComboBox(roleCombo);
        
        inputPanel.add(createLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(createLabel("Password:"));
        inputPanel.add(passwordField);
        inputPanel.add(createLabel("Role:"));
        inputPanel.add(roleCombo);
        
        JButton createBtn = createStyledButton("➕ Create Account", SUCCESS_COLOR);
        createBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String role = (String) roleCombo.getSelectedItem();
            
            if (username.isEmpty() || password.isEmpty()) {
                showMessage("Username and password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            List<User> users = FileManager.loadUsers();
            
            // Check if username exists
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    showMessage("Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            User newUser = role.equals("Manager") ? new Manager(username, password) : new Cashier(username, password);
            users.add(newUser);
            FileManager.saveUsers(users);
            
            showMessage("Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            usernameField.setText("");
            passwordField.setText("");
            roleCombo.setSelectedIndex(0);
        });
        
        inputPanel.add(new JLabel());
        inputPanel.add(createBtn);
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JTextArea infoText = new JTextArea(
            "Account Management\n\n" +
            "• Cashiers can view and search books\n" +
            "• Cashiers can process sales transactions\n" +
            "• Managers have all cashier permissions\n" +
            "• Managers can add/edit/delete books\n" +
            "• Managers can create new user accounts\n\n" +
            "Default Accounts:\n" +
            "Username: admin | Password: admin123 (Manager)\n" +
            "Username: cashier | Password: cash123 (Cashier)"
        );
        infoText.setFont(new Font("Arial", Font.PLAIN, 14));
        infoText.setEditable(false);
        infoText.setOpaque(false);
        infoPanel.add(infoText);
        
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(infoPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Helper methods
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 35));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private void styleTextField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 30));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
    
    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(new Font("Arial", Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(200, 30));
    }
    
    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setSelectionBackground(ACCENT_COLOR);
        table.setSelectionForeground(WHITE);
        table.setGridColor(new Color(200, 200, 200));
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(WHITE);
        header.setPreferredSize(new Dimension(0, 40));
    }
    
    private void refreshBooksTable(List<Book> bookList) {
        booksTableModel.setRowCount(0);
        for (Book book : bookList) {
            booksTableModel.addRow(new Object[]{
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                String.format("$%.2f", book.getPrice()),
                book.getQuantity()
            });
        }
    }
    
    private void refreshManageBooksTable() {
        manageBooksTableModel.setRowCount(0);
        for (Book book : books) {
            manageBooksTableModel.addRow(new Object[]{
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                String.format("$%.2f", book.getPrice()),
                book.getQuantity()
            });
        }
    }
    
    private void refreshCartTable() {
        cartTableModel.setRowCount(0);
        double total = 0;
        for (CartItem item : cart) {
            cartTableModel.addRow(new Object[]{
                item.getBookId(),
                item.getTitle(),
                item.getQuantity(),
                String.format("$%.2f", item.getPrice()),
                String.format("$%.2f", item.getTotal())
            });
            total += item.getTotal();
        }
        totalLabel.setText(String.format("Total: $%.2f", total));
    }
    
    private void refreshTransactionTable() {
        transactionsTableModel.setRowCount(0);
        transactions = FileManager.loadTransactions();
        for (Transaction trans : transactions) {
            transactionsTableModel.addRow(new Object[]{
                trans.getTransactionId(),
                trans.getBookId(),
                trans.getBookTitle(),
                trans.getQuantity(),
                String.format("$%.2f", trans.getPrice()),
                String.format("$%.2f", trans.getTotal()),
                trans.getCashier(),
                trans.getDateTime()
            });
        }
    }
    
    private Book getBookFromTable(int row) {
        String bookId = (String) booksTableModel.getValueAt(row, 0);
        for (Book book : books) {
            if (book.getBookId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }
    
    private void addToCart(Book book, int quantity) {
        if (book.getQuantity() < quantity) {
            showMessage("Insufficient stock! Available: " + book.getQuantity(), "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Check if book already in cart
        boolean found = false;
        for (CartItem item : cart) {
            if (item.getBookId().equals(book.getBookId())) {
                int newQty = item.getQuantity() + quantity;
                if (newQty > book.getQuantity()) {
                    showMessage("Insufficient stock! Available: " + book.getQuantity(), "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                item.setQuantity(newQty);
                found = true;
                break;
            }
        }
        
        if (!found) {
            cart.add(new CartItem(book.getBookId(), book.getTitle(), quantity, book.getPrice()));
        }
        
        refreshCartTable();
        showMessage("Book added to cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void removeFromCart() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow >= 0) {
            cart.remove(selectedRow);
            refreshCartTable();
        } else {
            showMessage("Please select an item to remove!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void clearCart() {
        if (cart.isEmpty()) {
            showMessage("Cart is already empty!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to clear the cart?", 
            "Confirm", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            cart.clear();
            refreshCartTable();
        }
    }
    
    private void checkout() {
        if (cart.isEmpty()) {
            showMessage("Cart is empty!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String transactionId = "TXN" + System.currentTimeMillis();
        
        // Process each cart item
        for (CartItem item : cart) {
            // Update book quantity
            for (Book book : books) {
                if (book.getBookId().equals(item.getBookId())) {
                    book.setQuantity(book.getQuantity() - item.getQuantity());
                    break;
                }
            }
            
            // Create transaction
            Transaction transaction = new Transaction(
                transactionId,
                item.getBookId(),
                item.getTitle(),
                item.getQuantity(),
                item.getPrice(),
                currentUser.getUsername()
            );
            transactions.add(transaction);
            FileManager.appendTransaction(transaction);
        }
        
        // Save updated books
        FileManager.saveBooks(books);
        
        double total = cart.stream().mapToDouble(CartItem::getTotal).sum();
        showMessage(
            String.format("Transaction completed!\n\nTransaction ID: %s\nTotal: $%.2f\n\nThank you for your purchase!", 
            transactionId, total),
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        clearCart();
        refreshBooksTable(books);
        refreshManageBooksTable();
        refreshTransactionTable();
    }
    
    private void addBook(JTextField idField, JTextField titleField, JTextField authorField, 
                        JComboBox<String> categoryCombo, JTextField priceField, JTextField quantityField) {
        try {
            String id = idField.getText().trim();
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String category = (String) categoryCombo.getSelectedItem();
            double price = Double.parseDouble(priceField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());
            
            if (id.isEmpty() || title.isEmpty() || author.isEmpty()) {
                showMessage("Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if ID exists
            for (Book book : books) {
                if (book.getBookId().equals(id)) {
                    showMessage("Book ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            Book book = new Book(id, title, author, category, price, quantity);
            books.add(book);
            FileManager.saveBooks(books);
            
            refreshManageBooksTable();
            refreshBooksTable(books);
            showMessage("Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear fields
            idField.setText("");
            titleField.setText("");
            authorField.setText("");
            priceField.setText("");
            quantityField.setText("");
            
        } catch (NumberFormatException e) {
            showMessage("Invalid price or quantity!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateBook(JTextField idField, JTextField titleField, JTextField authorField, 
                           JComboBox<String> categoryCombo, JTextField priceField, JTextField quantityField) {
        int selectedRow = manageBooksTable.getSelectedRow();
        if (selectedRow < 0) {
            showMessage("Please select a book to update!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            String id = idField.getText().trim();
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String category = (String) categoryCombo.getSelectedItem();
            double price = Double.parseDouble(priceField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());
            
            Book book = books.get(selectedRow);
            book.setBookId(id);
            book.setTitle(title);
            book.setAuthor(author);
            book.setCategory(category);
            book.setPrice(price);
            book.setQuantity(quantity);
            
            FileManager.saveBooks(books);
            refreshManageBooksTable();
            refreshBooksTable(books);
            showMessage("Book updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            showMessage("Invalid price or quantity!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteBook() {
        int selectedRow = manageBooksTable.getSelectedRow();
        if (selectedRow < 0) {
            showMessage("Please select a book to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this book?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            books.remove(selectedRow);
            FileManager.saveBooks(books);
            refreshManageBooksTable();
            refreshBooksTable(books);
            showMessage("Book deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginSystem();
        }
    }
    
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}

// CartItem class
class CartItem {
    private String bookId;
    private String title;
    private int quantity;
    private double price;
    
    public CartItem(String bookId, String title, int quantity, double price) {
        this.bookId = bookId;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
    public double getTotal() {
        return quantity * price;
    }
}

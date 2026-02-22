import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import java.util.List;

// ============================================================
// ABSTRACT BASE CLASS - Demonstrates ABSTRACTION
// ============================================================
abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String fullName;

    public Person(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public abstract String getRole();

    @Override
    public String toString() {
        return fullName + " (" + getRole() + ")";
    }

    public String displayInfo() {
        return "Name: " + fullName + " | Role: " + getRole();
    }
}

// ============================================================
// INHERITANCE - Cashier extends Person
// ============================================================
class Cashier extends Person {
    private static final long serialVersionUID = 1L;

    public Cashier(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    @Override
    public String getRole() {
        return "Cashier";
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() + " | Access: View & Search Books";
    }
}

// ============================================================
// INHERITANCE - Manager extends Person
// ============================================================
class Manager extends Person {
    private static final long serialVersionUID = 1L;

    public Manager(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    @Override
    public String getRole() {
        return "Manager";
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() + " | Access: Full Control";
    }
}

// ============================================================
// ENCAPSULATION - Book class with private fields
// ============================================================
class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bookId;
    private String title;
    private String author;
    private String category;
    private double price;
    private int stockQuantity;

    public Book(String bookId, String title, String author, String category, double price, int stockQuantity) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public String toFileString() {
        return bookId + "," + title + "," + author + "," + category + "," + price + "," + stockQuantity;
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }
}

// ============================================================
// ENCAPSULATION - Category class
// ============================================================
class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private String categoryId;
    private String categoryName;
    private String description;

    public Category(String categoryId, String categoryName, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
    }

    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String toFileString() {
        return categoryId + "," + categoryName + "," + description;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}

// ============================================================
// INTERFACE - Demonstrates ABSTRACTION
// ============================================================
interface FileOperations {
    void saveToFile();
    void loadFromFile();
}

// ============================================================
// Data Manager - Implements Interface (ABSTRACTION)
// ============================================================
class DataManager implements FileOperations {
    private List<Person> users;
    private List<Book> books;
    private List<Category> categories;
    private static final String USERS_FILE = "users.csv";
    private static final String BOOKS_FILE = "books.csv";
    private static final String CATEGORIES_FILE = "categories.csv";

    public DataManager() {
        users = new ArrayList<>();
        books = new ArrayList<>();
        categories = new ArrayList<>();
        loadFromFile();
        if (users.isEmpty()) {
            initializeDefaultData();
            saveToFile();
        }
    }

    private void initializeDefaultData() {
        users.add(new Manager("manager", "manager123", "John Smith"));
        users.add(new Cashier("cashier", "cashier123", "Jane Doe"));

        categories.add(new Category("CAT001", "Fiction", "Fictional novels and stories"));
        categories.add(new Category("CAT002", "Non-Fiction", "Non-fictional books and biographies"));
        categories.add(new Category("CAT003", "Science", "Science and technology books"));
        categories.add(new Category("CAT004", "History", "Historical books and references"));
        categories.add(new Category("CAT005", "Children", "Books for children"));

        books.add(new Book("BK001", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 1500.00, 25));
        books.add(new Book("BK002", "To Kill a Mockingbird", "Harper Lee", "Fiction", 1200.00, 30));
        books.add(new Book("BK003", "A Brief History of Time", "Stephen Hawking", "Science", 2000.00, 15));
        books.add(new Book("BK004", "Sapiens", "Yuval Noah Harari", "History", 1800.00, 20));
        books.add(new Book("BK005", "The Cat in the Hat", "Dr. Seuss", "Children", 800.00, 40));
        books.add(new Book("BK006", "1984", "George Orwell", "Fiction", 1100.00, 35));
        books.add(new Book("BK007", "Becoming", "Michelle Obama", "Non-Fiction", 2200.00, 18));
        books.add(new Book("BK008", "Cosmos", "Carl Sagan", "Science", 1900.00, 12));
        books.add(new Book("BK009", "The Diary of a Young Girl", "Anne Frank", "History", 950.00, 28));
        books.add(new Book("BK010", "Charlotte's Web", "E.B. White", "Children", 750.00, 45));
    }

    @Override
    public void saveToFile() {
        saveUsersToFile();
        saveBooksToFile();
        saveCategoriesToFile();
    }

    @Override
    public void loadFromFile() {
        loadUsersFromFile();
        loadBooksFromFile();
        loadCategoriesFromFile();
    }

    private void saveUsersToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            writer.println("Role,Username,Password,FullName");
            for (Person user : users) {
                writer.println(user.getRole() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getFullName());
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private void loadUsersFromFile() {
        File file = new File(USERS_FILE);
        if (!file.exists()) return;
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine(); // skip CSV header
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", 4);
                if (parts.length == 4) {
                    if (parts[0].equals("Manager")) {
                        users.add(new Manager(parts[1], parts[2], parts[3]));
                    } else {
                        users.add(new Cashier(parts[1], parts[2], parts[3]));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    private void saveBooksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            writer.println("BookID,Title,Author,Category,Price,StockQuantity");
            for (Book book : books) {
                writer.println(book.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    private void loadBooksFromFile() {
        File file = new File(BOOKS_FILE);
        if (!file.exists()) return;
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine(); // skip CSV header
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", 6);
                if (parts.length == 6) {
                    books.add(new Book(parts[0], parts[1], parts[2], parts[3],
                            Double.parseDouble(parts[4]), Integer.parseInt(parts[5])));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }

    private void saveCategoriesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CATEGORIES_FILE))) {
            writer.println("CategoryID,CategoryName,Description");
            for (Category cat : categories) {
                writer.println(cat.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving categories: " + e.getMessage());
        }
    }

    private void loadCategoriesFromFile() {
        File file = new File(CATEGORIES_FILE);
        if (!file.exists()) return;
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine(); // skip CSV header
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    categories.add(new Category(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading categories: " + e.getMessage());
        }
    }

    public Person authenticate(String username, String password) {
        for (Person user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean addUser(Person user) {
        for (Person existingUser : users) {
            if (existingUser.getUsername().equals(user.getUsername())) {
                return false;
            }
        }
        users.add(user);
        saveToFile();
        return true;
    }

    public void addBook(Book book) {
        books.add(book);
        saveToFile();
    }

    public void addCategory(Category category) {
        categories.add(category);
        saveToFile();
    }

    public List<Book> getAllBooks() { return books; }
    public List<Category> getAllCategories() { return categories; }
    public List<Person> getAllUsers() { return users; }

    public List<Book> searchByCategory(String category) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getCategory().toLowerCase().contains(category.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByName(String name) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(name.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByPrice(double maxPrice) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getPrice() <= maxPrice) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByStock(int minStock) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getStockQuantity() >= minStock) {
                result.add(book);
            }
        }
        return result;
    }

    public String generateBookId() {
        int max = 0;
        for (Book book : books) {
            String num = book.getBookId().replace("BK", "");
            try {
                int n = Integer.parseInt(num);
                if (n > max) max = n;
            } catch (NumberFormatException e) { }
        }
        return String.format("BK%03d", max + 1);
    }

    public String generateCategoryId() {
        int max = 0;
        for (Category cat : categories) {
            String num = cat.getCategoryId().replace("CAT", "");
            try {
                int n = Integer.parseInt(num);
                if (n > max) max = n;
            } catch (NumberFormatException e) { }
        }
        return String.format("CAT%03d", max + 1);
    }
}

// ============================================================
// Custom Rounded Button
// ============================================================
class RoundedButton extends JButton {
    private Color backgroundColor;
    private Color hoverColor;
    private Color pressColor;
    private boolean isHovered = false;
    private boolean isPressed = false;
    private int radius = 15;

    public RoundedButton(String text, Color bgColor) {
        super(text);
        this.backgroundColor = bgColor;
        this.hoverColor = bgColor.brighter();
        this.pressColor = bgColor.darker();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(180, 45));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
            @Override
            public void mouseExited(MouseEvent e) { isHovered = false; isPressed = false; repaint(); }
            @Override
            public void mousePressed(MouseEvent e) { isPressed = true; repaint(); }
            @Override
            public void mouseReleased(MouseEvent e) { isPressed = false; repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color currentColor = backgroundColor;
        if (isPressed) currentColor = pressColor;
        else if (isHovered) currentColor = hoverColor;

        g2.setColor(currentColor);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));

        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(getText(), x, y);
        g2.dispose();
    }
}

// ============================================================
// Custom Rounded Panel
// ============================================================
class RoundedPanel extends JPanel {
    private int radius;
    private Color bgColor;

    public RoundedPanel(int radius, Color bgColor) {
        this.radius = radius;
        this.bgColor = bgColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bgColor);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
        g2.dispose();
        super.paintComponent(g);
    }
}

// ============================================================
// MAIN APPLICATION CLASS
// ============================================================
public class CityBookshop extends JFrame {

    private static final Color PRIMARY_DARK = new Color(27, 38, 59);
    private static final Color PRIMARY_MID = new Color(65, 90, 119);
    private static final Color PRIMARY_LIGHT = new Color(119, 141, 169);
    private static final Color ACCENT_BLUE = new Color(52, 152, 219);
    private static final Color ACCENT_GREEN = new Color(39, 174, 96);
    private static final Color ACCENT_ORANGE = new Color(243, 156, 18);
    private static final Color ACCENT_RED = new Color(231, 76, 60);
    private static final Color ACCENT_PURPLE = new Color(142, 68, 173);
    private static final Color BG_WHITE = new Color(236, 240, 241);
    private static final Color CARD_WHITE = new Color(255, 255, 255);
    private static final Color TEXT_DARK = new Color(44, 62, 80);
    private static final Color TEXT_LIGHT = new Color(149, 165, 166);
    private static final Color SIDEBAR_COLOR = new Color(30, 39, 46);
    private static final Color SIDEBAR_HOVER = new Color(47, 54, 64);
    private static final Color TABLE_HEADER_BG = new Color(41, 128, 185);
    private static final Color TABLE_HEADER_FG = Color.WHITE;

    private DataManager dataManager;
    private Person currentUser;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public CityBookshop() {
        dataManager = new DataManager();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("City Bookshop Management System");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1100, 700));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(createLoginPanel(), "LOGIN");
        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
        setVisible(true);
    }

    // ============================================================
    // LOGIN PANEL
    // ============================================================
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_DARK);

        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(52, 152, 219),
                        0, getHeight(), new Color(142, 68, 173));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(new Color(255, 255, 255, 40));
                g2.fillOval(-50, -50, 300, 300);
                g2.fillOval(100, getHeight() - 200, 250, 250);

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 42));
                FontMetrics fm = g2.getFontMetrics();
                String title = "City Bookshop";
                int x = (getWidth() - fm.stringWidth(title)) / 2;
                g2.drawString(title, x, getHeight() / 2 - 40);

                g2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                fm = g2.getFontMetrics();
                String sub = "Management System";
                x = (getWidth() - fm.stringWidth(sub)) / 2;
                g2.drawString(sub, x, getHeight() / 2);

                g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                fm = g2.getFontMetrics();
                String tagline = "Automate your bookshop transactions";
                x = (getWidth() - fm.stringWidth(tagline)) / 2;
                g2.drawString(tagline, x, getHeight() / 2 + 40);
            }
        };
        leftPanel.setPreferredSize(new Dimension(450, 0));

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(CARD_WHITE);

        RoundedPanel loginCard = new RoundedPanel(25, CARD_WHITE);
        loginCard.setLayout(new BoxLayout(loginCard, BoxLayout.Y_AXIS));
        loginCard.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        loginCard.setPreferredSize(new Dimension(400, 520));

        JLabel loginTitle = new JLabel("Welcome Back!");
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        loginTitle.setForeground(TEXT_DARK);
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginCard.add(loginTitle);
        loginCard.add(Box.createVerticalStrut(5));

        JLabel loginSubtitle = new JLabel("Sign in to your account");
        loginSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginSubtitle.setForeground(TEXT_LIGHT);
        loginSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginCard.add(loginSubtitle);
        loginCard.add(Box.createVerticalStrut(30));

        JLabel userLabel = new JLabel("  Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setForeground(TEXT_DARK);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setMaximumSize(new Dimension(300, 20));
        loginCard.add(userLabel);
        loginCard.add(Box.createVerticalStrut(5));

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setMaximumSize(new Dimension(300, 42));
        usernameField.setPreferredSize(new Dimension(300, 42));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        loginCard.add(usernameField);
        loginCard.add(Box.createVerticalStrut(15));

        JLabel passLabel = new JLabel("  Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setForeground(TEXT_DARK);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setMaximumSize(new Dimension(300, 20));
        loginCard.add(passLabel);
        loginCard.add(Box.createVerticalStrut(5));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(300, 42));
        passwordField.setPreferredSize(new Dimension(300, 42));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        loginCard.add(passwordField);
        loginCard.add(Box.createVerticalStrut(25));

        RoundedButton loginBtn = new RoundedButton("Sign In", ACCENT_BLUE);
        loginBtn.setMaximumSize(new Dimension(300, 45));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginCard.add(loginBtn);
        loginCard.add(Box.createVerticalStrut(25));

        JPanel demoPanel = new RoundedPanel(15, new Color(232, 245, 233));
        demoPanel.setLayout(new BoxLayout(demoPanel, BoxLayout.Y_AXIS));
        demoPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        demoPanel.setMaximumSize(new Dimension(300, 140));
        demoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel demoTitle = new JLabel("Demo Credentials");
        demoTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        demoTitle.setForeground(new Color(27, 94, 32));
        demoTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        demoPanel.add(demoTitle);
        demoPanel.add(Box.createVerticalStrut(8));

        JLabel managerCred = new JLabel("Manager  :  manager / manager123");
        managerCred.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        managerCred.setForeground(new Color(56, 142, 60));
        managerCred.setAlignmentX(Component.LEFT_ALIGNMENT);
        demoPanel.add(managerCred);
        demoPanel.add(Box.createVerticalStrut(4));

        JLabel cashierCred = new JLabel("Cashier    :  cashier / cashier123");
        cashierCred.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        cashierCred.setForeground(new Color(56, 142, 60));
        cashierCred.setAlignmentX(Component.LEFT_ALIGNMENT);
        demoPanel.add(cashierCred);

        loginCard.add(demoPanel);

        rightPanel.add(loginCard);

        ActionListener loginAction = e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                showStyledMessage("Please enter both username and password.", "Input Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Person user = dataManager.authenticate(username, password);
            if (user != null) {
                currentUser = user;
                mainPanel.add(createDashboardPanel(), "DASHBOARD");
                cardLayout.show(mainPanel, "DASHBOARD");
                usernameField.setText("");
                passwordField.setText("");
            } else {
                showStyledMessage("Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        };

        loginBtn.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);
        usernameField.addActionListener(loginAction);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.CENTER);

        return panel;
    }

    // ============================================================
    // DASHBOARD PANEL
    // ============================================================
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel sidebar = createSidebar();
        panel.add(sidebar, BorderLayout.WEST);

        CardLayout contentLayout = new CardLayout();
        JPanel contentPanel = new JPanel(contentLayout);
        contentPanel.setBackground(BG_WHITE);

        contentPanel.add(createHomeContent(), "HOME");
        contentPanel.add(createViewBooksContent(), "VIEW_BOOKS");
        contentPanel.add(createSearchBooksContent(), "SEARCH_BOOKS");

        if (currentUser instanceof Manager) {
            contentPanel.add(createAddBookContent(), "ADD_BOOK");
            contentPanel.add(createAddCategoryContent(), "ADD_CATEGORY");
            contentPanel.add(createCreateAccountContent(), "CREATE_ACCOUNT");
        }

        contentLayout.show(contentPanel, "HOME");
        panel.add(contentPanel, BorderLayout.CENTER);

        panel.putClientProperty("contentPanel", contentPanel);
        panel.putClientProperty("contentLayout", contentLayout);

        return panel;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(new Color(22, 29, 38));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));
        profilePanel.setMaximumSize(new Dimension(250, 130));

        JLabel shopLabel = new JLabel("City Bookshop");
        shopLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        shopLabel.setForeground(Color.WHITE);
        shopLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        profilePanel.add(shopLabel);
        profilePanel.add(Box.createVerticalStrut(15));

        JLabel nameLabel = new JLabel(currentUser.getFullName());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameLabel.setForeground(new Color(200, 200, 200));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        profilePanel.add(nameLabel);
        profilePanel.add(Box.createVerticalStrut(5));

        JLabel roleLabel = new JLabel("Role: " + currentUser.getRole());
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        roleLabel.setForeground(ACCENT_BLUE);
        roleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        profilePanel.add(roleLabel);

        sidebar.add(profilePanel);
        sidebar.add(Box.createVerticalStrut(10));

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(250, 1));
        sep.setForeground(new Color(60, 70, 80));
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(10));

        JLabel menuLabel = new JLabel("   MENU");
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        menuLabel.setForeground(new Color(120, 130, 140));
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(menuLabel);
        sidebar.add(Box.createVerticalStrut(5));

        addSidebarButton(sidebar, "Dashboard", "HOME");
        addSidebarButton(sidebar, "View All Books", "VIEW_BOOKS");
        addSidebarButton(sidebar, "Search Books", "SEARCH_BOOKS");

        if (currentUser instanceof Manager) {
            sidebar.add(Box.createVerticalStrut(10));
            JSeparator sep2 = new JSeparator();
            sep2.setMaximumSize(new Dimension(250, 1));
            sep2.setForeground(new Color(60, 70, 80));
            sidebar.add(sep2);
            sidebar.add(Box.createVerticalStrut(10));

            JLabel mgmtLabel = new JLabel("   MANAGEMENT");
            mgmtLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
            mgmtLabel.setForeground(new Color(120, 130, 140));
            mgmtLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            sidebar.add(mgmtLabel);
            sidebar.add(Box.createVerticalStrut(5));

            addSidebarButton(sidebar, "Add New Book", "ADD_BOOK");
            addSidebarButton(sidebar, "Add Category", "ADD_CATEGORY");
            addSidebarButton(sidebar, "Create Account", "CREATE_ACCOUNT");
        }

        sidebar.add(Box.createVerticalGlue());

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutBtn.setForeground(ACCENT_RED);
        logoutBtn.setBackground(SIDEBAR_COLOR);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setHorizontalAlignment(SwingConstants.LEFT);
        logoutBtn.setMaximumSize(new Dimension(250, 50));
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 10));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { logoutBtn.setBackground(SIDEBAR_HOVER); logoutBtn.setContentAreaFilled(true); }
            public void mouseExited(MouseEvent e) { logoutBtn.setContentAreaFilled(false); }
        });
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "LOGIN");
        });
        sidebar.add(logoutBtn);
        sidebar.add(Box.createVerticalStrut(20));

        return sidebar;
    }

    private void addSidebarButton(JPanel sidebar, String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(new Color(200, 200, 200));
        btn.setBackground(SIDEBAR_COLOR);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(250, 45));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(SIDEBAR_HOVER);
                btn.setContentAreaFilled(true);
                btn.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                btn.setContentAreaFilled(false);
                btn.setForeground(new Color(200, 200, 200));
            }
        });

        btn.addActionListener(e -> {
            Container parent = sidebar.getParent();
            if (parent instanceof JPanel) {
                JPanel contentPanel = (JPanel) ((JPanel) parent).getClientProperty("contentPanel");
                CardLayout cl = (CardLayout) ((JPanel) parent).getClientProperty("contentLayout");
                if (contentPanel != null && cl != null) {
                    if (cardName.equals("VIEW_BOOKS")) {
                        contentPanel.add(createViewBooksContent(), "VIEW_BOOKS");
                    } else if (cardName.equals("SEARCH_BOOKS")) {
                        contentPanel.add(createSearchBooksContent(), "SEARCH_BOOKS");
                    } else if (cardName.equals("HOME")) {
                        contentPanel.add(createHomeContent(), "HOME");
                    }
                    cl.show(contentPanel, cardName);
                }
            }
        });

        sidebar.add(btn);
    }

    // ============================================================
    // HOME CONTENT
    // ============================================================
    private JPanel createHomeContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_WHITE);

        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getFullName() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcomeLabel.setForeground(TEXT_DARK);
        headerPanel.add(welcomeLabel, BorderLayout.NORTH);

        JLabel subtitleLabel = new JLabel("Here's your bookshop overview");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_LIGHT);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 20, 0));
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);

        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setBackground(BG_WHITE);
        statsPanel.setPreferredSize(new Dimension(0, 140));

        int totalBooks = dataManager.getAllBooks().size();
        int totalStock = 0;
        int totalCategories = dataManager.getAllCategories().size();
        int totalUsers = dataManager.getAllUsers().size();
        for (Book b : dataManager.getAllBooks()) totalStock += b.getStockQuantity();

        statsPanel.add(createStatCard("Total Books", String.valueOf(totalBooks), ACCENT_BLUE));
        statsPanel.add(createStatCard("Total Stock", String.valueOf(totalStock), ACCENT_GREEN));
        statsPanel.add(createStatCard("Categories", String.valueOf(totalCategories), ACCENT_ORANGE));
        statsPanel.add(createStatCard("Users", String.valueOf(totalUsers), ACCENT_PURPLE));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BG_WHITE);
        centerPanel.add(statsPanel, BorderLayout.NORTH);

        RoundedPanel recentPanel = new RoundedPanel(15, CARD_WHITE);
        recentPanel.setLayout(new BorderLayout());
        recentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel recentTitle = new JLabel("Recent Books in Stock");
        recentTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        recentTitle.setForeground(TEXT_DARK);
        recentTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        recentPanel.add(recentTitle, BorderLayout.NORTH);

        JTable recentTable = createStyledTable(getBookTableData(dataManager.getAllBooks()));
        JScrollPane scrollPane = new JScrollPane(recentTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(CARD_WHITE);
        recentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(BG_WHITE);
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        tableWrapper.add(recentPanel, BorderLayout.CENTER);

        centerPanel.add(tableWrapper, BorderLayout.CENTER);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatCard(String title, String value, Color color) {
        RoundedPanel card = new RoundedPanel(15, CARD_WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setForeground(TEXT_LIGHT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(valueLabel);

        JPanel indicator = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), 4, 4, 4);
            }
        };
        indicator.setOpaque(false);
        indicator.setPreferredSize(new Dimension(0, 8));

        card.add(indicator, BorderLayout.NORTH);
        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }

    // ============================================================
    // VIEW BOOKS CONTENT
    // ============================================================
    private JPanel createViewBooksContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("All Books");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(TEXT_DARK);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        RoundedPanel tablePanel = new RoundedPanel(15, CARD_WHITE);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTable table = createStyledTable(getBookTableData(dataManager.getAllBooks()));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(CARD_WHITE);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        summaryPanel.setOpaque(false);
        int totalBooks = dataManager.getAllBooks().size();
        int totalStock = 0;
        for (Book b : dataManager.getAllBooks()) totalStock += b.getStockQuantity();

        JLabel summaryLabel = new JLabel("Total: " + totalBooks + " books | " + totalStock + " items in stock");
        summaryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        summaryLabel.setForeground(ACCENT_BLUE);
        summaryPanel.add(summaryLabel);
        tablePanel.add(summaryPanel, BorderLayout.SOUTH);

        panel.add(tablePanel, BorderLayout.CENTER);
        return panel;
    }

    // ============================================================
    // SEARCH BOOKS CONTENT
    // ============================================================
    private JPanel createSearchBooksContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Search Books");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(TEXT_DARK);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        RoundedPanel searchPanel = new RoundedPanel(15, CARD_WHITE);
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        controlsPanel.setOpaque(false);

        JLabel searchTypeLabel = new JLabel("Search By:");
        searchTypeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchTypeLabel.setForeground(TEXT_DARK);
        controlsPanel.add(searchTypeLabel);

        String[] searchOptions = {"Book Name", "Category", "Max Price", "Min Stock"};
        JComboBox<String> searchTypeCombo = new JComboBox<>(searchOptions);
        searchTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchTypeCombo.setPreferredSize(new Dimension(150, 38));
        searchTypeCombo.setBackground(CARD_WHITE);
        controlsPanel.add(searchTypeCombo);

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(250, 38));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        controlsPanel.add(searchField);

        RoundedButton searchBtn = new RoundedButton("Search", ACCENT_BLUE);
        searchBtn.setPreferredSize(new Dimension(130, 38));
        controlsPanel.add(searchBtn);

        RoundedButton resetBtn = new RoundedButton("Reset", TEXT_LIGHT);
        resetBtn.setPreferredSize(new Dimension(120, 38));
        controlsPanel.add(resetBtn);

        searchPanel.add(controlsPanel, BorderLayout.NORTH);

        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setOpaque(false);
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JLabel resultsLabel = new JLabel("Search Results:");
        resultsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resultsLabel.setForeground(TEXT_DARK);
        resultsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        resultsPanel.add(resultsLabel, BorderLayout.NORTH);

        JTable resultsTable = createStyledTable(getBookTableData(dataManager.getAllBooks()));
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(CARD_WHITE);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);

        JLabel countLabel = new JLabel("Showing " + dataManager.getAllBooks().size() + " results");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        countLabel.setForeground(TEXT_LIGHT);
        countLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        resultsPanel.add(countLabel, BorderLayout.SOUTH);

        searchPanel.add(resultsPanel, BorderLayout.CENTER);

        searchBtn.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                showStyledMessage("Please enter a search term.", "Search", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<Book> results;
            String type = (String) searchTypeCombo.getSelectedItem();

            try {
                switch (type) {
                    case "Category":
                        results = dataManager.searchByCategory(query);
                        break;
                    case "Max Price":
                        results = dataManager.searchByPrice(Double.parseDouble(query));
                        break;
                    case "Min Stock":
                        results = dataManager.searchByStock(Integer.parseInt(query));
                        break;
                    default:
                        results = dataManager.searchByName(query);
                        break;
                }

                JTable newTable = createStyledTable(getBookTableData(results));
                scrollPane.setViewportView(newTable);
                countLabel.setText("Showing " + results.size() + " results for \"" + query + "\"");
                resultsLabel.setText("Search Results - " + type + ": " + query);

            } catch (NumberFormatException ex) {
                showStyledMessage("Please enter a valid number for price or stock search.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        resetBtn.addActionListener(e -> {
            searchField.setText("");
            JTable newTable = createStyledTable(getBookTableData(dataManager.getAllBooks()));
            scrollPane.setViewportView(newTable);
            countLabel.setText("Showing " + dataManager.getAllBooks().size() + " results");
            resultsLabel.setText("Search Results:");
        });

        searchField.addActionListener(e -> searchBtn.doClick());

        panel.add(searchPanel, BorderLayout.CENTER);
        return panel;
    }

    // ============================================================
    // ADD BOOK CONTENT (Manager Only)
    // ============================================================
    private JPanel createAddBookContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Add New Book");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(TEXT_DARK);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        RoundedPanel formPanel = new RoundedPanel(15, CARD_WHITE);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField bookIdField = createStyledTextField();
        bookIdField.setText(dataManager.generateBookId());
        bookIdField.setEditable(false);
        bookIdField.setBackground(new Color(240, 240, 240));

        JTextField titleField = createStyledTextField();
        JTextField authorField = createStyledTextField();

        JComboBox<String> categoryCombo = new JComboBox<>();
        categoryCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        categoryCombo.setPreferredSize(new Dimension(300, 38));
        for (Category cat : dataManager.getAllCategories()) {
            categoryCombo.addItem(cat.getCategoryName());
        }

        JTextField priceField = createStyledTextField();
        JTextField stockField = createStyledTextField();

        addFormRow(formPanel, gbc, 0, "Book ID:", bookIdField);
        addFormRow(formPanel, gbc, 1, "Book Title:", titleField);
        addFormRow(formPanel, gbc, 2, "Author:", authorField);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.weightx = 0;
        JLabel catLabel = new JLabel("Category:");
        catLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        catLabel.setForeground(TEXT_DARK);
        formPanel.add(catLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(categoryCombo, gbc);

        addFormRow(formPanel, gbc, 4, "Price (Rs.):", priceField);
        addFormRow(formPanel, gbc, 5, "Stock Quantity:", stockField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        RoundedButton addBtn = new RoundedButton("Add Book", ACCENT_GREEN);
        RoundedButton clearBtn = new RoundedButton("Clear", TEXT_LIGHT);

        buttonPanel.add(addBtn);
        buttonPanel.add(clearBtn);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        formPanel.add(buttonPanel, gbc);

        addBtn.addActionListener(e -> {
            try {
                String bookTitle = titleField.getText().trim();
                String author = authorField.getText().trim();
                String category = (String) categoryCombo.getSelectedItem();
                String priceText = priceField.getText().trim();
                String stockText = stockField.getText().trim();

                if (bookTitle.isEmpty() || author.isEmpty() || priceText.isEmpty() || stockText.isEmpty()) {
                    showStyledMessage("Please fill all fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double price = Double.parseDouble(priceText);
                int stock = Integer.parseInt(stockText);

                if (price <= 0 || stock < 0) {
                    showStyledMessage("Price must be positive and stock cannot be negative!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Book book = new Book(bookIdField.getText(), bookTitle, author, category, price, stock);
                dataManager.addBook(book);

                showStyledMessage("Book \"" + bookTitle + "\" added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                bookIdField.setText(dataManager.generateBookId());
                titleField.setText("");
                authorField.setText("");
                priceField.setText("");
                stockField.setText("");

            } catch (NumberFormatException ex) {
                showStyledMessage("Please enter valid numbers for Price and Stock!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearBtn.addActionListener(e -> {
            titleField.setText("");
            authorField.setText("");
            priceField.setText("");
            stockField.setText("");
            bookIdField.setText(dataManager.generateBookId());
        });

        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.setBackground(BG_WHITE);
        centerWrapper.add(formPanel);
        panel.add(centerWrapper, BorderLayout.CENTER);

        return panel;
    }

    // ============================================================
    // ADD CATEGORY CONTENT (Manager Only)
    // ============================================================
    private JPanel createAddCategoryContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Add New Category");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(TEXT_DARK);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        JPanel contentWrapper = new JPanel(new GridLayout(1, 2, 20, 0));
        contentWrapper.setBackground(BG_WHITE);

        RoundedPanel formPanel = new RoundedPanel(15, CARD_WHITE);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField catIdField = createStyledTextField();
        catIdField.setText(dataManager.generateCategoryId());
        catIdField.setEditable(false);
        catIdField.setBackground(new Color(240, 240, 240));

        JTextField catNameField = createStyledTextField();
        JTextField descField = createStyledTextField();

        addFormRow(formPanel, gbc, 0, "Category ID:", catIdField);
        addFormRow(formPanel, gbc, 1, "Category Name:", catNameField);
        addFormRow(formPanel, gbc, 2, "Description:", descField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        RoundedButton addBtn = new RoundedButton("Add Category", ACCENT_ORANGE);
        RoundedButton clearBtn = new RoundedButton("Clear", TEXT_LIGHT);
        buttonPanel.add(addBtn);
        buttonPanel.add(clearBtn);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        formPanel.add(buttonPanel, gbc);

        RoundedPanel listPanel = new RoundedPanel(15, CARD_WHITE);
        listPanel.setLayout(new BorderLayout());
        listPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel listTitle = new JLabel("Existing Categories");
        listTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        listTitle.setForeground(TEXT_DARK);
        listTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        listPanel.add(listTitle, BorderLayout.NORTH);

        String[] catColumns = {"ID", "Name", "Description"};
        List<Category> cats = dataManager.getAllCategories();
        Object[][] catData = new Object[cats.size()][3];
        for (int i = 0; i < cats.size(); i++) {
            catData[i][0] = cats.get(i).getCategoryId();
            catData[i][1] = cats.get(i).getCategoryName();
            catData[i][2] = cats.get(i).getDescription();
        }
        JTable catTable = createStyledTableCustom(catData, catColumns);
        JScrollPane scrollPane = new JScrollPane(catTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(CARD_WHITE);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        addBtn.addActionListener(e -> {
            String name = catNameField.getText().trim();
            String desc = descField.getText().trim();
            if (name.isEmpty() || desc.isEmpty()) {
                showStyledMessage("Please fill all fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Category category = new Category(catIdField.getText(), name, desc);
            dataManager.addCategory(category);
            showStyledMessage("Category \"" + name + "\" added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            catIdField.setText(dataManager.generateCategoryId());
            catNameField.setText("");
            descField.setText("");

            List<Category> updatedCats = dataManager.getAllCategories();
            Object[][] newData = new Object[updatedCats.size()][3];
            for (int i = 0; i < updatedCats.size(); i++) {
                newData[i][0] = updatedCats.get(i).getCategoryId();
                newData[i][1] = updatedCats.get(i).getCategoryName();
                newData[i][2] = updatedCats.get(i).getDescription();
            }
            JTable newTable = createStyledTableCustom(newData, catColumns);
            scrollPane.setViewportView(newTable);
        });

        clearBtn.addActionListener(e -> {
            catNameField.setText("");
            descField.setText("");
            catIdField.setText(dataManager.generateCategoryId());
        });

        contentWrapper.add(formPanel);
        contentWrapper.add(listPanel);
        panel.add(contentWrapper, BorderLayout.CENTER);

        return panel;
    }

    // ============================================================
    // CREATE ACCOUNT CONTENT (Manager Only)
    // ============================================================
    private JPanel createCreateAccountContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Create New Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(TEXT_DARK);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        JPanel contentWrapper = new JPanel(new GridLayout(1, 2, 20, 0));
        contentWrapper.setBackground(BG_WHITE);

        RoundedPanel formPanel = new RoundedPanel(15, CARD_WHITE);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField fullNameField = createStyledTextField();
        JTextField usernameField = createStyledTextField();
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(300, 38));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));

        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Cashier", "Manager"});
        roleCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleCombo.setPreferredSize(new Dimension(300, 38));

        addFormRow(formPanel, gbc, 0, "Full Name:", fullNameField);
        addFormRow(formPanel, gbc, 1, "Username:", usernameField);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passLabel.setForeground(TEXT_DARK);
        formPanel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.weightx = 0;
        JLabel roleLabel = new JLabel("Account Type:");
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        roleLabel.setForeground(TEXT_DARK);
        formPanel.add(roleLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(roleCombo, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        RoundedButton createBtn = new RoundedButton("Create Account", ACCENT_PURPLE);
        RoundedButton clearBtn = new RoundedButton("Clear", TEXT_LIGHT);
        buttonPanel.add(createBtn);
        buttonPanel.add(clearBtn);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        formPanel.add(buttonPanel, gbc);

        RoundedPanel listPanel = new RoundedPanel(15, CARD_WHITE);
        listPanel.setLayout(new BorderLayout());
        listPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel listTitle = new JLabel("Existing Users");
        listTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        listTitle.setForeground(TEXT_DARK);
        listTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        listPanel.add(listTitle, BorderLayout.NORTH);

        String[] userColumns = {"Username", "Full Name", "Role"};
        List<Person> userList = dataManager.getAllUsers();
        Object[][] userData = new Object[userList.size()][3];
        for (int i = 0; i < userList.size(); i++) {
            userData[i][0] = userList.get(i).getUsername();
            userData[i][1] = userList.get(i).getFullName();
            userData[i][2] = userList.get(i).getRole();
        }
        JTable userTable = createStyledTableCustom(userData, userColumns);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(CARD_WHITE);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        createBtn.addActionListener(e -> {
            String fullName = fullNameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = (String) roleCombo.getSelectedItem();

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                showStyledMessage("Please fill all fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (password.length() < 6) {
                showStyledMessage("Password must be at least 6 characters!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Person newUser;
            if (role.equals("Manager")) {
                newUser = new Manager(username, password, fullName);
            } else {
                newUser = new Cashier(username, password, fullName);
            }

            if (dataManager.addUser(newUser)) {
                showStyledMessage("Account for \"" + fullName + "\" (" + role + ") created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                fullNameField.setText("");
                usernameField.setText("");
                passwordField.setText("");

                List<Person> updatedUsers = dataManager.getAllUsers();
                Object[][] newData = new Object[updatedUsers.size()][3];
                for (int i = 0; i < updatedUsers.size(); i++) {
                    newData[i][0] = updatedUsers.get(i).getUsername();
                    newData[i][1] = updatedUsers.get(i).getFullName();
                    newData[i][2] = updatedUsers.get(i).getRole();
                }
                JTable newTable = createStyledTableCustom(newData, userColumns);
                scrollPane.setViewportView(newTable);
            } else {
                showStyledMessage("Username \"" + username + "\" already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearBtn.addActionListener(e -> {
            fullNameField.setText("");
            usernameField.setText("");
            passwordField.setText("");
        });

        contentWrapper.add(formPanel);
        contentWrapper.add(listPanel);
        panel.add(contentWrapper, BorderLayout.CENTER);

        return panel;
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 38));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        return field;
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_DARK);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    private Object[][] getBookTableData(List<Book> books) {
        Object[][] data = new Object[books.size()][6];
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            data[i][0] = b.getBookId();
            data[i][1] = b.getTitle();
            data[i][2] = b.getAuthor();
            data[i][3] = b.getCategory();
            data[i][4] = String.format("Rs. %.2f", b.getPrice());
            data[i][5] = b.getStockQuantity();
        }
        return data;
    }

    private JTable createStyledTable(Object[][] data) {
        String[] columns = {"Book ID", "Title", "Author", "Category", "Price", "Stock"};
        return createStyledTableCustom(data, columns);
    }

    private JTable createStyledTableCustom(Object[][] data, String[] columns) {
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(232, 245, 253));
        table.setSelectionForeground(TEXT_DARK);
        table.setBackground(CARD_WHITE);
        table.setForeground(TEXT_DARK);

        // ---- FIXED: Custom header renderer for clear visibility ----
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value != null ? value.toString() : "");
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setForeground(TABLE_HEADER_FG);
                label.setBackground(TABLE_HEADER_BG);
                label.setOpaque(true);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 2, 1, new Color(36, 113, 163)),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)));
                return label;
            }
        });
        header.setPreferredSize(new Dimension(0, 48));
        header.setReorderingAllowed(false);

        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? CARD_WHITE : new Color(245, 248, 250));
                }
                setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
                return c;
            }
        });

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        return table;
    }

    private void showStyledMessage(String message, String title, int messageType) {
        UIManager.put("OptionPane.background", CARD_WHITE);
        UIManager.put("Panel.background", CARD_WHITE);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 13));
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    // ============================================================
    // MAIN METHOD
    // ============================================================
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new CityBookshop();
        });
    }
}
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import java.util.List;

// =====================================================
// ABSTRACT CLASS - Person (Base class for all users)
// =====================================================
abstract class Person implements Serializable {
    private String username;
    private String password;
    private String fullName;

    public Person(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    // Abstract method - each subclass must define its role
    public abstract String getRole();

    public String displayInfo() {
        return "Name: " + fullName + " | Role: " + getRole();
    }

    public String toString() {
        return fullName + " (" + getRole() + ")";
    }
}

// =====================================================
// CASHIER CLASS - Inherits from Person
// =====================================================
class Cashier extends Person {
    public Cashier(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    public String getRole() { return "Cashier"; }

    public String displayInfo() {
        return super.displayInfo() + " | Access: View & Search Books";
    }
}

// =====================================================
// MANAGER CLASS - Inherits from Person
// =====================================================
class Manager extends Person {
    public Manager(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    public String getRole() { return "Manager"; }

    public String displayInfo() {
        return super.displayInfo() + " | Access: Full Control";
    }
}

// =====================================================
// BOOK CLASS - Represents a book in the shop
// =====================================================
class Book implements Serializable {
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

    // Getters and Setters
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

    // Convert to CSV line
    public String toCSV() {
        return bookId + "," + title + "," + author + "," + category + "," + price + "," + stockQuantity;
    }

    public String toString() { return title + " by " + author; }
}

// =====================================================
// CATEGORY CLASS - Represents a book category
// =====================================================
class Category implements Serializable {
    private String categoryId;
    private String categoryName;
    private String description;

    public Category(String categoryId, String categoryName, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
    }

    // Getters and Setters
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Convert to CSV line
    public String toCSV() {
        return categoryId + "," + categoryName + "," + description;
    }

    public String toString() { return categoryName; }
}

// =====================================================
// INTERFACE - Contract for file operations
// =====================================================
interface FileOperations {
    void saveToFile();
    void loadFromFile();
}

// =====================================================
// DATA MANAGER - Handles all data and file operations
// =====================================================
class DataManager implements FileOperations {
    private List<Person> users = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public DataManager() {
        loadFromFile();
        if (users.isEmpty()) {
            loadDefaultData();
            saveToFile();
        }
    }

    // Load sample data for first run
    private void loadDefaultData() {
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

    // ----- FILE SAVE METHODS -----
    public void saveToFile() {
        // Save users
        try (PrintWriter w = new PrintWriter(new FileWriter("users.csv"))) {
            w.println("Role,Username,Password,FullName");
            for (Person u : users)
                w.println(u.getRole() + "," + u.getUsername() + "," + u.getPassword() + "," + u.getFullName());
        } catch (IOException e) { System.out.println("Error saving users: " + e.getMessage()); }

        // Save books
        try (PrintWriter w = new PrintWriter(new FileWriter("books.csv"))) {
            w.println("BookID,Title,Author,Category,Price,StockQuantity");
            for (Book b : books) w.println(b.toCSV());
        } catch (IOException e) { System.out.println("Error saving books: " + e.getMessage()); }

        // Save categories
        try (PrintWriter w = new PrintWriter(new FileWriter("categories.csv"))) {
            w.println("CategoryID,CategoryName,Description");
            for (Category c : categories) w.println(c.toCSV());
        } catch (IOException e) { System.out.println("Error saving categories: " + e.getMessage()); }
    }

    // ----- FILE LOAD METHODS -----
    public void loadFromFile() {
        // Load users
        File userFile = new File("users.csv");
        if (userFile.exists()) {
            try (Scanner sc = new Scanner(userFile)) {
                if (sc.hasNextLine()) sc.nextLine(); // skip header
                while (sc.hasNextLine()) {
                    String[] p = sc.nextLine().trim().split(",", 4);
                    if (p.length == 4) {
                        if (p[0].equals("Manager")) users.add(new Manager(p[1], p[2], p[3]));
                        else users.add(new Cashier(p[1], p[2], p[3]));
                    }
                }
            } catch (IOException e) { System.out.println("Error loading users: " + e.getMessage()); }
        }

        // Load books
        File bookFile = new File("books.csv");
        if (bookFile.exists()) {
            try (Scanner sc = new Scanner(bookFile)) {
                if (sc.hasNextLine()) sc.nextLine(); // skip header
                while (sc.hasNextLine()) {
                    String[] p = sc.nextLine().trim().split(",", 6);
                    if (p.length == 6)
                        books.add(new Book(p[0], p[1], p[2], p[3], Double.parseDouble(p[4]), Integer.parseInt(p[5])));
                }
            } catch (IOException e) { System.out.println("Error loading books: " + e.getMessage()); }
        }

        // Load categories
        File catFile = new File("categories.csv");
        if (catFile.exists()) {
            try (Scanner sc = new Scanner(catFile)) {
                if (sc.hasNextLine()) sc.nextLine(); // skip header
                while (sc.hasNextLine()) {
                    String[] p = sc.nextLine().trim().split(",", 3);
                    if (p.length == 3) categories.add(new Category(p[0], p[1], p[2]));
                }
            } catch (IOException e) { System.out.println("Error loading categories: " + e.getMessage()); }
        }
    }

    // ----- USER OPERATIONS -----
    public Person authenticate(String username, String password) {
        for (Person user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    public boolean addUser(Person user) {
        for (Person existing : users) {
            if (existing.getUsername().equals(user.getUsername())) return false;
        }
        users.add(user);
        saveToFile();
        return true;
    }

    // ----- BOOK OPERATIONS -----
    public void addBook(Book book) { books.add(book); saveToFile(); }

    public List<Book> searchByName(String name) {
        List<Book> result = new ArrayList<>();
        for (Book b : books)
            if (b.getTitle().toLowerCase().contains(name.toLowerCase())) result.add(b);
        return result;
    }

    public List<Book> searchByCategory(String category) {
        List<Book> result = new ArrayList<>();
        for (Book b : books)
            if (b.getCategory().toLowerCase().contains(category.toLowerCase())) result.add(b);
        return result;
    }

    public List<Book> searchByPrice(double maxPrice) {
        List<Book> result = new ArrayList<>();
        for (Book b : books)
            if (b.getPrice() <= maxPrice) result.add(b);
        return result;
    }

    public List<Book> searchByStock(int minStock) {
        List<Book> result = new ArrayList<>();
        for (Book b : books)
            if (b.getStockQuantity() >= minStock) result.add(b);
        return result;
    }

    // ----- CATEGORY OPERATIONS -----
    public void addCategory(Category cat) { categories.add(cat); saveToFile(); }

    // ----- GETTERS -----
    public List<Book> getAllBooks() { return books; }
    public List<Category> getAllCategories() { return categories; }
    public List<Person> getAllUsers() { return users; }

    // ----- ID GENERATORS -----
    public String generateBookId() {
        int max = 0;
        for (Book b : books) {
            try { int n = Integer.parseInt(b.getBookId().replace("BK", "")); if (n > max) max = n; }
            catch (NumberFormatException e) {}
        }
        return String.format("BK%03d", max + 1);
    }

    public String generateCategoryId() {
        int max = 0;
        for (Category c : categories) {
            try { int n = Integer.parseInt(c.getCategoryId().replace("CAT", "")); if (n > max) max = n; }
            catch (NumberFormatException e) {}
        }
        return String.format("CAT%03d", max + 1);
    }
}

// =====================================================
// ROUNDED BUTTON - Custom styled button
// =====================================================
class RoundedButton extends JButton {
    private Color bgColor, hoverColor, pressColor;
    private boolean hovered = false, pressed = false;

    public RoundedButton(String text, Color bgColor) {
        super(text);
        this.bgColor = bgColor;
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
            public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
            public void mouseExited(MouseEvent e) { hovered = false; pressed = false; repaint(); }
            public void mousePressed(MouseEvent e) { pressed = true; repaint(); }
            public void mouseReleased(MouseEvent e) { pressed = false; repaint(); }
        });
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pick color based on state
        Color color = pressed ? pressColor : (hovered ? hoverColor : bgColor);
        g2.setColor(color);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));

        // Draw text centered
        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(getText(), (getWidth() - fm.stringWidth(getText())) / 2,
                (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
        g2.dispose();
    }
}

// =====================================================
// ROUNDED PANEL - Panel with rounded corners
// =====================================================
class RoundedPanel extends JPanel {
    private int radius;
    private Color bgColor;

    public RoundedPanel(int radius, Color bgColor) {
        this.radius = radius;
        this.bgColor = bgColor;
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bgColor);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
        g2.dispose();
        super.paintComponent(g);
    }
}

// =====================================================
// MAIN APPLICATION CLASS
// =====================================================
public class CityBookshop extends JFrame {

    // ---- COLOR CONSTANTS ----
    static final Color DARK_BG = new Color(27, 38, 59);
    static final Color BLUE = new Color(52, 152, 219);
    static final Color GREEN = new Color(39, 174, 96);
    static final Color ORANGE = new Color(243, 156, 18);
    static final Color RED = new Color(231, 76, 60);
    static final Color PURPLE = new Color(142, 68, 173);
    static final Color LIGHT_BG = new Color(236, 240, 241);
    static final Color WHITE = new Color(255, 255, 255);
    static final Color TEXT = new Color(44, 62, 80);
    static final Color GRAY_TEXT = new Color(149, 165, 166);
    static final Color SIDEBAR = new Color(30, 39, 46);
    static final Color SIDEBAR_HL = new Color(47, 54, 64);
    static final Color HEADER_BLUE = new Color(41, 128, 185);

    private DataManager data;
    private Person currentUser;
    private JPanel mainPanel;
    private CardLayout mainLayout;

    // ---- CONSTRUCTOR ----
    public CityBookshop() {
        data = new DataManager();

        setTitle("City Bookshop Management System");
        setSize(1200, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1100, 700));

        mainLayout = new CardLayout();
        mainPanel = new JPanel(mainLayout);
        mainPanel.add(buildLoginPanel(), "LOGIN");
        add(mainPanel);

        setVisible(true);
    }

    // ==========================================================
    //                     LOGIN SCREEN
    // ==========================================================
    private JPanel buildLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // ---- LEFT SIDE (Decorative) ----
        JPanel left = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, BLUE, 0, getHeight(), PURPLE));
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(new Color(255, 255, 255, 40));
                g2.fillOval(-50, -50, 300, 300);
                g2.fillOval(100, getHeight() - 200, 250, 250);

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 42));
                drawCentered(g2, "City Bookshop", getWidth(), getHeight() / 2 - 40);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                drawCentered(g2, "Management System", getWidth(), getHeight() / 2);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                drawCentered(g2, "Automate your bookshop transactions", getWidth(), getHeight() / 2 + 40);
            }
        };
        left.setPreferredSize(new Dimension(450, 0));

        // ---- RIGHT SIDE (Login Form) ----
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(WHITE);

        JPanel card = new JPanel();
        card.setBackground(WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Title
        addCenteredLabel(card, "Welcome Back!", new Font("Segoe UI", Font.BOLD, 28), TEXT);
        addCenteredLabel(card, "Sign in to your account", new Font("Segoe UI", Font.PLAIN, 14), GRAY_TEXT);
        card.add(Box.createVerticalStrut(30));

        // Username
        addFieldLabel(card, "Username");
        JTextField userField = createTextField();
        card.add(userField);
        card.add(Box.createVerticalStrut(15));

        // Password
        addFieldLabel(card, "Password");
        JPasswordField passField = new JPasswordField();
        styleTextField(passField);
        card.add(passField);
        card.add(Box.createVerticalStrut(25));

        // Login button
        RoundedButton loginBtn = new RoundedButton("Sign In", BLUE);
        loginBtn.setMaximumSize(new Dimension(300, 45));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(loginBtn);
        card.add(Box.createVerticalStrut(25));

        // Demo credentials box
        JPanel demoBox = new RoundedPanel(15, new Color(232, 245, 233));
        demoBox.setLayout(new BoxLayout(demoBox, BoxLayout.Y_AXIS));
        demoBox.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        demoBox.setMaximumSize(new Dimension(300, 120));
        demoBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel demoTitle = new JLabel("Demo Credentials");
        demoTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        demoTitle.setForeground(new Color(27, 94, 32));
        demoBox.add(demoTitle);
        demoBox.add(Box.createVerticalStrut(8));

        JLabel mgr = new JLabel("Manager  :  manager / manager123");
        mgr.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        mgr.setForeground(new Color(56, 142, 60));
        demoBox.add(mgr);
        demoBox.add(Box.createVerticalStrut(4));

        JLabel csh = new JLabel("Cashier    :  cashier / cashier123");
        csh.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        csh.setForeground(new Color(56, 142, 60));
        demoBox.add(csh);

        card.add(demoBox);
        right.add(card);

        // ---- LOGIN ACTION ----
        ActionListener loginAction = e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                showMsg("Please enter both username and password.", "Input Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Person p = data.authenticate(user, pass);
            if (p != null) {
                currentUser = p;
                mainPanel.add(buildDashboard(), "DASHBOARD");
                mainLayout.show(mainPanel, "DASHBOARD");
                userField.setText("");
                passField.setText("");
            } else {
                showMsg("Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                passField.setText("");
            }
        };

        loginBtn.addActionListener(loginAction);
        passField.addActionListener(loginAction);
        userField.addActionListener(loginAction);

        panel.add(left, BorderLayout.WEST);
        panel.add(right, BorderLayout.CENTER);
        return panel;
    }

    // ==========================================================
    //                     DASHBOARD
    // ==========================================================
    private JPanel buildDashboard() {
        JPanel panel = new JPanel(new BorderLayout());

        // Content area
        CardLayout contentLayout = new CardLayout();
        JPanel contentPanel = new JPanel(contentLayout);
        contentPanel.setBackground(LIGHT_BG);

        // Add all pages
        contentPanel.add(buildHomePage(), "HOME");
        contentPanel.add(buildViewBooksPage(), "VIEW_BOOKS");
        contentPanel.add(buildSearchPage(), "SEARCH_BOOKS");

        if (currentUser instanceof Manager) {
            contentPanel.add(buildAddBookPage(), "ADD_BOOK");
            contentPanel.add(buildAddCategoryPage(), "ADD_CATEGORY");
            contentPanel.add(buildCreateAccountPage(), "CREATE_ACCOUNT");
        }

        // Sidebar
        JPanel sidebar = buildSidebar(contentPanel, contentLayout);
        panel.add(sidebar, BorderLayout.WEST);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    // ---- SIDEBAR ----
    private JPanel buildSidebar(JPanel contentPanel, CardLayout contentLayout) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(250, 0));

        // Profile section
        JPanel profile = new JPanel();
        profile.setLayout(new BoxLayout(profile, BoxLayout.Y_AXIS));
        profile.setBackground(new Color(22, 29, 38));
        profile.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));
        profile.setMaximumSize(new Dimension(250, 130));

        JLabel shopName = new JLabel("City Bookshop");
        shopName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        shopName.setForeground(Color.WHITE);
        profile.add(shopName);
        profile.add(Box.createVerticalStrut(15));

        JLabel userName = new JLabel(currentUser.getFullName());
        userName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userName.setForeground(new Color(200, 200, 200));
        profile.add(userName);
        profile.add(Box.createVerticalStrut(5));

        JLabel userRole = new JLabel("Role: " + currentUser.getRole());
        userRole.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userRole.setForeground(BLUE);
        profile.add(userRole);

        sidebar.add(profile);
        sidebar.add(Box.createVerticalStrut(10));
        addSeparator(sidebar);

        // Menu section
        addMenuLabel(sidebar, "MENU");
        addNavButton(sidebar, "Dashboard", "HOME", contentPanel, contentLayout);
        addNavButton(sidebar, "View All Books", "VIEW_BOOKS", contentPanel, contentLayout);
        addNavButton(sidebar, "Search Books", "SEARCH_BOOKS", contentPanel, contentLayout);

        if (currentUser instanceof Manager) {
            sidebar.add(Box.createVerticalStrut(10));
            addSeparator(sidebar);
            addMenuLabel(sidebar, "MANAGEMENT");
            addNavButton(sidebar, "Add New Book", "ADD_BOOK", contentPanel, contentLayout);
            addNavButton(sidebar, "Add Category", "ADD_CATEGORY", contentPanel, contentLayout);
            addNavButton(sidebar, "Create Account", "CREATE_ACCOUNT", contentPanel, contentLayout);
        }

        sidebar.add(Box.createVerticalGlue());

        // Logout button
        JButton logout = createSidebarBtn("Logout");
        logout.setForeground(RED);
        logout.addActionListener(e -> {
            currentUser = null;
            mainLayout.show(mainPanel, "LOGIN");
        });
        sidebar.add(logout);
        sidebar.add(Box.createVerticalStrut(20));

        return sidebar;
    }

    // ---- Add navigation button to sidebar ----
    private void addNavButton(JPanel sidebar, String text, String page,
                              JPanel contentPanel, CardLayout contentLayout) {
        JButton btn = createSidebarBtn(text);
        btn.addActionListener(e -> {
            // Refresh dynamic pages
            if (page.equals("HOME")) contentPanel.add(buildHomePage(), "HOME");
            if (page.equals("VIEW_BOOKS")) contentPanel.add(buildViewBooksPage(), "VIEW_BOOKS");
            if (page.equals("SEARCH_BOOKS")) contentPanel.add(buildSearchPage(), "SEARCH_BOOKS");
            contentLayout.show(contentPanel, page);
        });
        sidebar.add(btn);
    }

    // ==========================================================
    //                     HOME PAGE
    // ==========================================================
    private JPanel buildHomePage() {
        JPanel page = createPage();

        // Welcome header
        addPageTitle(page, "Welcome, " + currentUser.getFullName() + "!");
        JLabel sub = new JLabel("Here's your bookshop overview");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(GRAY_TEXT);
        sub.setBorder(BorderFactory.createEmptyBorder(5, 0, 20, 0));
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        page.add(sub);

        // Stats cards
        int totalBooks = data.getAllBooks().size();
        int totalStock = 0;
        for (Book b : data.getAllBooks()) totalStock += b.getStockQuantity();

        JPanel stats = new JPanel(new GridLayout(1, 4, 20, 0));
        stats.setOpaque(false);
        stats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        stats.setAlignmentX(Component.LEFT_ALIGNMENT);

        stats.add(buildStatCard("Total Books", String.valueOf(totalBooks), BLUE));
        stats.add(buildStatCard("Total Stock", String.valueOf(totalStock), GREEN));
        stats.add(buildStatCard("Categories", String.valueOf(data.getAllCategories().size()), ORANGE));
        stats.add(buildStatCard("Users", String.valueOf(data.getAllUsers().size()), PURPLE));
        page.add(stats);
        page.add(Box.createVerticalStrut(20));

        // Books table
        JPanel tableCard = createCard();
        tableCard.setLayout(new BorderLayout());
        addCardTitle(tableCard, "Recent Books in Stock");
        tableCard.add(wrapTable(buildBookTable(data.getAllBooks())), BorderLayout.CENTER);
        tableCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Make table card fill remaining space
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(tableCard, BorderLayout.CENTER);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        page.add(wrapper);

        return page;
    }

    // ---- Stat Card ----
    private JPanel buildStatCard(String title, String value, Color color) {
        RoundedPanel card = new RoundedPanel(15, WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Color bar at top
        JPanel bar = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), 4, 4, 4);
            }
        };
        bar.setOpaque(false);
        bar.setPreferredSize(new Dimension(0, 8));
        bar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 8));
        card.add(bar);

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.setForeground(GRAY_TEXT);
        card.add(t);
        card.add(Box.createVerticalStrut(10));

        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 32));
        v.setForeground(color);
        card.add(v);

        return card;
    }

    // ==========================================================
    //                   VIEW BOOKS PAGE
    // ==========================================================
    private JPanel buildViewBooksPage() {
        JPanel page = createPage();
        addPageTitle(page, "All Books");

        JPanel card = createCard();
        card.setLayout(new BorderLayout());

        card.add(wrapTable(buildBookTable(data.getAllBooks())), BorderLayout.CENTER);

        // Summary
        int total = data.getAllBooks().size();
        int stock = 0;
        for (Book b : data.getAllBooks()) stock += b.getStockQuantity();

        JLabel summary = new JLabel("Total: " + total + " books | " + stock + " items in stock");
        summary.setFont(new Font("Segoe UI", Font.BOLD, 14));
        summary.setForeground(BLUE);
        summary.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        card.add(summary, BorderLayout.SOUTH);

        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(card, BorderLayout.CENTER);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        page.add(wrapper);

        return page;
    }

    // ==========================================================
    //                   SEARCH BOOKS PAGE
    // ==========================================================
    private JPanel buildSearchPage() {
        JPanel page = createPage();
        addPageTitle(page, "Search Books");

        JPanel card = createCard();
        card.setLayout(new BorderLayout());

        // Search controls
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        controls.setOpaque(false);

        controls.add(makeLabel("Search By:", true));

        String[] options = {"Book Name", "Category", "Max Price", "Min Stock"};
        JComboBox<String> typeBox = new JComboBox<>(options);
        typeBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        typeBox.setPreferredSize(new Dimension(150, 38));
        controls.add(typeBox);

        JTextField searchField = createTextField();
        searchField.setPreferredSize(new Dimension(250, 38));
        searchField.setMaximumSize(new Dimension(250, 38));
        controls.add(searchField);

        RoundedButton searchBtn = new RoundedButton("Search", BLUE);
        searchBtn.setPreferredSize(new Dimension(120, 38));
        controls.add(searchBtn);

        RoundedButton resetBtn = new RoundedButton("Reset", GRAY_TEXT);
        resetBtn.setPreferredSize(new Dimension(110, 38));
        controls.add(resetBtn);

        card.add(controls, BorderLayout.NORTH);

        // Results area
        JPanel resultsArea = new JPanel(new BorderLayout());
        resultsArea.setOpaque(false);
        resultsArea.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JLabel resultsTitle = new JLabel("Search Results:");
        resultsTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resultsTitle.setForeground(TEXT);
        resultsTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        resultsArea.add(resultsTitle, BorderLayout.NORTH);

        JTable table = buildBookTable(data.getAllBooks());
        JScrollPane scroll = wrapTable(table);
        resultsArea.add(scroll, BorderLayout.CENTER);

        JLabel countLabel = new JLabel("Showing " + data.getAllBooks().size() + " results");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        countLabel.setForeground(GRAY_TEXT);
        countLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        resultsArea.add(countLabel, BorderLayout.SOUTH);

        card.add(resultsArea, BorderLayout.CENTER);

        // Search action
        ActionListener doSearch = e -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                showMsg("Please enter a search term.", "Search", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String type = (String) typeBox.getSelectedItem();
                List<Book> results;

                switch (type) {
                    case "Category": results = data.searchByCategory(query); break;
                    case "Max Price": results = data.searchByPrice(Double.parseDouble(query)); break;
                    case "Min Stock": results = data.searchByStock(Integer.parseInt(query)); break;
                    default: results = data.searchByName(query); break;
                }

                scroll.setViewportView(buildBookTable(results));
                countLabel.setText("Showing " + results.size() + " results for \"" + query + "\"");
                resultsTitle.setText("Search Results - " + type + ": " + query);
            } catch (NumberFormatException ex) {
                showMsg("Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        };

        searchBtn.addActionListener(doSearch);
        searchField.addActionListener(doSearch);

        resetBtn.addActionListener(e -> {
            searchField.setText("");
            scroll.setViewportView(buildBookTable(data.getAllBooks()));
            countLabel.setText("Showing " + data.getAllBooks().size() + " results");
            resultsTitle.setText("Search Results:");
        });

        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(card, BorderLayout.CENTER);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        page.add(wrapper);

        return page;
    }

    // ==========================================================
    //              ADD BOOK PAGE (Manager Only)
    // ==========================================================
    private JPanel buildAddBookPage() {
        JPanel page = createPage();
        addPageTitle(page, "Add New Book");

        RoundedPanel form = new RoundedPanel(15, WHITE);
        form.setLayout(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 10, 8, 10);
        g.fill = GridBagConstraints.HORIZONTAL;

        // Form fields
        JTextField idField = createTextField();
        idField.setText(data.generateBookId());
        idField.setEditable(false);
        idField.setBackground(new Color(240, 240, 240));

        JTextField titleField = createTextField();
        JTextField authorField = createTextField();

        JComboBox<String> catBox = new JComboBox<>();
        catBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        for (Category c : data.getAllCategories()) catBox.addItem(c.getCategoryName());

        JTextField priceField = createTextField();
        JTextField stockField = createTextField();

        // Add rows
        addRow(form, g, 0, "Book ID:", idField);
        addRow(form, g, 1, "Book Title:", titleField);
        addRow(form, g, 2, "Author:", authorField);
        addRow(form, g, 3, "Category:", catBox);
        addRow(form, g, 4, "Price (Rs.):", priceField);
        addRow(form, g, 5, "Stock Quantity:", stockField);

        // Buttons
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btns.setOpaque(false);

        RoundedButton addBtn = new RoundedButton("Add Book", GREEN);
        RoundedButton clearBtn = new RoundedButton("Clear", GRAY_TEXT);
        btns.add(addBtn);
        btns.add(clearBtn);

        g.gridx = 0; g.gridy = 6; g.gridwidth = 2;
        g.insets = new Insets(20, 10, 10, 10);
        form.add(btns, g);

        // Add action
        addBtn.addActionListener(e -> {
            try {
                String t = titleField.getText().trim();
                String a = authorField.getText().trim();
                String pr = priceField.getText().trim();
                String st = stockField.getText().trim();

                if (t.isEmpty() || a.isEmpty() || pr.isEmpty() || st.isEmpty()) {
                    showMsg("Please fill all fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double price = Double.parseDouble(pr);
                int stock = Integer.parseInt(st);

                if (price <= 0 || stock < 0) {
                    showMsg("Price must be positive and stock cannot be negative!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                data.addBook(new Book(idField.getText(), t, a, (String) catBox.getSelectedItem(), price, stock));
                showMsg("Book \"" + t + "\" added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                idField.setText(data.generateBookId());
                titleField.setText(""); authorField.setText("");
                priceField.setText(""); stockField.setText("");
            } catch (NumberFormatException ex) {
                showMsg("Please enter valid numbers for Price and Stock!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearBtn.addActionListener(e -> {
            titleField.setText(""); authorField.setText("");
            priceField.setText(""); stockField.setText("");
            idField.setText(data.generateBookId());
        });

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        center.setOpaque(false);
        center.add(form);
        center.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(center, BorderLayout.CENTER);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        page.add(wrapper);

        return page;
    }

    // ==========================================================
    //            ADD CATEGORY PAGE (Manager Only)
    // ==========================================================
    private JPanel buildAddCategoryPage() {
        JPanel page = createPage();
        addPageTitle(page, "Add New Category");

        JPanel content = new JPanel(new GridLayout(1, 2, 20, 0));
        content.setOpaque(false);
        content.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ---- Left: Form ----
        RoundedPanel form = new RoundedPanel(15, WHITE);
        form.setLayout(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 10, 8, 10);
        g.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = createTextField();
        idField.setText(data.generateCategoryId());
        idField.setEditable(false);
        idField.setBackground(new Color(240, 240, 240));

        JTextField nameField = createTextField();
        JTextField descField = createTextField();

        addRow(form, g, 0, "Category ID:", idField);
        addRow(form, g, 1, "Category Name:", nameField);
        addRow(form, g, 2, "Description:", descField);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btns.setOpaque(false);
        RoundedButton addBtn = new RoundedButton("Add Category", ORANGE);
        RoundedButton clearBtn = new RoundedButton("Clear", GRAY_TEXT);
        btns.add(addBtn);
        btns.add(clearBtn);

        g.gridx = 0; g.gridy = 3; g.gridwidth = 2;
        g.insets = new Insets(20, 10, 10, 10);
        form.add(btns, g);

        // ---- Right: Existing categories table ----
        RoundedPanel listCard = new RoundedPanel(15, WHITE);
        listCard.setLayout(new BorderLayout());
        listCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addCardTitle(listCard, "Existing Categories");

        String[] cols = {"ID", "Name", "Description"};
        JScrollPane scroll = wrapTable(buildCategoryTable());
        listCard.add(scroll, BorderLayout.CENTER);

        // Actions
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String desc = descField.getText().trim();
            if (name.isEmpty() || desc.isEmpty()) {
                showMsg("Please fill all fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            data.addCategory(new Category(idField.getText(), name, desc));
            showMsg("Category \"" + name + "\" added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            idField.setText(data.generateCategoryId());
            nameField.setText(""); descField.setText("");
            scroll.setViewportView(buildCategoryTable());
        });

        clearBtn.addActionListener(e -> {
            nameField.setText(""); descField.setText("");
            idField.setText(data.generateCategoryId());
        });

        content.add(form);
        content.add(listCard);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(content, BorderLayout.CENTER);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        page.add(wrapper);

        return page;
    }

    // ==========================================================
    //           CREATE ACCOUNT PAGE (Manager Only)
    // ==========================================================
    private JPanel buildCreateAccountPage() {
        JPanel page = createPage();
        addPageTitle(page, "Create New Account");

        JPanel content = new JPanel(new GridLayout(1, 2, 20, 0));
        content.setOpaque(false);
        content.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ---- Left: Form ----
        RoundedPanel form = new RoundedPanel(15, WHITE);
        form.setLayout(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 10, 8, 10);
        g.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = createTextField();
        JTextField userField = createTextField();

        JPasswordField passField = new JPasswordField();
        styleTextField(passField);

        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Cashier", "Manager"});
        roleBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        addRow(form, g, 0, "Full Name:", nameField);
        addRow(form, g, 1, "Username:", userField);
        addRow(form, g, 2, "Password:", passField);
        addRow(form, g, 3, "Account Type:", roleBox);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btns.setOpaque(false);
        RoundedButton createBtn = new RoundedButton("Create Account", PURPLE);
        RoundedButton clearBtn = new RoundedButton("Clear", GRAY_TEXT);
        btns.add(createBtn);
        btns.add(clearBtn);

        g.gridx = 0; g.gridy = 4; g.gridwidth = 2;
        g.insets = new Insets(20, 10, 10, 10);
        form.add(btns, g);

        // ---- Right: Existing users table ----
        RoundedPanel listCard = new RoundedPanel(15, WHITE);
        listCard.setLayout(new BorderLayout());
        listCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addCardTitle(listCard, "Existing Users");

        JScrollPane scroll = wrapTable(buildUserTable());
        listCard.add(scroll, BorderLayout.CENTER);

        // Actions
        createBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String role = (String) roleBox.getSelectedItem();

            if (name.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                showMsg("Please fill all fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (pass.length() < 6) {
                showMsg("Password must be at least 6 characters!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // POLYMORPHISM - creating different types based on role
            Person newUser = role.equals("Manager") ?
                    new Manager(user, pass, name) : new Cashier(user, pass, name);

            if (data.addUser(newUser)) {
                showMsg("Account for \"" + name + "\" (" + role + ") created!", "Success", JOptionPane.INFORMATION_MESSAGE);
                nameField.setText(""); userField.setText(""); passField.setText("");
                scroll.setViewportView(buildUserTable());
            } else {
                showMsg("Username \"" + user + "\" already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearBtn.addActionListener(e -> {
            nameField.setText(""); userField.setText(""); passField.setText("");
        });

        content.add(form);
        content.add(listCard);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(content, BorderLayout.CENTER);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        page.add(wrapper);

        return page;
    }

    // ==========================================================
    //              TABLE BUILDING METHODS
    // ==========================================================

    // Build a styled table for books
    private JTable buildBookTable(List<Book> books) {
        String[] cols = {"Book ID", "Title", "Author", "Category", "Price", "Stock"};
        Object[][] rows = new Object[books.size()][6];
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            rows[i] = new Object[]{b.getBookId(), b.getTitle(), b.getAuthor(),
                    b.getCategory(), String.format("Rs. %.2f", b.getPrice()), b.getStockQuantity()};
        }
        return styleTable(new JTable(new DefaultTableModel(rows, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        }));
    }

    // Build a styled table for categories
    private JTable buildCategoryTable() {
        String[] cols = {"ID", "Name", "Description"};
        List<Category> cats = data.getAllCategories();
        Object[][] rows = new Object[cats.size()][3];
        for (int i = 0; i < cats.size(); i++) {
            Category c = cats.get(i);
            rows[i] = new Object[]{c.getCategoryId(), c.getCategoryName(), c.getDescription()};
        }
        return styleTable(new JTable(new DefaultTableModel(rows, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        }));
    }

    // Build a styled table for users
    private JTable buildUserTable() {
        String[] cols = {"Username", "Full Name", "Role"};
        List<Person> users = data.getAllUsers();
        Object[][] rows = new Object[users.size()][3];
        for (int i = 0; i < users.size(); i++) {
            Person p = users.get(i);
            rows[i] = new Object[]{p.getUsername(), p.getFullName(), p.getRole()};
        }
        return styleTable(new JTable(new DefaultTableModel(rows, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        }));
    }

    // Apply styling to any table
    private JTable styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(232, 245, 253));
        table.setSelectionForeground(TEXT);
        table.setBackground(WHITE);

        // Header style - bright blue with white text
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean focus, int row, int col) {
                JLabel lbl = new JLabel(val != null ? val.toString() : "");
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lbl.setForeground(Color.WHITE);
                lbl.setBackground(HEADER_BLUE);
                lbl.setOpaque(true);
                lbl.setHorizontalAlignment(SwingConstants.LEFT);
                lbl.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 2, 1, new Color(36, 113, 163)),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)));
                return lbl;
            }
        });
        table.getTableHeader().setPreferredSize(new Dimension(0, 48));
        table.getTableHeader().setReorderingAllowed(false);

        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean focus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                if (!sel) c.setBackground(row % 2 == 0 ? WHITE : new Color(245, 248, 250));
                setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
                return c;
            }
        });

        return table;
    }

    // Wrap table in scroll pane
    private JScrollPane wrapTable(JTable table) {
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(WHITE);
        return sp;
    }

    // ==========================================================
    //              UI HELPER METHODS
    // ==========================================================

    // Create a standard page panel
    private JPanel createPage() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(LIGHT_BG);
        p.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        return p;
    }

    // Create a white card panel
    private JPanel createCard() {
        RoundedPanel card = new RoundedPanel(15, WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return card;
    }

    // Add a page title
    private void addPageTitle(JPanel page, String text) {
        JLabel title = new JLabel(text);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(TEXT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        page.add(title);
    }

    // Add a card title
    private void addCardTitle(JPanel card, String text) {
        JLabel title = new JLabel(text);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(TEXT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        card.add(title, BorderLayout.NORTH);
    }

    // Create a styled text field
    private JTextField createTextField() {
        JTextField field = new JTextField();
        styleTextField(field);
        return field;
    }

    // Apply styling to text field
    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 38));
        field.setMaximumSize(new Dimension(300, 42));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
    }

    // Add a form row (label + field)
    private void addRow(JPanel panel, GridBagConstraints g, int row, String label, JComponent field) {
        g.gridx = 0; g.gridy = row; g.gridwidth = 1; g.weightx = 0;
        panel.add(makeLabel(label, true), g);
        g.gridx = 1; g.weightx = 1;
        panel.add(field, g);
    }

    // Create a label
    private JLabel makeLabel(String text, boolean bold) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", bold ? Font.BOLD : Font.PLAIN, 14));
        lbl.setForeground(TEXT);
        return lbl;
    }

    // Add a centered label
    private void addCenteredLabel(JPanel panel, String text, Font font, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(color);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lbl);
    }

    // Add a field label
    private void addFieldLabel(JPanel panel, String text) {
        JLabel lbl = new JLabel("  " + text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(TEXT);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setMaximumSize(new Dimension(300, 20));
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(5));
    }

    // Create a sidebar button
    private JButton createSidebarBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(new Color(200, 200, 200));
        btn.setBackground(SIDEBAR);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(250, 45));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(SIDEBAR_HL);
                btn.setContentAreaFilled(true);
                btn.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                btn.setContentAreaFilled(false);
                btn.setForeground(new Color(200, 200, 200));
            }
        });
        return btn;
    }

    // Add a separator line to sidebar
    private void addSeparator(JPanel sidebar) {
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(250, 1));
        sep.setForeground(new Color(60, 70, 80));
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(10));
    }

    // Add a menu section label to sidebar
    private void addMenuLabel(JPanel sidebar, String text) {
        JLabel lbl = new JLabel("   " + text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(new Color(120, 130, 140));
        sidebar.add(lbl);
        sidebar.add(Box.createVerticalStrut(5));
    }

    // Draw centered text helper
    private void drawCentered(Graphics2D g2, String text, int width, int y) {
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(text, (width - fm.stringWidth(text)) / 2, y);
    }

    // Show styled message dialog
    private void showMsg(String msg, String title, int type) {
        UIManager.put("OptionPane.background", WHITE);
        UIManager.put("Panel.background", WHITE);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        JOptionPane.showMessageDialog(this, msg, title, type);
    }

    // ==========================================================
    //                     MAIN METHOD
    // ==========================================================
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace(); }

        SwingUtilities.invokeLater(() -> new CityBookshop());
    }
}
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String USERS_FILE = "users.csv";
    private static final String BOOKS_FILE = "books.csv";
    private static final String TRANSACTIONS_FILE = "transactions.csv";
    
    // Load users from CSV
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        
        if (!file.exists()) {
            // Create default users
            users.add(new Manager("admin", "admin123"));
            users.add(new Cashier("cashier", "cash123"));
            saveUsers(users);
            return users;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    if (data[2].equals("Manager")) {
                        users.add(new Manager(data[0], data[1]));
                    } else {
                        users.add(new Cashier(data[0], data[1]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return users;
    }
    
    // Save users to CSV
    public static void saveUsers(List<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                pw.println(user.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Load books from CSV
    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        File file = new File(BOOKS_FILE);
        
        if (!file.exists()) {
            return books;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    Book book = new Book(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        Double.parseDouble(data[4]),
                        Integer.parseInt(data[5])
                    );
                    books.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return books;
    }
    
    // Save books to CSV
    public static void saveBooks(List<Book> books) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                pw.println(book.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Load transactions from CSV
    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);
        
        if (!file.exists()) {
            return transactions;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 8) {
                    Transaction transaction = new Transaction(
                        data[0],
                        data[1],
                        data[2],
                        Integer.parseInt(data[3]),
                        Double.parseDouble(data[4]),
                        Double.parseDouble(data[5]),
                        data[6],
                        data[7]
                    );
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return transactions;
    }
    
    // Append a transaction to CSV
    public static void appendTransaction(Transaction transaction) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            pw.println(transaction.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String transactionId;
    private String bookId;
    private String bookTitle;
    private int quantity;
    private double price;
    private double total;
    private String cashier;
    private String dateTime;
    
    public Transaction(String transactionId, String bookId, String bookTitle, int quantity, double price, String cashier) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.quantity = quantity;
        this.price = price;
        this.total = quantity * price;
        this.cashier = cashier;
        this.dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    public Transaction(String transactionId, String bookId, String bookTitle, int quantity, 
                      double price, double total, String cashier, String dateTime) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.cashier = cashier;
        this.dateTime = dateTime;
    }
    
    // Getters
    public String getTransactionId() {
        return transactionId;
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public String getBookTitle() {
        return bookTitle;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
    public double getTotal() {
        return total;
    }
    
    public String getCashier() {
        return cashier;
    }
    
    public String getDateTime() {
        return dateTime;
    }
    
    @Override
    public String toString() {
        return transactionId + "," + bookId + "," + bookTitle + "," + quantity + "," + 
               price + "," + total + "," + cashier + "," + dateTime;
    }
}

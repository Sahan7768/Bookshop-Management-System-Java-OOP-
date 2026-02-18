# City Bookshop Management System

## 📖 Overview
A comprehensive bookshop management system built with Java using Object-Oriented Programming principles. The system features user authentication, inventory management, sales processing, and transaction tracking with a beautiful, colorful graphical user interface.

## ✨ Features

### For All Users:
- 🔐 **Secure Login System** - Username/password authentication
- 📚 **Browse Books** - View all available books with details
- 🔍 **Advanced Search** - Search by title, author, category, or price range
- 🛒 **Shopping Cart** - Add multiple books before checkout
- 💳 **Checkout System** - Complete transactions with automatic stock updates
- 📊 **Transaction History** - View all past transactions

### For Managers Only:
- ➕ **Add Books** - Add new books to inventory
- ✏️ **Update Books** - Modify existing book details
- 🗑️ **Delete Books** - Remove books from inventory
- 👥 **User Management** - Create new cashier or manager accounts
- 📈 **Full Access** - All cashier features plus management capabilities

## 🎨 User Interface
- **Modern Design** - Clean, professional interface with attractive colors
- **Color Scheme**:
  - Primary Blue: Navigation and headers
  - Teal/Green: Success actions
  - Orange: Warnings
  - Red: Delete/Logout actions
- **User-Friendly** - Intuitive navigation with tabbed interface
- **Responsive** - Proper table formatting and button styling

## 🔧 Object-Oriented Programming Concepts Applied

### 1. **Class and Object**
```java
// Class definition
public class Book {
    private String bookId;
    private String title;
    // ... other attributes
}

// Object creation
Book book = new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald", 
                     "Fiction", 12.99, 25);
```
**Explanation**: Classes are blueprints that define the structure and behavior. Objects are instances of classes. We have classes like `Book`, `User`, `Transaction`, etc.

### 2. **Encapsulation**
```java
public class Book {
    private String bookId;      // Private attributes
    private String title;
    
    // Public getters and setters
    public String getBookId() {
        return bookId;
    }
    
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
```
**Explanation**: Encapsulation hides internal data and provides controlled access through getter/setter methods. All attributes in our classes are private with public accessors.

### 3. **Inheritance**
```java
// Parent class
public abstract class User {
    protected String username;
    protected String password;
    // ... methods
}

// Child classes inherit from User
public class Manager extends User {
    public Manager(String username, String password) {
        super(username, password, "Manager");
    }
}

public class Cashier extends User {
    public Cashier(String username, String password) {
        super(username, password, "Cashier");
    }
}
```
**Explanation**: Inheritance allows classes to inherit properties and methods from parent classes. Both `Manager` and `Cashier` inherit from the `User` class, promoting code reuse.

### 4. **Polymorphism**
```java
// Method overriding - different behavior in child classes
public abstract class User {
    public abstract boolean hasPermission(String action);
}

public class Cashier extends User {
    @Override
    public boolean hasPermission(String action) {
        return action.equals("VIEW") || action.equals("SEARCH");
    }
}

public class Manager extends User {
    @Override
    public boolean hasPermission(String action) {
        return true;  // Manager has all permissions
    }
}
```
**Explanation**: Polymorphism allows objects of different classes to be treated as objects of a common parent class. The same method `hasPermission()` behaves differently for Manager and Cashier.

### 5. **Abstraction**
```java
public abstract class User {
    // Abstract method - no implementation in parent class
    public abstract boolean hasPermission(String action);
    
    // Concrete methods
    public String getUsername() {
        return username;
    }
}
```
**Explanation**: Abstraction hides complex implementation details and shows only essential features. The `User` class is abstract with an abstract method that must be implemented by subclasses.

## 📁 File Structure

```
BookshopSystem/
│
├── User.java              - Abstract user class (Abstraction)
├── Manager.java           - Manager class (Inheritance, Polymorphism)
├── Cashier.java           - Cashier class (Inheritance, Polymorphism)
├── Book.java              - Book model (Encapsulation)
├── Transaction.java       - Transaction model (Encapsulation)
├── FileManager.java       - CSV file operations
├── LoginSystem.java       - Login interface
├── BookshopSystem.java    - Main application GUI
├── books.csv              - Book data storage
├── users.csv              - User credentials storage
└── transactions.csv       - Transaction records storage
```

## 💾 Data Storage (CSV Files)

### users.csv
```
username,password,role
admin,admin123,Manager
cashier,cash123,Cashier
```

### books.csv
```
bookId,title,author,category,price,quantity
B001,The Great Gatsby,F. Scott Fitzgerald,Fiction,12.99,25
```

### transactions.csv
```
transactionId,bookId,bookTitle,quantity,price,total,cashier,dateTime
TXN1234567890,B001,The Great Gatsby,2,12.99,25.98,admin,2024-01-15 10:30:45
```

## 🚀 How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Visual Studio Code with Java Extension Pack (or any Java IDE)

### Compilation and Execution

**Using Command Line:**
```bash
# Navigate to project directory
cd BookshopSystem

# Compile all Java files
javac *.java

# Run the application
java LoginSystem
```

**Using Visual Studio Code:**
1. Open the project folder in VS Code
2. Ensure Java Extension Pack is installed
3. Open `LoginSystem.java`
4. Click the "Run" button or press F5
5. Alternatively, use the integrated terminal and run the commands above

**Using IDE (Eclipse, IntelliJ, NetBeans):**
1. Create a new Java project
2. Add all .java files to the project
3. Set `LoginSystem.java` as the main class
4. Click Run

## 👤 Default Login Credentials

### Manager Account
- **Username**: admin
- **Password**: admin123
- **Permissions**: Full access to all features

### Cashier Account
- **Username**: cashier
- **Password**: cash123
- **Permissions**: View books, search, and process sales

## 📚 Usage Guide

### 1. Login
- Launch the application
- Enter username and password
- Click "LOGIN" button

### 2. Browse Books (All Users)
- Navigate to "📚 Browse Books" tab
- View all available books in the table
- Use search functionality to filter books
- Select a book and specify quantity
- Click "Add to Cart" to add to shopping cart

### 3. Search Books
- Enter search term in the search field
- Select search type (Title, Author, Category, Price Range, or All)
- Click "Search" button
- Click "Clear" to show all books again

### 4. Shopping Cart (All Users)
- Navigate to "🛒 Shopping Cart" tab
- Review items in cart
- Remove items if needed
- Click "💳 Checkout" to complete purchase
- View total amount before checkout

### 5. Manage Books (Manager Only)
- Navigate to "📖 Manage Books" tab
- **Add Book**: Fill in book details and click "➕ Add Book"
- **Update Book**: Select a book from table, modify details, click "✏️ Update"
- **Delete Book**: Select a book and click "🗑️ Delete"

### 6. Manage Accounts (Manager Only)
- Navigate to "👥 Manage Accounts" tab
- Enter username and password for new user
- Select role (Cashier or Manager)
- Click "➕ Create Account"

### 7. Transaction History (All Users)
- Navigate to "📊 Transaction History" tab
- View all completed transactions
- See details: Transaction ID, books sold, quantities, prices, cashier, date/time
- Click "Refresh" to update the view

## 🎯 Key Functionalities

1. **User Authentication**: Secure login with role-based access control
2. **Inventory Management**: Add, update, delete books (Manager only)
3. **Sales Processing**: Add books to cart and checkout
4. **Search & Filter**: Multiple search options for finding books
5. **Transaction Tracking**: Complete history of all sales
6. **Stock Management**: Automatic stock updates after sales
7. **User Management**: Create new user accounts (Manager only)
8. **Data Persistence**: All data saved to CSV files

## 🛠️ Technical Details

- **Language**: Java
- **GUI Framework**: Java Swing
- **Data Storage**: CSV files
- **Design Pattern**: MVC-inspired architecture
- **File I/O**: BufferedReader/PrintWriter for CSV operations

## ⚠️ Important Notes

1. **CSV Files**: Created automatically on first run if not present
2. **Data Persistence**: All changes are saved immediately to CSV files
3. **Stock Validation**: System prevents overselling (quantity validation)
4. **Role-Based Access**: Different features available based on user role
5. **Transaction IDs**: Unique IDs generated using timestamp

## 🐛 Troubleshooting

**Problem**: Application doesn't start
- **Solution**: Ensure JDK is properly installed and JAVA_HOME is set

**Problem**: CSV files not created
- **Solution**: Check write permissions in the project directory

**Problem**: GUI appears incorrectly
- **Solution**: Ensure you're using JDK 8 or higher

**Problem**: Search not working
- **Solution**: Verify books.csv file exists with proper data

## 📝 Assessment Criteria Coverage

### LO1: Object-Oriented Programming Concepts ✅
- **Class & Object**: Book, User, Transaction, Manager, Cashier classes
- **Encapsulation**: Private attributes with public getters/setters
- **Inheritance**: Manager and Cashier extend User class
- **Polymorphism**: Overridden hasPermission() method
- **Abstraction**: Abstract User class with abstract methods

### LO2: Design ✅
- Proper class hierarchy and relationships
- Separation of concerns (GUI, Data, Logic)
- File-based data persistence

### LO3: Development ✅
- Fully functional application
- User-friendly GUI with Swing
- Error handling and validation
- CSV file operations for data storage

## 🎓 Learning Outcomes

This project demonstrates:
1. Practical application of OOP principles
2. GUI development with Java Swing
3. File I/O operations
4. User authentication and authorization
5. Data validation and error handling
6. Software design and architecture

## 👨‍💻 Author
Developed as part of Object-Oriented Programming coursework

## 📄 License
Educational project for academic purposes

---
**City Bookshop Management System** - Combining Object-Oriented Programming excellence with practical business solutions! 📚✨

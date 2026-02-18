// Cashier class - Inherits from User
public class Cashier extends User {
    
    public Cashier(String username, String password) {
        super(username, password, "Cashier");
    }
    
    // Polymorphism - Override abstract method
    @Override
    public boolean hasPermission(String action) {
        // Cashier can only view and search
        return action.equals("VIEW") || action.equals("SEARCH");
    }
}

// Manager class - Inherits from User
public class Manager extends User {
    
    public Manager(String username, String password) {
        super(username, password, "Manager");
    }
    
    // Polymorphism - Override abstract method with different implementation
    @Override
    public boolean hasPermission(String action) {
        // Manager has all permissions
        return true;
    }
}

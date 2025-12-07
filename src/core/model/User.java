package core.model;

/**
 * User: System users with role-based access control
 */
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;
    
    public enum UserRole {
        ADMIN, MANAGER, STAFF, VIEWER
    }
    
    private String username;
    private String passwordHash;
    private String fullName;
    private String email;
    private String phone;
    private UserRole role;
    private String department;
    private boolean locked;
    private int failedLoginAttempts;
    
    public User() {
        super();
        this.locked = false;
        this.failedLoginAttempts = 0;
    }
    
    public User(String username, String fullName) {
        super();
        this.username = username;
        this.fullName = fullName;
        this.locked = false;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public boolean isLocked() {
        return locked;
    }
    
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }
    
    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }
}

package core.model;

/**
 * Supplier: Represents suppliers for inventory
 */
public class Supplier extends BaseEntity {
    private static final long serialVersionUID = 1L;
    
    private String supplierName;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private double creditLimit;
    private double currentDebt;
    private int paymentTermsDays;
    
    public Supplier() {
        super();
    }
    
    public Supplier(String supplierName, String contactPerson) {
        super();
        this.supplierName = supplierName;
        this.contactPerson = contactPerson;
    }
    
    // Getters and Setters
    public String getSupplierName() {
        return supplierName;
    }
    
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
    public String getContactPerson() {
        return contactPerson;
    }
    
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
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
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public double getCreditLimit() {
        return creditLimit;
    }
    
    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }
    
    public double getCurrentDebt() {
        return currentDebt;
    }
    
    public void setCurrentDebt(double currentDebt) {
        this.currentDebt = currentDebt;
    }
    
    public int getPaymentTermsDays() {
        return paymentTermsDays;
    }
    
    public void setPaymentTermsDays(int paymentTermsDays) {
        this.paymentTermsDays = paymentTermsDays;
    }
}

package model;

public abstract class Customer {
    private static int customerCounter = 0;   // shared by ALL customers

    private String customerId;
    private String name;
    private int age;
    private String contact;
    private String address;

    public Customer(String name, int age, String contact, String address) {
        customerCounter++;
        this.customerId = String.format("CUS%03d", customerCounter);   // CUS001, CUS002...
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.address = address;
    }

    public String getCustomerId() { return customerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public abstract void displayCustomerDetails();
    public abstract String getCustomerType();
}
package model;

public class RegularCustomer extends Customer {

    public RegularCustomer(String name, int age, String contact, String address) {
        super(name, age, contact, address);   // sends the data up to Customer
    }

    @Override
    public void displayCustomerDetails() {
        System.out.println(getCustomerId() + " | " + getName() + " | Age: " + getAge()
                + " | " + getContact() + " | " + getAddress());
    }

    @Override
    public String getCustomerType() {
        return "Regular";
    }
}
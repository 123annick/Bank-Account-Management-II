package model;

public class PremiumCustomer extends Customer {
    private double minimumBalance = 10000;   // needed to keep premium status

    public PremiumCustomer(String name, int age, String contact, String address) {
        super(name, age, contact, address);
    }

    public double getMinimumBalance() { return minimumBalance; }
    public void setMinimumBalance(double minimumBalance) { this.minimumBalance = minimumBalance; }

    @Override
    public void displayCustomerDetails() {
        System.out.println(getCustomerId() + " | " + getName() + " | Age: " + getAge()
                + " | " + getContact() + " | " + getAddress());
        System.out.println("Premium benefits: no monthly fees, higher limits, priority service");
    }

    @Override
    public String getCustomerType() {
        return "Premium";
    }

    public boolean hasWaivedFees() {
        return true;
    }
}
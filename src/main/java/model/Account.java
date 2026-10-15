package model;

public abstract class Account implements Transactable {
    private static int accountCounter = 0;   // shared by ALL accounts

    private String accountNumber;
    private Customer customer;               // composition: an Account HAS a Customer
    private double balance;
    private String status;

    public Account(Customer customer, double initialBalance) {
        accountCounter++;
        this.accountNumber = String.format("ACC%03d", accountCounter);   // ACC001...
        this.customer = customer;
        this.balance = initialBalance;
        this.status = "Active";
    }

    public String getAccountNumber() { return accountNumber; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public abstract void displayAccountDetails();
    public abstract String getAccountType();

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    // Only CHECKS the rule (does not change the balance). Subclasses override it.
    public boolean canWithdraw(double amount) {
        return amount > 0 && amount <= balance;
    }

    // Actually withdraws if allowed. Returns true if it worked.
    public boolean withdraw(double amount) {
        if (canWithdraw(amount)) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public boolean processTransaction(double amount, String type) {
        if (amount <= 0) {
            return false;
        }
        if (type.equalsIgnoreCase("DEPOSIT")) {
            deposit(amount);
            return true;
        } else if (type.equalsIgnoreCase("WITHDRAWAL")) {
            return withdraw(amount);   // polymorphism: runs Savings or Checking version
        }
        return false;
    }
}
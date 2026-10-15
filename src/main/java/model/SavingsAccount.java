package model;

public class SavingsAccount extends Account {
    private double interestRate = 3.5;       // percent per year
    private double minimumBalance = 500;

    public SavingsAccount(Customer customer, double initialBalance) {
        super(customer, initialBalance);
    }

    public double getInterestRate() { return interestRate; }
    public double getMinimumBalance() { return minimumBalance; }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    // Savings rule: balance must stay at or above the minimum
    @Override
    public boolean canWithdraw(double amount) {
        return amount > 0 && getBalance() - amount >= minimumBalance;
    }

    @Override
    public boolean withdraw(double amount) {
        if (canWithdraw(amount)) {
            setBalance(getBalance() - amount);
            return true;
        }
        return false;
    }

    // Interest earned in one year
    public double calculateInterest() {
        return getBalance() * interestRate / 100;
    }

    @Override
    public void displayAccountDetails() {
        System.out.printf("%-8s | %-17s | %-17s | %-12s | %s%n",
                getAccountNumber(), getCustomer().getName(), getAccountType(),
                String.format("$%,.2f", getBalance()), getStatus());
        System.out.printf("         | Customer ID: %s | Interest Rate: %.1f%% | Min Balance: $%,.2f%n",
                getCustomer().getCustomerId(), interestRate, minimumBalance);
    }
}
package model;

public class CheckingAccount extends Account {
    private double overdraftLimit = 1000;
    private double monthlyFee = 10;

    public CheckingAccount(Customer customer, double initialBalance) {
        super(customer, initialBalance);
        // Premium customers do not pay the monthly fee
        if (customer instanceof PremiumCustomer) {
            monthlyFee = 0;
        }
    }

    public double getOverdraftLimit() { return overdraftLimit; }
    public double getMonthlyFee() { return monthlyFee; }

    @Override
    public String getAccountType() {
        return "Checking";
    }

    // Checking rule: balance may go negative, but not below -overdraftLimit
    @Override
    public boolean canWithdraw(double amount) {
        return amount > 0 && getBalance() - amount >= -overdraftLimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (canWithdraw(amount)) {
            setBalance(getBalance() - amount);
            return true;
        }
        return false;
    }

    public void applyMonthlyFee() {
        setBalance(getBalance() - monthlyFee);
    }

    @Override
    public void displayAccountDetails() {
        System.out.printf("%-8s | %-17s | %-17s | %-12s | %s%n",
                getAccountNumber(), getCustomer().getName(), getAccountType(),
                String.format("$%,.2f", getBalance()), getStatus());
        System.out.printf("         | Customer ID: %s | Overdraft Limit: $%,.2f | Monthly Fee: $%,.2f%n",
                getCustomer().getCustomerId(), overdraftLimit, monthlyFee);    }
}
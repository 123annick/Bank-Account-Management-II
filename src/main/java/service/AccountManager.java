package service;

import model.Account;

public class AccountManager {
    private Account[] accounts = new Account[50];   // fixed-size array
    private int accountCount = 0;                   // how many slots are really used

    public boolean addAccount(Account account) {
        if (accountCount >= accounts.length) {
            return false;                           // array is full
        }
        accounts[accountCount] = account;
        accountCount++;
        return true;
    }

    // Linear search: checks one by one from the start -> O(n)
    public Account findAccount(String accountNumber) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber().equalsIgnoreCase(accountNumber)) {
                return accounts[i];
            }
        }
        return null;   // not found
    }

    public void viewAllAccounts() {
        String line = "─".repeat(74);
        System.out.println("\nACCOUNT LISTING");
        System.out.println(line);
        System.out.printf("%-8s | %-17s | %-17s | %-12s | %s%n",
                "ACC NO", "CUSTOMER NAME", "TYPE", "BALANCE", "STATUS");
        System.out.println(line);

        for (int i = 0; i < accountCount; i++) {
            accounts[i].displayAccountDetails();    // polymorphism: each type prints itself
            System.out.println(line);
        }

        System.out.println("\nTotal Accounts: " + accountCount);
        System.out.printf("Total Bank Balance: $%,.2f%n", getTotalBalance());
    }

    public double getTotalBalance() {
        double total = 0;
        for (int i = 0; i < accountCount; i++) {
            total += accounts[i].getBalance();
        }
        return total;
    }

    public int getAccountCount() {
        return accountCount;
    }
}
package service;

import model.Transaction;

public class TransactionManager {
    private Transaction[] transactions = new Transaction[200];
    private int transactionCount = 0;

    public boolean addTransaction(Transaction transaction) {
        if (transactionCount >= transactions.length) {
            return false;
        }
        transactions[transactionCount] = transaction;
        transactionCount++;
        return true;
    }

    public void viewTransactionsByAccount(String accountNumber) {
        String line = "─".repeat(71);
        boolean found = false;

        // Stored oldest -> newest, so looping BACKWARDS prints newest first
        for (int i = transactionCount - 1; i >= 0; i--) {
            if (transactions[i].getAccountNumber().equalsIgnoreCase(accountNumber)) {
                if (!found) {
                    System.out.println("TRANSACTION HISTORY");
                    System.out.println(line);
                    System.out.printf("%-7s | %-19s | %-10s | %-11s | %s%n",
                            "TXN ID", "DATE/TIME", "TYPE", "AMOUNT", "BALANCE");
                    System.out.println(line);
                    found = true;
                }
                transactions[i].displayTransactionDetails();
            }
        }

        if (!found) {
            System.out.println(line);
            System.out.println("No transactions recorded for this account.");
            System.out.println(line);
            return;
        }

        System.out.println(line);

        // Count this account's transactions
        int count = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equalsIgnoreCase(accountNumber)) {
                count++;
            }
        }

        double deposits = calculateTotalDeposits(accountNumber);
        double withdrawals = calculateTotalWithdrawals(accountNumber);
        double net = deposits - withdrawals;

        System.out.println("\nTotal Transactions: " + count);
        System.out.printf("Total Deposits: $%,.2f%n", deposits);
        System.out.printf("Total Withdrawals: $%,.2f%n", withdrawals);
        System.out.printf("Net Change: %s$%,.2f%n", net >= 0 ? "+" : "-", Math.abs(net));
    }

    public double calculateTotalDeposits(String accountNumber) {
        double total = 0;
        for (int i = 0; i < transactionCount; i++) {
            Transaction t = transactions[i];
            if (t.getAccountNumber().equalsIgnoreCase(accountNumber)
                    && t.getType().equals("DEPOSIT")) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public double calculateTotalWithdrawals(String accountNumber) {
        double total = 0;
        for (int i = 0; i < transactionCount; i++) {
            Transaction t = transactions[i];
            if (t.getAccountNumber().equalsIgnoreCase(accountNumber)
                    && t.getType().equals("WITHDRAWAL")) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public int getTransactionCount() {
        return transactionCount;
    }
}
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private static int transactionCounter = 0;   // shared by ALL transactions

    private String transactionId;
    private String accountNumber;
    private String type;
    private double amount;
    private double balanceAfter;
    private String timestamp;

    public Transaction(String accountNumber, String type, double amount, double balanceAfter) {
        transactionCounter++;
        this.transactionId = String.format("TXN%03d", transactionCounter);   // TXN001...
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a"));
    }

    // Shows the ID the NEXT transaction will get, without using up the counter
    public static String peekNextId() {
        return String.format("TXN%03d", transactionCounter + 1);
    }

    public String getTransactionId() { return transactionId; }
    public String getAccountNumber() { return accountNumber; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalanceAfter() { return balanceAfter; }
    public String getTimestamp() { return timestamp; }

    public void displayTransactionDetails() {
        String sign = type.equals("DEPOSIT") ? "+" : "-";
        System.out.printf("%-7s | %-19s | %-10s | %-11s | %s%n",
                transactionId, timestamp, type,
                sign + String.format("$%,.2f", amount),
                String.format("$%,.2f", balanceAfter));
    }
}
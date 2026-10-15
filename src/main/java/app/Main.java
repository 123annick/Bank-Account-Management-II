package app;

import model.*;
import service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AccountManager accountManager = new AccountManager();
    private static final TransactionManager transactionManager = new TransactionManager();

    public static void main(String[] args) {
        // Start with sample data, unless the reviewer passes --empty
        boolean empty = args.length > 0 && args[0].equals("--empty");
        if (!empty) {
            loadSampleData();
        }

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Enter choice: ", 1, 5);

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> {
                    accountManager.viewAllAccounts();
                    pause();
                }
                case 3 -> processTransaction();
                case 4 -> viewHistory();
                case 5 -> {
                    System.out.println("\nThank you for using Bank Account Management System!");
                    System.out.println("Goodbye!");
                    running = false;
                }
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   BANK ACCOUNT MANAGEMENT - MAIN MENU      ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        System.out.println("1. Create Account");
        System.out.println("2. View Accounts");
        System.out.println("3. Process Transaction");
        System.out.println("4. View Transaction History");
        System.out.println("5. Exit\n");
    }

    // 3 Savings + 2 Checking, as the lab requires
    private static void loadSampleData() {
        accountManager.addAccount(new SavingsAccount(
                new RegularCustomer("John Smith", 40, "+1-555-0001", "1 Main St"), 5250));
        accountManager.addAccount(new CheckingAccount(
                new RegularCustomer("Sarah Johnson", 34, "+1-555-0002", "2 Main St"), 3450));
        accountManager.addAccount(new SavingsAccount(
                new PremiumCustomer("Michael Chen", 45, "+1-555-0003", "3 Main St"), 15750));
        accountManager.addAccount(new CheckingAccount(
                new RegularCustomer("Emily Brown", 29, "+1-555-0004", "4 Main St"), 890));
        accountManager.addAccount(new SavingsAccount(
                new PremiumCustomer("David Wilson", 52, "+1-555-0005", "5 Main St"), 25300));
    }

    // ---------------- US-2: Create Account ----------------
    private static void createAccount() {
        System.out.println("\nACCOUNT CREATION");
        System.out.println("─".repeat(45) + "\n");

        String name = readNonEmpty("Enter customer name: ");
        int age = readInt("Enter customer age: ", 18, 120);
        String contact = readNonEmpty("Enter customer contact: ");
        String address = readNonEmpty("Enter customer address: ");

        System.out.println("\nCustomer type:");
        System.out.println("1. Regular Customer (Standard banking services)");
        System.out.println("2. Premium Customer (Enhanced benefits, min balance $10,000)");
        int customerType = readInt("\nSelect type (1-2): ", 1, 2);

        System.out.println("\nAccount type:");
        System.out.println("1. Savings Account (Interest: 3.5%, Min Balance: $500)");
        System.out.println("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");
        int accountType = readInt("\nSelect type (1-2): ", 1, 2);

        double deposit = readDouble("\nEnter initial deposit amount: $");

        // Validate BEFORE creating objects, otherwise the ID counters get used up for nothing
        if (customerType == 2 && deposit < 10000) {
            System.out.println("\n✗ Premium customers need an initial deposit of at least $10,000.");
            pause();
            return;
        }
        if (accountType == 1 && deposit < 500) {
            System.out.println("\n✗ Savings accounts need an initial deposit of at least $500.");
            pause();
            return;
        }

        Customer customer;
        if (customerType == 1) {
            customer = new RegularCustomer(name, age, contact, address);
        } else {
            customer = new PremiumCustomer(name, age, contact, address);
        }

        Account account;
        if (accountType == 1) {
            account = new SavingsAccount(customer, deposit);
        } else {
            account = new CheckingAccount(customer, deposit);
        }

        if (!accountManager.addAccount(account)) {
            System.out.println("\n✗ The bank is full. Cannot add more accounts.");
            pause();
            return;
        }

        System.out.println("\n✓ Account created successfully!");
        System.out.println("  Account Number: " + account.getAccountNumber());
        System.out.println("  Customer ID: " + customer.getCustomerId());
        System.out.println("  Customer: " + name + " (" + customer.getCustomerType() + ")");
        System.out.println("  Account Type: " + account.getAccountType());
        System.out.printf("  Initial Balance: $%,.2f%n", account.getBalance());

        if (account instanceof SavingsAccount s) {
            System.out.println("  Interest Rate: " + s.getInterestRate() + "%");
            System.out.printf("  Minimum Balance: $%,.2f%n", s.getMinimumBalance());
        } else if (account instanceof CheckingAccount c) {
            System.out.printf("  Overdraft Limit: $%,.2f%n", c.getOverdraftLimit());
            if (c.getMonthlyFee() == 0) {
                System.out.println("  Monthly Fee: $0.00 (WAIVED - Premium Customer)");
            } else {
                System.out.printf("  Monthly Fee: $%,.2f%n", c.getMonthlyFee());
            }
        }
        System.out.println("  Status: " + account.getStatus());
        pause();
    }

    // ---------------- US-3: Process Transaction ----------------
    private static void processTransaction() {
        System.out.println("\nPROCESS TRANSACTION");
        System.out.println("─".repeat(45) + "\n");

        String accNo = readNonEmpty("Enter Account Number: ");
        Account account = accountManager.findAccount(accNo);
        if (account == null) {
            System.out.println("\n✗ Account not found.");
            pause();
            return;
        }

        System.out.println("\nAccount Details:");
        System.out.println("Customer: " + account.getCustomer().getName());
        System.out.println("Account Type: " + account.getAccountType());
        System.out.printf("Current Balance: $%,.2f%n", account.getBalance());

        System.out.println("\nTransaction type:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        int choice = readInt("\nSelect type (1-2): ", 1, 2);
        String type = (choice == 1) ? "DEPOSIT" : "WITHDRAWAL";

        double amount = readDouble("\nEnter amount: $");

        double previous = account.getBalance();

        // Check the rules BEFORE showing the confirmation
        boolean allowed = type.equals("DEPOSIT") ? amount > 0 : account.canWithdraw(amount);
        if (!allowed) {
            System.out.println("\n✗ Transaction not allowed: insufficient funds, "
                    + "minimum balance, or overdraft limit.");
            pause();
            return;
        }

        double newBalance = type.equals("DEPOSIT") ? previous + amount : previous - amount;
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a"));

        System.out.println("\nTRANSACTION CONFIRMATION");
        System.out.println("─".repeat(45));
        System.out.println("Transaction ID: " + Transaction.peekNextId());
        System.out.println("Account: " + account.getAccountNumber());
        System.out.println("Type: " + type);
        System.out.printf("Amount: $%,.2f%n", amount);
        System.out.printf("Previous Balance: $%,.2f%n", previous);
        System.out.printf("New Balance: $%,.2f%n", newBalance);
        System.out.println("Date/Time: " + now);
        System.out.println("─".repeat(45));

        System.out.print("\nConfirm transaction? (Y/N): ");
        String answer = scanner.nextLine().trim();
        if (!answer.equalsIgnoreCase("Y")) {
            System.out.println("\nTransaction cancelled.");
            pause();
            return;
        }

        if (account.processTransaction(amount, type)) {
            Transaction t = new Transaction(account.getAccountNumber(), type, amount, account.getBalance());
            transactionManager.addTransaction(t);
            System.out.println("\n✓ Transaction completed successfully!");
        } else {
            System.out.println("\n✗ Transaction failed.");
        }
        pause();
    }

    // ---------------- US-4: View Transaction History ----------------
    private static void viewHistory() {
        System.out.println("\nVIEW TRANSACTION HISTORY");
        System.out.println("─".repeat(25) + "\n");

        String accNo = readNonEmpty("Enter Account Number: ");
        Account account = accountManager.findAccount(accNo);
        if (account == null) {
            System.out.println("\n✗ Account not found.");
            pause();
            return;
        }

        System.out.println("\nAccount: " + account.getAccountNumber()
                + " - " + account.getCustomer().getName());
        System.out.println("Account Type: " + account.getAccountType());
        System.out.printf("Current Balance: $%,.2f%n%n", account.getBalance());

        transactionManager.viewTransactionsByAccount(account.getAccountNumber());
        pause();
    }

    // ---------------- Input helpers (validation) ----------------
    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // fall through to the message below
            }
            System.out.println("Invalid input. Please enter a number from " + min + " to " + max + ".");
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim().replace(",", ""));
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // fall through to the message below
            }
            System.out.println("Invalid amount. Please enter a positive number.");
        }
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = scanner.nextLine().trim();
            if (!text.isEmpty()) {
                return text;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    private static void pause() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
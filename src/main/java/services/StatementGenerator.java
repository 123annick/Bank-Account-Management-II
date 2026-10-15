package services;

import models.Account;
import models.Transaction;
import utils.FormatUtils;

public class StatementGenerator {
  private static final String SEPARATOR = "─".repeat(72);

  private final TransactionHistory transactionHistory;

  /**
   * Creates a generator that reads transactions from the given history.
   *
   * @param transactionHistory where the transactions are read from
   */
  public StatementGenerator(TransactionHistory transactionHistory) {
    this.transactionHistory = transactionHistory;
  }

  /**
   * Builds a statement with the account summary, transactions (newest first) and totals.
   *
   * @param account the account to describe
   * @return the statement text, ready to print
   */
  public String generateStatement(Account account) {
    Transaction[] transactions =
        transactionHistory.getTransactionsFor(account.getAccountNumber());
    StringBuilder statement = new StringBuilder();
    appendHeader(statement, account);
    if (transactions.length == 0) {
      statement.append("No transactions recorded for this account.\n");
      return statement.toString();
    }
    appendTransactions(statement, transactions);
    appendSummary(statement, transactions);
    return statement.toString();
  }

  /**
   * Adds up all money that came into the account.
   *
   * @param transactions the transactions to add up
   * @return the total of deposits and transfers in
   */
  public double calculateTotalDeposits(Transaction[] transactions) {
    double total = 0;
    for (Transaction transaction : transactions) {
      if (transaction.getType().isCredit()) {
        total += transaction.getAmount();
      }
    }
    return total;
  }

  /**
   * Adds up all money that left the account.
   *
   * @param transactions the transactions to add up
   * @return the total of withdrawals and transfers out
   */
  public double calculateTotalWithdrawals(Transaction[] transactions) {
    double total = 0;
    for (Transaction transaction : transactions) {
      if (!transaction.getType().isCredit()) {
        total += transaction.getAmount();
      }
    }
    return total;
  }

  private void appendHeader(StringBuilder statement, Account account) {
    statement.append(String.format("Account: %s - %s (%s)%n", account.getAccountNumber(),
        account.getCustomer().getName(), account.getAccountType().getDisplayName()));
    statement.append("Current Balance: ")
        .append(FormatUtils.currency(account.getBalance())).append("\n\n");
    statement.append("Transactions:\n").append(SEPARATOR).append("\n");
  }

  private void appendTransactions(StringBuilder statement, Transaction[] transactions) {
    for (int i = transactions.length - 1; i >= 0; i--) {
      Transaction transaction = transactions[i];
      statement.append(String.format("%-7s | %-19s | %-12s | %-12s | %s%n",
          transaction.getTransactionId(),
          FormatUtils.dateTime(transaction.getTimestamp()),
          transaction.getType(),
          FormatUtils.signedCurrency(transaction.getSignedAmount()),
          FormatUtils.currency(transaction.getBalanceAfter())));
    }
    statement.append(SEPARATOR).append("\n");
  }

  private void appendSummary(StringBuilder statement, Transaction[] transactions) {
    double deposits = calculateTotalDeposits(transactions);
    double withdrawals = calculateTotalWithdrawals(transactions);
    statement.append("Total Transactions: ").append(transactions.length).append("\n");
    statement.append("Total Deposits: ").append(FormatUtils.currency(deposits)).append("\n");
    statement.append("Total Withdrawals: ")
        .append(FormatUtils.currency(withdrawals)).append("\n");
    statement.append("Net Change: ")
        .append(FormatUtils.signedCurrency(deposits - withdrawals)).append("\n");
  }
}

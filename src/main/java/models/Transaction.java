package models;

import java.time.LocalDateTime;

public class Transaction {
  private final String transactionId;
  private final String accountNumber;
  private final TransactionType type;
  private final double amount;
  private final double balanceAfter;
  private final LocalDateTime timestamp;

  /**
   * Creates a record of one completed transaction.
   *
   * @param transactionId the unique transaction id, for example TXN001
   * @param accountNumber the account the transaction belongs to
   * @param type the kind of transaction
   * @param amount the amount moved
   * @param balanceAfter the account balance after the transaction
   * @param timestamp when the transaction happened
   */
  public Transaction(String transactionId, String accountNumber, TransactionType type,
      double amount, double balanceAfter, LocalDateTime timestamp) {
    this.transactionId = transactionId;
    this.accountNumber = accountNumber;
    this.type = type;
    this.amount = amount;
    this.balanceAfter = balanceAfter;
    this.timestamp = timestamp;
  }

  /**
   * Returns the unique transaction id.
   *
   * @return the transaction id
   */
  public String getTransactionId() {
    return transactionId;
  }

  /**
   * Returns the account number this transaction belongs to.
   *
   * @return the account number
   */
  public String getAccountNumber() {
    return accountNumber;
  }

  /**
   * Returns the kind of transaction.
   *
   * @return the transaction type
   */
  public TransactionType getType() {
    return type;
  }

  /**
   * Returns the amount moved.
   *
   * @return the amount
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Returns the balance right after this transaction.
   *
   * @return the balance after
   */
  public double getBalanceAfter() {
    return balanceAfter;
  }

  /**
   * Returns when the transaction happened.
   *
   * @return the timestamp
   */
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  /**
   * Returns the amount with a plus sign for money in and a minus sign for money out.
   *
   * @return the signed amount
   */
  public double getSignedAmount() {
    return type.isCredit() ? amount : -amount;
  }
}

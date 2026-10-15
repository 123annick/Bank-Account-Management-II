package services;

import java.time.LocalDateTime;
import models.Account;
import models.Transaction;
import models.TransactionType;
import models.exceptions.BankException;
import models.exceptions.InvalidAccountException;
import models.exceptions.StorageFullException;
import utils.IdGenerator;

public class TransactionManager implements TransactionHistory {
  private static final int MAX_TRANSACTIONS = 200;

  private final Transaction[] transactions = new Transaction[MAX_TRANSACTIONS];
  private final IdGenerator transactionIds = new IdGenerator("TXN");
  private int transactionCount;

  /**
   * Deposits money into an account and records the transaction.
   *
   * @param account the account receiving the money
   * @param amount the amount to deposit
   * @return the recorded transaction
   * @throws BankException when the amount is invalid or the log is full
   */
  public Transaction deposit(Account account, double amount) throws BankException {
    validateTransaction(1);
    account.deposit(amount);
    return applyTransaction(account, TransactionType.DEPOSIT, amount);
  }

  /**
   * Withdraws money from an account and records the transaction.
   *
   * @param account the account giving the money
   * @param amount the amount to withdraw
   * @return the recorded transaction
   * @throws BankException when the amount is invalid, a balance rule is broken or the log is full
   */
  public Transaction withdraw(Account account, double amount) throws BankException {
    validateTransaction(1);
    account.withdraw(amount);
    return applyTransaction(account, TransactionType.WITHDRAWAL, amount);
  }

  /**
   * Moves money from one account to another and records both sides.
   *
   * @param source the account giving the money
   * @param target the account receiving the money
   * @param amount the amount to move
   * @throws BankException when the transfer breaks any rule; no balance changes in that case
   */
  public void transfer(Account source, Account target, double amount) throws BankException {
    validateTransfer(source, target, amount);
    validateTransaction(2);
    source.withdraw(amount);
    target.deposit(amount);
    applyTransaction(source, TransactionType.TRANSFER_OUT, amount);
    applyTransaction(target, TransactionType.TRANSFER_IN, amount);
  }

  /**
   * Checks a transfer without moving any money.
   *
   * @param source the account giving the money
   * @param target the account receiving the money
   * @param amount the amount to move
   * @throws BankException when the accounts are the same or the source cannot pay
   */
  public void validateTransfer(Account source, Account target, double amount)
      throws BankException {
    if (source.getAccountNumber().equals(target.getAccountNumber())) {
      throw new InvalidAccountException("Cannot transfer money to the same account.");
    }
    source.checkWithdrawal(amount);
  }

  /**
   * Returns the transactions of one account in the order they were recorded.
   *
   * @param accountNumber the account to look for
   * @return a new array with only that account's transactions
   */
  @Override
  public Transaction[] getTransactionsFor(String accountNumber) {
    Transaction[] result = new Transaction[countTransactionsFor(accountNumber)];
    int index = 0;
    for (int i = 0; i < transactionCount; i++) {
      if (belongsTo(transactions[i], accountNumber)) {
        result[index] = transactions[i];
        index++;
      }
    }
    return result;
  }

  /**
   * Returns how many transactions are recorded for all accounts.
   *
   * @return the number of transactions
   */
  public int getTransactionCount() {
    return transactionCount;
  }

  /**
   * Returns the id the next recorded transaction will get.
   *
   * @return the upcoming transaction id
   */
  public String peekNextTransactionId() {
    return transactionIds.peek();
  }

  private void validateTransaction(int slotsNeeded) throws StorageFullException {
    if (transactionCount + slotsNeeded > MAX_TRANSACTIONS) {
      throw new StorageFullException("Transaction log is full. Cannot record more transactions.");
    }
  }

  private Transaction applyTransaction(Account account, TransactionType type, double amount) {
    Transaction transaction = new Transaction(transactionIds.next(),
        account.getAccountNumber(), type, amount, account.getBalance(), LocalDateTime.now());
    transactions[transactionCount] = transaction;
    transactionCount++;
    return transaction;
  }

  private int countTransactionsFor(String accountNumber) {
    int count = 0;
    for (int i = 0; i < transactionCount; i++) {
      if (belongsTo(transactions[i], accountNumber)) {
        count++;
      }
    }
    return count;
  }

  private boolean belongsTo(Transaction transaction, String accountNumber) {
    return transaction.getAccountNumber().equalsIgnoreCase(accountNumber);
  }
}

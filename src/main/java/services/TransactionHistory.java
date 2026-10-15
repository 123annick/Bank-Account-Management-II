package services;

import models.Transaction;

public interface TransactionHistory {

  /**
   * Returns the transactions of one account in the order they were recorded.
   *
   * @param accountNumber the account to look for
   * @return a new array with only that account's transactions
   */
  Transaction[] getTransactionsFor(String accountNumber);
}

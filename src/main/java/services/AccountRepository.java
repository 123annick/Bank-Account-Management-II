package services;

import models.Account;
import models.exceptions.InvalidAccountException;
import models.exceptions.StorageFullException;

public interface AccountRepository {

  /**
   * Stores a new account.
   *
   * @param account the account to store
   * @throws StorageFullException when there is no space left
   */
  void addAccount(Account account) throws StorageFullException;

  /**
   * Finds an account by its number, ignoring upper or lower case.
   *
   * @param accountNumber the number to look for, for example ACC001
   * @return the matching account
   * @throws InvalidAccountException when no account has that number
   */
  Account findAccount(String accountNumber) throws InvalidAccountException;

  /**
   * Returns all stored accounts in the order they were added.
   *
   * @return a new array containing every account
   */
  Account[] getAllAccounts();

  /**
   * Returns how many accounts are stored.
   *
   * @return the number of accounts
   */
  int getAccountCount();

  /**
   * Tells whether no more accounts can be stored.
   *
   * @return true when storage is full
   */
  boolean isFull();

  /**
   * Adds up the balances of all accounts.
   *
   * @return the total bank balance
   */
  double getTotalBalance();
}

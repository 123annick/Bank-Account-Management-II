package services;

import java.util.Arrays;
import models.Account;
import models.exceptions.InvalidAccountException;
import models.exceptions.StorageFullException;

public class AccountManager implements AccountRepository {
  private static final int MAX_ACCOUNTS = 50;

  private final Account[] accounts = new Account[MAX_ACCOUNTS];
  private int accountCount;

  /**
   * Stores a new account at the next free position of the array.
   *
   * @param account the account to store
   * @throws StorageFullException when all positions are used
   */
  @Override
  public void addAccount(Account account) throws StorageFullException {
    if (isFull()) {
      throw new StorageFullException("The bank is full. Cannot add more accounts.");
    }
    accounts[accountCount] = account;
    accountCount++;
  }

  /**
   * Finds an account with a linear search, checking each stored account in turn.
   *
   * @param accountNumber the number to look for
   * @return the matching account
   * @throws InvalidAccountException when no account has that number
   */
  @Override
  public Account findAccount(String accountNumber) throws InvalidAccountException {
    for (int i = 0; i < accountCount; i++) {
      if (accounts[i].getAccountNumber().equalsIgnoreCase(accountNumber.trim())) {
        return accounts[i];
      }
    }
    throw new InvalidAccountException(
        "Account not found. Please check the account number and try again.");
  }

  /**
   * Returns a copy of the used part of the array.
   *
   * @return every stored account
   */
  @Override
  public Account[] getAllAccounts() {
    return Arrays.copyOf(accounts, accountCount);
  }

  /**
   * Returns how many accounts are stored.
   *
   * @return the number of accounts
   */
  @Override
  public int getAccountCount() {
    return accountCount;
  }

  /**
   * Tells whether every position of the array is used.
   *
   * @return true when storage is full
   */
  @Override
  public boolean isFull() {
    return accountCount >= MAX_ACCOUNTS;
  }

  /**
   * Adds up the balances of all accounts.
   *
   * @return the total bank balance
   */
  @Override
  public double getTotalBalance() {
    double total = 0;
    for (int i = 0; i < accountCount; i++) {
      total += accounts[i].getBalance();
    }
    return total;
  }
}

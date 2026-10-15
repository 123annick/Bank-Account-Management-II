package services;

import models.AccountType;
import models.CustomerDetails;
import models.CustomerType;
import models.exceptions.BankException;

public final class SampleDataLoader {

  private SampleDataLoader() {
  }

  /**
   * Adds the five Week 1 sample accounts: three savings and two checking.
   *
   * @param accounts where the accounts are stored
   * @param factory used to create the accounts with new ids
   * @throws BankException when a sample account cannot be created or stored
   */
  public static void load(AccountRepository accounts, AccountFactory factory)
      throws BankException {
    accounts.addAccount(factory.createAccount(
        new CustomerDetails("John Smith", 40, "+1-555-0001", "1 Main St"),
        CustomerType.REGULAR, AccountType.SAVINGS, 5250));
    accounts.addAccount(factory.createAccount(
        new CustomerDetails("Sarah Johnson", 34, "+1-555-0002", "2 Main St"),
        CustomerType.REGULAR, AccountType.CHECKING, 3450));
    accounts.addAccount(factory.createAccount(
        new CustomerDetails("Michael Chen", 45, "+1-555-0003", "3 Main St"),
        CustomerType.PREMIUM, AccountType.SAVINGS, 15750));
    accounts.addAccount(factory.createAccount(
        new CustomerDetails("Emily Brown", 29, "+1-555-0004", "4 Main St"),
        CustomerType.REGULAR, AccountType.CHECKING, 890));
    accounts.addAccount(factory.createAccount(
        new CustomerDetails("David Wilson", 52, "+1-555-0005", "5 Main St"),
        CustomerType.PREMIUM, AccountType.SAVINGS, 25300));
  }
}

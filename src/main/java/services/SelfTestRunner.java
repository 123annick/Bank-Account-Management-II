package services;

import models.Account;
import models.CheckingAccount;
import models.Customer;
import models.CustomerDetails;
import models.RegularCustomer;
import models.SavingsAccount;
import models.exceptions.BankException;
import models.exceptions.InsufficientFundsException;
import models.exceptions.InvalidAmountException;
import models.exceptions.OverdraftExceededException;

public class SelfTestRunner {

  /**
   * Runs quick checks of the core rules on fresh test accounts that do not affect real data.
   *
   * @return one result for each check
   */
  public TestResult[] runAll() {
    return new TestResult[] {
        new TestResult("depositUpdatesBalance", depositUpdatesBalance()),
        new TestResult("negativeDepositThrowsException", negativeDepositThrowsException()),
        new TestResult("withdrawBelowMinimumThrowsException",
            withdrawBelowMinimumThrowsException()),
        new TestResult("overdraftWithinLimitAllowed", overdraftWithinLimitAllowed()),
        new TestResult("overdraftExceedThrowsException", overdraftExceedThrowsException()),
        new TestResult("transferUpdatesBothAccounts", transferUpdatesBothAccounts())
    };
  }

  private boolean depositUpdatesBalance() {
    try {
      Account account = newSavingsAccount(1000);
      new TransactionManager().deposit(account, 500);
      return account.getBalance() == 1500;
    } catch (BankException e) {
      return false;
    }
  }

  private boolean negativeDepositThrowsException() {
    try {
      newSavingsAccount(1000).deposit(-200);
      return false;
    } catch (InvalidAmountException e) {
      return true;
    }
  }

  private boolean withdrawBelowMinimumThrowsException() {
    try {
      newSavingsAccount(1000).withdraw(600);
      return false;
    } catch (InsufficientFundsException e) {
      return true;
    } catch (BankException e) {
      return false;
    }
  }

  private boolean overdraftWithinLimitAllowed() {
    try {
      Account account = newCheckingAccount(500);
      account.withdraw(1200);
      return account.getBalance() == -700;
    } catch (BankException e) {
      return false;
    }
  }

  private boolean overdraftExceedThrowsException() {
    try {
      newCheckingAccount(500).withdraw(1600);
      return false;
    } catch (OverdraftExceededException e) {
      return true;
    } catch (BankException e) {
      return false;
    }
  }

  private boolean transferUpdatesBothAccounts() {
    try {
      Account source = newSavingsAccount(2000);
      Account target = newCheckingAccount(100);
      new TransactionManager().transfer(source, target, 700);
      return source.getBalance() == 1300 && target.getBalance() == 800;
    } catch (BankException e) {
      return false;
    }
  }

  private Account newSavingsAccount(double balance) {
    return new SavingsAccount("TEST001", newCustomer(), balance);
  }

  private Account newCheckingAccount(double balance) {
    return new CheckingAccount("TEST002", newCustomer(), balance);
  }

  private Customer newCustomer() {
    return new RegularCustomer("TESTCUS", new CustomerDetails("Test User", 30, "000", "Test St"));
  }
}

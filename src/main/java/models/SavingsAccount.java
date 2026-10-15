package models;

import models.exceptions.InsufficientFundsException;
import utils.FormatUtils;

public class SavingsAccount extends Account implements InterestBearing {
  public static final double MINIMUM_BALANCE = 500;
  public static final double INTEREST_RATE = 3.5;

  /**
   * Creates a savings account.
   *
   * @param accountNumber the unique account number
   * @param customer the owner of the account
   * @param initialBalance the opening balance
   */
  public SavingsAccount(String accountNumber, Customer customer, double initialBalance) {
    super(accountNumber, customer, initialBalance);
  }

  /**
   * Returns the balance that must always stay in the account.
   *
   * @return the minimum balance
   */
  public double getMinimumBalance() {
    return MINIMUM_BALANCE;
  }

  /**
   * Returns the yearly interest rate as a percentage.
   *
   * @return the interest rate
   */
  @Override
  public double getInterestRate() {
    return INTEREST_RATE;
  }

  /**
   * Calculates the interest earned in one year on the current balance.
   *
   * @return the yearly interest amount
   */
  @Override
  public double calculateInterest() {
    return getBalance() * INTEREST_RATE / 100;
  }

  /**
   * Returns {@link AccountType#SAVINGS}.
   *
   * @return the account type
   */
  @Override
  public AccountType getAccountType() {
    return AccountType.SAVINGS;
  }

  /**
   * Describes the interest rate and minimum balance.
   *
   * @return the account features as text
   */
  @Override
  public String getAccountFeatures() {
    return String.format("Interest Rate: %.1f%% | Min Balance: %s",
        INTEREST_RATE, FormatUtils.currency(MINIMUM_BALANCE));
  }

  @Override
  protected void validateWithdrawalRules(double amount) throws InsufficientFundsException {
    if (getBalance() - amount < MINIMUM_BALANCE) {
      throw new InsufficientFundsException(String.format(
          "Insufficient funds. Current balance: %s. A minimum balance of %s must remain.",
          FormatUtils.currency(getBalance()), FormatUtils.currency(MINIMUM_BALANCE)));
    }
  }
}

package models;

import models.exceptions.InsufficientFundsException;
import models.exceptions.InvalidAmountException;
import models.exceptions.OverdraftExceededException;

public interface Transactable {

  /**
   * Adds money to the balance.
   *
   * @param amount the amount to add, must be greater than zero
   * @throws InvalidAmountException when the amount is zero or negative
   */
  void deposit(double amount) throws InvalidAmountException;

  /**
   * Takes money from the balance when the account rules allow it.
   *
   * @param amount the amount to take, must be greater than zero
   * @throws InvalidAmountException when the amount is zero or negative
   * @throws InsufficientFundsException when a minimum balance would be broken
   * @throws OverdraftExceededException when the overdraft limit would be passed
   */
  void withdraw(double amount)
      throws InvalidAmountException, InsufficientFundsException, OverdraftExceededException;
}

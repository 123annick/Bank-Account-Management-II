package models.exceptions;

public class InsufficientFundsException extends BankException {

  /**
   * Creates the exception with a message that can be shown to the user.
   *
   * @param message the user-friendly error message
   */
  public InsufficientFundsException(String message) {
    super(message);
  }
}

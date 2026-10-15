package models.exceptions;

public class InvalidAmountException extends BankException {

  /**
   * Creates the exception with a message that can be shown to the user.
   *
   * @param message the user-friendly error message
   */
  public InvalidAmountException(String message) {
    super(message);
  }
}

package models.exceptions;

public class InvalidAccountException extends BankException {

  /**
   * Creates the exception with a message that can be shown to the user.
   *
   * @param message the user-friendly error message
   */
  public InvalidAccountException(String message) {
    super(message);
  }
}

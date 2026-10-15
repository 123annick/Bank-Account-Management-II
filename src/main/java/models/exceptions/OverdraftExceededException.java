package models.exceptions;

public class OverdraftExceededException extends BankException {

  /**
   * Creates the exception with a message that can be shown to the user.
   *
   * @param message the user-friendly error message
   */
  public OverdraftExceededException(String message) {
    super(message);
  }
}

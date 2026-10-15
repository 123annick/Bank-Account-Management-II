package models.exceptions;

public class BankException extends Exception {

  /**
   * Creates the exception with a message that can be shown to the user.
   *
   * @param message the user-friendly error message
   */
  public BankException(String message) {
    super(message);
  }
}

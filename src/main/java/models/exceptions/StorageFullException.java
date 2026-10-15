package models.exceptions;

public class StorageFullException extends BankException {

  /**
   * Creates the exception with a message that can be shown to the user.
   *
   * @param message the user-friendly error message
   */
  public StorageFullException(String message) {
    super(message);
  }
}

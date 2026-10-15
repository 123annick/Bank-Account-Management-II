package ui;

public class ConsoleOutput {
  private static final String SEPARATOR = "─".repeat(45);

  /**
   * Prints one line of text.
   *
   * @param text the text to print
   */
  public void println(String text) {
    System.out.println(text);
  }

  /**
   * Prints a section title followed by a separator line.
   *
   * @param title the title to print
   */
  public void printTitle(String title) {
    System.out.println();
    System.out.println(title);
    System.out.println(SEPARATOR);
  }

  /**
   * Prints a separator line.
   */
  public void printSeparator() {
    System.out.println(SEPARATOR);
  }

  /**
   * Prints an input or lookup error.
   *
   * @param message the error message
   */
  public void printError(String message) {
    System.out.println("❌ Error: " + message);
  }

  /**
   * Prints a transaction that was refused by the bank rules.
   *
   * @param message the reason for the failure
   */
  public void printTransactionFailure(String message) {
    System.out.println("❌ Transaction Failed: " + message);
  }

  /**
   * Prints a success message.
   *
   * @param message the success message
   */
  public void printSuccess(String message) {
    System.out.println("✓ " + message);
  }
}

package ui;

public class BankConsoleApp {
  private final ConsoleInput input;
  private final ConsoleOutput output;
  private final MenuAction[] menuActions;

  /**
   * Creates the application with the options shown in the main menu.
   *
   * @param input reads the keyboard
   * @param output prints messages
   * @param menuActions the main menu options, in display order
   */
  public BankConsoleApp(ConsoleInput input, ConsoleOutput output, MenuAction[] menuActions) {
    this.input = input;
    this.output = output;
    this.menuActions = menuActions;
  }

  /**
   * Shows the main menu again and again until the user chooses Exit.
   */
  public void run() {
    int exitOption = menuActions.length + 1;
    int choice = showMainMenu();
    while (choice != exitOption) {
      menuActions[choice - 1].execute();
      input.pause();
      choice = showMainMenu();
    }
    printGoodbye();
  }

  private int showMainMenu() {
    output.println("\n╔════════════════════════════════════════════╗");
    output.println("║      BANK ACCOUNT MANAGEMENT SYSTEM        ║");
    output.println("╚════════════════════════════════════════════╝");
    output.println("\nMain Menu:");
    output.println("-----------");
    for (int i = 0; i < menuActions.length; i++) {
      output.println((i + 1) + ". " + menuActions[i].getTitle());
    }
    int exitOption = menuActions.length + 1;
    output.println(exitOption + ". Exit");
    return input.readInt("\nEnter your choice: ", 1, exitOption);
  }

  private void printGoodbye() {
    output.println("\nThank you for using the Bank Account Management System!");
    output.println("All data saved in memory. Remember to commit your latest changes to Git!");
    output.println("Goodbye!");
  }
}

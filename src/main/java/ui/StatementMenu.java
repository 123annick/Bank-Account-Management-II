package ui;

import models.Account;
import services.StatementGenerator;

public class StatementMenu implements MenuAction {
  private final ConsoleOutput output;
  private final AccountSelector accountSelector;
  private final StatementGenerator statementGenerator;

  /**
   * Creates the Generate Account Statements menu.
   *
   * @param output prints messages
   * @param accountSelector asks the user to pick an account
   * @param statementGenerator builds the statement text
   */
  public StatementMenu(ConsoleOutput output, AccountSelector accountSelector,
      StatementGenerator statementGenerator) {
    this.output = output;
    this.accountSelector = accountSelector;
    this.statementGenerator = statementGenerator;
  }

  /**
   * Returns the main menu title of this option.
   *
   * @return the title
   */
  @Override
  public String getTitle() {
    return "Generate Account Statements";
  }

  /**
   * Asks for an account and prints its statement.
   */
  @Override
  public void execute() {
    output.printTitle("GENERATE ACCOUNT STATEMENT");
    Account account = accountSelector.selectAccount("Enter Account Number");
    if (account == null) {
      return;
    }
    output.println("");
    output.println(statementGenerator.generateStatement(account));
    output.printSuccess("Statement generated successfully.");
  }
}

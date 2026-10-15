import java.util.Scanner;
import models.exceptions.BankException;
import services.AccountFactory;
import services.AccountManager;
import services.AccountRepository;
import services.SampleDataLoader;
import services.StatementGenerator;
import services.TransactionManager;
import ui.AccountMenu;
import ui.AccountSelector;
import ui.BankConsoleApp;
import ui.ConsoleInput;
import ui.ConsoleOutput;
import ui.MenuAction;
import ui.StatementMenu;
import ui.TransactionMenu;

public class Main {
  private static final String EMPTY_START_FLAG = "--empty";

  /**
   * Builds all objects, loads sample data unless --empty is passed, and starts the menu.
   *
   * @param args use --empty to start without sample accounts
   */
  public static void main(String[] args) {
    AccountRepository accounts = new AccountManager();
    AccountFactory accountFactory = new AccountFactory();
    TransactionManager transactionManager = new TransactionManager();
    ConsoleOutput output = new ConsoleOutput();
    if (!isEmptyStart(args)) {
      loadSampleData(accounts, accountFactory, output);
    }
    ConsoleInput input = new ConsoleInput(new Scanner(System.in), output);
    AccountSelector accountSelector = new AccountSelector(input, output, accounts);
    MenuAction[] menuActions = {
        new AccountMenu(input, output, accounts, accountFactory),
        new TransactionMenu(input, output, accountSelector, transactionManager),
        new StatementMenu(output, accountSelector, new StatementGenerator(transactionManager))
    };
    new BankConsoleApp(input, output, menuActions).run();
  }

  private static boolean isEmptyStart(String[] args) {
    return args.length > 0 && args[0].equals(EMPTY_START_FLAG);
  }

  private static void loadSampleData(AccountRepository accounts, AccountFactory accountFactory,
      ConsoleOutput output) {
    try {
      SampleDataLoader.load(accounts, accountFactory);
    } catch (BankException e) {
      output.printError("Could not load sample data. " + e.getMessage());
    }
  }
}

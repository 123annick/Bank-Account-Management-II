import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

import models.Account;
import models.AccountType;
import models.CustomerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import services.AccountFactory;
import services.AccountManager;

@ExtendWith(ConsoleTestLogger.class)
class AccountManagerTest {
  private AccountManager accountManager;
  private AccountFactory accountFactory;

  @BeforeEach
  void setUp() {
    accountManager = new AccountManager();
    accountFactory = new AccountFactory();
  }

  @Test
  void factoryGeneratesSequentialIds() throws Exception {
    Account first = createRegularChecking("Ann", 100);
    Account second = createRegularChecking("Ben", 200);
    assertEquals("ACC001", first.getAccountNumber());
    assertEquals("ACC002", second.getAccountNumber());
    assertEquals("CUS002", second.getCustomer().getCustomerId());
  }

  @Test
  void findAccountIgnoresCaseAndSpaces() throws Exception {
    Account account = createRegularChecking("Ann", 100);
    accountManager.addAccount(account);
    assertSame(account, accountManager.findAccount("  acc001 "));
  }

  @Test
  void totalBalanceAddsAllAccounts() throws Exception {
    accountManager.addAccount(createRegularChecking("Ann", 100.25));
    accountManager.addAccount(createRegularChecking("Ben", 200.50));
    assertEquals(300.75, accountManager.getTotalBalance(), 0.001);
    assertEquals(2, accountManager.getAccountCount());
  }

  @Test
  void getAllAccountsKeepsInsertionOrder() throws Exception {
    Account first = createRegularChecking("Ann", 100);
    Account second = createRegularChecking("Ben", 200);
    accountManager.addAccount(first);
    accountManager.addAccount(second);
    Account[] all = accountManager.getAllAccounts();
    assertEquals(2, all.length);
    assertSame(first, all[0]);
    assertSame(second, all[1]);
  }

  @Test
  void newManagerIsNotFull() {
    assertFalse(accountManager.isFull());
  }

  private Account createRegularChecking(String name, double deposit) throws Exception {
    return accountFactory.createAccount(TestData.customerDetails(name), CustomerType.REGULAR,
        AccountType.CHECKING, deposit);
  }
}

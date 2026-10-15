import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import models.CheckingAccount;
import models.SavingsAccount;
import models.exceptions.InsufficientFundsException;
import models.exceptions.InvalidAmountException;
import models.exceptions.OverdraftExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ConsoleTestLogger.class)
class AccountTest {
  private SavingsAccount savings;
  private CheckingAccount checking;

  @BeforeEach
  void setUp() {
    savings = TestData.savingsAccount(1000);
    checking = TestData.checkingAccount(500);
  }

  @Test
  void depositUpdatesBalance() throws Exception {
    savings.deposit(500);
    assertEquals(1500, savings.getBalance());
  }

  @Test
  void negativeDepositThrowsInvalidAmountException() {
    assertThrows(InvalidAmountException.class, () -> savings.deposit(-200));
    assertEquals(1000, savings.getBalance());
  }

  @Test
  void zeroDepositThrowsInvalidAmountException() {
    assertThrows(InvalidAmountException.class, () -> savings.deposit(0));
  }

  @Test
  void withdrawWithinSavingsRulesUpdatesBalance() throws Exception {
    savings.withdraw(500);
    assertEquals(500, savings.getBalance());
  }

  @Test
  void withdrawBelowMinimumThrowsException() {
    assertThrows(InsufficientFundsException.class, () -> savings.withdraw(600));
    assertEquals(1000, savings.getBalance());
  }

  @Test
  void negativeWithdrawalThrowsInvalidAmountException() {
    assertThrows(InvalidAmountException.class, () -> checking.withdraw(-50));
  }

  @Test
  void overdraftWithinLimitAllowed() throws Exception {
    checking.withdraw(1200);
    assertEquals(-700, checking.getBalance());
  }

  @Test
  void overdraftExactlyAtLimitAllowed() throws Exception {
    checking.withdraw(1500);
    assertEquals(-1000, checking.getBalance());
  }

  @Test
  void overdraftExceedThrowsException() {
    assertThrows(OverdraftExceededException.class, () -> checking.withdraw(1500.01));
    assertEquals(500, checking.getBalance());
  }

  @Test
  void checkWithdrawalDoesNotChangeBalance() throws Exception {
    savings.checkWithdrawal(100);
    assertEquals(1000, savings.getBalance());
  }

  @Test
  void regularCustomerPaysMonthlyFee() {
    checking.applyMonthlyFee();
    assertEquals(10, checking.getMonthlyFee());
    assertEquals(490, checking.getBalance());
  }

  @Test
  void premiumCustomerHasMonthlyFeeWaived() {
    CheckingAccount premiumChecking =
        new CheckingAccount("ACC003", TestData.premiumCustomer(), 20000);
    premiumChecking.applyMonthlyFee();
    assertEquals(0, premiumChecking.getMonthlyFee());
    assertEquals(20000, premiumChecking.getBalance());
  }

  @Test
  void savingsInterestIsCalculatedOnBalance() {
    SavingsAccount rich = TestData.savingsAccount(10000);
    assertEquals(350, rich.calculateInterest(), 0.001);
  }
}

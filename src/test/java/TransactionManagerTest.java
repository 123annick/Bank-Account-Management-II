import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import models.Account;
import models.Transaction;
import models.TransactionType;
import models.exceptions.InsufficientFundsException;
import models.exceptions.InvalidAccountException;
import models.exceptions.InvalidAmountException;
import models.exceptions.OverdraftExceededException;
import models.exceptions.StorageFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import services.TransactionManager;

@ExtendWith(ConsoleTestLogger.class)
class TransactionManagerTest {
  private TransactionManager transactionManager;
  private Account savings;
  private Account checking;

  @BeforeEach
  void setUp() {
    transactionManager = new TransactionManager();
    savings = TestData.savingsAccount(2000);
    checking = TestData.checkingAccount(500);
  }

  @Test
  void depositUpdatesBalanceAndRecordsTransaction() throws Exception {
    Transaction transaction = transactionManager.deposit(savings, 1500);
    assertEquals(3500, savings.getBalance());
    assertEquals(TransactionType.DEPOSIT, transaction.getType());
    assertEquals(3500, transaction.getBalanceAfter());
    assertEquals(1, transactionManager.getTransactionCount());
  }

  @Test
  void invalidDepositIsNotRecorded() {
    assertThrows(InvalidAmountException.class, () -> transactionManager.deposit(savings, -200));
    assertEquals(0, transactionManager.getTransactionCount());
  }

  @Test
  void withdrawUpdatesBalanceAndRecordsTransaction() throws Exception {
    Transaction transaction = transactionManager.withdraw(savings, 750);
    assertEquals(1250, savings.getBalance());
    assertEquals(TransactionType.WITHDRAWAL, transaction.getType());
    assertEquals(1, transactionManager.getTransactionCount());
  }

  @Test
  void failedWithdrawalIsNotRecorded() {
    assertThrows(OverdraftExceededException.class,
        () -> transactionManager.withdraw(checking, 10000));
    assertEquals(500, checking.getBalance());
    assertEquals(0, transactionManager.getTransactionCount());
  }

  @Test
  void transferUpdatesBothAccounts() throws Exception {
    transactionManager.transfer(savings, checking, 700);
    assertEquals(1300, savings.getBalance());
    assertEquals(1200, checking.getBalance());
  }

  @Test
  void transferRecordsTransferOutAndTransferIn() throws Exception {
    transactionManager.transfer(savings, checking, 700);
    Transaction[] sourceHistory = transactionManager.getTransactionsFor("ACC001");
    Transaction[] targetHistory = transactionManager.getTransactionsFor("ACC002");
    assertEquals(TransactionType.TRANSFER_OUT, sourceHistory[0].getType());
    assertEquals(TransactionType.TRANSFER_IN, targetHistory[0].getType());
    assertEquals(2, transactionManager.getTransactionCount());
  }

  @Test
  void transferWithInsufficientFundsChangesNothing() {
    assertThrows(InsufficientFundsException.class,
        () -> transactionManager.transfer(savings, checking, 1600));
    assertEquals(2000, savings.getBalance());
    assertEquals(500, checking.getBalance());
    assertEquals(0, transactionManager.getTransactionCount());
  }

  @Test
  void transferToSameAccountThrowsInvalidAccountException() {
    assertThrows(InvalidAccountException.class,
        () -> transactionManager.transfer(savings, savings, 100));
    assertEquals(2000, savings.getBalance());
  }

  @Test
  void transactionIdsAreSequential() throws Exception {
    assertEquals("TXN001", transactionManager.peekNextTransactionId());
    Transaction first = transactionManager.deposit(savings, 100);
    Transaction second = transactionManager.withdraw(savings, 50);
    assertEquals("TXN001", first.getTransactionId());
    assertEquals("TXN002", second.getTransactionId());
  }

  @Test
  void historyContainsOnlyTheRequestedAccount() throws Exception {
    transactionManager.deposit(savings, 100);
    transactionManager.deposit(checking, 200);
    transactionManager.deposit(savings, 300);
    Transaction[] history = transactionManager.getTransactionsFor("acc001");
    assertEquals(2, history.length);
    assertEquals(300, history[1].getAmount());
  }

  @Test
  void fullTransactionLogThrowsStorageFullException() throws Exception {
    for (int i = 0; i < 200; i++) {
      transactionManager.deposit(checking, 1);
    }
    assertThrows(StorageFullException.class, () -> transactionManager.deposit(checking, 1));
    assertEquals(700, checking.getBalance());
  }

  @Test
  void transferNeedsRoomForTwoRecords() throws Exception {
    for (int i = 0; i < 199; i++) {
      transactionManager.deposit(checking, 1);
    }
    assertThrows(StorageFullException.class,
        () -> transactionManager.transfer(savings, checking, 100));
    assertEquals(2000, savings.getBalance());
  }
}

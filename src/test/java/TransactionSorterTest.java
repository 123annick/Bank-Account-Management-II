import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;
import models.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.TransactionSorter;

@ExtendWith(ConsoleTestLogger.class)
class TransactionSorterTest {
  private static final LocalDateTime NOON = LocalDateTime.of(2026, 10, 15, 12, 0);

  @Test
  void sortsTransactionsNewestFirst() {
    Transaction oldest = TestData.depositAt("TXN001", NOON.minusDays(2));
    Transaction newest = TestData.depositAt("TXN002", NOON);
    Transaction middle = TestData.depositAt("TXN003", NOON.minusDays(1));
    Transaction[] sorted = TransactionSorter.sortNewestFirst(
        new Transaction[] {oldest, newest, middle});
    assertSame(newest, sorted[0]);
    assertSame(middle, sorted[1]);
    assertSame(oldest, sorted[2]);
  }

  @Test
  void sameTimestampPutsLaterRecordFirst() {
    Transaction first = TestData.depositAt("TXN001", NOON);
    Transaction second = TestData.depositAt("TXN002", NOON);
    Transaction[] sorted = TransactionSorter.sortNewestFirst(new Transaction[] {first, second});
    assertSame(second, sorted[0]);
  }

  @Test
  void originalArrayIsNotChanged() {
    Transaction first = TestData.depositAt("TXN001", NOON.minusDays(1));
    Transaction second = TestData.depositAt("TXN002", NOON);
    Transaction[] original = {first, second};
    TransactionSorter.sortNewestFirst(original);
    assertSame(first, original[0]);
  }

  @Test
  void emptyArrayStaysEmpty() {
    assertEquals(0, TransactionSorter.sortNewestFirst(new Transaction[0]).length);
  }
}

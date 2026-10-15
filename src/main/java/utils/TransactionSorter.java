package utils;

import java.util.Arrays;
import models.Transaction;

public final class TransactionSorter {

  private TransactionSorter() {
  }

  /**
   * Returns a copy of the transactions sorted from newest to oldest using insertion sort.
   * Transactions with the same timestamp keep newest-recorded first.
   *
   * @param transactions the transactions in the order they were recorded
   * @return a new array sorted newest first
   */
  public static Transaction[] sortNewestFirst(Transaction[] transactions) {
    Transaction[] sorted = Arrays.copyOf(transactions, transactions.length);
    for (int i = 1; i < sorted.length; i++) {
      Transaction current = sorted[i];
      int position = i - 1;
      while (position >= 0 && isNotOlder(current, sorted[position])) {
        sorted[position + 1] = sorted[position];
        position--;
      }
      sorted[position + 1] = current;
    }
    return sorted;
  }

  private static boolean isNotOlder(Transaction first, Transaction second) {
    return !first.getTimestamp().isBefore(second.getTimestamp());
  }
}

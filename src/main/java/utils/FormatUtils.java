package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class FormatUtils {
  private static final DateTimeFormatter DATE_TIME_FORMAT =
      DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a", Locale.ENGLISH);

  private FormatUtils() {
  }

  /**
   * Formats money with two decimals, for example $1,500.00 or -$200.00.
   *
   * @param amount the amount to format
   * @return the formatted amount
   */
  public static String currency(double amount) {
    String sign = amount < 0 ? "-" : "";
    return sign + String.format(Locale.US, "$%,.2f", Math.abs(amount));
  }

  /**
   * Formats money with a leading plus or minus sign, for example +$750.00.
   *
   * @param amount the amount to format
   * @return the formatted amount with its sign
   */
  public static String signedCurrency(double amount) {
    String sign = amount < 0 ? "-" : "+";
    return sign + String.format(Locale.US, "$%,.2f", Math.abs(amount));
  }

  /**
   * Formats a date and time, for example 15-10-2026 02:30 PM.
   *
   * @param dateTime the date and time to format
   * @return the formatted text
   */
  public static String dateTime(LocalDateTime dateTime) {
    return dateTime.format(DATE_TIME_FORMAT);
  }
}

package models;

public class RegularCustomer extends Customer {

  /**
   * Creates a regular customer.
   *
   * @param customerId the unique customer id
   * @param details the customer's personal details
   */
  public RegularCustomer(String customerId, CustomerDetails details) {
    super(customerId, details);
  }

  /**
   * Returns {@link CustomerType#REGULAR}.
   *
   * @return the customer type
   */
  @Override
  public CustomerType getCustomerType() {
    return CustomerType.REGULAR;
  }

  /**
   * Regular customers pay the normal monthly fees.
   *
   * @return false
   */
  @Override
  public boolean hasWaivedFees() {
    return false;
  }
}

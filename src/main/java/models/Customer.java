package models;

public abstract class Customer {
  private final String customerId;
  private String name;
  private int age;
  private String contact;
  private String address;

  protected Customer(String customerId, CustomerDetails details) {
    this.customerId = customerId;
    this.name = details.name();
    this.age = details.age();
    this.contact = details.contact();
    this.address = details.address();
  }

  /**
   * Returns the unique customer identifier, for example CUS001.
   *
   * @return the customer id
   */
  public String getCustomerId() {
    return customerId;
  }

  /**
   * Returns the customer's full name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Changes the customer's full name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the customer's age.
   *
   * @return the age in years
   */
  public int getAge() {
    return age;
  }

  /**
   * Changes the customer's age.
   *
   * @param age the new age in years
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * Returns the customer's contact details.
   *
   * @return the contact
   */
  public String getContact() {
    return contact;
  }

  /**
   * Changes the customer's contact details.
   *
   * @param contact the new contact
   */
  public void setContact(String contact) {
    this.contact = contact;
  }

  /**
   * Returns the customer's address.
   *
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * Changes the customer's address.
   *
   * @param address the new address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Returns the kind of customer.
   *
   * @return the customer type
   */
  public abstract CustomerType getCustomerType();

  /**
   * Tells whether monthly account fees are waived for this customer.
   *
   * @return true when fees are waived
   */
  public abstract boolean hasWaivedFees();
}

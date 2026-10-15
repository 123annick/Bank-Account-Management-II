package ui;

public interface MenuAction {

  /**
   * Returns the text shown for this option in the main menu.
   *
   * @return the menu title
   */
  String getTitle();

  /**
   * Runs this menu option.
   */
  void execute();
}

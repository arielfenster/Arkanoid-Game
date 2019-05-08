package menu;

import animation.Animation;

/**
 * A menu interface.
 *
 * @param <T> - a generic type of menu
 * @author Ariel Fenster
 */
public interface Menu<T> extends Animation {

    /**
     * Function name: addSelection.
     * Add the given selection to the menu data
     *
     * @param key         - the key to press to choose the selection
     * @param message     - the selection's message to display
     * @param returnValue - the value to return when the selection is chosen
     */
    void addSelection(String key, String message, T returnValue);

    /**
     * Function name: getStatus.
     * Return the chosen selection
     *
     * @return chosen selection
     */
    T getStatus();

    /**
     * Function name: addSubMenu.
     * Add the given sub menu to the main menu's data.
     *
     * @param key     - the key to press to choose the sub menu
     * @param message - the sub menu's message to display
     * @param subMenu - the sub menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}

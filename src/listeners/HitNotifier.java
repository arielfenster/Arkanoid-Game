package listeners;

/**
 * A listener interface that determines that the implementing object can be added and removed from the game.
 *
 * @author Ariel Fenster
 */
public interface HitNotifier {

    /**
     * Function name: addHitListener.
     * Adding a given listener to the list of listeners - usually when the object is created
     * @param hl - the added listener
     */
    void addHitListener(HitListener hl);

    /**
     * Function name: removeHitListener.
     * Removing a given listener from the list of listeners - usually when the object is removed from the game
     * @param hl - the removed listener
     */
    void removeHitListener(HitListener hl);
}
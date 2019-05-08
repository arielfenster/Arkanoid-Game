package listeners;

/**
 * A class that keeps track of amount of items in the game (balls/blocks etc.).
 *
 * @author Ariel Fenster
 */
public class Counter {

    // member
    private int count;

    /**
     * Function name: Counter.
     * Constructor for the class.
     */
    public Counter() {
        this.count = 0;
    }

    /**
     * Function name: increase.
     * Increasing the current count by the given amount
     *
     * @param amount - the increasing amount
     */
    public void increase(int amount) {
        this.count += amount;
    }

    /**
     * Function name: decrease.
     * Decreasing the current count by the given amount
     *
     * @param amount - the decreasing amount
     */
    public void decrease(int amount) {
        this.count -= amount;
    }

    /**
     * Function name: getValue.
     * Return the current value of the count
     *
     * @return current count
     */
    public int getValue() {
        return this.count;
    }
}

package menu;

/**
 * A task interface.
 *
 * @param <T> - a generic type of task
 * @author Ariel Fenster
 */
public interface Task<T> {

    /**
     * Function name: run.
     * Run the received task.
     *
     * @return after the task is done, return a value corresponding to the type of the task
     */
    T run();
}

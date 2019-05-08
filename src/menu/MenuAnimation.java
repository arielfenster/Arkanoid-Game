package menu;

import animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A menu animation class.
 *
 * @param <T> - a generic type of menu
 * @author Ariel Fenster
 */
public class MenuAnimation<T> implements Menu<T> {

    private String title;
    private KeyboardSensor keyboard;
    private AnimationRunner animationRunner;

    private Menu<T> subMenu;
    private boolean showSubMenu;

    // list of terminating keys - if the player pressed one of them - end the animation
    private List<String> keys;
    // list of messages to display to the player
    private List<String> messages;
    // list of different return tasks - each task corresponds to a different key
    private List<T> tasks;
    // list of indicators whether a specific key was pressed
    private List<Boolean> isKeyPressed;
    private boolean shouldAnimationStop;

    /**
     * Function name: MenuAnimation.
     * Constructor
     *
     * @param menuTitle - the title of the menu
     * @param keyboard  - object linking the player to the game
     * @param ar        - used to display the sub menu
     */
    public MenuAnimation(String menuTitle, KeyboardSensor keyboard, AnimationRunner ar) {
        this.title = menuTitle;
        this.keyboard = keyboard;
        this.animationRunner = ar;
        this.keys = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.isKeyPressed = new ArrayList<>();
        this.shouldAnimationStop = false;
        this.showSubMenu = false;
    }

    /**
     * Function name: addSelection.
     * * Add the given selection to the menu data
     *
     * @param key           - the key to press to choose the selection
     * @param message       - the selection's message to display
     * @param taskToExecute - the task to return when the selection is chosen
     */
    @Override
    public void addSelection(String key, String message, T taskToExecute) {
        this.keys.add(key);
        this.messages.add(message);
        this.tasks.add(taskToExecute);
        this.isKeyPressed.add(false);
    }

    /**
     * Function name: addSubMenu.
     * Add the given sub menu to the main menu's data.
     *
     * @param key     - the key to press to choose the sub menu
     * @param message - the sub menu's message to display
     * @param sub     - a sub menu that follows the main menu
     */
    public void addSubMenu(String key, String message, Menu<T> sub) {
        this.keys.add(key);
        this.messages.add(message);
        this.tasks.add(null);
        this.isKeyPressed.add(false);
        this.subMenu = sub;
    }

    /**
     * Function name: getStatus.
     * Return the chosen selection
     *
     * @return chosen selection
     */
    @Override
    public T getStatus() {
        int index = this.isKeyPressed.indexOf(true);
        T status;
        // if the action is to show the sub menu
        if (this.showSubMenu) {
            // reset the flag and get the status from the sub menu
            this.showSubMenu = false;
            this.animationRunner.run(this.subMenu);
            status = this.subMenu.getStatus();
            // if not, get the status from the list of tasks
        } else {
            status = this.tasks.get(index);
        }
        // resetting the values for future menu displays
        this.isKeyPressed.set(index, false);
        this.shouldAnimationStop = false;
        return status;
    }

    /**
     * Function name: doOneFrame.
     * Perform one frame of the animation and display it on the given draw surface
     *
     * @param d  - the platform on which the outcome of the action is displayed
     * @param dt - the time that passed since the last frame was shown
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLUE);
        d.drawText(d.getWidth() / 3, 50, this.title, 20);

        // displaying all the text
        for (int i = 0; i < this.messages.size(); i++) {
            d.drawText(50, 50 * (i + 3), this.messages.get(i), 20);
        }

        for (int i = 0; i < this.keys.size(); i++) {
            if (this.keyboard.isPressed(this.keys.get(i))) {
                // if the task is null - it's a task to show the sub menu
                if (this.tasks.get(i) == null) {
                    this.showSubMenu = true;
                }
                this.isKeyPressed.set(i, true);
                this.shouldAnimationStop = true;
                break;
            }
        }
    }

    /**
     * Function name: shouldStop.
     * Returns whether the current animation should stop or continue
     *
     * @return - true if the animation should stop, false otherwise
     */
    @Override
    public boolean shouldStop() {
        return this.shouldAnimationStop;
    }
}

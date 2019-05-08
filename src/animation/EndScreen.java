package animation;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * At the end of the game, when a player has won the game or lost all his lives, an end screen is presented with
 * a message and the final score.
 *
 * @author Ariel Fenster
 */
public class EndScreen implements Animation {

    // member
    private String message;

    /**
     * Function name: EndScreen.
     * Constructor for the class
     *
     * @param message - the displayed message
     */
    public EndScreen(String message) {
        this.message = message;
    }

    /**
     * Function name: doOneFrame.
     * Displaying the game over screen until the player has pressed the space key to close it
     *
     * @param d  - the platform on which the outcome of the action is displayed
     * @param dt - the time that passed since the last frame was shown
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // displaying the message
        int fontSize = 20;
        d.setColor(Color.BLACK);
        d.drawText(10, d.getHeight() - 10, this.message + ". Press SPACE to continue", fontSize);
    }

    /**
     * Function name: shouldStop.
     * This function's return value is ignored.
     *
     * @return true
     */
    public boolean shouldStop() {
        return true;
    }
}

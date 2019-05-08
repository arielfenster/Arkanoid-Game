package animation;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * During the game, the player can pause the game by pressing a key and a different screen will appear until
 * he decides to resume the game.
 *
 * @author Ariel Fenster
 */
public class PauseScreen implements Animation {

    /**
     * Function name: doOneFrame.
     * Displaying the pause screen until the player has pressed the space key to resume play
     *
     * @param d  - the platform on which the outcome of the action is displayed
     * @param dt - the time that passed since the last frame was shown
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // displaying the message
        int fontSize = 20;
        d.setColor(Color.BLACK);
        d.drawText(10, d.getHeight() - 10, "Paused - press SPACE to resume", fontSize);
    }

    /**
     * Function name: shouldStop.
     * The function returns whether the pause animation should stop, meaning the game should resume to itself
     *
     * @return true if the game should resume, false otherwise
     */
    public boolean shouldStop() {
        return true;
    }
}

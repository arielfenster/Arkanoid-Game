package indicators;

import collidablesdata.Sprite;
import listeners.Counter;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Displaying the player how many lives he has left before he loses the game.
 *
 * @author Ariel Fenster
 */
public class LivesIndicator implements Sprite {

    // member - holds a reference to the lives counter
    private Counter currentLives;

    /**
     * Function name: LivesIndicator.
     * Constructor for the class
     *
     * @param livesCounter - a counter that keeps track of how many lives the player has left
     */
    public LivesIndicator(Counter livesCounter) {
        this.currentLives = livesCounter;
    }

    /**
     * Function name: drawOn.
     * Drawing the number of lives remaining on the given draw surface
     *
     * @param surface - the platform on which we draw the number of lives
     */
    public void drawOn(DrawSurface surface) {
        // drawing the surrounding block
        surface.setColor(Color.LIGHT_GRAY);
        surface.fillRectangle(0, 0, surface.getWidth(), 30);

        String displayLives = "Lives: " + this.currentLives.getValue();
        surface.setColor(Color.BLACK);
        surface.drawText(75, 20, displayLives, 18);
    }

    /**
     * Function name: timePassed.
     * Currently the function doesn't do anything. Future actions will be added.
     *
     * @param dt - the time that passed since the last frame was shown
     */
    public void timePassed(double dt) {

    }
}

package indicators;

import collidablesdata.Sprite;
import listeners.Counter;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Displaying the player how many points he earned so far in the game.
 *
 * @author Ariel Fenster
 */
public class ScoreIndicator implements Sprite {

    // member - holding a reference to the score counter
    private Counter currentScore;

    /**
     * Function name: ScoreIndicator.
     * Constructor for the class
     *
     * @param scoreCounter - a counter that keeps track of the current score
     */
    public ScoreIndicator(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Function name: drawOn.
     * Drawing the current score the player has on the given draw surface
     *
     * @param surface - the platform on which we draw the player's score
     */
    public void drawOn(DrawSurface surface) {
        String displayScore = "Score: " + this.currentScore.getValue();
        surface.setColor(Color.BLACK);
        surface.drawText(275, 20, displayScore, 18);
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

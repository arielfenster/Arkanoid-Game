package indicators;

import collidablesdata.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Displaying the name of the current playing level.
 *
 * @author Ariel Fenster
 */
public class NameLevelIndicator implements Sprite {

    // member
    private String name;

    /**
     * Function name: NameLevelIndicator.
     * Constructor for the class.
     *
     * @param levelName - the name of the current level
     */
    public NameLevelIndicator(String levelName) {
        this.name = levelName;
    }

    /**
     * Function name: drawOn.
     * Drawing the name of the level on the given draw surface
     *
     * @param surface - the platform on which we draw the name of the level
     */
    public void drawOn(DrawSurface surface) {
        String displayScore = "Level Name: " + this.name;
        surface.setColor(Color.BLACK);
        surface.drawText(475, 20, displayScore, 18);
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

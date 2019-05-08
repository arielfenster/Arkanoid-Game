package levelsdata;

import collidablesdata.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;
import java.awt.Image;

/**
 * Draws a background for a level - either a color or an image.
 *
 * @author Ariel Fenster
 */
public class BackgroundGenerator implements Sprite {

    private Image image;
    private Color color;

    /**
     * Function name: BackgroundGenerator.
     * Constructor
     *
     * @param background - the background string - either a color or a path to an image
     */
    public BackgroundGenerator(String background) {
        this.image = null;
        this.color = null;
        this.setBackground(background);
    }

    /**
     * Function name: setBackground.
     * Sets the background for the level.
     *
     * @param background - the background string - either a color or a path to an image
     */
    private void setBackground(String background) {
        this.color = new ColorsParser().colorFromString(background);
        if (this.color == null) {
            this.image = new ImageParser().imageFromString(background);
        }
    }

    /**
     * Function name: drawOn.
     * Drawing the background on the given draw surface
     *
     * @param d - the draw surface on which we draw the object
     */
    @Override
    public void drawOn(DrawSurface d) {
        // if the background is a color
        if (this.image == null) {
            d.setColor(this.color);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
            // if the background is an image
        } else {
            d.drawImage(0, 0, this.image);
        }
    }

    /**
     * Function name: timePassed.
     * Currently the function doesn't do anything. Future actions will be added.
     *
     * @param dt - the time that passed since the last frame was shown
     */
    @Override
    public void timePassed(double dt) {
    }
}

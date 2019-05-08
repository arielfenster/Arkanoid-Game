package collidablesdata;

import biuoop.DrawSurface;

/**
 * The interface is used to indicate that an object can be drawn and can be affected by the game objects.
 *
 * @author Ariel Fenster
 */
public interface Sprite {
    /**
     * Function name: drawOn.
     * The function draws the sprite object on the given draw surface
     *
     * @param d - the draw surface on which we draw the object
     */
    void drawOn(DrawSurface d);

    /**
     * Function name: timePassed.
     * Notify the sprite object that time has passed. The implementation is different for every implementing class
     *
     * @param dt - the time that passed since the last frame was shown
     */
    void timePassed(double dt);
}
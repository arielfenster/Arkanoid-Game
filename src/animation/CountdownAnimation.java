package animation;

import collidablesdata.SpriteCollection;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Before each level and after every time a player losses a life, a counting down animation is presented in order
 * to prepare the player and give him a primary view of the current level.
 *
 * @author Ariel Fenster
 */
public class CountdownAnimation implements Animation {

    //members
    private SpriteCollection background;
    private long miliSecondsRequested;
    private int countFrom;
    private long miliSecondsPerCount;
    private long totalTimePassed;

    /**
     * Function name: CountdownAnimation.
     * Constructor for the class
     *
     * @param levelBackground - the background design of the current level
     * @param numOfSeconds    - the duration of the animation
     * @param countFrom       - the starting counting number
     */
    public CountdownAnimation(SpriteCollection levelBackground, double numOfSeconds, int countFrom) {
        this.background = levelBackground;
        this.miliSecondsRequested = (long) numOfSeconds * 1000;
        this.countFrom = countFrom;
        this.miliSecondsPerCount = this.miliSecondsRequested / countFrom;
        this.totalTimePassed = 0;
    }

    /**
     * Function name: doOneFrame.
     * The function performs one frame of the animation and displays it on the given draw surface
     *
     * @param d  - the platform on which the outcome of the action is displayed
     * @param dt - the time that passed since the last frame was shown
     */
    public void doOneFrame(DrawSurface d, double dt) {
        long startTime = System.currentTimeMillis();
        // drawing the background
        this.background.drawAllOn(d);

        // drawing the number
        d.setColor(Color.RED);
        d.drawText(d.getWidth() / 2, d.getHeight() / 3 * 2, Integer.toString(this.countFrom), 30);
        // delay
        long endTime = System.currentTimeMillis() - startTime;
        while (endTime < this.miliSecondsPerCount) {
            endTime = System.currentTimeMillis() - startTime;
        }
        // adjust
        this.countFrom--;
        this.totalTimePassed += this.miliSecondsPerCount;
    }

    /**
     * Function name: shouldStop.
     * Returns whether the counting animation should stop playing or continue - if the time that passed from
     * the start of the count is longer than requested - stop the animation.
     *
     * @return true if the animation should stop, false otherwise
     */
    public boolean shouldStop() {
        return this.totalTimePassed >= this.miliSecondsRequested;
    }
}
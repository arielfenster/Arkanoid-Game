package animation;

import biuoop.DrawSurface;

/**
 * The interface determines the capabilities of an animation - it can be played and be stopped.
 *
 * @author Ariel Fenster
 */
public interface Animation {

    /**
     * Function name: doOneFrame.
     * The function performs one frame of the animation and displays it on the given draw surface
     *
     * @param d  - the platform on which the outcome of the action is displayed
     * @param dt - the time that passed since the last frame was shown
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * Function name: shouldStop.
     * Returns whether the current animation should stop or continue
     *
     * @return - true if the animation should stop, false otherwise
     */
    boolean shouldStop();
}

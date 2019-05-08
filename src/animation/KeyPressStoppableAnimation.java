package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * A decorating class used to wrap all animations that are stopped by pressing a specific key.
 *
 * @author Ariel Fenster
 */
public class KeyPressStoppableAnimation implements Animation {

    // members
    private Animation animation;
    private KeyboardSensor keyboard;
    private String terminateKey;
    private boolean shouldAnimationStop;
    private boolean isAlreadyPressed;

    /**
     * Function name: KeyPressStoppableAnimation.
     * Constructor.
     *
     * @param animation    - the animation to play
     * @param keyboard     - object linking the player to the game
     * @param terminateKey - the key to stop the animation
     */
    public KeyPressStoppableAnimation(Animation animation, KeyboardSensor keyboard, String terminateKey) {
        this.animation = animation;
        this.keyboard = keyboard;
        this.terminateKey = terminateKey;
        this.shouldAnimationStop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * Function name: doOneFrame.
     * Perform one frame of the animation and display it on the given draw surface.
     *
     * @param d  - the platform on which the outcome of the action is displayed
     * @param dt - the time that passed since the last frame was shown
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        // drawing a background
        d.setColor(Color.GRAY);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        this.animation.doOneFrame(d, dt);

        // if the player pressed the animation-terminating key
        if (this.keyboard.isPressed(this.terminateKey)) {
            // if the terminating key wasn't already pressed due to a previous animation - stop the current one
            if (!this.isAlreadyPressed) {
                this.shouldAnimationStop = true;
                this.isAlreadyPressed = true;
            }
        } else {
            this.isAlreadyPressed = false;
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
        // if the animation needs to stop - reset the value for future animations but return true for the current one
        if (this.shouldAnimationStop) {
            this.shouldAnimationStop = false;
            return true;
        }
        return false;
    }
}
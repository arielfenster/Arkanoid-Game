package animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * The class displays on screen a given animation until the animation is terminated.
 *
 * @author Ariel Fenster
 */
public class AnimationRunner {

    // members
    private GUI gui;
    private int fps;
    private double dt;

    /**
     * Function name: AnimationRunner.
     * Constructor for the class.
     *
     * @param gui - the gui of the game
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        this.fps = 60;
        this.dt = 1.0 / this.fps;
    }

    /**
     * Function name: run.
     * The function is responsible for the time-management of the game - making sure the game runs smoothly
     * and plays the given animation.
     *
     * @param animation - the animation that will be displayed
     */
    public void run(Animation animation) {
        Sleeper sleeper = new Sleeper();

        // how long each frame lasts
        int millisecondsPerFrame = 1000 / this.fps;

        while (!animation.shouldStop()) {
            // start measuring the time it takes to complete one cycle of actions
            long startTime = System.currentTimeMillis();

            DrawSurface d = this.gui.getDrawSurface();
            // continuously displaying the animation (each frame at a time) until the animation should stop
            animation.doOneFrame(d, this.dt);
            this.gui.show(d);

            /*
             * stop measuring time.
             * Unlike in previous assignment, we now work with many objects from multiple classes and it takes
             * relatively long time to compute all of the actions. The time isn't fixed.
             * Because of that, we take precaution by measuring the time and seeing how long we can delay the program,
             * to ensure a smooth and consistent animation.
             */
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}

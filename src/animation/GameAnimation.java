package animation;

import gamelogic.GameFlow;
import levelsdata.LevelInformation;
import biuoop.DrawSurface;
import levelsdata.LevelSetReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to run the animation of the game itself with given levels.
 *
 * @author Ariel Fenster
 */
public class GameAnimation implements Animation {

    // members
    private GameFlow game;
    private String difficulty;
    private LevelSetReader reader;
    private List<LevelInformation> levelsToPlay;
    private boolean shouldAnimationStop;

    /**
     * Function name: GameAnimation.
     * Constructor.
     *
     * @param game       - the game object
     * @param difficulty - the difficulty of the requested levels to play
     * @param reader     - reads through a specific levels set and generates all the levels specified
     */
    public GameAnimation(GameFlow game, String difficulty, LevelSetReader reader) {
        this.game = game;
        this.difficulty = difficulty;
        this.reader = reader;
        this.levelsToPlay = new ArrayList<>();
        this.shouldAnimationStop = false;
    }

    /**
     * Function name: doOneFrame.
     * Performs one frame of the animation and display it on the given draw surface.
     *
     * @param d  - the platform on which the outcome of the action is displayed
     * @param dt - the time that passed since the last frame was shown
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.levelsToPlay.isEmpty()) {
            this.levelsToPlay.addAll(this.reader.getLevels(this.difficulty));
        }
        this.game.runLevels(this.levelsToPlay);
        this.shouldAnimationStop = true;
    }

    /**
     * Function name: shouldStop.
     * Returns whether the current animation should stop or continue
     *
     * @return - true if the animation should stop, false otherwise
     */
    @Override
    public boolean shouldStop() {
        if (this.shouldAnimationStop) {
            this.shouldAnimationStop = false;
            return true;
        }
        return false;
    }
}

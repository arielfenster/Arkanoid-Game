package listeners;

import gamelogic.GameLevel;
import gameobjects.Ball;
import gameobjects.Block;

/**
 * When a ball has fallen below the paddle and outside the frame of the game, it is considered out of play.
 * The class is a listener that is attached to a block at the bottom of the screen to notify that if it is been hit,
 * the hitting ball must be removed from play and cannot bounce back up, decreasing the number of balls by 1.
 *
 * @author Ariel Fenster
 */
public class BallRemover implements HitListener {

    // members
    private GameLevel gameLevel;
    private Counter ballsCounter;

    /**
     * Function name: BallRemover.
     * Constructor for the class.
     *
     * @param gameLevel    - the current playing level
     * @param ballsCounter - a counter that keeps track of how many balls are remaining
     */
    public BallRemover(GameLevel gameLevel, Counter ballsCounter) {
        this.gameLevel = gameLevel;
        this.ballsCounter = ballsCounter;
    }

    /**
     * Function name: hitEvent.
     * Removing a ball from play when it is outside the screen and updating the number of remaining balls
     *
     * @param beingHit - a special block indicating that it is been hit and a ball needs to be removed
     * @param hitter   - the ball that is removed
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        this.ballsCounter.decrease(1);
    }
}

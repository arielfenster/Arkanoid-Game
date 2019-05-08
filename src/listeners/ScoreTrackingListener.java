package listeners;

import gameobjects.Ball;
import gameobjects.Block;

/**
 * Each time the player hits a block, his score is increased.
 *
 * @author Ariel Fenster
 */
public class ScoreTrackingListener implements HitListener {

    // member
    private Counter currentScore;

    /**
     * Function name: ScoreTrackingListener.
     * Constructor for the class
     *
     * @param scoreCounter - a counter that keeps track of the current score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Function name: hitEvent.
     * Updating the player's score if he hit or destroyed a block
     *
     * @param beingHit - the block that is hit
     * @param hitter   - the ball that hit the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // adding 5 points for hitting a block
        this.currentScore.increase(5);
        // adding 10 additional points for destroying a block
        if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(10);
        }
    }
}

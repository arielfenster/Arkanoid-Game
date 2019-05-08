package listeners;

import gamelogic.GameLevel;
import gameobjects.Ball;
import gameobjects.Block;

/**
 * A listener that is attached to all the blocks in the game and in the case a block is hit, checks if the block
 * needs to be removed from the game and if so - removes it.
 *
 * @author Ariel Fenster
 */
public class BlockRemover implements HitListener {

    // members
    private GameLevel gameLevel;
    private Counter blocksRemoved;

    /**
     * Function name: BlockRemover.
     * Constructor for the class
     *
     * @param gameLevel     - the current playing level
     * @param blocksRemoved - a counter that keeps track of how many blocks have been removed
     */
    public BlockRemover(GameLevel gameLevel, Counter blocksRemoved) {
        this.gameLevel = gameLevel;
        this.blocksRemoved = blocksRemoved;
    }

    /**
     * Function name: hitEvent.
     * Removing a single-hit block from the game when it is been hit.
     *
     * @param beingHit - the block that is hit
     * @param hitter   - the ball that is hitting the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeFromGame(this.gameLevel);
            this.blocksRemoved.increase(1);
            beingHit.removeHitListener(this);
        }
    }
}

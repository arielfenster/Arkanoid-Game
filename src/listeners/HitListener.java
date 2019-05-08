package listeners;

import gameobjects.Ball;
import gameobjects.Block;

/**
 * A base listener interface that is executed when a block has been hit.
 * Different hit listeners operate differently and their actions affect the game and help preserve the game logic
 *
 * @author Ariel Fenster
 */
public interface HitListener {

    /**
     * Function name: hitEvent.
     * When a block has been hit a different action is executed, depending on the implementing listener
     *
     * @param beingHit - the block that is hit
     * @param hitter   - the ball that hit the block
     */
    void hitEvent(Block beingHit, Ball hitter);
}
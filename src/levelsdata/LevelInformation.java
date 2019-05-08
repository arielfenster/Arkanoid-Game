package levelsdata;

import collidablesdata.Sprite;
import collidablesdata.Velocity;
import gameobjects.Block;

import java.util.List;

/**
 * The interface contains all the necessary information needed to compose a playing level.
 *
 * @author Ariel Fenster
 */
public interface LevelInformation {

    /**
     * Function name: numberOfBalls.
     * Return the number of balls in the current level
     *
     * @return amount of balls in the level
     */
    int numberOfBalls();

    /**
     * Function name: initiateBalLVelocities.
     * Creating different velocity for each to-be-created ball in the current level
     *
     * @return a list of velocities
     */
    List<Velocity> initialBallVelocities();

    /**
     * Function name: paddleSpeed.
     * Return the speed of the paddle in the current level
     *
     * @return paddle's speed
     */
    int paddleSpeed();

    /**
     * Function name: paddleWidth.
     * Return the width of the paddle in the current level
     *
     * @return paddle's width
     */
    int paddleWidth();

    /**
     * Function name: levelName.
     * Return the name of the current level which will be displayed at the top of the screen
     *
     * @return level's name
     */
    String levelName();

    /**
     * Function name: getBackground.
     * Returns a sprite with the background of the level
     *
     * @return a sprite object with the background design of the level
     */
    Sprite getBackground();

    /**
     * Function name: blocks.
     * Creating the blocks that make up the specific level - each with a size, color and position.
     *
     * @return a list of the created blocks
     */
    List<Block> blocks();

    /**
     * Function name: numberOfBlocksToRemove.
     * Returns the number of blocks that should be removed before the level is complete.
     *
     * @return numbers of blocks needed to remove to complete the level
     */
    int numberOfBlocksToRemove();
}

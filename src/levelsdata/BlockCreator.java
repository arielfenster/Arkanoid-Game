package levelsdata;

import gameobjects.Block;

/**
 * An interface that is used to create game blocks.
 *
 * @author Ariel Fenster
 */
public interface BlockCreator {

    /**
     * Function name: create.
     * Creates a block at the specified location
     *
     * @param xPos - x coordinate
     * @param yPos - y coordinate
     * @return a block at the position
     */
    Block create(int xPos, int yPos);
}

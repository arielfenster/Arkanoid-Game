package levelsdata;

import gameobjects.Block;

import java.util.Map;

/**
 * A blocks creating factory. Each block has a specific and unique symbol, where when called, creates the block
 * with the associated information mapped from the information files.
 *
 * @author Ariel Fenster
 */
public class BlocksFromSymbolsFactory {

    // members
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Function name: BlocksFromSymbolsFactory.
     * Constructor
     *
     * @param spacerWidths  - map of symbols:width
     * @param blockCreators - map of symbol:associated type block creator
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * Function name: isSpaceSymbol.
     * Checks whether the given symbol is of a defined spacer.
     *
     * @param s - string to check
     * @return true if the string is of a game spacer, false otherwise
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * Function name: isBlockSymbol.
     * Checks whether the given symbol is of a defined block
     *
     * @param s - string to check
     * @return true if the string is of a game block, false otherwise
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     * Function name: getBlock.
     * Return a block according to the definitions associated with the given symbol. The block will be located
     * at position (xPos, yPos)
     *
     * @param blockSymbol - the symbol of the block to create
     * @param xPos        - x coordinate to generate the block in
     * @param yPos        - y coordinate to generate the block in
     * @return a block at the given location
     */
    public Block getBlock(String blockSymbol, int xPos, int yPos) {
        return this.blockCreators.get(blockSymbol).create(xPos, yPos);
    }

    /**
     * Function name: getSpaceWidth.
     * Returns the width in pixels associated with the given spacer-symbol.
     *
     * @param s - the symbol of the spacer
     * @return spacer's width
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}

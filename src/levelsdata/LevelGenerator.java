package levelsdata;

import collidablesdata.Sprite;
import collidablesdata.Velocity;
import gameobjects.Block;

import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A level generator for the game.
 *
 * @author Ariel Fenster
 */
public class LevelGenerator implements LevelInformation {

    // the information about the level
    private Map<String, String> levelDetails;

    // the layout of the blocks in the level
    private List<String> blocksLayout;

    // builds the level blocks based on the blocks definitions file
    private BlocksFromSymbolsFactory blocksFactory;

    // all the level details separated into different variables
    private String name;
    private Sprite background;
    private List<Velocity> velocityList;
    private int paddleSpeed;
    private int paddleWidth;
    private int blocksStartX;
    private int blocksStartY;
    private int rowHeight;
    private int numBlocks;

    /**
     * Function name: LevelGenerator.
     * Constructor.
     */
    public LevelGenerator() {
        this.velocityList = new ArrayList<>();
    }

    /**
     * Function name: createLevel.
     * Creating a level based on the details in the mapped information maps.
     *
     * @param details    - general level details
     * @param blocksInfo - blocks layout details
     * @return a level
     */
    public LevelInformation createLevel(Map<String, String> details, List<String> blocksInfo) {
        this.levelDetails = details;
        this.blocksLayout = blocksInfo;

        // try to create a level. if there is missing data, catch the exception and return an empty level as error.
        try {
            this.setMembers();
            return this;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Function name: setMembers.
     * Setting all the members in the class
     *
     * @throws NullPointerException - if one of the maps don't contain the appropriate information
     */
    private void setMembers() throws NullPointerException {
        this.setName();
        this.setVelocityList();
        this.setBackgroundDesign();
        this.setPaddleInfo();
        this.setBlocksFactory();
        this.setBlocksStartPos();
        this.setRowHeight();
        this.setNumBlocks();
    }

    /**
     * Function name: setName.
     * Get the name of the level from the map and set as a member.
     *
     * @throws NullPointerException - if the map doesn't contain appropriate information
     */
    private void setName() throws NullPointerException {
        this.name = this.levelDetails.get("level_name");
    }

    /**
     * Function name: setBackgroundDesign.
     * Get the background design data from the map and set a newly created background as a member
     *
     * @throws NullPointerException - if the map doesn't contain appropriate information
     */
    private void setBackgroundDesign() throws NullPointerException {
        this.background = new BackgroundGenerator(this.levelDetails.get("background"));
    }

    /**
     * Function name: setVelocityList.
     * Get the velocities data from the map and add to the level's velocities list.
     *
     * @throws NullPointerException - if the map doesn't contain appropriate information
     */
    private void setVelocityList() throws NullPointerException {
        String data = this.levelDetails.get("ball_velocities");
        // using regex to retrieve the actual numbers in the string
//        Pattern pattern = Pattern.compile("[-]?\\d+,\\d+");
        Pattern pattern = Pattern.compile("[-]?\\d+");
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {
            double angle = Double.parseDouble(data.substring(matcher.start(), matcher.end()));
            matcher.find();
            double speed = Double.parseDouble(data.substring(matcher.start(), matcher.end()));
            this.velocityList.add(Velocity.fromAngleAndSpeed(angle, speed));
        }
    }

    /**
     * Function name: setPaddleInfo.
     * Get the paddle's speed and width values from the map and set them as members
     *
     * @throws NullPointerException - if the map doesn't contain appropriate information
     */
    private void setPaddleInfo() throws NullPointerException {
        this.paddleSpeed = Integer.parseInt(this.levelDetails.get("paddle_speed"));
        this.paddleWidth = Integer.parseInt(this.levelDetails.get("paddle_width"));
    }

    /**
     * Function name: setBlocksFactory.
     * Get the path of the file specified in the information map and create a blocks creating factory
     * based on the information written in that file
     *
     * @throws NullPointerException - if the map doesn't contain appropriate information
     */
    private void setBlocksFactory() throws NullPointerException {
        String path = this.levelDetails.get("block_definitions");
        InputStream is = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
            this.blocksFactory = BlocksDefinitionReader.fromReader(new InputStreamReader(is));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        }
    }

    /**
     * Function name: setBlocksStartPos.
     * Get the blocks starting position values from the map and set them as members
     *
     * @throws NullPointerException - if the map doesn't contain appropriate information
     */
    private void setBlocksStartPos() throws NullPointerException {
        this.blocksStartX = Integer.parseInt(this.levelDetails.get("blocks_start_x"));
        this.blocksStartY = Integer.parseInt(this.levelDetails.get("blocks_start_y"));
    }

    /**
     * Function name: setRowHeight.
     * Get the row height value from the map and set it as a member
     *
     * @throws NullPointerException - if the map doesn't contain appropriate information
     */
    private void setRowHeight() throws NullPointerException {
        this.rowHeight = Integer.parseInt(this.levelDetails.get("row_height"));
    }

    /**
     * Function name: setNumBlocks.
     * Get the amount of block to destroy to complete the level from the map and set it as a member
     *
     * @throws NullPointerException - if the map dos
     */
    private void setNumBlocks() throws NullPointerException {
        this.numBlocks = Integer.parseInt(this.levelDetails.get("num_blocks"));
    }

    /**
     * Function name: numberOfBalls.
     * Return the number of balls in the current level
     *
     * @return amount of balls in the level
     */
    public int numberOfBalls() {
        return this.velocityList.size();
    }

    /**
     * Function name: initiateBalLVelocities.
     * Creating different velocity for each to-be-created ball in the current level
     *
     * @return a list of velocities
     */
    public List<Velocity> initialBallVelocities() {
        return this.velocityList;
    }

    /**
     * Function name: paddleSpeed.
     * Return the speed of the paddle in the current level
     *
     * @return paddle's speed
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * Function name: paddleWidth.
     * Return the width of the paddle in the current level
     *
     * @return paddle's width
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * Function name: levelName.
     * Return the name of the current level which will be displayed at the top of the screen
     *
     * @return level's name
     */
    public String levelName() {
        return this.name;
    }

    /**
     * Function name: getBackground.
     * Returns a sprite with the background of the level
     *
     * @return a sprite object with the background design of the level
     */
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * Function name: blocks.
     * Creating the blocks that make up the specific level - each with a size, color and position.
     *
     * @return a list of the created blocks
     */
    public List<Block> blocks() {
        List<Block> blocksList = new ArrayList<>(this.numBlocks);

        for (int i = 0; i < this.blocksLayout.size(); i++) {
            // get the current line of layout formation
            char[] currentLine = this.blocksLayout.get(i).toCharArray();
            // set the initial position for each line of blocks
            int currentX = this.blocksStartX;
            int currentY = this.blocksStartY + (this.rowHeight * i);

            // check each character if it's a space symbol or block symbol
            for (char currentChar : currentLine) {
                String symbol = Character.toString(currentChar);

                // if the current character is a symbol of a block - create the appropriate block and add to the list
                if (this.blocksFactory.isBlockSymbol(symbol)) {
                    Block block = this.blocksFactory.getBlock(symbol, currentX, currentY);
                    currentX = (int) block.getCollisionRectangle().getUpperRight().getX();
                    blocksList.add(block);
                    // if the character is a space symbol - advance the x position by that symbol's width setting
                } else if (this.blocksFactory.isSpaceSymbol(symbol)) {
                    currentX += this.blocksFactory.getSpaceWidth(symbol);
                }
            }
        }
        return blocksList;
    }

    /**
     * Function name: numberOfBlocksToRemove.
     * Returns the number of blocks that should be removed before the level is complete.
     *
     * @return numbers of blocks needed to remove to complete the level
     */
    public int numberOfBlocksToRemove() {
        return this.numBlocks;
    }
}

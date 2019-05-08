package gamelogic;

import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import gameobjects.Ball;
import gameobjects.Block;
import gameobjects.Point;
import gameobjects.Paddle;
import collidablesdata.GameEnvironment;
import collidablesdata.SpriteCollection;
import collidablesdata.Collidable;
import collidablesdata.Sprite;
import listeners.Counter;
import listeners.HitListener;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import indicators.NameLevelIndicator;
import indicators.LivesIndicator;
import indicators.ScoreIndicator;
import levelsdata.LevelInformation;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.List;

/**
 * The class is responsible for each level's logic and variables. It adds all the game objects to the game data
 * and initiates the animation loop for each level.
 *
 * @author Ariel Fenster
 */
public class GameLevel implements Animation {
    // members
    private LevelInformation levelInfo;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private biuoop.GUI gui;
    private Counter blocksDestroyedCounter;
    private Counter ballsCounter;
    private Counter scoreCounter;
    private Counter livesCounter;

    // used for the animation
    private AnimationRunner animator;
    private boolean shouldAnimationContinue;

    // the size of the GUI of the game
    private int maxWidth;
    private int maxHeight;

    // end of variables declaration

    /**
     * Function name: GameLevel.
     * Constructor for the class.
     *
     * @param info         - the current level settings
     * @param gui          - the gui of the game
     * @param animator     - used to display each level
     * @param scoreCounter - an updating score counter across all the levels
     * @param livesCounter - an updating lives counter across all the levels
     */
    public GameLevel(LevelInformation info, GUI gui, AnimationRunner animator, Counter scoreCounter,
                     Counter livesCounter) {
        this.levelInfo = info;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = gui;

        this.blocksDestroyedCounter = new Counter();
        this.ballsCounter = new Counter();
        this.scoreCounter = scoreCounter;
        this.livesCounter = livesCounter;

        this.animator = animator;
        this.maxWidth = this.gui.getDrawSurface().getWidth();
        this.maxHeight = this.gui.getDrawSurface().getHeight();
    }

    /**
     * Function name: addCollidable.
     * The function adds a collidable object to its data.
     *
     * @param c - the object we add to the game data
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Function name: removeCollidable.
     * The function removes a collidable object from its data.
     *
     * @param c - the object we remove from the game
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Function name: addSprite.
     * The function adds a sprite object to its data
     *
     * @param s - the object we add to the game data
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Function name: removeSprite.
     * The function removes a sprite object from its data
     *
     * @param s - the object we remove from the game
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Function name: createEdgeBlocks.
     * The function creates the 4 surrounding blocks that keep the game ball from going out of range and add them
     * to the game data
     */
    private void createEdgeBlocks() {
        int edgeBlockLength = 25;

        double downBlockStartY = this.maxHeight + edgeBlockLength;
        double rightBlockStartX = this.maxWidth - edgeBlockLength;
        // used to distinguish the edge blocks from the regular game blocks in the blocks-destruction process
        double hitPoints = Double.POSITIVE_INFINITY;
        java.awt.Color edgeColor = Color.GRAY;

        Point p = new Point(0, edgeBlockLength);
        Block up = new Block(p, this.maxWidth, edgeBlockLength, edgeColor, hitPoints);

        p = new Point(0, downBlockStartY);
        Block down = new Block(p, this.maxWidth, edgeBlockLength, edgeColor, hitPoints);

        p = new Point(0, edgeBlockLength);
        Block left = new Block(p, edgeBlockLength, this.maxHeight, edgeColor, hitPoints);

        p = new Point(rightBlockStartX, edgeBlockLength);
        Block right = new Block(p, edgeBlockLength, this.maxHeight, edgeColor, hitPoints);

        up.addToGame(this);
        down.addToGame(this);
        left.addToGame(this);
        right.addToGame(this);

        // the bottom block acts as a "death region" - if a ball touches it - it's out of the game
        HitListener ballRemover = new BallRemover(this, this.ballsCounter);
        down.addHitListener(ballRemover);
    }

    /**
     * Function name: addBlocksToGame.
     * Adding all the previously created blocks to the game data and adding game-managing listeners to them
     *
     * @param blockRemover - a listener that removes blocks when hit
     * @param scoreTracker - a listener that adds points to the score when a block is hit
     */
    private void addBlocksToGame(HitListener blockRemover, HitListener scoreTracker) {
        // adding each block from the list to the game and adding to it the required listeners
        List<Block> list = this.levelInfo.blocks();

        for (Block block : list) {
            block.addToGame(this);
            block.addHitListener(blockRemover);
            block.addHitListener(scoreTracker);
        }
    }

    /**
     * Function name: createLevelBalls.
     * Creating the balls and adding them to the level data - each level has different ball settings.
     */
    private void createLevelBalls() {
        // creating the balls at the center of the paddle
        int edgeBlockLength = 25;
        int paddleHeight = 30;

        double paddleYPosition = this.maxHeight - paddleHeight - edgeBlockLength;
        Point p = new Point(this.maxWidth / 2, paddleYPosition);
        int ballRadius = 5;

        // creating each ball with the initially created velocity
        for (int i = 0; i < this.levelInfo.numberOfBalls(); i++) {
            Ball b = new Ball(p, ballRadius, Color.WHITE);
            b.addToGame(this);
            b.setVelocity(this.levelInfo.initialBallVelocities().get(i));
        }
        this.ballsCounter.increase(this.levelInfo.numberOfBalls());
    }

    /**
     * Function name: createLevelPaddle.
     * Creating the game paddle and adding to the level data - each level has different paddle settings.
     */
    private void createLevelPaddle() {
        // creating the keyboard from the GUI
        biuoop.KeyboardSensor keyboard = this.gui.getKeyboardSensor();
        int paddleHeight = 30;
        int edgeBlockLength = 25;

        double upperLeftX = (this.maxWidth / 2) - (this.levelInfo.paddleWidth() / 2);
        double upperLeftY = this.maxHeight - paddleHeight / 2 - edgeBlockLength;
        Point p = new Point(upperLeftX, upperLeftY);

        // creating the paddle and adding to the game data
        Paddle paddle = new Paddle(p, this.levelInfo.paddleWidth(), paddleHeight, this.levelInfo.paddleSpeed(),
                keyboard);
        paddle.setRangeOfMovement(this.gui, edgeBlockLength);
        paddle.addToGame(this);
    }

    /**
     * Function name: initialize.
     * The function creates all the game objects - balls, paddle and blocks and adds them to the game.
     */
    public void initialize() {
        this.sprites.addSprite(this.levelInfo.getBackground());
        this.createLevelPaddle();

        // creating a block remover
        HitListener blockRemover = new BlockRemover(this, this.blocksDestroyedCounter);

        // creating a lives indicator
        LivesIndicator livesIndicator = new LivesIndicator(this.livesCounter);
        this.sprites.addSprite(livesIndicator);

        // creating score-related objects
        HitListener scoreTracker = new ScoreTrackingListener(this.scoreCounter);
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.scoreCounter);
        this.sprites.addSprite(scoreIndicator);

        // creating a level name indicator
        Sprite levelName = new NameLevelIndicator(this.levelInfo.levelName());
        this.sprites.addSprite(levelName);

        // creating all the blocks (with the added remover and score tracker)
        this.createEdgeBlocks();
        this.addBlocksToGame(blockRemover, scoreTracker);
    }

    /**
     * Function name: playOneTurn.
     * The function starts the animation loop
     */
    public void playOneTurn() {
        // creating balls
        this.createLevelBalls();
        // placing the paddle in the center
        double upperLeftX = (this.maxWidth / 2) - (this.levelInfo.paddleWidth() / 2);
        this.environment.getGamePaddle().getCollisionRectangle().setUpperLeftXPosTo(upperLeftX);

        // displaying the counting down animation before each turn
        CountdownAnimation countDownScreen = new CountdownAnimation(this.sprites, 2, 3);
        this.animator.run(countDownScreen);

        // run the actual play animation
        this.shouldAnimationContinue = true;
        this.animator.run(this);
    }

    /**
     * Function name: shouldStop.
     * Returns whether the current animation should stop or continue
     *
     * @return true if the animation should stop, false otherwise
     */
    public boolean shouldStop() {
        return !this.shouldAnimationContinue;
    }

    /**
     * Function name: doOneFrame.
     * The function performs one frame of the animation and displays it on the given draw surface
     *
     * @param d  - the platform on which the outcome of the action is displayed
     * @param dt - the time that passed since the last frame was shown
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // drawing all the sprite objects and 'notifying' them that time has passed - act their respective actions
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);

        // if there aren't any more balls - you lose a life
        if (this.ballsCounter.getValue() == 0) {
            this.livesCounter.decrease(1);
            this.shouldAnimationContinue = false;
        }
        // if there aren't any more blocks (beside the edges) - you win a round and receive 100 points
        if (this.blocksDestroyedCounter.getValue() == this.levelInfo.numberOfBlocksToRemove()) {
            this.scoreCounter.increase(100);
            this.shouldAnimationContinue = false;
        }
        // if the user pressed 'p' in the keyboard to pause the game - do it
        if (this.gui.getKeyboardSensor().isPressed("p")) {
            this.animator.run(new KeyPressStoppableAnimation(new PauseScreen(), this.gui.getKeyboardSensor(),
                    KeyboardSensor.SPACE_KEY));
        }
    }

    /**
     * Function name: getNumBlocksDestroyed.
     * Returns the amount of blocks destroyed in the level
     *
     * @return amount of blocks destroyed
     */
    public int getNumBlocksDestroyed() {
        return this.blocksDestroyedCounter.getValue();
    }

    /**
     * Function name: getEnvironment.
     * The function returns the object that stores the information about all the collidable objects
     * in the game
     *
     * @return game environment object
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }
}
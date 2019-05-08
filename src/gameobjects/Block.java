package gameobjects;

import collidablesdata.Collidable;
import collidablesdata.Sprite;
import collidablesdata.Velocity;
import gamelogic.GameLevel;
import listeners.HitListener;
import listeners.HitNotifier;
import biuoop.DrawSurface;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The block is a game object that a ball hits.
 * Each block has a position in the game and a color.
 * The program adds the block to the game data and draws the block on a given draw surface.
 * When a block is hit - it changes the ball's velocity based on the position of the hit.
 *
 * @author Ariel Fenster
 */
public class Block implements Collidable, Sprite, HitNotifier {
    // members:

    // the block itself is an instance of the Rectangle class
    private Rectangle block;

    // each block has display options and an amount of hit points
    private Map<Integer, Color> fillColors;
    private Map<Integer, Image> fillImages;
    private Color currentColor;
    private Image currentImage;
    private java.awt.Color outlineColor;
    private double hitPoints;
    private java.awt.Color color;
    private boolean isEdgeBlock;

    // a list of listeners that are notified when the block is hit
    private List<HitListener> hitListeners;

    /**
     * Function name: Block.
     * Constructor for the edge blocks
     *
     * @param upperLeft - the upper left corner of the block
     * @param width     - the block's width
     * @param height    - the block's height
     * @param color     - the block's color
     * @param hitPoints - amount of hits before the block will disappear
     */
    public Block(Point upperLeft, double width, double height, java.awt.Color color, double hitPoints) {
        this.block = new Rectangle(upperLeft, width, height);
        this.hitPoints = hitPoints;
        this.color = color;
        this.hitListeners = new ArrayList<>();
        this.isEdgeBlock = true;
        this.currentColor = null;
        this.currentImage = null;
    }

    /**
     * Function name: Block.
     * Constructor for the game blocks.
     *
     * @param upperLeft    - the upper left corner of the block
     * @param width        - the block's width
     * @param height       - the block's height
     * @param fillColors   - a map of colors to fill the block with
     * @param fillImages   - a map of images to fill the block with
     * @param outlineColor - the block's outline color
     * @param hitPoints    - amount of hits before the block will disappear
     */
    public Block(Point upperLeft, double width, double height, Map<Integer, Color> fillColors,
                 Map<Integer, Image> fillImages, java.awt.Color outlineColor, double hitPoints) {
        this.block = new Rectangle(upperLeft, width, height);
        this.hitPoints = hitPoints;
        this.fillColors = fillColors;
        this.fillImages = fillImages;
        this.outlineColor = outlineColor;
        this.hitListeners = new ArrayList<>();
        this.isEdgeBlock = false;
        this.changeCurrentFill();
    }

    /**
     * Function name: addToGame.
     * The function adds the brick to the level data
     *
     * @param level - the level which we add the block to
     */
    public void addToGame(GameLevel level) {
        // adding to the game's variables and data
        level.addSprite(this);
        level.addCollidable(this);
    }

    /**
     * Function name: removeFromGame.
     * The function removes the brick from the level data
     *
     * @param level - the level which we remove the block from
     */
    public void removeFromGame(GameLevel level) {
        // removing the the block from the game data
        level.removeCollidable(this);
        level.removeSprite(this);
    }

    /**
     * Function name: drawOn.
     * The function draws the brick on the given draw surface and its display number using another function.
     *
     * @param surface - the drawing surface on which we draw the brick
     */
    public void drawOn(DrawSurface surface) {
        int xPos = (int) this.block.getUpperLeft().getX();
        int yPos = (int) this.block.getUpperLeft().getY();
        int width = (int) this.block.getWidth();
        int height = (int) this.block.getHeight();

        // edge blocks are only painted with their given color
        if (this.isEdgeBlock) {
            surface.setColor(this.color);
            surface.fillRectangle(xPos, yPos, width, height);
            return;
        }
        // if the current fill is a color
        if (this.currentImage == null) {
            surface.setColor(this.currentColor);
            surface.fillRectangle(xPos, yPos, width, height);
            // if the current fill is an image
        } else {
            surface.drawImage(xPos, yPos, this.currentImage);
        }
        // draw the outline frame of the block (if exists)
        if (this.outlineColor != null) {
            surface.setColor(this.outlineColor);
            surface.drawRectangle(xPos, yPos, width, height);
        }
    }

    /**
     * Function name: changeCurrentFill.
     * Changing the fill color/image based on the amount of hit points it has left
     */
    private void changeCurrentFill() {
        // resetting the values
        this.currentColor = null;
        this.currentImage = null;
        // getting the new values
        if (this.fillColors.containsKey((int) this.hitPoints)) {
            this.currentColor = this.fillColors.get((int) this.hitPoints);
        } else if (this.fillColors.containsKey(1)) {
            this.currentColor = this.fillColors.get(1);
        } else if (this.fillImages.containsKey((int) this.hitPoints)) {
            this.currentImage = this.fillImages.get((int) this.hitPoints);
        } else {
            this.currentImage = this.fillImages.get(1);
        }
    }

    /**
     * Function name: timePassed.
     * In this assignment this function does nothing. We will add to it in the future.
     *
     * @param dt - the time that passed since the last frame was shown
     */
    public void timePassed(double dt) {

    }

    /**
     * Function name: getCollisionRectangle.
     * The function returns this block object
     *
     * @return the block
     */
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**
     * Function name: hit.
     * The function notifies the object that the ball collided with it and changes the ball's velocity based on
     * where the ball hit the block.
     *
     * @param hitter          - the ball that is hitting the block
     * @param collisionPoint  - the collision point of the ball with the block
     * @param currentVelocity - the velocity of the ball
     * @return a new velocity based on where the ball hit the block
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity newVelocity;
        // if the collision point is on the top or bottom of the block - change Y speed
        if (this.block.getTopLine().isPointOnSegment(collisionPoint)
                || this.block.getBottomLine().isPointOnSegment(collisionPoint)) {
            newVelocity = new Velocity(currentVelocity.getVelocityDx(), -1 * currentVelocity.getVelocityDy());
            // if the collision is on the side of the block - change X speed
        } else {
            newVelocity = new Velocity(-1 * currentVelocity.getVelocityDx(), currentVelocity.getVelocityDy());
        }
        this.hitPoints--;
        if (!this.isEdgeBlock) {
            this.changeCurrentFill();
        }
        // notifying all the hit listeners related to this block that it has been hit
        this.notifyHit(hitter);
        // return the new velocity
        return newVelocity;
    }

    /**
     * Function name: notifyHit.
     * Notifying all the block's listeners that it has been hit.
     *
     * @param hitter - the object that hit the block
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener listener : listeners) {
            listener.hitEvent(this, hitter);
        }
    }

    /**
     * Function name: addHitListener.
     * Adding a listener to the block's list of listeners
     *
     * @param listener - a listener we add to the list
     */
    public void addHitListener(HitListener listener) {
        this.hitListeners.add(listener);
    }

    /**
     * Function name: removeHitListener.
     * Removing a listener from the block's list of listeners
     *
     * @param listener - a listener we remove from the list
     */
    public void removeHitListener(HitListener listener) {
        this.hitListeners.remove(listener);
    }

    /**
     * Function name: getColor.
     * Return the block's color
     *
     * @return block's color
     */
    public java.awt.Color getColor() {
        return this.fillColors.get((int) this.hitPoints);
    }

    /**
     * Function name: getHitPoints.
     * Return ths number of life points remaining for the block
     *
     * @return block's life points
     */
    public double getHitPoints() {
        return this.hitPoints;
    }
}



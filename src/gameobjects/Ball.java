package gameobjects;

import collidablesdata.Sprite;
import collidablesdata.Velocity;
import collidablesdata.GameEnvironment;
import collidablesdata.Collidable;
import collidablesdata.CollisionInfo;
import gamelogic.GameLevel;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The Ball class is a game object that hits blocks in the game. A ball has size, color, velocity and line of movement
 * called trajectory. The program adds the ball to the game data and draws it on a given draw surface.
 * It determines whether a ball will hit a brick and, if so, calculates the ball's new trajectory based
 * on its current velocity after the hit.
 *
 * @author Ariel Fenster
 */
public class Ball implements Sprite {
    // members
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private GameEnvironment gameArea;
    private boolean wasSpeedAdjusted;

    /**
     * Function name: Ball.
     * Constructor for the Ball class
     *
     * @param center - the center point of the ball
     * @param r      - the radius of the call
     * @param color  - the color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.wasSpeedAdjusted = false;
    }

    /**
     * Function name: drawOn.
     * The function draws the ball on the given DrawSurface
     *
     * @param surface - a tool used to draw circles (balls) and color them
     */
    public void drawOn(DrawSurface surface) {
        int x = (int) center.getX();
        int y = (int) center.getY();
        int r = this.radius;

        surface.setColor(this.color);
        surface.fillCircle(x, y, r);
        surface.setColor(Color.BLACK);
        surface.drawCircle(x, y, r);
    }

    /**
     * Function name: timePassed.
     * The function announces to the ball that a time frame has passed and moves the ball
     *
     * @param dt - the time that passed since the last frame was shown
     */
    public void timePassed(double dt) {
        // error correcting - if the ball is inside the paddle - fix its position
        Collidable paddle = this.gameArea.getGamePaddle();
        if (this.isBallInsidePaddle(paddle)) {
            this.moveBallOutsidePaddle(paddle);
        }
        // regardless if the ball was inside the paddle - call the moving function to continue the movement
        this.moveOneStep(dt);
    }

    /**
     * Function name: addToGame.
     * The function adds the ball to the level data
     *
     * @param gameLevel - the level which we add the ball to
     */
    public void addToGame(GameLevel gameLevel) {
        // linking the ball to the gameLevel area and adding the ball to the sprite collection of the gameLevel
        this.setGameArea(gameLevel.getEnvironment());
        gameLevel.addSprite(this);
    }

    /**
     * Function name: removeFromGame.
     * The function removes the ball from the level data
     *
     * @param gameLevel - the level which we remove the ball from
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }

    /**
     * Function name: moveOneStep.
     * The function moves the ball according to its velocity and checks if it will hit a brick in the game due to its
     * trajectory.
     *
     * @param dt - the time that passed since the last frame was shown
     */
    private void moveOneStep(double dt) {
        // first adjusting the ball's speed based on the frame-rate of the game (only once)
        if (!wasSpeedAdjusted) {
            this.adjustVelocity(dt);
            this.wasSpeedAdjusted = true;
        }
        CollisionInfo info = this.gameArea.getClosestCollision(this.getTrajectory());
        // if there won't be a collision
        if (info == null) {
            // move the ball as normal
            this.center = this.velocity.applyToPoint(this.center);
            // if there will be a collision
        } else {
            Point collisionPoint = info.collisionPoint();
            this.center = moveToAlmostCollisionPoint(collisionPoint, this.velocity);
            this.setVelocity(info.collisionObject().hit(this, collisionPoint, this.velocity));
        }
    }

    /**
     * Function name: moveToAlmostCollisionPoint.
     * The function is called when a collision has been detected by moveOneStep due to the ball's current trajectory.
     * The function receives the calculated collision point to be and, based on the direction the ball is moving,
     * sets the ball's next position to be right next to the collision object to ensure the ball won't get stuck and
     * maintain a smooth play animation.
     *
     * @param collPoint - calculated collision point with a game object
     * @param v         - the ball's current velocity
     * @return a point right next to the calculated collision point
     */
    private Point moveToAlmostCollisionPoint(Point collPoint, Velocity v) {
        double newX;
        double newY;
        // the ball goes right
        if (v.getVelocityDx() > 0) {
            newX = collPoint.getX() - 2;
            // the ball goes left
        } else {
            newX = collPoint.getX() + 2;
        }
        // the ball goes down
        if (v.getVelocityDy() > 0) {
            newY = collPoint.getY() - 2;
            // the ball goes up
        } else {
            newY = collPoint.getY() + 2;
        }
        return new Point(newX, newY);
    }

    /**
     * Function name: isBallInsidePaddle.
     * The function checks whether the ball is inside the boundaries of the paddle due to the paddle's movement
     * and the ball not detecting it, resulting in the ball getting stuck in the paddle.
     *
     * @param paddle - the game paddle
     * @return true if the ball is inside the paddle, false otherwise
     */
    private boolean isBallInsidePaddle(Collidable paddle) {
        // if x is between the side edges
        return (this.center.getX() < paddle.getCollisionRectangle().getUpperRight().getX()
                && this.center.getX() > paddle.getCollisionRectangle().getUpperLeft().getX()
                // if y is between top and bottom edges
                && this.center.getY() < paddle.getCollisionRectangle().getDownRight().getY()
                && this.center.getY() > paddle.getCollisionRectangle().getUpperRight().getY());
    }

    /**
     * Function name: moveBallOutsidePaddle.
     * The function frees the ball from the paddle by changing the ball's position to be the middle
     * of the top edge of the paddle. In addition, because the ball didn't detect the collision, adjust the
     * direction of the ball's speed as if a collision would have been detected
     *
     * @param paddle - the game paddle
     */
    private void moveBallOutsidePaddle(Collidable paddle) {
        // moving outside the paddle - placing the ball in the middle of the top edge of the paddle
        this.center = paddle.getCollisionRectangle().getTopLine().middle();

        // changing the y speed direction to send the ball on its merry way
        this.setVelocity(this.velocity.getVelocityDx(), -1 * this.velocity.getVelocityDy());
    }

    /**
     * Function name: getTrajectory.
     * Setting the trajectory of the ball based on its current position and velocity and return it
     *
     * @return the ball's trajectory - direction of movement
     */
    private Line getTrajectory() {
        // the start is where the ball is now
        Point start = this.center;
        // the end is where the ball will be at the next frame
        Point end = this.velocity.applyToPoint(this.center);
        // the trajectory is the line connecting between these two points
        return new Line(start, end);
    }

    // set functions:

    /**
     * Function name: setGameArea.
     * The function links between the ball object and the game data
     *
     * @param area - the game which we link the ball to
     */
    private void setGameArea(GameEnvironment area) {
        this.gameArea = area;
    }

    /**
     * Function name: setVelocity (#1).
     * Setting the ball's speed using a given velocity object
     *
     * @param v - a velocity object
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Function name: setVelocity (#2).
     * Setting the ball's speed using given speeds in the X and Y axis.
     *
     * @param dx - speed in X axis
     * @param dy - speed in Y axis
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Function name: getDiagonalSpeed.
     * Return the velocity of the ball in the direction it's heading
     *
     * @return ball's angular velocity
     */
    public double getDiagonalSpeed() {
        return Math.sqrt(Math.pow(this.velocity.getVelocityDx(), 2) + Math.pow(this.velocity.getVelocityDy(), 2));
    }

    /**
     * Function name: adjustVelocity.
     * Adjusting the ball's velocity based on the frame-rate of the game
     *
     * @param dt - the time that passed since the last frame was shown
     */
    private void adjustVelocity(double dt) {
        this.setVelocity(this.velocity.getVelocityDx() * dt, this.velocity.getVelocityDy() * dt);
    }
}
package gameobjects;

import collidablesdata.Collidable;
import collidablesdata.Sprite;
import collidablesdata.Velocity;
import gamelogic.GameLevel;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * The paddle is the player-controlled game object that moves by pressing the keyboard arrow keys.
 * The program moves the paddle according to the player, changes the ball's velocity based on the position
 * the ball hit the paddle (the paddle is divided into 5 regions where each region changes the velocity differently).
 * The program adds the paddle to the game data and draws it.
 */
public class Paddle implements Sprite, Collidable {
    // members:

    // connecting between the user's actions and the paddle's movement
    private biuoop.KeyboardSensor keyboard;

    // the paddle is an instance of the Rectangle class
    private Rectangle paddle;

    // the paddle's movement speed every time the user presses an arrow key
    private int paddleSpeed;

    // dividing the paddle into 5 equally-spaced regions: all of the regions are part of the top line of the paddle
    private Line region1;
    private Line region2;
    private Line region3;
    private Line region4;
    private Line region5;

    // the allowed range of movement for the paddle (specific for the paddle's upper left corner)
    private double minX;
    private double maxX;

    // end of members

    /**
     * Function name: Paddle.
     * Constructor for the class
     *
     * @param upperLeft - the upper left corner of the paddle
     * @param width     - the width of the paddle
     * @param height    - the height of the paddle
     * @param speed     - the movement speed of the paddle
     * @param keyboard  - the object that links between the player's actions and the actual movement of the paddle
     */
    public Paddle(Point upperLeft, double width, double height, int speed, KeyboardSensor keyboard) {
        this.paddle = new Rectangle(upperLeft, width, height);
        this.paddleSpeed = speed;
        this.keyboard = keyboard;
        this.updateRegions();
    }

    /**
     * Function name: setRangeOfMovement.
     * The function assigns the maximum and minimum 'x' values for the upper left corner of the paddle
     *
     * @param gui              - the gui of the game - holds the game size dimensions
     * @param distanceFromEdge - width of the edge block
     */
    public void setRangeOfMovement(GUI gui, int distanceFromEdge) {
        this.minX = distanceFromEdge;
        this.maxX = gui.getDrawSurface().getWidth() - distanceFromEdge - this.paddle.getWidth();
    }

    /**
     * Function name: timePassed.
     * The function moves the paddle according to the player's actions and updates its new position
     *
     * @param dt - the time that passed since the last frame was shown
     */
    public void timePassed(double dt) {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft(dt);
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight(dt);
        }
        // every time we move the paddle we update its corners (and by so its regions) positions
        this.paddle.updateCorners();
        this.updateRegions();
    }

    /**
     * Function name: drawOn.
     * The function draws the paddle on the given draw surface
     *
     * @param d - the draw surface on which we draw the paddle
     */
    public void drawOn(DrawSurface d) {
        int x = (int) this.paddle.getUpperLeft().getX();
        int y = (int) this.paddle.getUpperLeft().getY();
        int width = (int) this.paddle.getWidth();
        int height = (int) this.paddle.getHeight();

        d.setColor(Color.YELLOW);
        d.fillRectangle(x, y, width, height);
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, width, height);
    }

    /**
     * Function name: getCollisionRectangle.
     * Return the paddle object
     *
     * @return the paddle object
     */
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }

    /**
     * Function name: hit.
     * The function changes the angle of the velocity of the ball based on what region of the paddle the ball hit.
     *
     * @param hitter          - the ball that is hitting the paddle
     * @param collisionPoint  - where the ball and the object collided
     * @param currentVelocity - the ball's current velocity
     * @return a new ball's velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity newVelocity;
        double ballSpeed = hitter.getDiagonalSpeed();
        double regOneAngle = -60;
        double regTwoAngle = -30;
        double regThreeAngle = 0;
        double regFourAngle = 30;
        double regFiveAngle = 60;

        // checking each region and changing the angle based on that hit
        if (this.region1.isPointOnSegment(collisionPoint)) {
            newVelocity = Velocity.fromAngleAndSpeed(regOneAngle, ballSpeed);

        } else if (this.region2.isPointOnSegment(collisionPoint)) {
            newVelocity = Velocity.fromAngleAndSpeed(regTwoAngle, ballSpeed);

        } else if (this.region3.isPointOnSegment(collisionPoint)) {
            newVelocity = Velocity.fromAngleAndSpeed(regThreeAngle, ballSpeed);

        } else if (this.region4.isPointOnSegment(collisionPoint)) {
            newVelocity = Velocity.fromAngleAndSpeed(regFourAngle, ballSpeed);

        } else if (this.region5.isPointOnSegment(collisionPoint)) {
            newVelocity = Velocity.fromAngleAndSpeed(regFiveAngle, ballSpeed);

            // if hitting one of the sides of the paddle
        } else {
            newVelocity = new Velocity(currentVelocity.getVelocityDx() * -1, currentVelocity.getVelocityDy());
        }
        return newVelocity;
    }

    /**
     * Function name: addToGame.
     * The function adds the paddle to the level data
     *
     * @param gameLevel - the level which we add the paddle to
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addCollidable(this);
        gameLevel.addSprite(this);
    }

    /**
     * Function name: moveLeft.
     * Move the paddle right. Ensures that the paddle does not exceed the range of the screen
     *
     * @param dt - the time that passed since the last frame was shown
     */
    private void moveLeft(double dt) {
        if (!isHittingLeftEdge()) {
            this.movePaddleBy(-this.paddleSpeed * dt);
        } else {
            this.paddle.setUpperLeftXPosTo(this.minX);
        }
    }

    /**
     * Function name: moveRight.
     * Move the paddle right. Ensures that the paddle does not exceed the range of the screen
     *
     * @param dt - the time that passed since the last frame was shown
     */
    private void moveRight(double dt) {
        if (!isHittingRightEdge()) {
            this.movePaddleBy(this.paddleSpeed * dt);
        } else {
            this.paddle.setUpperLeftXPosTo(this.maxX);
        }
    }

    /**
     * Function name: movePaddleBy.
     * Changing the coordinates of the upper left corner of the rectangle by a given amount - used in Paddle class
     *
     * @param amount - the amount we change the position of the corner by
     */
    private void movePaddleBy(double amount) {
        double x = this.paddle.getUpperLeft().getX() + amount;
        double y = this.paddle.getUpperLeft().getY();
        this.paddle = new Rectangle(new Point(x, y), this.paddle.getWidth(), this.paddle.getHeight());
    }

    /**
     * Function name: isHittingLeftEdge.
     * Determines whether the paddle is exceeding the left side of the frame
     *
     * @return true if the paddle is outside of the left side of the frame, false otherwise
     */
    private boolean isHittingLeftEdge() {
        return (this.paddle.getUpperLeft().getX() <= this.minX);
    }

    /**
     * Function name: isHittingRightEdge.
     * Determines whether the paddle is exceeding the right side of the frame
     *
     * @return true if the paddle is outside of the right side of the frame, false otherwise
     */
    private boolean isHittingRightEdge() {
        return (this.paddle.getUpperLeft().getX() >= this.maxX);
    }

    // set functions for the rectangle's top line regions

    /**
     * Function name: updateRegions.
     * The function updates the positions of the regions of the paddle every time the paddle has moved.
     */
    private void updateRegions() {
        // calling the setting function to update new positions
        this.divideToRegions();
    }

    /**
     * Function name: divideToRegions.
     * A collection of setting functions.
     */
    private void divideToRegions() {
        this.setRegion1();
        this.setRegion2();
        this.setRegion3();
        this.setRegion4();
        this.setRegion5();
    }

    /**
     * Function name: setRegion1.
     * The function assigns the first region of the top of the paddle.
     */
    private void setRegion1() {
        // the X value for the end point is a 1/5 of the paddle's width away from the start X value
        Point start = this.paddle.getUpperLeft();
        Point end = new Point(start.getX() + this.paddle.getWidth() / 5, start.getY());
        this.region1 = new Line(start, end);
    }

    /**
     * Function name: setRegion2.
     * The function assigns the second region of the top of the paddle.
     * Each new starting region point is the end point of the previous region.
     */
    private void setRegion2() {
        Point start = this.region1.end();
        Point end = new Point(start.getX() + this.paddle.getWidth() / 5, start.getY());
        this.region2 = new Line(start, end);
    }

    /**
     * Function name: setRegion3.
     * The function assigns the third region of the top of the paddle.
     */
    private void setRegion3() {
        Point start = this.region2.end();
        Point end = new Point(start.getX() + this.paddle.getWidth() / 5, start.getY());
        this.region3 = new Line(start, end);
    }

    /**
     * Function name: setRegion4.
     * The function assigns the fourth region of the top of the paddle.
     */
    private void setRegion4() {
        Point start = this.region3.end();
        Point end = new Point(start.getX() + this.paddle.getWidth() / 5, start.getY());
        this.region4 = new Line(start, end);
    }

    /**
     * Function name: setRegion5.
     * The function assigns the fifth and final region of the top of the paddle.
     */
    private void setRegion5() {
        Point start = this.region4.end();
        Point end = new Point(start.getX() + this.paddle.getWidth() / 5, start.getY());
        this.region5 = new Line(start, end);
    }
}
package collidablesdata;

import gameobjects.Ball;
import gameobjects.Point;
import gameobjects.Rectangle;

/**
 * The interface indicates that a game object can be collided with - can be hit.
 * Every collidable object in the game can change the ball's velocity and send the information about the shape
 * of the collision.
 *
 * @author Ariel Fenster
 */
public interface Collidable {

    /**
     * Function name: getCollisionRectangle.
     * The function returns the game object itself that took part in the collision - block/paddle etc.
     *
     * @return the object the ball collided with
     */
    Rectangle getCollisionRectangle();

    /**
     * Function name: hit.
     * The function changes the ball's velocity based on where it hit the object
     *
     * @param collisionPoint  - where the ball and the object collided
     * @param currentVelocity - the ball's current velocity
     * @param hitter          - the object that is hitting the collidable object
     * @return a new ball velocity based on the place of impact
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}

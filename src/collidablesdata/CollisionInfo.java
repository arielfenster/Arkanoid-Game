package collidablesdata;

import gameobjects.Point;

/**
 * The class holds information about a collision between the ball and a game object - brick or the player's paddle.
 * It holds the collision point and the collided object.
 *
 * @author Ariel Fenster
 */
public class CollisionInfo {
    // members
    private Point collPoint;
    private Collidable collObject;

    /**
     * Function name: CollisionInfo.
     * Constructor
     *
     * @param collPoint  - the collision point
     * @param collObject - the object the ball collided with
     */
    public CollisionInfo(Point collPoint, Collidable collObject) {
        this.collPoint = collPoint;
        this.collObject = collObject;
    }

    /**
     * Function name: collisionPoint.
     * Return the point where the collision occurs
     *
     * @return the collision point
     */
    public Point collisionPoint() {
        return this.collPoint;
    }

    /**
     * Function name: collisionObject.
     * Return the collidable object involved in the collision
     *
     * @return the object the ball collided with
     */
    public Collidable collisionObject() {
        return this.collObject;
    }
}

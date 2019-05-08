package collidablesdata;

import gameobjects.Line;
import gameobjects.Point;
import gameobjects.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * The program stores all the information about all the collidable objects in the game.
 * It can determine whether a moving object (ball) will hit any of the game objects and, if so, return its information.
 *
 * @author Ariel Fenster
 */
public class GameEnvironment {
    // member - holds a list of all the collidable objects in the game
    private List<Collidable> collidables;

    /**
     * Function name: GameEnvironment.
     * Constructor
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Function name: addCollidable.
     * Add the given collidable to the game environment
     *
     * @param c - the collidable object we add to the game data
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Function name: removeCollidable.
     * Remove the given collidable from the game environment
     *
     * @param c - the collidable object we remove from the game data
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Function name: getClosestCollision.
     * The function goes through all the collidable objects in the game and detects whether the game ball will
     * hit any of them.
     *
     * @param trajectory - the ball's line of movement
     * @return if a collision will occur - the information about the closest collision that is going to occur,
     * otherwise null
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // default creation - in case there won't be a collision
        CollisionInfo collInfo = null;

        // creating a copy of the existing list of collidables and iterating over them
        List<Collidable> newCollidablesList = new ArrayList<>(this.collidables);

        // going through all the game objects
        for (Collidable collObject : newCollidablesList) {
            // get the current rectangle from the list of collidables
            Rectangle rect = collObject.getCollisionRectangle();
            // get the intersection point between the trajectory line and the current rectangle.
            Point collPoint = trajectory.closestIntersectionToStartOfLine(rect);

            // if the point of intersection isn't null - meaning we hit an object
            if (collPoint != null) {
                collInfo = new CollisionInfo(collPoint, collObject);
                break;
            }
        }
        // if won't hit anything - collInfo will be null, otherwise will be the collision information
        return collInfo;
    }

    /**
     * Function name: getGamePaddle.
     * Returns the user's game paddle
     *
     * @return game paddle
     */
    public Collidable getGamePaddle() {
        // the paddle is the first collidable we add to the game
        return this.collidables.get(0);
    }
}

package collidablesdata;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * The program keeps a list of information of all the sprite objects in the game - objects that can be drawn
 * and can be affected by the game objects.
 *
 * @author Ariel Fenster
 */
public class SpriteCollection {
    // member - holds a list of all sprite objects in the game
    private ArrayList<Sprite> sprites;

    /**
     * Function name: SpriteCollection.
     * Constructor for the class
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Function name: addSprite.
     * Adding a given sprite object to the game
     *
     * @param s - the object we add to the game data
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Function name: removeSprite.
     * Removing a given sprite object from the game
     *
     * @param s - the object we remove from the game
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * Function name: notifyAllTimePassed.
     * The function calls the timePassed function on all the sprite objects.
     *
     * @param dt - the time that passed since the last frame was shown
     */
    public void notifyAllTimePassed(double dt) {
        // creating a copy of the sprites list and iterating over them
        List<Sprite> newSpritesList = new ArrayList<>(this.sprites);
        for (Sprite s : newSpritesList) {
            s.timePassed(dt);
        }
    }

    /**
     * Function name: drawAllOn.
     * The function calls the drawOn function on all the sprite objects.
     *
     * @param d - the draw surface on which we draw the objects
     */
    public void drawAllOn(DrawSurface d) {
        // creating a copy of the sprites list and iterating over them
        List<Sprite> newSpritesList = new ArrayList<>(this.sprites);
        for (Sprite s : newSpritesList) {
            s.drawOn(d);
        }
    }
}
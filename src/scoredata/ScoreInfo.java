package scoredata;

import java.io.Serializable;

/**
 * Each high score register consists of a name and a score.
 *
 * @author Ariel Fenster
 */
public class ScoreInfo implements Serializable {

    // members
    private String name;
    private int score;

    /**
     * Function name: ScoreInfo.
     * Constructor
     *
     * @param name  - the name of the player
     * @param score - the score the player achieved in the game
     */
    public ScoreInfo(String name, int score) {
        this.score = score;
        this.name = name;
    }

    /**
     * Function name: getName.
     * Return the name of the player
     *
     * @return player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Function name: getScore.
     * Return the score of the player
     *
     * @return player's score
     */
    public int getScore() {
        return this.score;
    }
}
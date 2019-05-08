package animation;

import scoredata.ScoreInfo;
import scoredata.HighScoresTable;
import biuoop.DrawSurface;

import java.util.List;
import java.awt.Color;

/**
 * Used to display the high scores.
 *
 * @author Ariel Fenster
 */
public class HighScoresAnimation implements Animation {

    // member
    private HighScoresTable highScores;

    /**
     * Function name: HighScoresAnimation.
     * Constructor.
     *
     * @param scores - the high scores
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.highScores = scores;
    }

    /**
     * Function name: doOneFrame.
     * Perform one frame of the animation and display it on the given draw surface.
     *
     * @param d  - the platform on which the outcome of the action is displayed
     * @param dt - the time that passed since the last frame was shown
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLUE);
        d.drawText(d.getWidth() / 3, 100, "High Scores Table", 30);

        int fontSize = 20;
        d.setColor(Color.BLACK);
        d.drawText(10, d.getHeight() - 10, "Press SPACE to exit", fontSize);

        int xPosName = 50;
        int xPosScore = 700;
        int yPos = 150;

        List<ScoreInfo> list = this.highScores.getHighScores();
        for (int i = 0; i < list.size(); i++) {
            // drawing the name and the score of the player
            d.drawText(xPosName, yPos + (30 * i), list.get(i).getName(), fontSize);
            d.drawText(xPosScore, yPos + (30 * i), Integer.toString(list.get(i).getScore()), fontSize);
        }
    }

    /**
     * Function name: shouldStop.
     * Returns whether the current animation should stop or continue
     *
     * @return - true if the animation should stop, false otherwise
     */
    @Override
    public boolean shouldStop() {
        return true;
    }
}
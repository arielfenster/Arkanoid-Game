package gamelogic;

import animation.AnimationRunner;
import animation.EndScreen;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import listeners.Counter;
import levelsdata.LevelInformation;
import scoredata.HighScoresTable;
import scoredata.ScoreInfo;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The class is the main running class that plays the levels entered by the user.
 *
 * @author Ariel Fenster
 */
public class GameFlow {

    // members
    private AnimationRunner animator;
    private KeyboardSensor keyboard;
    private GUI gui;
    private HighScoresTable table;
    private File highScoresFile;

    /**
     * Function name: GameFlow.
     * Constructor for the class
     *
     * @param ar         - animation runner used to display each game animation
     * @param ks         - a keyboard connecting the player to the game
     * @param gui        - the gui of the game
     * @param table      - the high scores table of the game
     * @param scoresFile - the file of the high scores table
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui, HighScoresTable table, File scoresFile) {
        this.animator = ar;
        this.keyboard = ks;
        this.gui = gui;
        this.table = table;
        this.highScoresFile = scoresFile;
    }

    /**
     * Function name: runLevels.
     * Play each level until all the levels are complete or the player has lost.
     * When finished, display a 'game over' message.
     *
     * @param levelsToPlay - list of levels to play entered by the user
     */
    public void runLevels(List<LevelInformation> levelsToPlay) {
        // a fresh new game starts with 7 lives and score 0
        Counter scoreCounter = new Counter();
        Counter livesCounter = new Counter();
        livesCounter.increase(7);
        boolean didWin = true;

        // creating the current level
        for (LevelInformation levelInfo : levelsToPlay) {
            GameLevel level = new GameLevel(levelInfo, this.gui, this.animator, scoreCounter, livesCounter);
            level.initialize();

            // keep playing as long as the player didn't complete the level or lost all his lives
            while (level.getNumBlocksDestroyed() != levelInfo.numberOfBlocksToRemove()
                    && livesCounter.getValue() > 0) {
                level.playOneTurn();
            }

            // if the loop above was stopped because the player lost all his lives - end the game
            if (livesCounter.getValue() == 0) {
                didWin = false;
                break;
            }
        }
        this.showEndScreen(didWin,scoreCounter.getValue());
        this.showHighScores(scoreCounter.getValue());
    }

    /**
     * Function name: showEndScreen.
     * Display the game over message based on if the player won or lost the game.
     *
     * @param didWin - indicator whether the player won or lost the game
     * @param score  - the final score of the player
     */
    private void showEndScreen(boolean didWin, int score) {
        String message;
        if (didWin) {
            message = "You Win! Your score is " + score;
        } else {
            message = "Game Over. Your score is " + score;
        }
        // running the animation of the game over screen
        this.animator.run(new KeyPressStoppableAnimation(new EndScreen(message), this.keyboard,
                KeyboardSensor.SPACE_KEY));
    }

    /**
     * Function name: showHighScores.
     * Showing the high scores table. If the player achieved enough points, add him to the table.
     *
     * @param currentScore - the score that player achieved in the game
     */
    private void showHighScores(int currentScore) {
        // checking if the player earned enough points to enter the table. if so - ask for name and add
        if (this.table.shouldAddScore(currentScore)) {
            DialogManager dialog = this.gui.getDialogManager();
            String playerName = dialog.showQuestionDialog("Name", "What is your name?", "");
            this.table.add(new ScoreInfo(playerName, currentScore));
        }
        // show the high scores and save the (new) table to the file
        this.animator.run(new KeyPressStoppableAnimation(new HighScoresAnimation(this.table), this.keyboard,
                KeyboardSensor.SPACE_KEY));
        try {
            this.table.save(this.highScoresFile);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}

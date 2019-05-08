import animation.AnimationRunner;
import animation.GameAnimation;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import gamelogic.GameFlow;

import levelsdata.LevelSetReader;
import menu.Menu;
import menu.MenuAnimation;
import menu.Task;
import scoredata.HighScoresTable;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.io.File;
import java.util.Map;

/**
 * The program sets the game in motion by calling the initiating functions from other classes.
 *
 * @author Ariel Fenster
 */
public class Ass6Game {

    /**
     * Function name: main.
     * Creating the game variables via different functions from different classes.
     *
     * @param args - an array of numbers entered by the user representing the levels the player wishes to play
     */
    public static void main(String[] args) {
        int gameWidth = 800;
        int gameHeight = 600;
        GUI gui = new GUI("Arkanoid", gameWidth, gameHeight);
        AnimationRunner animator = new AnimationRunner(gui);
        KeyboardSensor keyboard = gui.getKeyboardSensor();

        // creating the game
        File highScoresFile = new File("highscores");
        HighScoresTable highScores = HighScoresTable.loadFromFile(highScoresFile);
        GameFlow fullGame = new GameFlow(animator, keyboard, gui, highScores, highScoresFile);

        // creating the menus and joining them together
        Menu<Task<Void>> menu = new MenuAnimation<>("Main Menu", keyboard, animator);
        Menu<Task<Void>> subMenu = new MenuAnimation<>("Choose Game Difficulty", keyboard, animator);

        // adding the selections for the main menu
        menu.addSubMenu("s", "S - Start Game", subMenu);
        menu.addSelection("h", "H - High Scores", new Task<Void>() {
            @Override
            public Void run() {
                animator.run(new KeyPressStoppableAnimation(new HighScoresAnimation(highScores), keyboard,
                        KeyboardSensor.SPACE_KEY));
                return null;
            }
        });
        menu.addSelection("q", "Q - Quit", new Task<Void>() {
            @Override
            public Void run() {
                System.exit(0);
                return null;
            }
        });

        // adding the levels options to the sub menu
        LevelSetReader levelSetReader;
        if (args.length == 0) {
            levelSetReader = new LevelSetReader("");
        } else {
            levelSetReader = new LevelSetReader(args[0]);
        }
        Map<String, String> levelsDescriptions = levelSetReader.getCharToLevelNameMap();
        for (Map.Entry<String, String> entry : levelsDescriptions.entrySet()) {
            subMenu.addSelection(entry.getKey(), entry.getKey().toUpperCase() + " - " + entry.getValue(), new Task<Void>() {
                @Override
                public Void run() {
                    animator.run(new GameAnimation(fullGame, entry.getKey(), levelSetReader));
                    return null;
                }
            });
        }

        // running the game
        while (true) {
            animator.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();
        }
    }
}

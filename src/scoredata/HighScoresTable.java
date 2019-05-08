package scoredata;


import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A high scores table class.
 *
 * @author Ariel Fenster
 */
public class HighScoresTable implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_TABLE_SIZE = 10;

    // members
    private int size;
    private List<ScoreInfo> highScores;

    /**
     * Function name: HighScoresTable.
     * Constructor - create an empty high scores table with the specified size.
     *
     * @param size - the size of the high scores table
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.highScores = new ArrayList<>(size);
    }

    /**
     * Function name: add.
     * Add a high score to the table
     *
     * @param info - name and score to add
     */
    public void add(ScoreInfo info) {
        this.highScores.add(info);
        // if the addition of the new score expanded the table beyond its original size - remove the lowest score
        if (this.highScores.size() > this.size) {
            this.removeLowestScore();
        }
    }

    /**
     * Function name: shouldAddScore.
     * Checks whether a given score is high enough to enter the table
     *
     * @param score - the score to check if eligible to enter the table
     * @return true if the score should be added to the table, false otherwise
     */
    public boolean shouldAddScore(int score) {
        return this.getRank(score) <= this.size;
    }

    /**
     * Function name: removeLowestScore.
     * Go through the table and remove the lowest score
     */
    private void removeLowestScore() {
        int minScore = this.highScores.get(0).getScore();
        int minIndex = 0;

        for (int i = 1; i < this.highScores.size(); i++) {
            if (this.highScores.get(i).getScore() <= minScore) {
                minScore = this.highScores.get(i).getScore();
                minIndex = i;
            }
        }
        this.highScores.remove(minIndex);
    }

    /**
     * Function name: size.
     * Return the size of the table
     *
     * @return table's size
     */
    public int size() {
        return this.size;
    }

    /**
     * Function name: getHighScores.
     * Return the current high scores sorted high to low
     *
     * @return a sorted scores table
     */
    public List<ScoreInfo> getHighScores() {
        List<ScoreInfo> list = this.highScores;
        // creating an anonymous class that overrides the compare method for the sort
        list.sort(new Comparator<ScoreInfo>() {
            @Override
            public int compare(ScoreInfo o1, ScoreInfo o2) {
                if (o1.getScore() < o2.getScore()) {
                    return 1;
                } else if (o1.getScore() > o2.getScore()) {
                    return -1;
                }
                return 0;
            }
        });
        return list;
    }

    /**
     * Function name: getRank.
     * Return the rank of the given score.
     *
     * @param newScore - a new score to check
     * @return score's rank in the table
     */
    private int getRank(int newScore) {
        // getting a sorted table
        List<ScoreInfo> currentScores = this.getHighScores();
        // if the table is empty - the new score should be rank 1
        if (currentScores.size() == 0) {
            return 1;
        }
        int i;
        // checking if the current score should be entered to the table
        for (i = 0; i < currentScores.size(); i++) {
            if (newScore > currentScores.get(i).getScore()) {
                return i + 1;
            }
        }
        // if the new score is the lowest and there is enough space to enter it
        if (currentScores.size() < this.size) {
            return i+1;
        }
        // if the table is full, return a number bigger than the current amount of scores in the table
        return this.size * 10;
    }

    /**
     * Function name: clear.
     * Clear the table
     */
    public void clear() {
        this.highScores.clear();
    }

    /**
     * Function name: load.
     * Load table data from file. Current table data is cleared.
     *
     * @param filename - the file to load the table from
     * @throws IOException - if an error occurs during the load process
     */
    public void load(File filename) throws IOException {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));
            HighScoresTable loadedScores = (HighScoresTable) objectInputStream.readObject();
            this.size = loadedScores.size();
            this.highScores = loadedScores.getHighScores();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Function name: save.
     * Save table data to the given file.
     *
     * @param filename - the file to save the table to
     * @throws IOException - if an error occurs during the save process
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
            objectOutputStream.writeObject(this);
        } catch (IOException i) {
            i.printStackTrace();
        } finally {
            // if the file didn't close successfully - an exception will be thrown
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        }
    }

    /**
     * Function name: loadFromFile.
     * Read a table from a file. If the file doesn't exist or there is a problem with reading it, an empty table
     * is returned.
     *
     * @param filename - the file to load the table from
     * @return a high scores table
     */
    public static HighScoresTable loadFromFile(File filename) {
        ObjectInputStream objectInputStream = null;
        try {
            // try to return the object in the file
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));
            return (HighScoresTable) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException f) {
            return new HighScoresTable(DEFAULT_TABLE_SIZE);
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        }
    }
}
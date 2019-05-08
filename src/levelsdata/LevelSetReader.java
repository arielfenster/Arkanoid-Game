package levelsdata;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * An object that reads a level sets file and creates the levels described in all the files mentioned.
 *
 * @author Ariel Fenster
 */
public class LevelSetReader {

    private static final String DEFAULT_LEVEL_SET = "level_sets.txt";

    private String levelsSet;
    private Map<String, String> charToLevelNameMap;
    private Map<String, String> charToLevelPathMap;

    /**
     * Function name: LevelSetReader.
     * Constructor.
     */
    public LevelSetReader(String levelsSetPath) {
        if (levelsSetPath.equals("")) {
            this.levelsSet = DEFAULT_LEVEL_SET;
        } else {
            this.levelsSet = levelsSetPath;
        }
        this.charToLevelNameMap = new TreeMap<>();
        this.charToLevelPathMap = new TreeMap<>();
    }

    /**
     * Function name: getLevels.
     * Read a specific levels set file based on the requested difficulty and create the levels described in the file
     *
     * @param difficulty - the type of levels to create
     * @return a list of levels to play
     */
    public List<LevelInformation> getLevels(String difficulty) {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(this.charToLevelPathMap.get(difficulty));
        if (is == null) {
            System.out.println("Error reading from the file. Terminating");
            System.exit(1);
        }
        java.io.Reader r = new InputStreamReader(is);
        LevelSpecificationReader levelSpecsReader = new LevelSpecificationReader();
        List<LevelInformation> levels = new ArrayList<>(levelSpecsReader.fromReader(r));
        return levels;
    }

    /**
     * Function name: analyzeLevelSet.
     * Read a levels sets file and map the information to the appropriate maps
     */
    private void analyzeLevelSet() {
        LineNumberReader lineReader = null;
        try {
            lineReader = new LineNumberReader(new FileReader(new File(this.levelsSet)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String currLine = lineReader.readLine();
            String levelKey = null;

            while (currLine != null) {
                // ignore all empty lines
                if (currLine.length() != 0) {
                    // odd number lines contain the key of the levels and the description
                    if (lineReader.getLineNumber() % 2 == 1) {
                        levelKey = Character.toString(currLine.charAt(0));
                        this.charToLevelNameMap.put(levelKey, currLine.substring(2));
                        // even number lines contain the levels' settings file path
                    } else {
                        if (levelKey != null) {
                            this.charToLevelPathMap.put(levelKey, currLine);
                        }
                    }
                }
                currLine = lineReader.readLine();
            }
        } catch (IOException i) {
            i.printStackTrace();
        } finally {
            if (lineReader != null) {
                try {
                    lineReader.close();
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        }
    }


    /**
     * Function name: getCharToLevelNameMap.
     * Return the mapped information of levels-set key and description
     *
     * @return mapped information of levels set key and description
     */
    public Map<String, String> getCharToLevelNameMap() {
        if (this.charToLevelNameMap.isEmpty()) {
            this.analyzeLevelSet();
        }
        return this.charToLevelNameMap;
    }
}

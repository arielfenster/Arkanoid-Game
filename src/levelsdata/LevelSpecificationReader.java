package levelsdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * The class receives a file containing information regarding level construction and returns a mapped information of
 * the details in the file.
 *
 * @author Ariel Fenster
 */
public class LevelSpecificationReader {

    /**
     * Function name: fromReader.
     * Creates a list of levels based on the information written in a file linked by a given reader object
     *
     * @param reader - the object that is linked to the information file
     * @return a list of levels
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        // the list of levels that will be created from files
        List<LevelInformation> levelsList = new ArrayList<>();

        BufferedReader br = null;
        try {
            // get the current line from the text
            br = new BufferedReader(reader);
            String currLine = br.readLine();

            // go through the whole text
            while (currLine != null) {
                br = this.moveReaderToNextLevel(br, currLine);

                // used to store all the level-specific details
                Map<String, String> levelDetails = new TreeMap<>();
                // used to store all the blocks information - each string is a row of information
                List<String> blocksLayoutInfo = new ArrayList<>();

                // retrieve each level settings and store them in the respective data holders
                this.mapLevelSettings(br, levelDetails);
                this.readBlocksSettings(br,blocksLayoutInfo);

                // if the new data bases are empty - meaning we reached the end of the file - end the reading
                if (levelDetails.size() == 0 || blocksLayoutInfo.size() == 0) {
                    break;
                }
                // create a level with the level generator and add it to the list of levels
                levelsList.add(new LevelGenerator().createLevel(levelDetails, blocksLayoutInfo));
            }
        } catch (IOException i) {
            i.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        }
        return levelsList;
    }

    /**
     * Function name: mapLevelSettings.
     * Mapping between a level setting to its value
     *
     * @param br           - reader object that reads through the file
     * @param levelInfoMap - a map containing the level details
     */
    private void mapLevelSettings(BufferedReader br, Map<String, String> levelInfoMap) {
        try {
            String line = br.readLine();
            if (line == null) {
                return;
            }
            // getting all the general level details and mapping them to a map
            while (!line.equals("START_BLOCKS")) {
                // the name is from the start of the line to the colon symbol (excluding)
                String detailName = line.substring(0, line.indexOf(':'));
                // the value is from the colon symbol to the end of the line
                String detailValue = line.substring(line.indexOf(':') + 1);
                levelInfoMap.put(detailName, detailValue);
                line = br.readLine();
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Function name: readBlocksSettings.
     * Reads the blocks settings section of the level description and adds the settings to a list
     *
     * @param br         - reader object that reads through the file
     * @param blocksInfo - a list containing the layout of the blocks in the level
     */
    private void readBlocksSettings(BufferedReader br, List<String> blocksInfo) {
        try {
            String line = br.readLine();
            if (line == null) {
                return;
            }
            while (!line.equals("END_BLOCKS")) {
                // ignore all empty lines
                if (line.length() != 0) {
                    blocksInfo.add(line);
                }
                line = br.readLine();
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Function name: moveReaderToNextLevel.
     * Advance the reader object to the next level written in the file
     *
     * @param br          - the reader object that reads through the file
     * @param currentLine - the current line the reader points to
     * @return advanced position of the reader
     */
    private BufferedReader moveReaderToNextLevel(BufferedReader br, String currentLine) {
        try {
            while (currentLine != null && !currentLine.equals("START_LEVEL")) {
                currentLine = br.readLine();
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
        return br;
    }
}
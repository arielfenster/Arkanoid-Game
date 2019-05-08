package levelsdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class receives a file containing information regarding blocks construction and returns a mapped information of
 * the details in the file.
 *
 * @author Ariel Fenster
 */
public class BlocksDefinitionReader {

    /**
     * Function name: fromReader.
     * Creates a blocks creator factory based on the information written in a file linked by given a reader object
     *
     * @param reader - the object that is linked to the information file
     * @return a blocks creator factory
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        // used to access the non static methods in the class
        BlocksDefinitionReader defReader = new BlocksDefinitionReader();

        // used as members for the to-be-created factory
        Map<String, Integer> spacerWidthsMap = null;
        Map<String, BlockCreator> blockCreatorsMap = new TreeMap<>();

        Map<String, String> defaultBlocksSettings = null;
        Map<String, String> specificBlockSettings;

        BufferedReader br = null;
        try {
            br = new BufferedReader(reader);
            String currentLine = br.readLine();

            // go through the whole text
            while (currentLine != null) {
                if (currentLine.startsWith("default")) {                       // mapping the default settings
                    defaultBlocksSettings = defReader.getSettingsFromLine(currentLine);

                } else if (currentLine.startsWith("bdef")) {                   // mapping a specific block settings
                    // once we have a defaults settings and a block settings, we can create a block generator
                    specificBlockSettings = defReader.getSettingsFromLine(currentLine);
                    // generating a block creator and adding to the map of creators
                    BlockCreator creator = new BlockTemplateCreator(defaultBlocksSettings, specificBlockSettings);
                    blockCreatorsMap.put(specificBlockSettings.get("symbol"), creator);

                } else if (currentLine.startsWith("sdef")) {                   // mapping the spacers widths settings
                    // if the spacers map is null - create it, otherwise expand it
                    if (spacerWidthsMap == null) {
                        spacerWidthsMap = defReader.getSpacersValues(currentLine);
                    } else {
                        spacerWidthsMap.putAll(defReader.getSpacersValues(currentLine));
                    }
                }
                // go to the next line
                currentLine = br.readLine();
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
        return new BlocksFromSymbolsFactory(spacerWidthsMap, blockCreatorsMap);
    }

    /**
     * Function name: getSettingsFromLine.
     * Map all the information in the line to a map in the form of key:value
     *
     * @param currLine - the current line
     * @return a mapped information
     */
    private Map<String, String> getSettingsFromLine(String currLine) {
        Map<String, String> map = new TreeMap<>();

        // pattern of 'key:value'
        Pattern keyValuePattern = Pattern.compile("\\b[[\\w][_|-]]*:\\S*|\\)\\b");
        Matcher matcher = keyValuePattern.matcher(currLine);

        // mapping all the definitions in the current line
        while (matcher.find()) {
            // get the position of the ':' in the current match to distinguish between the key and the value
            int indexOfColon = currLine.indexOf(":", matcher.start());
            String defType = currLine.substring(matcher.start(), indexOfColon);
            String defValue = currLine.substring(indexOfColon + 1, matcher.end());
            map.put(defType, defValue);
        }
        return map;
    }

    /**
     * Function name: getSpacersValues.
     * Map the information in the line regarding spacers definitions to symbol:width pattern
     *
     * @param line - the current line
     * @return a mapped information
     */
    private Map<String, Integer> getSpacersValues(String line) {
        Map<String, Integer> map = new TreeMap<>();
        // get only the spacer symbol and width
        Pattern patt = Pattern.compile(":\\S*");
        Matcher matcher = patt.matcher(line);

        while (matcher.find()) {
            String symbol = Character.toString(line.charAt(matcher.start() + 1));
            // checking that the line also contains the width associated with the spacer symbol
            if (matcher.find()) {
                int width = Integer.parseInt(line.substring(matcher.start() + 1));
                map.put(symbol, width);
            } else {
                map.put(symbol, null);
            }
        }
        return map;
    }
}

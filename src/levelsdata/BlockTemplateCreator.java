package levelsdata;

import gameobjects.Block;
import gameobjects.Point;

import java.awt.Color;
import java.awt.Image;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class is a blueprint that holds information regarding different block type and their information.
 *
 * @author Ariel Fenster
 */
public class BlockTemplateCreator implements BlockCreator {

    // contain all the necessary information - retrieved by reading the text
    private Map<String, String> defaultDetails;
    private Map<String, String> blockTypeDetails;

    // distribute the information to different variables
    private String symbol;
    private Map<Integer, Color> fillColors;
    private Map<Integer, Image> fillImages;
    private Color stroke;
    private double width;
    private double height;
    private double hitPoints;

    /**
     * Function name: BlockTemplateCreator.
     * Constructor.
     *
     * @param defaultDetails   - the information listed in the 'default' section of the information file
     * @param blockTypeDetails - the information listed in a single block information line
     */
    public BlockTemplateCreator(Map<String, String> defaultDetails, Map<String, String> blockTypeDetails) {
        this.defaultDetails = defaultDetails;
        this.blockTypeDetails = blockTypeDetails;
        this.fillColors = new TreeMap<>();
        this.fillImages = new TreeMap<>();
    }

    /**
     * Function name: create.
     * Creates a block at the specified location.
     *
     * @param xPos - x coordinate
     * @param yPos - y coordinate
     * @return a block at the position
     */
    @Override
    public Block create(int xPos, int yPos) {
        // try to set the members. if there is missing data, catch the thrown exception and return null to notify error
        try {
            this.setMembers();
            Point p = new Point(xPos, yPos);
            return new Block(p, this.width, this.height, this.fillColors, this.fillImages, this.stroke, this.hitPoints);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Function name: setMembers.
     * Setting all the members in the class
     *
     * @throws NullPointerException - if one of the maps don't contain the appropriate information
     */
    private void setMembers() throws NullPointerException {
        this.setSymbol();
        this.setBlockFillMaps();
        this.setStroke();
        this.setWidth();
        this.setHeight();
        this.setHitPoints();
    }

    /**
     * Function name: setSymbol.
     * Gets the symbol from the information maps and sets as a member
     *
     * @throws NullPointerException - if the map doesn't contain the appropriate information
     */
    private void setSymbol() throws NullPointerException {
        this.symbol = this.blockTypeDetails.get("symbol");
    }

    private void setBackgroundChange() {

    }

    /**
     * Function name: setBlockFillMaps.
     * Getting the mapped information from the maps and if is fill information - add to the appropriate map
     *
     * @throws NullPointerException - if both of the maps don't contain the appropriate information
     */
    private void setBlockFillMaps() throws NullPointerException {
        if (this.defaultDetails != null) {
            for (Map.Entry<String, String> entry : this.defaultDetails.entrySet()) {
                this.addToFillMaps(entry.getKey() + ":" + entry.getValue());
            }
        }
        for (Map.Entry<String, String> entry : this.blockTypeDetails.entrySet()) {
            this.addToFillMaps(entry.getKey() + ":" + entry.getValue());
        }
        // if the design maps are empty, meaning there is missing information, throw an exception to notify an error
        if (this.fillImages.size() == 0 && this.fillColors.size() == 0) {
            throw new NullPointerException();
        }
    }

    /**
     * Function name: addToFillMaps.
     * Checking if the given string is regarding fill information about the block. If so, add the information to the
     * appropriate map
     *
     * @param data - string representation of a key:value from the maps
     */
    private void addToFillMaps(String data) {
        if (data.startsWith("fill")) {
            String key = data.substring(0, data.indexOf(':'));
            String value = data.substring(data.indexOf(':') + 1);

            // if it's a color
            if (value.contains("color")) {
                ColorsParser parser = new ColorsParser();
                this.fillColors.put(this.getFillNumberFrom(key), parser.colorFromString(value));
                // if it's an image
            } else {
                ImageParser parser = new ImageParser();
                this.fillImages.put(this.getFillNumberFrom(key), parser.imageFromString(value));
            }
        }
    }

    /**
     * Function name: getFillNumberFrom.
     * Receives a 'fill-k' string and checks what is the value of 'k' - an integer
     *
     * @param s - 'fill-k' string
     * @return the number written in the given string
     */
    private int getFillNumberFrom(String s) {
        // if the 'fill' isn't followed by a number, we treat it as being fill-1 as default
        int fillNumber = 1;
        Pattern fillNumberPattern = Pattern.compile("\\d+");
        Matcher fillNumberMatcher = fillNumberPattern.matcher(s);

        if (fillNumberMatcher.find()) {
            // get only the number of the fill without the '-' sign
            fillNumber = Integer.parseInt(s.substring(fillNumberMatcher.start(), fillNumberMatcher.end()));
        }
        return fillNumber;
    }

    /**
     * Function name: setStroke.
     * Get the stroke color from the information maps and set as a member
     */
    private void setStroke() {
        ColorsParser parser = new ColorsParser();
        this.stroke = parser.colorFromString(this.getValueFromMapsOf("stroke"));
    }

    /**
     * Function name: setWidth.
     * Get the width value from the information maps and set as a member
     *
     * @throws NullPointerException - if both of the maps don't contain appropriate information
     */
    private void setWidth() throws NullPointerException {
        this.width = Double.parseDouble(this.getValueFromMapsOf("width"));
    }

    /**
     * Function name: setHeight.
     * Get the height value from the information maps and set as a member
     *
     * @throws NullPointerException - if both of the maps don't contain appropriate information
     */
    private void setHeight() throws NullPointerException {
        this.height = Double.parseDouble(this.getValueFromMapsOf("height"));
    }

    /**
     * Function name: setHitPoints.
     * Get the hit points value from the information maps and set as a member
     *
     * @throws NullPointerException - if both of the maps don't contain appropriate information
     */
    private void setHitPoints() throws NullPointerException {
        this.hitPoints = Double.parseDouble(this.getValueFromMapsOf("hit_points"));
    }

    /**
     * Function name: getValueFromMapsOf.
     * Returns the value of the given attribute (key) from the information maps.
     *
     * @param attribute - the type of information to receive the value of
     * @return value of the given key
     * @throws NullPointerException - if both of the maps don't contain the appropriate information
     */
    private String getValueFromMapsOf(String attribute) throws NullPointerException {
        // check both the maps and return the value of the attribute
        if (this.blockTypeDetails.containsKey(attribute)) {
            return this.blockTypeDetails.get(attribute);
        }
        if (this.defaultDetails == null) {
            // the stroke can be null which means there is no outline frame for the block
            if (attribute.equals("stroke")) {
                return null;
            } else {
                // if the attribute isn't defined in any of the maps throw an exception to notify an error
                throw new NullPointerException();
            }
        }
        return this.defaultDetails.get(attribute);
    }
}

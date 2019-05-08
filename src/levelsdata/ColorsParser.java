package levelsdata;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Creates a color for the game.
 *
 * @author Ariel Fenster
 */
public class ColorsParser {

    /**
     * Function name: colorFromString.
     * Returns a color by checking if it is an RGB color or the name of the color
     *
     * @param s - string representation of the color
     * @return string-based color
     */
    public java.awt.Color colorFromString(String s) {
        if (s == null) {
            return null;
        }
        // get the specific characters of the color type
        String colorAsString = s.substring(s.indexOf('(') + 1, s.length() - 1);
        if (colorAsString.startsWith("RGB")) {
            return this.colorByRGBValues(colorAsString);
        } else {
            return this.colorByName(colorAsString);
        }
    }

    /**
     * Function name: colorByRGBValues.
     * Returns a color based on the r,g,b values in the given string
     *
     * @param colorAsString - string representation of the color
     * @return rgb-based color
     */
    private java.awt.Color colorByRGBValues(String colorAsString) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(colorAsString);

        // get the r,g,b values from the string
        matcher.find();
        int r = Integer.parseInt(colorAsString.substring(matcher.start(), matcher.end()));
        matcher.find();
        int g = Integer.parseInt(colorAsString.substring(matcher.start(), matcher.end()));
        matcher.find();
        int b = Integer.parseInt(colorAsString.substring(matcher.start(), matcher.end()));
        return new Color(r, g, b);
    }

    /**
     * Function name: colorByName.
     * Returns a color based on its name
     *
     * @param name - the name of the color
     * @return word-based color
     */
    private java.awt.Color colorByName(String name) {
        switch (name) {
            case ("black"):
                return Color.BLACK;
            case ("blue"):
                return Color.BLUE;
            case ("cyan"):
                return Color.CYAN;
            case ("gray"):
                return Color.GRAY;
            case ("lightGray"):
                return Color.LIGHT_GRAY;
            case ("green"):
                return Color.GREEN;
            case ("orange"):
                return Color.ORANGE;
            case ("pink"):
                return Color.PINK;
            case ("red"):
                return Color.RED;
            case ("white"):
                return Color.WHITE;
            case ("yellow"):
                return Color.YELLOW;
            default:
                return null;
        }
    }

}

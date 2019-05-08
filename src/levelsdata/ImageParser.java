package levelsdata;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

/**
 * Creates an image for the game.
 *
 * @author Ariel Fenster
 */
public class ImageParser {

    /**
     * Function name: imageFromString.
     * Returns the image written in the given file path
     *
     * @param filePath - the path of the image file
     * @return the image in the file
     */
    public Image imageFromString(String filePath) {
        // adjust the file path to start from inside the resources directory
        filePath = filePath.substring(filePath.indexOf('(') + 1, filePath.length() - 1);
        InputStream is = null;
        Image img = null;
        try {
            // read the image from the file
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);
            img = ImageIO.read(is);
        } catch (IOException i) {
            i.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return img;
    }
}

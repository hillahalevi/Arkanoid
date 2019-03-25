package blocks;

import inter.Fill;
import levels.ColoredBackground;
import levels.ImageBackground;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Filled.
 */
public class Filled {
    private Map<String, Color> map = new TreeMap<String, Color>();

    /**
     * maps of colors.
     */
    private void setMap() {
        map.put("yellow", Color.yellow);
        map.put("red", Color.red);
        map.put("black", Color.black);
        map.put("blue", Color.blue);
        map.put("cyan", Color.cyan);
        map.put("gray", Color.gray);
        map.put("lightGray", Color.lightGray);
        map.put("green", Color.green);
        map.put("orange", Color.orange);
        map.put("pink", Color.pink);
        map.put("white", Color.white);
    }

    /**
     * give us the relevant part of a string.
     *
     * @param s     the string to process
     * @param start the start of the relevant part.
     * @param end   the end of the relevant part.
     * @return the relevant string.
     */
    public String relevantPart(String s, String start, String end) {
        return s.substring(start.length(), s.length() - end.length());
    }

    /**
     * makes sprite in demand.
     *
     * @param s s the data about the sprite
     * @return sprite || null in case of an error
     */
    public Fill fill(String s) {
        String s1;
        Fill fine = null;
        if (s.startsWith("color(RGB(") && s.endsWith("))")) {
            //fill:color(RGB(x,y,z)) format
            Color c = colors(s);
            fine = new ColoredBackground(c);

        } else if (s.startsWith("color(") && s.endsWith(")")) {
            Color c = colors(s);
            fine = new ColoredBackground(c);
        } else if (s.startsWith("image(") && s.endsWith(")")) {
            //fill:image(filename.png) format
            s1 = relevantPart(s, "image(", ")");
            InputStream is = null;

            try {
                is = ClassLoader.getSystemClassLoader().getResourceAsStream(s1);
                BufferedImage image = ImageIO.read(is);
                fine = new ImageBackground(image);
            } catch (IOException var18) {
                throw new RuntimeException("Failed loading image: " + s1, var18);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException var17) {
                        throw new RuntimeException("Failed loading image: " + s1, var17);
                    }
                }

            }
        }
        return fine;
    }

    /**
     * Colors color.
     *
     * @param s the s
     * @return the color
     */
    public Color colors(String s) {
        setMap();
        String s1 = s;
        Color c = null;
        if (s.startsWith("color(RGB(") && s.endsWith("))")) {
            //fill:color(RGB(x,y,z)) format
            s1 = relevantPart(s, "color(RGB(", "))");
            String[] details = s1.split(",");
            int x = Integer.parseInt(details[0]);
            int y = Integer.parseInt(details[1]);
            int z = Integer.parseInt(details[2]);
            c = new Color(x, y, z);


        } else if (s.startsWith("color(") && s.endsWith(")")) {
            //fill:color(red) format
            s1 = relevantPart(s, "color(", ")");
            if (map.containsKey(s1)) {
                c = map.get(s1);
            }

        }
        if (c == null) {
            //the color is not in the system.
            throw new RuntimeException("illegal color name: " + s1);
        }
        return c;
    }
}

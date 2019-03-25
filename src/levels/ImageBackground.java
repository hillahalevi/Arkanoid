package levels;

import biuoop.DrawSurface;
import geo.Rectangle;
import inter.Fill;
import inter.Sprite;

import java.awt.Image;

/**
 * The type Image background.
 */
public class ImageBackground implements Fill, Sprite {
    private Image image;

    /**
     * Instantiates a new Image background.
     *
     * @param image the image
     */
    public ImageBackground(Image image) {
        this.image = image;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d         the surface.
     * @param rectangle the rectangle
     */
    @Override
    public void drawAt(DrawSurface d, Rectangle rectangle) {
        d.drawImage((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(), this.image);
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.drawImage(0, 0, this.image);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    @Override
    public void timePassed(double dt) {

    }
}

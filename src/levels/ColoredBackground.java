package levels;


import biuoop.DrawSurface;
import geo.Rectangle;
import inter.Fill;
import inter.Sprite;

import java.awt.Color;

/**
 * The type Colored background.
 */
public class ColoredBackground implements Fill, Sprite {

    private Color color;

    /**
     * Instantiates a new Colored background.
     *
     * @param color the color
     */
    public ColoredBackground(Color color) {
        this.color = color;
    }


    /**
     * draw the sprite to the screen.
     *
     * @param d the surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
    }


    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    public void timePassed(double dt) {
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d         the surface.
     * @param rectangle the rectangle
     */
    @Override
    public void drawAt(DrawSurface d, Rectangle rectangle) {
        d.setColor(this.color);
        int x = (int) rectangle.getUpperLeft().getX();
        int y = (int) rectangle.getUpperLeft().getY();
        d.fillRectangle(x, y, (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }
}
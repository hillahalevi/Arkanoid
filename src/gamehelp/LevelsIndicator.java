package gamehelp;

import animated.GameLevel;
import biuoop.DrawSurface;
import geo.Rectangle;
import inter.Sprite;

import java.awt.Color;

/**
 * The type Levels indicator.
 */
public class LevelsIndicator implements Sprite {
    private Rectangle block;
    private GameLevel g;

    /**
     * Instantiates a new Levels indicator.
     *
     * @param block the block
     * @param g     the g
     */
    public LevelsIndicator(Rectangle block, GameLevel g) {
        this.block = block;
        this.g = g;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        int height = (int) block.getHeight();
        int width = (int) block.getWidth();
        int x = (int) (block.getUpperLeft().getX() + 0.2 * width);
        int font = 20;
        if (g.getInfo().levelName().length() > 10) {
            font = 14;
        }
        int y = (int) (block.getUpperLeft().getY() + 0.9 * height);
        block.drawOn(d, Color.darkGray);

        d.setColor(Color.white);
        d.drawText(x, y, "Level Name: " + g.getInfo().levelName(), font);

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

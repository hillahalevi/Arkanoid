package gamehelp;

import biuoop.DrawSurface;
import animated.GameLevel;
import geo.Rectangle;
import inter.Sprite;

import java.awt.Color;

/**
 * The type Lives indicator.
 */
public class LivesIndicator implements Sprite {
    private Rectangle block;
    private GameLevel g;

    /**
     * Instantiates a new Lives indicator.
     *
     * @param block the block
     * @param g     the g
     */
    public LivesIndicator(Rectangle block, GameLevel g) {
        this.block = block;
        this.g = g;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the surface.
     */
    public void drawOn(DrawSurface d) {
        int height = (int) block.getHeight();
        int width = (int) block.getWidth();
        int x = (int) (block.getUpperLeft().getX() + 0.1 * width);
        int y = (int) (block.getUpperLeft().getY() + 0.9 * height);
        block.drawOn(d, Color.darkGray);
        int font = 20;
        d.setColor(Color.white);
        d.drawText(x, y, "Lives: " + g.getNumberoflives().getValue(), font);
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

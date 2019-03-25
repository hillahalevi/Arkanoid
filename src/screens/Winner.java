package screens;

import biuoop.DrawSurface;
import inter.Sprite;

import java.awt.Color;

/**
 * The type Winner.
 */
public class Winner implements Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d the surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        new Screen().paint(d);
        d.setColor(Color.white);
        d.drawText((int) (d.getWidth() * 0.15), d.getHeight() / 4 + 40, "Congratulations!", 80);
        d.drawText(20, d.getHeight() - 90, "You're awesome !!!", 80);
        d.fillRectangle(0, 270, d.getWidth(), 30);
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

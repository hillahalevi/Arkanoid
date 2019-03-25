package screens;

import biuoop.DrawSurface;

import inter.Sprite;

import java.awt.Color;

/**
 * The type Level 4.
 */
public class Screen implements Sprite {




    /**
     * draw the sprite to the screen.
     *
     * @param d the surface.
     */
    public void drawOn(DrawSurface d) {
        paint(d);
        cloud1(d);
        cloud2(d);


    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    @Override
    public void timePassed(double dt) {

    }

    /**
     * Paint.
     *
     * @param d the d
     */
    public void paint(DrawSurface d) {
        int y = 0;
        int width = d.getWidth();
        int height = 30;
        Color color = new Color(32, 178, 170).brighter();
        while (y < d.getHeight() / 2) {
            d.setColor(color);
            d.fillRectangle(0, y, width, height);
            if (y % 20 == 0) {
                color = color.brighter();
            }
            y = y + height;
        }
        y = d.getHeight();
        color = new Color(32, 178, 170);
        while (y > d.getHeight() / 2) {
            d.setColor(color);
            d.fillRectangle(0, y, width, height);
            if (y % 20 == 0) {
                color = color.brighter();
            }
            y = y - height;

        }
        d.setColor(new Color(224, 255, 255));
        d.fillRectangle(0, 270, d.getWidth(), 30);
        d.fillRectangle(0, 240, d.getWidth(), 30);
        d.fillRectangle(0, d.getHeight() / 2, d.getWidth(), 30);
    }

    /**
     * clouds num 1.
     *
     * @param d draw surface.
     */
    private void cloud1(DrawSurface d) {
        d.setColor(Color.white);
        int x = d.getWidth() - 150;
        int y = d.getWidth() - 110;
        for (int k = 0; k < 2; k++) {
            for (int j = 0; j < 11; j++) {
                d.drawLine(x - k - 13 * j, d.getHeight(), y - k - 13 * j, d.getHeight() - 135);

            }
        }
        d.setColor(new Color(224, 224, 224));
        d.fillCircle(d.getWidth() - 190, d.getHeight() - 100, 40);
        d.setColor(new Color(192, 192, 192));
        d.fillCircle(d.getWidth() - 165, d.getHeight() - 140, 45);
        d.setColor(new Color(160, 160, 160));
        d.fillCircle(d.getWidth() - 120, d.getHeight() - 120, 45);
        d.fillCircle(d.getWidth() - 145, d.getHeight() - 90, 30);
        d.setColor(new Color(224, 224, 224));
        d.fillCircle(d.getWidth() - 240, d.getHeight() - 150, 35);
    }

    /**
     * clouds num 2.
     *
     * @param d draw surface.
     */
    private void cloud2(DrawSurface d) {
        d.setColor(Color.white);
        for (int k = 0; k < 2; k++) {
            for (int j = 0; j < 11; j++) {
                d.drawLine(20 + 70 + k + 13 * j, d.getHeight(), 70 + k + 13 * j, d.getHeight() - 200);

            }
        }

        d.setColor(new Color(224, 224, 224));
        d.fillCircle(150, d.getHeight() - 195, 40);
        d.setColor(new Color(192, 192, 192));
        d.fillCircle(145, d.getHeight() - 245, 45);
        d.setColor(new Color(160, 160, 160));
        d.fillCircle(70, d.getHeight() - 215, 45);
        d.fillCircle(100, d.getHeight() - 175, 30);
        d.setColor(new Color(224, 224, 224));
        d.fillCircle(210, d.getHeight() - 215, 35);
        d.fillCircle(210, d.getHeight() - 255, 25);

    }

}

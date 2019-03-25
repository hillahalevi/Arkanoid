package animated;

import biuoop.DrawSurface;
import inter.Animation;

import java.awt.Color;

/**
 * The type Pause screen.
 */
public class PauseScreen implements Animation {


    /**
     * Instantiates a new Pause screen.
     *
     */
    public PauseScreen() {
    }

    /**
     * Do one frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.pink);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.lightGray);
        d.drawText(150, d.getHeight() / 2, "paused -- press space to continue", 32);
        d.setColor(Color.white);
        d.drawText(150, d.getHeight() / 2 - 5, "paused -- press space to continue", 32);
    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() {
        return false;
    }
}
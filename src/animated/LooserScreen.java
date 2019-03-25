package animated;

import biuoop.DrawSurface;

import gameset.SpriteCollection;
import inter.Animation;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

/**
 * The type Looser screen.
 */
public class LooserScreen implements Animation {
    private int numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private Long timeTruck;
    private boolean running;
    private int haight;

    /**
     * Instantiates a new Looser screen.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public LooserScreen(int numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.haight = countFrom;
        running = true;
        timeTruck = null;
    }



    /**
     * Do one frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (timeTruck == null) {
            //first time -initiate.
            timeTruck = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        }
        Long current = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        if (current - timeTruck >= 1) {
            haight--;
            timeTruck = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        }
        if (countFrom - haight >= numOfSeconds + 2) {
            running = false;
            haight++;
        }
        //show the world what we got !
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.lightGray);
        d.drawText((int) (d.getWidth() * 0.21), d.getHeight() / 2 + 50 + 10 * haight, "Game Over!", 100);
        d.setColor(Color.white);
        d.drawText((int) (d.getWidth() * 0.2), d.getHeight() / 2 + 50 + 10 * haight - 5, "Game Over!", 100);


    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() {
        return !this.running;
    }
}

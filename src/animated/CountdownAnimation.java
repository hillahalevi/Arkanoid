package animated;

import biuoop.DrawSurface;
import gameset.SpriteCollection;
import inter.Animation;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

/**
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) secods, before
 * it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {
    private int numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private Long timeTruck = null;
    private boolean running = true;
    private int time4Display;

    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds1 the num of seconds
     * @param countFrom1    the count from
     * @param gameScreen1   the game screen
     */
    public CountdownAnimation(int numOfSeconds1, int countFrom1, SpriteCollection gameScreen1) {
        this.numOfSeconds = numOfSeconds1;
        this.countFrom = countFrom1;
        this.gameScreen = gameScreen1;
        this.time4Display = countFrom1;

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
            time4Display--;
            timeTruck = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        }
        if (countFrom - time4Display >= numOfSeconds + 1) {
            running = false;
            time4Display++;
        }
        //show the world what we got !
        this.gameScreen.drawAllOn(d);
        if (!running) {
            d.setColor(Color.lightGray);
            d.drawText((int) (d.getWidth() * 0.3), d.getHeight() / 2 + 50, "GO!", 200);
            d.setColor(Color.white);
            d.drawText((int) (d.getWidth() * 0.29), d.getHeight() / 2 + 45, "GO!", 200);
        } else {
            d.setColor(Color.lightGray);
            d.drawText((int) (d.getWidth() * 0.43), d.getHeight() / 2, "" + time4Display, 200);
            d.setColor(Color.white);
            d.drawText((int) (d.getWidth() * 0.42), d.getHeight() / 2 - 5, "" + time4Display, 200);
        }


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
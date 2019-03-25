package animated;

import biuoop.DrawSurface;
import gamehelp.Counter;
import gameobj.Ball;
import gameset.Velocity;
import geo.Point;
import inter.Animation;
import screens.Looser;
import screens.Winner;

import java.awt.Color;

/**
 * The type End screen.
 */
public class EndScreen implements Animation {
    private String msg;
    private Counter s;
    // private SpriteCollection balls;


    /**
     * Instantiates a new End screen.
     *
     * @param masssage the masssage
     * @param score    the score
     */
    public EndScreen(String masssage, Counter score) {
        this.msg = masssage;
        this.s = score;
        // balls = new SpriteCollection();
        //  ballsCreator();

    }

    /**
     * ballsCreator.
     */
    public void ballsCreator() {
        double x = 400;
        double y = 500;
        for (int i = 0; i < 50; i++) {
            //creates the ball
            Ball ball = new Ball(new Point(x, y), 10, Color.pink, Velocity.fromAngleAndSpeed(10 + i, 10));
            //adding it to the game
            //   balls.addSprite(ball);

        }

    }

    /**
     * print.
     *
     * @param d the surface to draw on
     */
    private void print(DrawSurface d) {

        d.setColor(Color.lightGray);
        d.drawText((int) (d.getWidth() * 0.21), d.getHeight() / 2 + 50, msg, 100);
        d.setColor(Color.white);
        d.drawText((int) (d.getWidth() * 0.20), d.getHeight() / 2 + 45, msg, 100);
        d.setColor(Color.gray);
        d.drawText((int) (d.getWidth() * 0.34), d.getHeight() / 2 + 100, " Your score is " + s.getValue(), 32);
        d.drawText((int) (d.getWidth() * 0.35), d.getHeight() / 2 + 140, " press space to continue", 20);

    }

    /**
     * Do one frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (msg.equals("Game Over")) {
            new Looser().drawOn(d);
        } else {
            new Winner().drawOn(d);
        }
        // this.balls.drawAllOn(d);
        //this.balls.notifyAllTimePassed();
        print(d);

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

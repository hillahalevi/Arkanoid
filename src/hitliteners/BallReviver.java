package hitliteners;

import gameobj.Ball;
import gameobj.Block;
import animated.GameLevel;
import inter.HitListener;

/**
 * The type Ball reviver.
 */
public class BallReviver implements HitListener {
    private GameLevel game;

    /**
     * Instantiates a new Ball reviver.
     *
     * @param g the g
     */
    public BallReviver(GameLevel g) {
        game = g;
    }

    /**
     * Hit event.
     * called whenever the beingHit object is hit.
     *
     * @param beingHit object that  being hit
     * @param hitter   the hitter ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        game.ballsCreator();
    }
}

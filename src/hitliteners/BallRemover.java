package hitliteners;

import gamehelp.Counter;
import gameobj.Ball;
import gameobj.Block;
import animated.GameLevel;
import inter.HitListener;

/**
 * The type Ball remover.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * Instantiates a new Block remover.
     *
     * @param g             the g
     * @param removeballs the removed blocks
     */
    public BallRemover(GameLevel g, Counter removeballs) {
        game = g;
        remainingBalls = removeballs;
    }

    /**
     * Hit event.
     * called whenever the beingHit object is hit.
     *
     * @param beingHit object that  being hit
     * @param hitter   the hitter ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        remainingBalls.decrease(1);
        hitter.removeFromGameLevel(game);
        hitter.removeHitListener(this);
    }
}

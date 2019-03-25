package hitliteners;

import gamehelp.Counter;
import gameobj.Ball;
import gameobj.Block;
import inter.HitListener;

/**
 * The type Score tracking listener.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Instantiates a new Score tracking listener.
     *
     * @param currentScore the current score
     */
    public ScoreTrackingListener(Counter currentScore) {
        this.currentScore = currentScore;
    }


    /**
     * Hit event.
     * called whenever the beingHit object is hit.
     *
     * @param beingHit object that  being hit
     * @param hitter   the hitter ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(5);
        if (beingHit.getHitPoints() == 0) {
            currentScore.increase(10);
        }
    }
}

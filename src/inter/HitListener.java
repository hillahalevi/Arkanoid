package inter;

import gameobj.Block;
import gameobj.Ball;

/**
 * The interface Hit listener.
 */
public interface HitListener {
    /**
     * Hit event.
     *called whenever the beingHit object is hit.
     * @param beingHit  object that  being hit
     * @param hitter   the hitter ball
     */

    void hitEvent(Block beingHit, Ball hitter);
}

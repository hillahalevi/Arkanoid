package hitliteners;

import gamehelp.Counter;
import gameobj.Ball;
import gameobj.Block;
import animated.GameLevel;
import inter.HitListener;

/**
 * BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * *of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * Instantiates a new Block remover.
     *
     * @param g             the game
     * @param removedBlocks the removed blocks
     */
    public BlockRemover(GameLevel g, Counter removedBlocks) {
        game = g;
        remainingBlocks = removedBlocks;
    }



    /**
     * Hit event.
     * called whenever the beingHit object is hit.
     *
     * @param beingHit object that  being hit
     * @param hitter   the hitter ball.
     *                 Blocks that are hit and reach 0 hit-points should be removed
     *                 from the game. Remember to remove this listener from the block
     *                 that is being removed from the game.
     */

    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            remainingBlocks.decrease(1);
            beingHit.removeFromGameLevel(game);
            beingHit.removeHitListener(this);
        }
    }
}
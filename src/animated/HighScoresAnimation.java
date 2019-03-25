package animated;

import biuoop.DrawSurface;
import gameobj.HighScoresTable;
import gameset.ScoreInfo;
import inter.Animation;

import java.awt.Color;

/**
 * The type High scores animation.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scoresTable;


    /**
     * Instantiates a new High scores animation.
     *
     * @param scoresTable the scores table
     */
    public HighScoresAnimation(HighScoresTable scoresTable) {
        this.scoresTable = scoresTable;
    }

    /**
     * Do one frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.pink);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        int i = 0;
        d.setColor(Color.white);
        d.drawText(200, 80, "Hall of Fame!", 60);
        d.drawLine(200, 130, 550, 130);
        d.drawText(200, 120, "Player", 25);
        d.drawText(480, 120, "Score", 25);
        for (ScoreInfo s : scoresTable.getHighScores()) {
            d.drawText(200, 160 + i, s.getName(), 25);
            d.drawText(500, 160 + i, "" + s.getScore(), 25);
            d.drawLine(200, 165 + i, 550, 165 + i);
            i = i + 50;
        }
        d.drawText(200, 495, "PRESS SPACE TO GET OUT ;)", 32);


    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean shouldStop() {
        return false;
    }

}
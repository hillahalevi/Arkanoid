package gameset;

import java.io.Serializable;

/**
 * The type Score info.
 */
public class ScoreInfo implements Comparable<ScoreInfo>, Serializable {
    private String n;
    private int s;

    /**
     * Instantiates a new Score info.
     *
     * @param name  the name
     * @param score the score
     */
    public ScoreInfo(String name, int score) {
        n = name;
        s = score;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return n;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return s;
    }

    /**
     * Compare int.
     *
     * @param o the info
     * @return the int
     */
    @Override
    public int compareTo(ScoreInfo o) {
        return Integer.compare(o.getScore(), this.getScore());

    }

}

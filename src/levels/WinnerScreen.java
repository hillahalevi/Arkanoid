package levels;

import gameobj.Block;
import gameset.Velocity;
import geo.Point;
import inter.LevelInformation;
import inter.Sprite;
import screens.Winner;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Winner screen.
 */
public class WinnerScreen implements LevelInformation {
    /**
     * Number of balls int.
     *
     * @return the int
     */
    @Override
    public int numberOfBalls() {
        return 40;
    }

    /**
     * Initial ball velocities list.
     * The initial velocity of each ball
     * Note that initialBallVelocities().size() == numberOfBalls()
     *
     * @return the list
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> speed = new ArrayList<Velocity>();
        Velocity velocity;
        for (int i = 0; i < numberOfBalls() / 2; i++) {
            velocity = Velocity.fromAngleAndSpeed(15 * i, 10 * 60);
            speed.add(velocity);
        }
        for (int i = 0; i < numberOfBalls() / 2; i++) {
            velocity = Velocity.fromAngleAndSpeed(-15 * i, 10 * 60);
            speed.add(velocity);
        }
        return speed;
    }

    /**
     * Paddle speed int.
     *
     * @return the int
     */
    @Override
    public int paddleSpeed() {
        return 10;
    }

    /**
     * Paddle width int.
     *
     * @return the int
     */
    @Override
    public int paddleWidth() {
        return 760;
    }

    /**
     * Level name string.
     *
     * @return the level name will be displayed at the top of the screen.
     */
    @Override
    public String levelName() {
        return "Winner time";
    }

    /**
     * Gets background.
     *
     * @return a sprite with the background of the level
     */
    @Override
    public Sprite getBackground() {
        return new Winner();
    }

    /**
     * Blocks list.
     * The Blocks that make up this level, each block contains its size, color and location.
     *
     * @return the list
     */
    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<Block>();
        Color c = Color.white;
        int colorscount = 0;
        for (int i = 23; colorscount < 15; i = i + 50, colorscount++) {
            if (colorscount == 0 || colorscount == 1) {
                c = Color.red;
            }
            if (colorscount == 2 || colorscount == 3) {
                c = Color.orange;
            }
            if (colorscount == 4 || colorscount == 5) {
                c = Color.YELLOW;
            }
            if (colorscount == 6 || colorscount == 7 || colorscount == 8) {
                c = Color.green;
            }
            if (colorscount == 9 || colorscount == 10) {
                c = Color.BLUE;
            }
            if (colorscount == 11 || colorscount == 12) {
                c = Color.pink;
            }
            if (colorscount == 13 || colorscount == 14) {
                c = Color.RED;
            }
            geo.Rectangle rectangle = new geo.Rectangle(new Point(i, 30), 50, 30);
            rectangle.setColor(c);
            blocks.add(new Block(rectangle));

        }
        c = Color.black;
        for (Block b : blocks) {
            b.setC(c);
        }
        return blocks;
    }

    /**
     * Number of blocks to remove .
     * Number of levels that should be removed
     * before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     *
     * @return the int
     */
    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
    }
}

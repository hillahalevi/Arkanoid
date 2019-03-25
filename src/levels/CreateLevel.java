package levels;

import gameobj.Block;
import gameset.Velocity;
import inter.LevelInformation;
import inter.Sprite;

import java.util.List;

/**
 * The type Create level.
 */
public class CreateLevel implements LevelInformation {
    private List<Velocity> velocities;
    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private List<Block> blocks;
    private int numberOfBlocksToClear;
    private Sprite backgroundSprite;

    /**
     * Instantiates a new Create level.
     *
     * @param velocities            the velocities
     * @param paddleSpeed           the paddle speed
     * @param paddleWidth           the paddle width
     * @param levelName             the level name
     * @param blocks                the blocks
     * @param numberOfBlocksToClear the number of blocks to clear
     * @param backgroundSprite      the background sprite
     */
    public CreateLevel(List<Velocity> velocities, int paddleSpeed, int paddleWidth, String levelName,
                       List<Block> blocks, int numberOfBlocksToClear, Sprite backgroundSprite) {
        this.velocities = velocities;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.levelName = levelName;
        this.blocks = blocks;
        this.numberOfBlocksToClear = numberOfBlocksToClear;
        this.backgroundSprite = backgroundSprite;
    }

    /**
     * Number of balls int.
     *
     * @return the int
     */
    @Override
    public int numberOfBalls() {
        return velocities.size();
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
        return velocities;
    }

    /**
     * Paddle speed int.
     *
     * @return the int
     */
    @Override
    public int paddleSpeed() {
        return paddleSpeed;
    }

    /**
     * Paddle width int.
     *
     * @return the int
     */
    @Override
    public int paddleWidth() {
        return paddleWidth;
    }

    /**
     * Level name string.
     *
     * @return the level name will be displayed at the top of the screen.
     */
    @Override
    public String levelName() {
        return levelName;
    }

    /**
     * Gets background.
     *
     * @return a sprite with the background of the level
     */
    @Override
    public Sprite getBackground() {
        return backgroundSprite;
    }

    /**
     * Blocks list.
     * The Blocks that make up this level, each block contains its size, color and location.
     *
     * @return the list
     */
    @Override
    public List<Block> blocks() {
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
        return numberOfBlocksToClear;
    }
}

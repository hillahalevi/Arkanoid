package blocks;

import gameobj.Block;
import geo.Point;
import geo.Rectangle;
import inter.BlockCreator;
import inter.Fill;


import java.awt.Color;
import java.util.HashMap;

/**
 * The type Base block creator.
 */
public class BaseBlockCreator implements BlockCreator {
    private int height;
    private int hitp;
    private int width;
    private HashMap<Integer, Fill> f;
    private Color c;

    /**
     * Instantiates a new Base block creator.
     *  @param height the height
     * @param width  the width
     * @param hitp   the hitp
     * @param f      the f
     * @param c      the c
     */
    public BaseBlockCreator(int height, int width, int hitp, HashMap<Integer, Fill> f, Color c) {
        this.height = height;
        this.hitp = hitp;
        this.width = width;
        this.f = f;
        this.c = c;
    }

    /**
     * Create a block at the specified location.
     *
     * @param xpos the xpos
     * @param ypos the ypos
     * @return the block
     */
    @Override
    public Block create(int xpos, int ypos) {
        return new Block(new Rectangle(new Point(xpos, ypos), width, height), hitp, f, c);
    }
}

package blocks;

import gameobj.Block;
import inter.BlockCreator;

import java.util.Map;

/**
 * The type Blocks from symbols factory.
 */
public class BlocksFromSymbolsFactory {
    private Map<Character, BlockCreator> blockCreators;
    private Map<Character, Integer> spacerWidths;

    /**
     * Instantiates a new Blocks from symbols factory.
     *
     * @param spacerWidths  the spacer widths
     * @param blockCreators the block creators
     */
    public BlocksFromSymbolsFactory(Map<Character, Integer> spacerWidths, Map<Character, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * Is space symbol boolean.
     *
     * @param s the s
     * @return true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(Character s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * Is block symbol boolean.
     *
     * @param s the s
     * @return true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(Character s) {
        return this.blockCreators.containsKey(s);
    }


    /**
     * Gets space width.
     * Returns the width in pixels associated with the given spacer-symbol.
     *
     * @param s the s
     * @return the space width
     */
    public int getSpaceWidth(Character s) {
        return this.spacerWidths.get(s);
    }

    /**
     * Gets block.
     *
     * @param s the s
     * @param x the x
     * @param y the y
     * @return a block according to the definitions associated and positioned (xpos, ypos).
     */
    public Block getBlock(Character s, int x, int y) {
        if (this.blockCreators.containsKey(s)) {
            return this.blockCreators.get(s).create(x, y);
        }
        return null;
    }
}
package factory;

import blocks.BlocksDefinitionReader;
import blocks.BlocksFromSymbolsFactory;
import blocks.Filled;
import gameobj.Block;
import gameset.Velocity;
import inter.LevelInformation;
import inter.Sprite;
import levels.CreateLevel;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Translator-takes a single level order and creates levelinformation object.
 */
public class LevelTranslator extends Filled {
    private List<String> levelrecepie;
    private List<String> blockrecepies;
    private List<String> keys;
    private List<String> values;
    private List<Velocity> velocities;
    private int paddleSpeed;
    private int paddleWidth;
    private int blocksX;
    private int blocksY;
    private int rowHeight;
    private String levelName;
    private List<Block> blocks;
    private int numberOfBlocksToClear;
    private String blocksFileName;
    private Sprite backgroundSprite;
    private BlocksFromSymbolsFactory factory;

    /**
     * Instantiates a new Translator.
     *
     * @param levelrecepie the levelrecepie
     */
    public LevelTranslator(List<String> levelrecepie) {
        this.levelrecepie = levelrecepie;
        this.blockrecepies = new LinkedList<String>();
        this.keys = new LinkedList<String>();
        this.values = new LinkedList<String>();
        this.velocities = new LinkedList<Velocity>();
        this.blocks = new LinkedList<Block>();

    }

    /**
     * arrange the info of the level in a way for us to work.
     * creates a list of keys && values
     */
    private void arrange() {
        String[] parts;
        boolean readblocks = false;
        for (String str : levelrecepie) {
            if (str.equals("START_LEVEL")) {
                continue;
            }
            if (str.equals("END_LEVEL")) {
                break;
            }
            if (str.equals("START_BLOCKS")) {
                readblocks = true;
                continue;
            }
            if (str.equals("END_BLOCKS")) {
                readblocks = false;
                continue;
            }
            if (readblocks) {
                blockrecepies.add(str);
                continue;
            }
            parts = str.split(":");
            keys.add(parts[0]);
            values.add(parts[1]);

        }

    }

    /**
     * set ball velocities.
     *
     * @param v the data about the ball Velocities
     */
    private void setVelocities(String v) {
        String[] allv = v.split(" ");
        String[] velocity;
        for (String vel : allv) {
            velocity = vel.split(",");
            double angle = Double.parseDouble(velocity[0]);
            double speed = Double.parseDouble(velocity[1]);
            velocities.add(Velocity.fromAngleAndSpeed(angle, speed));
        }

    }


    /**
     * set the level properties.
     *
     * @return false if one or more data fields are missing.
     */
    private boolean setLevel() {
        arrange();
        if ((keys.size() != 10) || (values.size() != 10)) {
            return false;
        }
        for (String key : keys) {
            String value = values.get(keys.indexOf(key));
            switch (key) {
                case "level_name":
                    levelName = value;
                    break;

                case "ball_velocities":
                    setVelocities(value);
                    break;

                case "background":
                    backgroundSprite = (Sprite) fill(value);
                    break;

                case "paddle_speed":
                    paddleSpeed = Integer.parseInt(value);
                    break;

                case "paddle_width":
                    paddleWidth = Integer.parseInt(value);
                    break;

                case "block_definitions":
                    blocksFileName = value;
                    break;

                case "blocks_start_x":
                    blocksX = Integer.parseInt(value);
                    break;

                case "blocks_start_y":
                    blocksY = Integer.parseInt(value);
                    break;

                case "row_height":
                    rowHeight = Integer.parseInt(value);
                    break;

                case "num_blocks":
                    numberOfBlocksToClear = Integer.parseInt(value);
                    break;

                default:
                    break;


            }
        }
        return true;
    }

    /**
     * compute the blocks factory and create blocks on demand.
     */
    private void setBlocks() {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(blocksFileName);
        Reader reader = new InputStreamReader(is);
        factory = BlocksDefinitionReader.fromReader(reader);
        String row;
        for (int i = 0; i < blockrecepies.size(); i++) {
            row = blockrecepies.get(i);
            computeBlocks(row, i);

        }


    }

    /**
     * translate a string of symbols into blocks.
     *
     * @param line     the string of symbols
     * @param rowIndex the row of the blocks.
     */
    private void computeBlocks(String line, int rowIndex) {
        int currentX = blocksX;
        for (int i = 0; i < line.length(); ++i) {
            char symbol = line.charAt(i);
            int currentY = rowIndex * rowHeight + blocksY;
            if (factory.isSpaceSymbol(symbol)) {
                currentX += factory.getSpaceWidth(symbol);
            } else {
                Block b = factory.getBlock(symbol, currentX, currentY);
                if (b == null) {
                    throw new RuntimeException("Failed creating block of type:" + symbol);
                }

                currentX = (int) ((double) currentX + b.getCollisionRectangle().getWidth());
                blocks.add(b);
            }
        }
    }

    /**
     * creates a level information objects.
     *
     * @return the level information
     */
    public LevelInformation creator() {
        if (setLevel()) {
            setBlocks();
            return new CreateLevel(velocities, paddleSpeed, paddleWidth, levelName, blocks,
                    numberOfBlocksToClear, backgroundSprite);
        } else {
            throw new RuntimeException("problem with creating levels ");

        }
    }

}

package menu;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Level sets.
 */
public class LevelSets {
    private List<SingleSet> levelSetList = new ArrayList<SingleSet>();

    /**
     * From reader level sets.
     *
     * @param reader the reader
     * @return the level sets
     * @throws IOException the io exception
     */
    public static LevelSets fromReader(Reader reader) throws IOException {
        LevelSets levelSets = new LevelSets();
        SingleSet set = null;
        LineNumberReader lineReader = null;

        try {
            lineReader = new LineNumberReader(reader);
            String line;
            line = lineReader.readLine();
            while (line != null) {
                if (!(lineReader.getLineNumber() % 2 == 0)) {
                    //Odd-numbered line - description of a new level
                    set = new SingleSet();
                    String[] lineParts = line.trim().split(":");
                    set.setKey(lineParts[0]);
                    set.setMessage(lineParts[1]);
                } else {
                    //Even-numbered line - path of the level we just took
                    Objects.requireNonNull(set).setPath(line.trim());
                    levelSets.addLevelSet(set);
                    set = null;
                }
                line = lineReader.readLine();
            }
        } finally {
            if (lineReader != null) {
                lineReader.close();
            }

        }

        return levelSets;
    }

    /**
     * Add level set.
     *
     * @param levelSet the level set
     */
    public void addLevelSet(SingleSet levelSet) {
        this.levelSetList.add(levelSet);
    }

    /**
     * Gets level set list.
     *
     * @return the level set list
     */
    public List<SingleSet> getLevelSetList() {
        return levelSetList;
    }
}

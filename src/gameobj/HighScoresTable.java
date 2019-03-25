package gameobj;

import gameset.ScoreInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The type High scores table.
 */
public class HighScoresTable implements Serializable {
    private int sizeTable;
    private List<ScoreInfo> scoreInfos;

    /**
     * Instantiates a new High scores table.
     * Create an empty high-scores table with the specified size.
     *
     * @param size The size means that the table holds up to size top scores.
     */
    public HighScoresTable(int size) {
        sizeTable = size;
        scoreInfos = new ArrayList<ScoreInfo>();
    }

    /**
     * Load from file high scores table.
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     *
     * @param filename the filename
     * @return the high scores table
     */
    public static HighScoresTable loadFromFile(File filename) {
        ObjectInputStream inputStream = null; // in order  to recognize it.
        try {
            // if file exist try to read from it
            FileInputStream fileInputStream = new FileInputStream(filename);
            inputStream = new ObjectInputStream(fileInputStream);
            return (HighScoresTable) inputStream.readObject();
        } catch (Exception e) {
            // Couldn't find or read the file - new empty table
            int sizesdyfult = 5;
            return new HighScoresTable(sizesdyfult);
        } finally {
            // close the ObjectInputStream
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e2) {
                System.out.println("problem reading " + e2);
            }
        }
    }


    /**
     * Main.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        HighScoresTable table = new HighScoresTable(3);
        table.add(new ScoreInfo("hilla", 100));
        table.add(new ScoreInfo("hil", 150));
        table.add(new ScoreInfo("hla", 700));
        table.add(new ScoreInfo("hia", 40));
        for (ScoreInfo s : table.scoreInfos) {
            System.out.println(s.getName() + s.getScore());
        }
    }


    /**
     * Add a high-score.
     *
     * @param score the score
     */
    public void add(ScoreInfo score) {
        if (this.getRank(score.getScore()) > this.size()) {
            //there is no place for this score - bye bye !
            return;
        }
        if (this.getRank(score.getScore()) == this.size()) {
            //this score should be tha last - just add it and return
            scoreInfos.add(score);
            return;
        }
        //else- add it somewhere and sort it all.
        scoreInfos.add(score);
        scoreInfos.sort(this.comparator());
        if (scoreInfos.size() > this.size()) {
            //there is to many items - drop the last(lowest)
            scoreInfos.remove(scoreInfos.size() - 1);
        }

    }

    /**
     * Size int.
     *
     * @return Return table size.
     */
    public int size() {
        return sizeTable;
    }

    /**
     * Gets high scores.
     * Return the current high scores.
     * The list is sorted such that the highest
     * scores come first.
     *
     * @return the high scores
     */
    public List<ScoreInfo> getHighScores() {
        return scoreInfos;
    }

    /**
     * return the rank of the current score: where will it
     * be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     * be added to the list.
     *
     * @param score the score
     * @return the rank
     */
    public int getRank(int score) {
        int flag = 0;
        for (ScoreInfo scoreInfo : scoreInfos) {
            if (score > scoreInfo.getScore()) {
                flag++;
            }
        }
        if (flag == scoreInfos.size()) {
            return 1;
        }
        if (flag == 0 && scoreInfos.size() < size()) {
            return size();
        }
        if (flag == 0 && scoreInfos.size() >= size()) {
            return size() + 1;
        }
        return -1;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.scoreInfos.clear();
    }

    /**
     * Load.
     * Load table data from file.
     * Current table data is cleared.
     *
     * @param filename the filename
     */
    public void load(File filename) {
        this.clear();
        this.scoreInfos = HighScoresTable.loadFromFile(filename).scoreInfos;
        this.sizeTable = HighScoresTable.loadFromFile(filename).sizeTable;
    }

    /**
     * Save.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void save(File filename) throws IOException {
        // Save table data to the specified file.
        FileOutputStream f = new FileOutputStream(filename);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(f);

        try {
            objectOutputStream.writeObject(this);
        } finally {
            objectOutputStream.close();

        }
    }

    /**
     * Comparator.
     *
     * @return scoreinfo comparator
     */
    private Comparator<ScoreInfo> comparator() {
        return new Comparator<ScoreInfo>() {
            @Override
            public int compare(ScoreInfo o1, ScoreInfo o2) {
                return o1.compareTo(o2);
            }
        };

    }
}
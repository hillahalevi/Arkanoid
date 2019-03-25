package gameset;

import gameobj.HighScoresTable;

import java.io.File;

/**
 * The type High scores table test.
 */
public class HighScoreFileTest {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        HighScoresTable table = new HighScoresTable(5);

        table.add(new ScoreInfo("d", 4));
        table.add(new ScoreInfo("h", 8));
        table.add(new ScoreInfo("a", 1));
        table.add(new ScoreInfo("g", 7));
        table.add(new ScoreInfo("b", 2));
        table.add(new ScoreInfo("c", 3));
        table.add(new ScoreInfo("f", 6));
        table.add(new ScoreInfo("e", 5));


        File file = new File("highscores");
        try {
            table.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        table.clear();
        //HighScoresTable t;
        try {
            table.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        table.add(new ScoreInfo("i", 9));


        File file1 = new File("highscores");
        try {
            table.save(file1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        table.clear();
        //HighScoresTable t;
        try {
            table.load(file1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < table.getHighScores().size(); i++) {
            System.out.print(table.getHighScores().get(i).getName());
            System.out.println(table.getHighScores().get(i).getScore());
        }


    }


}
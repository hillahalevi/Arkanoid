import animated.HighScoresAnimation;
import animated.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import factory.LevelSpecificationReader;
import gamehelp.AnimationRunner;
import gameobj.HighScoresTable;
import gameset.GameFlow;
import inter.LevelInformation;
import menu.LevelSets;
import menu.Menu;
import menu.MenuAnimation;
import menu.SingleSet;
import menu.Task;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * The type Ass 6.
 */
public class Ass6Game {
    /**
     * set and run the gameobj.
     *
     * @param args never used
     */
    public static void main(String[] args) {
        //default
        String levelSetsPath = "level_sets.txt";
        if (args.length > 0) {
            //chosen format
            levelSetsPath = args[0];
        }
        LevelSets levelSets;
        GUI gui = new GUI("its game time", 800, 600);
        Sleeper sleeper = new Sleeper();
        File f = new File("highscores");
        final HighScoresTable h = HighScoresTable.loadFromFile(f);
        final DialogManager dialogManager = gui.getDialogManager();
        KeyboardSensor keyboardSensor = gui.getKeyboardSensor();
        int framesPerSecond = 60;
        AnimationRunner runner = new AnimationRunner(gui, framesPerSecond, sleeper);

        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelSetsPath);
            levelSets = LevelSets.fromReader(new InputStreamReader(is));
        } catch (IOException var13) {
            throw new RuntimeException("ERROR - LevelSets Load");
        }
        MenuAnimation gameMenu = new MenuAnimation(runner, keyboardSensor, "Take your pick");
        for (SingleSet set : levelSets.getLevelSetList()) {
            gameMenu.addSelection(set.getKey(), set.getMessage(), new Task<Void>() {
                public void run() {
                    GameFlow gameFlow = new GameFlow(keyboardSensor, runner, h, dialogManager);
                    List<LevelInformation> levels;

                    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(set.getPath());
                    Reader reader = new InputStreamReader(is);
                    LevelSpecificationReader balbla = new LevelSpecificationReader();
                    levels = balbla.fromReader(reader);

                    gameFlow.runLevels(levels);
                    try {
                        h.save(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    runner.run(new KeyPressStoppableAnimation(new HighScoresAnimation(h), keyboardSensor, "space"));
                }
            });
        }

        Menu<Task<Void>> mainMenu = new MenuAnimation<>(runner, keyboardSensor, "GameTime");
        mainMenu.addSubMenu("s", "Game", gameMenu);
        mainMenu.addSelection("h", "High Scores", new Task<Void>() {
            public void run() {
                runner.run(new KeyPressStoppableAnimation(new HighScoresAnimation(h), keyboardSensor, "space"));
            }
        });
        mainMenu.addSelection("q", "Quit", new Task<Void>() {
            public void run() {
                System.exit(0);
            }
        });


        while (true) {
            runner.run(mainMenu);
            Task<Void> task = mainMenu.getStatus();
            task.run();
            mainMenu.goOver();
        }


    }

}

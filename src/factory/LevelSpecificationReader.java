package factory;

import inter.LevelInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader {
    private List<LevelInformation> levelSetList = new ArrayList<LevelInformation>();
    private List<List<String>> alllevels = new ArrayList<List<String>>();


    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list of level informatins
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        try {
            separateLevels(reader);
            translateLevels();
            return levelSetList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads the entire file and divide it to levels.
     *
     * @param reader the reader
     * @throws IOException exception
     */
    private void separateLevels(java.io.Reader reader) throws IOException {
        List<String> levelrecepie = new ArrayList<String>();
        BufferedReader lineReader = null;
        try {
            lineReader = new LineNumberReader(reader);
            String line;
            while (true) {
                while (true) {
                    do {
                        do {
                            line = lineReader.readLine();
                            if (line == null) {
                                //reach the end !
                                return;
                            }
                            line = line.trim();
                        } while ("".equals(line));
                    } while (line.startsWith("#"));

                    switch (line) {
                        case "START_LEVEL":
                            //clear all in and start a new read
                            levelrecepie.clear();
                            break;
                        case "END_LEVEL":
                            //finished reading single level ! add it to the list!
                            List<String> temp = new ArrayList<String>(levelrecepie);
                            alllevels.add(temp);
                            levelrecepie.clear();
                            break;
                        default:
                            //add line
                            levelrecepie.add(line);
                            break;
                    }
                }
            }
        } finally {
            if (lineReader != null) {
                lineReader.close();
            }

        }

    }

    /**
     * for each item in "alllevels" translate it into Levelinformation format.
     */
    private void translateLevels() {
        // every item in all levels is a list of strings - recepie for creating a level.
        LevelTranslator levelTranslator;
        for (List<String> levelrecepie : alllevels) {
            levelTranslator = new LevelTranslator(levelrecepie);
            LevelInformation levelInformation = levelTranslator.creator();
            levelSetList.add(levelInformation);
        }

    }


}
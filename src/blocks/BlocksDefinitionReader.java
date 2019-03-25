package blocks;

import inter.BlockCreator;
import inter.Fill;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Blocks definition reader.
 */
public class BlocksDefinitionReader extends Filled {
    private Map<String, String> defaultsMap;
    private Map<Character, BlockCreator> blocksMap;
    private Map<Character, Integer> spacerWidths;


    /**
     * Instantiates a new Blocks definition reader.
     */
    public BlocksDefinitionReader() {
        defaultsMap = new HashMap<String, String>();
        blocksMap = new HashMap<Character, BlockCreator>();
        spacerWidths = new HashMap<Character, Integer>();
    }


    /**
     * From reader blocks from symbols factory.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        BlocksDefinitionReader definitionReader = new BlocksDefinitionReader();
        definitionReader.arrange(reader);
        return new BlocksFromSymbolsFactory(definitionReader.spacerWidths, definitionReader.blocksMap);
    }

    /**
     * arrange all the file info into the right place.
     *
     * @param reader the reader.
     */
    private void arrange(java.io.Reader reader) {
        BufferedReader lineReader = null;
        String[] strings;
        //gets format width,x
        String[] temp;
        try {
            lineReader = new LineNumberReader(reader);
            String line;
            while (true) {
                line = lineReader.readLine();
                if (line == null) {
                    break;
                }
                if (line.equals("") || line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith("default")) {
                    line = line.replaceAll("\\bdefault \\b", "");
                    readline(line, (HashMap) defaultsMap);
                    continue;
                }
                if (line.startsWith("bdef")) {
                    //removes "bdef"
                    line = line.replaceAll("\\bbdef \\b", "");
                    //gets format a, blablabla
                    strings = symbaloicLine(line);
                    //gets format a:......
                    setBlocksMap(strings[0].charAt(0), strings[1]);
                    continue;


                }
                if (line.startsWith("sdef")) {
                    //removes "sdef"
                    line = line.replaceAll("\\bsdef\\b", "");
                    //gets format a, width:x
                    strings = symbaloicLine(line);
                    //gets format width,x
                    temp = strings[1].split(":");
                    if (!temp[0].equals("width")) {
                        throw new RuntimeException("problem in width of spacers");
                    }
                    //spaces[0]=a  temp[1]=x
                    spacerWidths.put(strings[0].charAt(0), Integer.parseInt(temp[1]));


                }
            }

        } catch (IOException e) {
            throw new RuntimeException("error reading blocks file");
        } finally {
            if (lineReader != null) {
                try {
                    lineReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("error reading blocks file");
                }
            }

        }

    }

    /**
     * set up blockmap.
     *
     * @param c the symbol
     * @param s the recepie.
     */
    private void setBlocksMap(char c, String s) {
        //!!!! remember dealing with fill-k && strike
        HashMap<String, String> h = new HashMap<String, String>();
        HashMap<Integer, Fill> f = new HashMap<Integer, Fill>();
        readline(s, h);
        Color color = null;
        int height = fillData("height", h);
        int hitp = fillData("hit_points", h);
        int width = fillData("width", h);
        Fill filled = null;
        if (h.containsKey("fill")) {
            filled = fill(h.get("fill"));
        } else if (h.containsKey("fill-1")) {
            filled = fill(h.get("fill-1"));
        }

        if (hitp > 1) {
            int hit = hitp;
            String j;
            while (hit > 0) {
                j = "fill-" + String.valueOf(hit);
                if (h.containsKey(j)) {
                    //different sprite
                    f.put(hit, fill(h.get(j)));
                } else if (defaultsMap.containsKey(s)) {
                    f.put(hit, fill(defaultsMap.get(j)));
                } else {
                    //default sprite
                    f.put(hit, filled);
                }
                hit--;

            }

        } else {
            f.put(1, filled);
        }
        if (h.containsKey("stroke")) {
            color = colors(h.get("stroke"));

        } else if (defaultsMap.containsKey("stroke")) {
            color = colors(defaultsMap.get("stroke"));

        }
        if (filled != null && width > 0 && height > 0 && hitp > 0) {
            blocksMap.put(c, new BaseBlockCreator(height, width, hitp, f, color));
        } else {
            throw new RuntimeException("error loading blocks info -fields missing|| illegal format");
        }


    }

    /**
     * returns data serached in hash maps.
     *
     * @param s the string to find
     * @param h the hash map to search
     * @return the data we wanted or -1 in case of failure.
     */
    private int fillData(String s, HashMap<String, String> h) {
        if (h.containsKey(s)) {
            return Integer.parseInt(h.get(s));
        } else if (defaultsMap.containsKey(s)) {
            return Integer.parseInt(defaultsMap.get(s));
        }
        return -1;

    }


    /**
     * read a line and update the hash map.
     *
     * @param s       the line to read
     * @param hashMap the hash map to update
     */
    private void readline(String s, HashMap hashMap) {
        String[] parts;
        parts = s.split(" ");
        String[] d;
        for (String def : parts) {
            if (def.equals("")) {
                continue;
            }
            d = def.split(":");
            if (d.length != 2) {
                throw new RuntimeException("illegal property format");
            }
            hashMap.put(d[0], d[1]);
        }


    }

    /**
     * divide symbolic line to the format a:.....
     *
     * @param s the line to divide
     * @return the new format
     */
    private String[] symbaloicLine(String s) {
        s = s.replaceAll("symbol:", " ");
        while (s.startsWith(" ")) {
            s = s.substring(1);
        }
        String b = s.substring(0, s.indexOf(" "));


        if (b.length() != 1) {
            throw new RuntimeException("symbol must be one character");
        }
        String[] parts = new String[2];
        parts[0] = b;
        s = s.substring(1);
        while (s.startsWith(" ")) {
            s = s.substring(1);
        }
        parts[1] = s;
        return parts;


    }


}

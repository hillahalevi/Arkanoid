package gameset;

import biuoop.DrawSurface;
import inter.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * in this class we will create a collection of sprite objects.
 */
public class SpriteCollection {
    private List<Sprite> spriteList = new ArrayList<Sprite>();

    /**
     * add sprite to the collection.
     *
     * @param s the sprite
     */
    public void addSprite(Sprite s) {
        if (s != null) {
            spriteList.add(s);
        }
    }


    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> seList = new ArrayList<Sprite>(this.spriteList);
        double dt = 1.0 / 60.0;
        for (Sprite temp : seList) {
            temp.timePassed(dt);
        }

    }

    /**
     * Gets sprite list.
     *
     * @return the sprite list
     */
    public List<Sprite> getSpriteList() {
        return spriteList;
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d the surface we draw on
     */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> seList = new ArrayList<Sprite>(this.spriteList);
        for (Sprite temp : seList) {
            temp.drawOn(d);
        }
    }
}
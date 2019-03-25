package inter;

import biuoop.DrawSurface;


/**
 * the sprite interface is used for things that can bedrawn on the screen, and can be notified that time has passed.
 */
public interface Sprite {


    /**
     * draw the sprite to the screen.
     *
     * @param d the surface.
     */
    void drawOn(DrawSurface d);



    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    void timePassed(double dt);
}
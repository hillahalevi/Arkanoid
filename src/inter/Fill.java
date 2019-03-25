package inter;

import biuoop.DrawSurface;
import geo.Rectangle;

/**
 * The interface Fill.
 */
public interface Fill {
    /**
     * draw the sprite to the screen.
     *
     * @param d         the surface.
     * @param rectangle the rectangle
     */
    void drawAt(DrawSurface d, Rectangle rectangle);
}

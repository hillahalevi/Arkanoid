package gameobj;

import animated.GameLevel;
import biuoop.DrawSurface;
import gameset.Velocity;
import geo.Line;
import geo.Point;
import geo.Rectangle;
import inter.Collidable;
import inter.Fill;
import inter.HitListener;
import inter.HitNotifier;
import inter.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * in the class we will build a block using rectangle that implements collidable and sprite.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private List<HitListener> hitListeners = new ArrayList<HitListener>();
    private Rectangle block;
    private int hitPoints;
    private Color c;
    private HashMap<Integer, Fill> f;


    /**
     * constructor.
     *
     * @param rectangle the shape of the block
     */
    public Block(Rectangle rectangle) {
        this.block = rectangle;
        this.f = null;
        this.c = null;
    }


    /**
     * constructor.
     *
     * @param rectangle the shape of the block
     * @param numofhits the number od hits the block can take
     * @param f         the f
     * @param c         the c
     */
    public Block(Rectangle rectangle, int numofhits, HashMap<Integer, Fill> f, Color c) {
        this.block = rectangle;
        this.hitPoints = numofhits;
        this.c = c;
        this.f = f;
    }


    /**
     * getter.
     *
     * @return the "collision shape" of the object.
     */
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**
     * insert the block to the gameobj.
     *
     * @param g the gameobj
     */
    public void addToGameLevel(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);

    }

    /**
     * draw on bold frame of a given block.
     *
     * @param d the surface we draw on
     */
    private void drawFrame(DrawSurface d) {
        int height = (int) block.getHeight();
        int width = (int) block.getWidth();
        int x = (int) (block.getUpperLeft().getX());
        int y = (int) (block.getUpperLeft().getY());

        for (int i = 0; i < 4; i++) {
            d.setColor(c);
            d.drawRectangle(x, y, width, height);
            y = y + 1;
            x = x + 1;
        }

    }

    /**
     * draw the block on the given DrawSurface.
     *
     * @param surface the surface we draw on
     */
    public void drawOn(DrawSurface surface) {
        if (f != null) {
            Fill bg = f.get(hitPoints);
            bg.drawAt(surface, block);
            //  return;
        } else {
            block.drawOn(surface, block.getColor());
        }
        if (c != null) {
            this.drawFrame(surface);
        }


    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    @Override
    public void timePassed(double dt) {

    }

    /**
     * Remove from game.
     *
     * @param game the game
     */
    public void removeFromGameLevel(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }


    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     *
     * @param collisionPoint  the hit point
     * @param currentVelocity the current velocity
     * @param hitter          the hitting ball
     * @return the new velocity expected after the hit
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Line[] arr = this.getCollisionRectangle().sideRect();
        //Random rand = new Random();
        //  Color color = Color.pink;
        for (Line anArr : arr) {
            //cheges the velocity values according to the point we hit
            if (anArr.onSegment(anArr, collisionPoint)) {
                if (anArr.horizental()) {
                    dy = -dy;
                } else {
                    dx = -dx;
                }
            }

        }
        if (hitPoints > 0) {
            //make it colorful
            ///color = new Color(rand.nextInt(0xFFFFFF));
            hitPoints--;
        }
        //   this.getCollisionRectangle().setColor(color);
        this.notifyHit(hitter);
        return new Velocity(dx, dy);


    }

    /**
     * Notify all listeners about a hit event.
     *
     * @param hitter the hitting ball
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Add hit listener.
     * Add hl as a listener to hit events.
     *
     * @param hl the hl
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * Remove hit listener.
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl the hl
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);

    }

    /**
     * Gets hit points.
     *
     * @return the hit points
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Sets hit points.
     *
     * @param hit the hit points
     */
    public void setHitPoints(int hit) {
        this.hitPoints = hit;
    }

    /**
     * Sets block.
     *
     * @param b the block
     */
    public void setBlock(Rectangle b) {
        this.block = b;
    }

    /**
     * Sets c.
     *
     * @param color the c
     */
    public void setC(Color color) {
        this.c = color;
    }
}

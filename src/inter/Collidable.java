package inter;


import gameobj.Ball;
import gameset.Velocity;
import geo.Point;
import geo.Rectangle;

/**
 * The inter.Collidable interface will be used by things that can be collided with.
 */
public interface Collidable {


    /***
     * get the shepe of the collidable object.
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     *
     * @param collisionPoint  the hit point
     * @param currentVelocity the current velocity
     * @param  hitter the hitting ball
     * @return the new velocity expected after the hit
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
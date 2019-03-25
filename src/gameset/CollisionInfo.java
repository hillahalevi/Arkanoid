package gameset;

import geo.Line;
import geo.Point;
import geo.Rectangle;
import inter.Collidable;

import java.util.List;

/**
 * in this class we will make a set of information about a given hit.
 */
public class CollisionInfo {
    private Point hitPoint;
    private Collidable collidable;

    /**
     * constructor.
     *
     * @param p the point of the collision
     * @param c the object we collide in
     */
    public CollisionInfo(Point p, Collidable c) {
        this.collidable = c;
        this.hitPoint = p;

    }


    /**
     * getters.
     *
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return hitPoint;
    }


    /**
     * getters.
     *
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return collidable;
    }

    /**
     * cheks if the hit point is on the edge of the object.
     *
     * @return true or false
     */
    public boolean onEdge() {
        int a = 0;
        Line[] lines = collidable.getCollisionRectangle().sideRect();

        for (Line line : lines) {
            if (line.onSegment(line, collisionPoint())) {
                a++;

            }
        }
        return a > 1;
    }

    /**
     * cheks if the hit point is on other  objects.
     *
     * @param collidableList all the collidables in the gameobj
     * @param tra            the moving path
     * @return true or false
     */
    public boolean twohit(List<Collidable> collidableList, Line tra) {
        int a = 0;
        Rectangle rect;
        for (Collidable aCollidableList : collidableList) {
            //the current collidable object
            rect = aCollidableList.getCollisionRectangle();
            //check if this object will intersect with the line
            if (rect.intersectionPoints(tra).isEmpty()) {
                continue;
            }
            a++;

        }
        return a > 1;
    }
}

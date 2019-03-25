package gameset;

import geo.Line;
import geo.Point;
import geo.Rectangle;
import inter.Collidable;

import java.util.ArrayList;
import java.util.List;

/**
 * in this class we will create gameobj environment.
 */
public class GameLevelEnvironment {
    private List<Collidable> collidableList = new ArrayList<Collidable>();


    /**
     * add the given collidable to the environment.
     *
     * @param c given collidable
     */
    public void addCollidable(Collidable c) {
        if (c != null) {
            collidableList.add(c);
        }
    }

    /**
     * getters.
     *
     * @return the list of all the collidble objects in the gameobj
     */
    public List<Collidable> getCollidableList() {
        return collidableList;
    }


    /**
     * returns information about the closest collision that can accrue.
     *
     * @param trajectory the line that the ball goes on.
     * @return null if theres no collision ||the information about the closest collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<Collidable> cList = new ArrayList<Collidable>(this.collidableList);
        if (cList.isEmpty()) {
            //there are no collidable objects.
            return null;
        } else {
            Rectangle rect;
            //a list of all the intersection points from all the collidable objects
            List<Point> temp = new ArrayList<Point>();
            //a list of all the indexes of the objects we collide with
            List<Integer> temprect = new ArrayList<Integer>();
            for (int i = 0; i < cList.size(); i++) {
                //the current collidable object
                rect = cList.get(i).getCollisionRectangle();
                //check if this object will intersect with the line
                if (rect.intersectionPoints(trajectory).isEmpty()) {
                    //there are no intersection with this collidable.
                    continue;
                }
                //add the closest intersection point
                temp.add(trajectory.closestIntersectionToStartOfLine(rect));
                //add the object's index
                temprect.add(i);


            }
            if (temprect.isEmpty()) {
                //there will be no intersection with this line
                return null;
            }
            //get the index of the closest point ever
            int i = temp.indexOf(trajectory.closestPointFromList(temp));
            //get the index of the collidable object
            int b = temprect.get(i);
            return new CollisionInfo(temp.get(i), cList.get(b));


        }

    }

}
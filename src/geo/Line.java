package geo;

import java.util.List;


/**
 * in this class we will create a new geo.Line object going from start to end.
 */
public class Line {

    private Point start;
    private Point end;


    /**
     * constructor ,uses two points.
     *
     * @param start point
     * @param end   point
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * * constructor ,uses two points in their prime form (x,y).
     *
     * @param x1 x value of the start point
     * @param y1 y value of the start point
     * @param x2 x value of the end point
     * @param y2 y value of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        start = new Point(x1, y1);
        end = new Point(x2, y2);
    }


    /**
     * length().
     *
     * @return the length of the line.
     */
    public double length() {
        return start.distance(end);
    }


    /**
     * middle().
     *
     * @return the middle point of the line
     */
    public Point middle() {
        double x = (start.getX() + end.getX()) / 2;
        double y = (start.getY() + end.getY()) / 2;
        return new Point(x, y);

    }


    /**
     * start().
     *
     * @return the start point of the line
     */
    public Point start() {
        return start;
    }

    /**
     * end().
     *
     * @return the end point of the line
     */
    public Point end() {
        return end;
    }


    /**
     * incline-the slope of a line according to two points.
     *
     * @param line the line we want to find her slope
     * @return the slope
     */
    private double incline(Line line) {
        double startx = line.start.getX();
        double starty = line.start.getY();
        double endx = line.end.getX();
        double endy = line.end.getY();
        //check division by 0
        if (startx == endx) {
            return 0;
        }
        return (starty - endy) / (startx - endx);
    }


    /**
     * freeNum-returns the free number according to the formula.
     *
     * @param line the line we work on
     * @return the free number according to the formula
     */
    private double freeNum(Line line) {
        double startx = line.start.getX();
        double starty = line.start.getY();
        return starty - (startx * incline(line));

    }


    /**
     * intersectionPoint.
     *
     * @param other other line that intersects with the main line
     * @return the intersection point of two lines
     */
    private Point intersectionPoint(Line other) {
        double x;
        double y;
        if (other.vertical()) {
            //if one line is parallel to the y axis
            x = other.start.getX();
        } else if (this.vertical()) {
            x = start.getX();
        } else {
            //form the x value according to the formula
            x = (other.freeNum(other) - freeNum(this)) / (incline(this) - other.incline(other));
        }
        if (other.horizental()) {
            y = other.start.getY();
        } else if (this.horizental()) {
            y = start.getY();
        } else {
            // /form the y value according to the formula
            if (this.vertical()) {
                y = incline(other) * x + freeNum(other);
            } else {
                y = incline(this) * x + freeNum(this);
            }
        }
        return new Point(x, y);

    }


    /**
     * checks if a given point is on a specific segment.
     *
     * @param line  segment
     * @param check a point we check if its on
     * @return true if its on false otherwise
     */
    public boolean onSegment(Line line, Point check) {
        double startx = line.start.getX();
        double endx = line.end.getX();
        double starty = line.start.getY();
        double endy = line.end.getY();
        double x = check.getX();
        double y = check.getY();
        boolean b = (y <= Math.max(starty, endy)) && (y >= Math.min(starty, endy));
        boolean a = (x <= Math.max(startx, endx)) && (x >= Math.min(startx, endx));
        return a && b;

    }

    /**
     * checks if our line is vertical.
     *
     * @return true or false.
     */
    public boolean vertical() {
        return start.getX() == end.getX();

    }

    /**
     * checks if our line is horizental.
     *
     * @return true or false.
     */
    public boolean horizental() {
        return start.getY() == end.getY();

    }


    /**
     * Returns true if the lines intersect, false otherwise.
     *
     * @param other other segment we check for intersection
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        //the lines are equal
        if (equals(other)) {
            return false;
        }
        //both lines are eather vertical or horizental
        if ((horizental() && other.horizental()) || vertical() && other.vertical()) {
            return false;

        }
        // Lines are parallel
        if ((incline(this) == incline(other)) && (incline(other) != 0)) {
            return false;
        }
        //the lines will intersect lets see where
        //the intersection point
        Point check = intersectionPoint(other);
        //returns if the point is on both lines
        return onSegment(this, check) && onSegment(other, check);

    }


    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     *
     * @param other other segment we check for intersection
     * @return intersection point or null
     */
    public Point intersectionWith(Line other) {
        if (!isIntersecting(other)) {
            //the lines don't intersect
            return null;
        } else {
            //returns the intersection point
            return intersectionPoint(other);


        }
    }


    /**
     * equals -- return true is the lines are equal, false otherwise.
     *
     * @param other another segment for comparision
     * @return true is the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return (start == other.start && end == other.end) || (start == other.end && end == other.start);


    }


    /**
     * where the closest intersection with a given rectangle will accure.
     *
     * @param rect the given rectangle.
     * @return if this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        //get all the intersection points with this line.
        List<Point> temp = rect.intersectionPoints(this);
        if (temp.isEmpty()) {
            //the line does not intersect with the rectangle
            return null;
        }
        return closestPointFromList(temp);
    }

    /**
     * chooses the closest intersection point from a list.
     *
     * @param temp the intersection points list.
     * @return the closest point to the start of the line
     */
    public Point closestPointFromList(List<Point> temp) {
        // creates a measure point and assign it to the first element
        Point close = temp.get(0);
        double dist = start.distance(temp.get(0));
        for (int i = 1; i < temp.size(); i++) {
            //move threw all the other points to see if theres a closer one
            if (dist > start.distance(temp.get(i))) {
                //update the new close and the min distance
                dist = start.distance(temp.get(i));
                close = temp.get(i);


            }


        }
        return close;
    }

}



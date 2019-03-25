package geo;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * in this class we will Create a rectangle with location and width/height.
 */
public class Rectangle {
    private Point upLeft;
    private double w;
    private double h;
    private java.awt.Color color = null;


    /**
     * constructor.
     *
     * @param upperLeft the upperlest point of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upLeft = upperLeft;
        this.w = width;
        this.h = height;

    }


    /**
     * intersection points with a given line.
     *
     * @param line line we intersect with
     * @return a (possibly empty) List of intersection points with line.
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersection = new ArrayList<Point>();
        if (upLeft != null) {
            Line[] arr = sideRect();
            Point p;
            for (Line anArr : arr) {
                if (anArr.isIntersecting(line)) {
                    p = anArr.intersectionWith(line);

                    intersection.add(p);


                }

            }

        }
        return intersection;
    }

    /**
     * setting up the color of the rectangle.
     *
     * @param c the color.
     */
    public void setColor(java.awt.Color c) {
        this.color = c;
    }

    /**
     * getters.
     *
     * @return the color of the rectangle.
     */
    public java.awt.Color getColor() {
        return this.color;
    }


    /**
     * getters. creates an array of line segments of the rectangle.
     *
     * @return the segments of the rectangle.
     */
    public Line[] sideRect() {
        if (upLeft != null) {
            Line[] arr = new Line[4];
            double x = upLeft.getX();
            double y = upLeft.getY();
            Point upRight = new Point(x + w, y);
            Point downleft = new Point(x, y + h);
            Point downRight = new Point(x + w, y + h);
            arr[0] = new Line(upLeft, upRight);
            arr[2] = new Line(downRight, downleft);
            arr[1] = new Line(upLeft, downleft);
            arr[3] = new Line(downRight, upRight);

            return arr;
        }
        return null;
    }


    /**
     * getters.
     *
     * @return the width of the rectangle
     */
    public double getWidth() {
        return w;
    }

    /**
     * getters.
     *
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return h;
    }


    /**
     * getters.
     *
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return upLeft;
    }

    /**
     * checks if a given point is inside the rectangle.
     *
     * @param point the check point
     * @return true or false
     */
    public Boolean arePeIn(Point point) {
        double x = point.getX();
        double y = point.getY();
        boolean a = (x >= upLeft.getX()) && (x <= upLeft.getX() + w);
        boolean b = (y >= upLeft.getY()) && (y <= upLeft.getY() + h);
        return a && b;
    }

    /**
     * draws the rectangle on a given surface.
     *
     * @param surface the surface we draw on.
     * @param c       the color we want.
     */
    public void drawOn(DrawSurface surface, java.awt.Color c) {
        int x = (int) upLeft.getX();
        int y = (int) upLeft.getY();
        int width = (int) w;
        int height = (int) h;
        surface.setColor(c);
        surface.fillRectangle(x, y, width, height);

    }

    /**
     * Sets w.
     *
     * @param wi the w
     */
    public void setW(double wi) {
        this.w = wi;
    }

    /**
     * Sets h.
     *
     * @param hi the h
     */
    public void setH(double hi) {
        this.h = hi;
    }
}

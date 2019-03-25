package gameset;

import geo.Point;

/**
 * gameset.Velocity specifies the change in position on the `x` and the `y` axes.
 */

public class Velocity {

    private double dx;
    private double dy;


    /**
     * constructor.
     *
     * @param dx of the velocity
     * @param dy of the velocity
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * fromAngleAndSpeed.
     *
     * @param angle direction
     * @param speed speed
     * @return velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = Math.toRadians(angle);
        double dx = speed * Math.sin(angle);
        double dy = -speed * Math.cos(angle);
        return new Velocity(dx, dy);
    }


    /**
     * getDx().
     *
     * @return the dx
     */
    public double getDx() {
        return dx;
    }

    /**
     * getDy().
     *
     * @return the dy
     */
    public double getDy() {
        return dy;
    }


    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p current position
     * @return point of the next position
     */
    public Point applyToPoint(Point p) {
        double x = p.getX() + dx;
        double y = p.getY() + dy;
        x = p.precision(x, 7);
        y = p.precision(y, 7);
        return new Point(x, y);
    }
}
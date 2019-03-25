package geo;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * in this class we will Implement 2D point using rectangular coordinates.
 */
public class Point {
    private double x;
    private double y;

    /**
     * constructor.
     *
     * @param x will be the x value
     * @param y will be the y value
     */
    public Point(double x, double y) {
        //all point must be accurate.
        this.x = precision(x, 7);
        this.y = precision(y, 7);
    }

    /**
     * distance -- return the distance of this point to the other point.
     *
     * @param other another point which we will calculate the distance from
     * @return the length between other and the point
     */
    public double distance(Point other) {
        double distance = ((x - other.x) * (x - other.x)) + ((y - other.y) * (y - other.y));
        distance = Math.sqrt(distance);
        return distance;
    }

    /**
     * equals -- return true is the points are equal, false otherwise.
     *
     * @param other other point to check
     * @return true is the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        return ((x == other.x) && (y == other.y));

    }

    /**
     * get(x).
     *
     * @return the x value of this point
     */
    public double getX() {
        return x;
    }

    /**
     * get(y).
     *
     * @return the y value of this point
     */
    public double getY() {
        return y;

    }

    /**
     * make all the points accurate.
     *
     * @param val    a double number.
     * @param places after dot.
     * @return the accurate number.
     */
    public double precision(double val, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal num = new BigDecimal(val);
        num = num.setScale(places, RoundingMode.HALF_UP);
        return num.doubleValue();
    }

}
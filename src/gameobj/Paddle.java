package gameobj;

import biuoop.DrawSurface;
import animated.GameLevel;
import gameset.Velocity;
import geo.Line;
import geo.Point;
import geo.Rectangle;
import inter.Collidable;
import inter.Sprite;


/**
 * in the class we will build a paddle using rectangle that implements collidable and sprite.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle paddle;
    private int speed;
    private java.awt.Color c;
    private double minValue;
    private double maxValue;
    private double dt;

    /**
     * constactor.
     *
     * @param rectangle the shape of the paddle.
     * @param sp        the speed of the paddle.
     * @param color     the color of the paddle.
     * @param k         Keyboard Sensor of the paddle.
     * @param min       the min value of the range of the paddle(border).
     * @param max       the max value of the range of the paddle(border).
     */
    public Paddle(Rectangle rectangle, int sp, java.awt.Color color, biuoop.KeyboardSensor k, double min, double max) {
        keyboard = k;
        paddle = rectangle;
        speed = sp;
        c = color;
        minValue = min;
        maxValue = max;
        dt = 1;


    }

    /**
     * Sets paddle.
     *
     * @param p the paddle
     */
    public void setPaddle(Rectangle p) {
        this.paddle = p;
    }

    /**
     * check if the movement will hit the border.
     *
     * @return true or false.
     */
    private boolean inRightRangend() {
        double current = paddle.getUpperLeft().getX();
        double way = paddle.getWidth();
        return (current + way + speed * dt <= maxValue);
    }

    /**
     * check if the movement will hit the border.
     *
     * @return true or false.
     */
    private boolean inRightRangestart() {
        double current = paddle.getUpperLeft().getX();
        return (current - speed * dt >= minValue);
    }

    /**
     * moves the paddle to the left if the movement is legit.
     */
    public void moveLeft() {
        //only if not hitting the left border
        if (inRightRangestart()) {
            double w = paddle.getWidth();
            double h = paddle.getHeight();
            Point point = paddle.getUpperLeft();
            //reposition the paddle
            paddle = new Rectangle(new Point(point.getX() - speed * dt, point.getY()), w, h);
        }

    }

    /**
     * moves the paddle to the right if the movement is legit.
     */
    public void moveRight() {
        if (inRightRangend()) {
            //only if not hitting the right border.
            double w = paddle.getWidth();
            double h = paddle.getHeight();
            Point point = paddle.getUpperLeft();
            //reposition the paddle
            paddle = new Rectangle(new Point(point.getX() + speed * dt, point.getY()), w, h);
        }
    }

    /**
     * divide the paddle into 5 different rigions.
     *
     * @return a list of all the rigions.
     */
    private Line[] rigions() {
        double epsilon = 0.005;
        double x = paddle.getUpperLeft().getX() + epsilon;
        double y = paddle.getUpperLeft().getY();
        double width = paddle.getWidth() - 5 * epsilon;
        double height = paddle.getHeight();
        int numOfRigions = 5;
        double w = (width + x) / numOfRigions;
        Line[] arr = new Line[numOfRigions * 2];
        int j = 0;
        while (j < 10) {
            for (int i = 0; i < numOfRigions; i++, j++) {
                Point p1 = new Point(x, y);
                Point p2 = new Point(x + w, y);
                arr[j] = new Line(p1, p2);
                x = x + w + epsilon / numOfRigions;
            }
            //handeling the bottom of the paddle.
            y = y + height;
            x = paddle.getUpperLeft().getX();
        }

        return arr;

    }


    /**
     * draw the paddle on the surface.
     *
     * @param d the surfuce.
     */
    public void drawOn(DrawSurface d) {
        paddle.drawOn(d, c);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param d the dt
     */
    @Override
    public void timePassed(double d) {
        dt = d;
        if (keyboard.isPressed(keyboard.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(keyboard.RIGHT_KEY)) {
            moveRight();
        }
    }


    /**
     * geters.
     *
     * @return the shape of the paddle.
     */
    public Rectangle getCollisionRectangle() {
        return paddle;
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
        Line[] rigion = rigions();
        double sp = Math.hypot(currentVelocity.getDx(), currentVelocity.getDy());
        Velocity v;

        for (int i = 0; i < rigion.length; i++) {
            if (rigion[i].onSegment(rigion[i], collisionPoint)) {
                //rigionn 1=0,2=1 3=2,4=3,5=4
                //rigion 6,7,8,9 are the bottom of the paddle
                switch (i) {
                    case 0:
                    case 9:
                        return Velocity.fromAngleAndSpeed(300, sp);
                    case 1:
                    case 8:
                        return Velocity.fromAngleAndSpeed(330, sp);
                    case 2:
                    case 7:
                        return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
                    case 3:
                    case 6:
                        return Velocity.fromAngleAndSpeed(30, sp);
                    case 4:
                    case 5:
                        return Velocity.fromAngleAndSpeed(60, sp);
                    default:
                        break;


                }


            }


        }
        v = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        return v;
    }

    /**
     * adding the paddle to the gameobj.
     *
     * @param g the gameobj.
     */
    public void addToGameLevel(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);

    }
}

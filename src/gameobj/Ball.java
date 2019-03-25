package gameobj;

import biuoop.DrawSurface;
import gameset.CollisionInfo;
import animated.GameLevel;
import gameset.GameLevelEnvironment;
import gameset.Velocity;
import geo.Line;
import geo.Point;
import geo.Rectangle;
import inter.Collidable;
import inter.HitListener;
import inter.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * in this class we will make a ball using radius and coordinates.
 */
public class Ball implements Sprite {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity v;
    private GameLevelEnvironment gameEnvironment;
    private List<HitListener> hitListeners;


    /**
     * constructor.
     *
     * @param center the center point of=g the ball
     * @param r      the radius
     * @param color  the color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
        hitListeners = new ArrayList<HitListener>();
    }

    /**
     * constructor.
     *
     * @param center the center point of=g the ball
     * @param r      the radius
     * @param color  the color of the ball
     * @param sp     the speed of the ball
     */
    public Ball(Point center, int r, java.awt.Color color, Velocity sp) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.v = sp;
        hitListeners = new ArrayList<HitListener>();
    }


    /**
     * accessors - getX().
     *
     * @return the x value of this point
     */
    public int getX() {
        return (int) center.getX();
    }

    /**
     * accessors - getY().
     *
     * @return the y value of this point
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * accessors - getSize().
     *
     * @return the radius value of this ball
     */
    public int getSize() {
        return r;
    }

    /**
     * accessors - getColor().
     *
     * @return the color of the ball
     */
    public java.awt.Color getColor() {
        return color;
    }

    /**
     * changing the color of the ball.
     *
     * @param c the color we change too
     */
    public void setColor(Color c) {
        this.color = c;
    }

    /**
     * accessors - getVelocity().
     *
     * @return the gameset.Velocity of the ball
     */
    public Velocity getVelocity() {
        return v;
    }

    /**
     * changing the velocity of thr ball.
     *
     * @param ve the velocity we change too
     */
    public void setVelocity(Velocity ve) {
        this.v = ve;

    }

    /**
     * changing the velocity of thr ball.
     *
     * @param dx position of x
     * @param dy position of y
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }


    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface the surface we draw on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(getColor());
        surface.fillCircle(getX(), getY(), getSize());


    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    @Override
    public void timePassed(double dt) {
        moveOneStep(dt);
    }


    /**
     * conduct the road that the ball will go on.
     *
     * @param gotopoint the point we will end with
     * @return a line betwin our center and gotopoint
     */
    private Line trajectory(Point gotopoint) {
        return new Line(center, gotopoint);
    }

    /**
     * seeters,place the gameobj enviorment.
     *
     * @param g the gameobj enviorment
     */
    public void setGameLevelEnvironment(GameLevelEnvironment g) {
        this.gameEnvironment = g;
    }

    /**
     * insert the ball to the gameobj.
     *
     * @param g the gameobj
     */
    public void addToGameLevel(GameLevel g) {
        setGameLevelEnvironment(g.getEnvironment());
        g.addSprite(this);
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
     * Remove from game.
     *
     * @param game the game
     */
    public void removeFromGameLevel(GameLevel game) {
        game.removeSprite(this);
    }

    /**
     * repositions the ball after it hits a collidable.
     *
     * @param c the information about the collidable and the hit point
     * @return the closest point to the collision point
     */
    private Point gopoint(CollisionInfo c) {
        Line[] l = c.collisionObject().getCollisionRectangle().sideRect();
        double x = c.collisionPoint().getX();
        double y = c.collisionPoint().getY();
        double epsilon = 0.005;
        for (int i = 0; i < l.length; i++) {
            //check from which side the ball hit and correct the (x,y) values
            if (l[i].onSegment(l[i], c.collisionPoint())) {
                switch (i) {
                    case 0:
                        y = y - r - epsilon;
                        break;
                    case 1:
                        x = x - r - epsilon;
                        break;
                    case 2:
                        y = y + r + epsilon;
                        break;
                    case 3:
                        x = x + r + epsilon;
                        break;
                    default:
                        break;
                }
            }

        }


        return new Point(x, y);
    }

    /**
     * repositions the ball after is gets inside a paddle.
     *
     * @param c the information about the paddle and the hit point
     * @return the closest point drom outside the paddle
     */
    private Point inPaddleMode(CollisionInfo c) {
        Rectangle paddle = c.collisionObject().getCollisionRectangle();
        double dis = center.getY() - paddle.getUpperLeft().getY();
        //on the surfuce of the paddle
        double y = center.getY() - dis - 1;
        return new Point(center.getX(), y);
    }


    /**
     * moveOneStep()-move the ball in a fixed way.
     *
     * @param dt the dt
     */
    private void moveOneStep(double dt) {
        Velocity vel = new Velocity(this.v.getDx() * dt, this.v.getDy() * dt);
        Point gotopoint = vel.applyToPoint(this.center);
        Line tra = trajectory(gotopoint);
        CollisionInfo cinfo = gameEnvironment.getClosestCollision(tra);
        if (cinfo == null) {
            //there are no hits
            this.center = gotopoint;
        } else {
            Random rand = new Random();
            Color c = new Color(rand.nextInt(0xFFFFFF));

            //place the ball next to the intersection point
            Collidable object = cinfo.collisionObject();
            if (object.getCollisionRectangle().arePeIn(center) && object instanceof Paddle) {
                //if the ball is inside the paddle
                this.center = inPaddleMode(cinfo);
            } else {
                this.center = gopoint(cinfo);
            }
            setVelocity(cinfo.collisionObject().hit(this, cinfo.collisionPoint(), v));

            //make it colorfull
            setColor(c);


        }
    }
}
package animated;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import gamehelp.AnimationRunner;
import gamehelp.Counter;
import gamehelp.LevelsIndicator;
import gamehelp.LivesIndicator;
import gamehelp.ScoreIndicator;
import gameobj.Ball;
import gameobj.Block;
import gameobj.Paddle;
import gameset.GameLevelEnvironment;
import gameset.SpriteCollection;
import geo.Point;
import geo.Rectangle;
import hitliteners.BallRemover;
import hitliteners.BallReviver;
import hitliteners.BlockRemover;
import hitliteners.ScoreTrackingListener;
import inter.Animation;
import inter.Collidable;
import inter.HitListener;
import inter.LevelInformation;
import inter.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * in this class we will set the game.
 */
public class GameLevel implements Animation {
    private final int width = 800;
    private final int height = 600;
    private final int frame = 20;
    private SpriteCollection sprites;
    private GameLevelEnvironment environment;
    private biuoop.KeyboardSensor keyboardSensor;
    private AnimationRunner runner;
    private LevelInformation info;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private HitListener ballremove;
    private Counter score;
    private Counter numberoflives;
    private Paddle paddle;
    private boolean running;

    /**
     * Instantiates a new Game level.
     *
     * @param info1   the info 1
     * @param sensor1 the sensor 1
     * @param runner1 the runner 1
     * @param score1  the score 1
     * @param lives1  the lives 1
     */
    public GameLevel(LevelInformation info1, KeyboardSensor sensor1, AnimationRunner runner1,
                     Counter score1, Counter lives1) {
        this.info = info1;
        this.keyboardSensor = sensor1;
        this.runner = runner1;
        this.score = score1;
        this.numberoflives = lives1;
    }


    /**
     * adding collidables to the game.
     *
     * @param c the collidable object
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * adding sprites to the game.
     *
     * @param s the sprite object
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * creates the frame of the gameobj and the collidables objects in it and adds them to the gameobj.
     */
    private void frame() {
        int width2 = width - 2 * frame;
        List<Block> list = new ArrayList<Block>();
        //setting up the frame(with 0 number of hits) and adding it to the game.
        Block a = new Block(new Rectangle(new Point(0, 0), frame, height));
        list.add(a);
        Block b = new Block(new Rectangle(new Point(frame, frame), width2, frame / 2));
        list.add(b);
        Block c = new Block(new Rectangle(new Point(width - frame, 0), frame, height));
        list.add(c);
        Rectangle r = new Rectangle(new Point(frame, 0), width2, frame);
        sprites.addSprite(new ScoreIndicator(r, this));
        r = new Rectangle(new Point(frame, 0), width2 / 3, frame);
        sprites.addSprite(new LivesIndicator(r, this));
        r = new Rectangle(new Point(2 * width2 / 3, 0), width2 / 3, frame);
        sprites.addSprite(new LevelsIndicator(r, this));
        for (Block o : list) {
            o.addToGameLevel(this);
            o.getCollisionRectangle().setColor(Color.darkGray);


        }
    }

    /**
     * creates the collidables objects for the gameobj and adds them to the gameobj.
     */
    private void blocks() {
        HitListener ballrevive = new BallReviver(this);
        HitListener bremove = new BlockRemover(this, this.remainingBlocks);
        HitListener scoreholder = new ScoreTrackingListener(score);
        int counter = 0;
        for (Block b : info.blocks()) {

            if (counter == 44 || counter == 20 || counter == 104) {
                //revive block
                b.addHitListener(ballrevive);
                b.getCollisionRectangle().setColor(Color.white);
            }
            if (counter == 30 || counter == 90) {
                //killer block
                b.addHitListener(ballremove);
                b.getCollisionRectangle().setColor(Color.black);
            }

            b.addHitListener(bremove);
            b.addHitListener(scoreholder);
            //adds the block to the game
            b.addToGameLevel(this);
            counter++;
        }

    }

    /**
     * ballsCreator.
     */
    public void ballsCreator() {
        remainingBalls.increase(info.numberOfBalls());
        double x = paddle.getCollisionRectangle().getUpperLeft().getX() + info.paddleWidth() / 2;
        double y = paddle.getCollisionRectangle().getUpperLeft().getY() - 50;
        for (int i = 0; i < info.numberOfBalls(); i++) {
            //creates the ball
            Ball ball = new Ball(new Point(x, y), 10, Color.pink, info.initialBallVelocities().get(i));
            ball.addHitListener(ballremove);
            //adding it to the game
            ball.addToGameLevel(this);

        }

    }

    /**
     * Special blocks create death region and sets the paddle.
     */
    private void specialBlocks() {
        Block l = new Block(new Rectangle(new Point(frame, height), width - 2 * frame, frame));
        l.getCollisionRectangle().setColor(Color.lightGray);
        l.addHitListener(ballremove);
        l.addToGameLevel(this);

    }

    /**
     * getters.
     *
     * @return the enviorment of the gameobj.
     */
    public GameLevelEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * remove from game enviormant.
     *
     * @param c the object to be removed
     */
    public void removeCollidable(Collidable c) {
        environment.getCollidableList().remove(c);
    }

    /**
     * remove from sprites collestion.
     *
     * @param s object to be removed
     */
    public void removeSprite(Sprite s) {
        sprites.getSpriteList().remove(s);
    }


    /**
     * Gets score.
     *
     * @return the score
     */
    public Counter getScore() {
        return score;
    }

    /**
     * Gets numberoflives.
     *
     * @return the numberoflives
     */
    public Counter getNumberoflives() {
        return numberoflives;
    }

    /**
     * Gets info.
     *
     * @return the info
     */
    public LevelInformation getInfo() {
        return info;
    }

    /**
     * Initialize a new gameobj: create the Blocks and gameobj.Ball (and gameobj.Paddle) and add them to the gameobj.
     */
    public void initialize() {
        //setting up the whole game surface
        environment = new GameLevelEnvironment();
        sprites = new SpriteCollection();
        remainingBalls = new Counter(0);
        remainingBlocks = new Counter(info.numberOfBlocksToRemove());
        ballremove = new BallRemover(this, this.remainingBalls);
        sprites.addSprite(info.getBackground());
        this.running = true;
        setPaddle();
        frame();
        blocks();


    }

    /**
     * set paddle.
     */
    private void setPaddle() {
        int x = width / 2 - info.paddleWidth() / 2;
        int y = height - 2 * frame;
        Rectangle rect = new Rectangle(new Point(x, y), info.paddleWidth(), frame);
        if (paddle == null) {
            paddle = new Paddle(rect, info.paddleSpeed(), Color.yellow, keyboardSensor, frame, width - frame);
        } else {
            paddle.setPaddle(rect);
        }
        paddle.addToGameLevel(this);


    }

    /**
     * Do one frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        // game-specific logic
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        if (this.remainingBalls.getValue() == 0) {
            numberoflives.decrease(1);
            this.running = false;
        }
        if (this.remainingBlocks.getValue() == 0) {
            score.increase(100);
            this.running = false;
        }

        if (this.keyboardSensor.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(new PauseScreen(), this.keyboardSensor, "space"));
        }


    }

    /**
     * shouldStop.
     *
     * @return true or false
     */
    public boolean shouldStop() {
        return !running;
    }


    /**
     * Run the gameo -- start the animation loop.
     */
    public void playOneTurn() {
        setPaddle();
        ballsCreator();
        specialBlocks();
        this.runner.run(new CountdownAnimation(2, 3, sprites));
        this.running = true;
        this.runner.run(this);

    }


    /**
     * Run.
     */
    public void run() {
        while (numberoflives.getValue() > 0) {
            playOneTurn();
            if (remainingBlocks.getValue() == 0) {
                break;
            }

        }

    }


}
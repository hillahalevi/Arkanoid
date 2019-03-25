package animated;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import inter.Animation;

/**
 * The type Key press stoppable toStop.
 */
public class KeyPressStoppableAnimation implements Animation {
    private Animation toStop;
    private boolean done;
    private KeyboardSensor keyboardSensor;
    private String key;
    private boolean ignore;
    private boolean istart;


    /**
     * Instantiates a new Key press stoppable toStop.
     *
     * @param decorated      the decorated
     * @param keyboardSensor the keyboard sensor
     * @param key            the key
     */
    public KeyPressStoppableAnimation(Animation decorated, KeyboardSensor keyboardSensor, String key) {
        this.toStop = decorated;
        this.done = false;
        this.key = key;
        this.keyboardSensor = keyboardSensor;
        this.istart = true;
        this.ignore = false;
    }

    /**
     * Do one frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.istart) {
            this.ignore = this.keyboardSensor.isPressed(this.key);
            this.istart = false;
        }

        this.toStop.doOneFrame(d, dt);
         if (this.keyboardSensor.isPressed(this.key)) {
            if (!this.ignore) {
                this.done = true;
            }
        } else {
            this.ignore = false;
        }

    }
    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() {
        return this.done;
    }


}


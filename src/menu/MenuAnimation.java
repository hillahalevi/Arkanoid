package menu;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import gamehelp.AnimationRunner;
import screens.Screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Menu animatio.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {
    private AnimationRunner runner;
    private KeyboardSensor keyboardSensor;
    private String title;
    private List<String> keys;
    private List<T> optionsVal;
    private List<String> optionsName;
    private T status;
    private boolean stop;
    private List<Boolean> isSubMenu;
    private List<Menu<T>> subMenus;

    /**
     * Instantiates a new Menu animatio.
     *
     * @param runner         the runner
     * @param keyboardSensor the keyboard sensor
     * @param title          the title
     */
    public MenuAnimation(AnimationRunner runner, KeyboardSensor keyboardSensor, String title) {
        this.runner = runner;
        this.keyboardSensor = keyboardSensor;
        this.title = title;
        this.isSubMenu = new ArrayList<Boolean>();
        this.subMenus = new ArrayList<Menu<T>>();
        keys = new ArrayList<String>();
        optionsName = new ArrayList<String>();
        optionsVal = new ArrayList<T>();
        this.stop = false;
    }

    /**
     * Add selection.
     *
     * @param key       the key
     * @param message   the message
     * @param returnVal the return val
     */
    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.optionsName.add(message);
        this.optionsVal.add(returnVal);
        this.isSubMenu.add(false);
        this.subMenus.add(null);


    }

    /**
     * Gets status.
     *
     * @return the status
     */
    @Override
    public T getStatus() {
        return this.status;
    }

    /**
     * Do one frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        new Screen().paint(d);
        int k = 0;
        d.setColor(Color.pink);
        d.drawText(50, 90, title, 50);
        for (String key : keys) {
            d.setColor(Color.pink);
            d.drawText(150, 230 + k, optionsName.get(keys.indexOf(key)), 45);
            d.drawText(600, 230 + k, key, 45);
            k = k + 70;

        }
        for (String key : keys) {
            if (keyboardSensor.isPressed(key)) {
                //this key was selected
                int i = keys.indexOf(key);
                if (!this.isSubMenu.get(i)) {
                    //not a sub menu - normal ! choose and bye bye
                    this.status = this.optionsVal.get(i);
                    this.stop = true;
                } else {
                    //sub menu run the sub menu,gets its selection and bye bye
                    Menu<T> subMenu = this.subMenus.get(i);
                    this.runner.run(subMenu);
                    this.stop = true;
                    this.status = subMenu.getStatus();
                    subMenu.goOver();
                    return;
                }

            }
        }

    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean shouldStop() {
        return this.status != null;
    }

    /**
     * Go over.
     */
    public void goOver() {
        this.stop = false;
        this.status = null;
    }

    /**
     * Add sub menu.
     *
     * @param key     the key
     * @param message the message
     * @param subMenu the sub menu
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.keys.add(key);
        this.optionsName.add(message);
        this.optionsVal.add(null);
        this.isSubMenu.add(true);
        this.subMenus.add(subMenu);
    }
}


package gamehelp;

/**
 * The type Counter.
 */
public class Counter {

    private int count;

    /**
     * Instantiates a new Counter.
     *
     * @param size the size
     */
    public Counter(int size) {
        count = size;
    }

    /**
     * Increase.
     *
     * @param number the number
     */
// add number to current count.
    public void increase(int number) {
        count = count + number;
    }

    /**
     * Decrease.
     *
     * @param number the number
     */
// subtract number from current count.
    public void decrease(int number) {
        count = count - number;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
// get current count.
    public int getValue() {
        return count;
    }
}


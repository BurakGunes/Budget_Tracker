

/**
 *
 * @author Burak
 */
public class UsernameNotAvailableException extends Exception {

    /**
     * Creates a new instance of <code>UsernameNotAvailableException</code>
     * without detail message.
     */
    public UsernameNotAvailableException() {
    }

    /**
     * Constructs an instance of <code>UsernameNotAvailableException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public UsernameNotAvailableException(String msg) {
        super(msg);
    }
}

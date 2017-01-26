
/*
 * Developed and Designed by BURAK
 * 
 */

/**
 *
 * @author Burak
 */
public class PasswordMismatchException extends Exception {

    /**
     * Creates a new instance of <code>PasswordMismatchException</code> without
     * detail message.
     */
    public PasswordMismatchException() {
    }

    /**
     * Constructs an instance of <code>PasswordMismatchException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PasswordMismatchException(String msg) {
        super(msg);
    }
}

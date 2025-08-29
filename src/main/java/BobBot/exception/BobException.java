package bobbot.exception;

/**
 * Custom exception class for BobBot-specific errors.
 * Extends the standard Exception class to provide meaningful error messages
 * in BobBot's distinctive style for various error conditions.
 */
public class BobException extends Exception {

    /**
     * Constructs a new BobException with the specified detail message.
     * The message should describe the specific error condition that occurred.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public BobException(String message) {
        super(message);
    }
}
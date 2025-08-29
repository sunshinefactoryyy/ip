package bobbot.exception;

/**
 * Represents an exception specific to BobBot application.
 * Used to handle application-specific error conditions with custom error messages.
 */
public class BobException extends Exception {

    /**
     * Creates a new BobException with the specified error message.
     *
     * @param message Error message describing the exception.
     */
    public BobException(String message) {
        super(message);
    }
}
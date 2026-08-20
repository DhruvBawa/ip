/**
 * Represents an invalid command or input that Larry can report to the user.
 */
public class LarryException extends Exception {
    private static final String ERROR_MESSAGE =
            "ERROR!! Fix your inputs Before EVIL LARRY comes after you!";

    /**
     * Creates a Larry-specific exception with the chatbot's standard error message.
     */
    public LarryException() {
        super(ERROR_MESSAGE);
    }
}

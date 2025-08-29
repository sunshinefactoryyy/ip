package bobbot.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with start and end times.
 * Supports both formatted datetime (yyyy-MM-dd HHmm) and free-form text.
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    protected LocalDateTime from;
    protected LocalDateTime to;
    protected String originalFromInput;
    protected String originalToInput;

    /**
     * Creates a new Event task with the specified description and time period.
     * Attempts to parse datetime in yyyy-MM-dd HHmm format, falls back to original string if parsing fails.
     *
     * @param desc Description of the event task.
     * @param from Start time in yyyy-MM-dd HHmm format or free-form text.
     * @param to End time in yyyy-MM-dd HHmm format or free-form text.
     */
    public Event(String desc, String from, String to) {
        super(desc);
        this.originalFromInput = from;
        this.originalToInput = to;
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
    }

    /**
     * Parses a datetime string into a LocalDateTime object.
     * Supports both input format (yyyy-MM-dd HHmm) and display format (MMM dd yyyy HHmm) for file loading.
     *
     * @param dateTimeString DateTime string to parse.
     * @return LocalDateTime object if parsing succeeds, null otherwise.
     */
    private LocalDateTime parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }
        
        try {
            // Try parsing yyyy-mm-dd HHmm format first (input format)
            return LocalDateTime.parse(dateTimeString, INPUT_FORMAT);
        } catch (DateTimeParseException e1) {
            try {
                // Try parsing display format "MMM dd yyyy HHmm" (when loading from file)
                return LocalDateTime.parse(dateTimeString, DISPLAY_FORMAT);
            } catch (DateTimeParseException e2) {
                // If both parsing attempts fail, return null and keep original string
                return null;
            }
        }
    }

    /**
     * Returns a string representation of this event task.
     * Formats datetime as "MMM dd yyyy HHmm" if parsable, otherwise uses original input.
     * The format is [E][X] description (from: start to: end) if done, or [E][ ] description (from: start to: end) if not done.
     *
     * @return String representation of this event task.
     */
    @Override
    public String toString() {
        String fromDisplay = (from != null) ? from.format(DISPLAY_FORMAT) : originalFromInput;
        String toDisplay = (to != null) ? to.format(DISPLAY_FORMAT) : originalToInput;

        return "[E]" + super.toString() + " (from: " + fromDisplay + " to: " + toDisplay + ")";

    }
}

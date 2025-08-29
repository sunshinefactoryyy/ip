package bobbot.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task with a due date.
 * Supports both formatted dates (yyyy-MM-dd) and free-form text.
 */
public class Deadline extends Task {
    protected LocalDate by;
    protected String originalInput;

    /**
     * Creates a new Deadline task with the specified description and due date.
     * Attempts to parse the date in yyyy-MM-dd format, falls back to original string if parsing fails.
     *
     * @param desc Description of the deadline task.
     * @param by Due date in yyyy-MM-dd format or free-form text.
     */
    public Deadline(String desc, String by) {
        super(desc);
        this.originalInput = by;
        this.by = parseDate(by);
    }

    /**
     * Parses a date string into a LocalDate object.
     * Supports both input format (yyyy-MM-dd) and display format (MMM dd yyyy) for file loading.
     *
     * @param date Date string to parse.
     * @return LocalDate object if parsing succeeds, null otherwise.
     */
    private LocalDate parseDate(String date) {
        try {
            // parse input format
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e1) {
            try {
                // parse when loading from file
                return LocalDate.parse(date, DateTimeFormatter.ofPattern("MMM dd yyyy"));
            } catch (DateTimeParseException e2) {
                return null;
            }
        }
    }

    /**
     * Returns a string representation of this deadline task.
     * Formats the date as "MMM dd yyyy" if parsable, otherwise uses original input.
     * The format is [D][X] description (by: date) if done, or [D][ ] description (by: date) if not done.
     *
     * @return String representation of this deadline task.
     */
    @Override
    public String toString() {
        String dateDisplay;
        if (by != null) {
            dateDisplay = by.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        } else {
            dateDisplay = originalInput;
        }
        return "[D]" + super.toString() + " (by: " + dateDisplay + ")";
    }
}

package bobbot.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    protected LocalDateTime from;
    protected LocalDateTime to;
    protected String originalFromInput;
    protected String originalToInput;

    public Event(String desc, String from, String to) {
        super(desc);
        this.originalFromInput = from;
        this.originalToInput = to;
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
    }

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

    @Override
    public String toString() {
        String fromDisplay = (from != null) ? from.format(DISPLAY_FORMAT) : originalFromInput;
        String toDisplay = (to != null) ? to.format(DISPLAY_FORMAT) : originalToInput;

        return "[E]" + super.toString() + " (from: " + fromDisplay + " to: " + toDisplay + ")";

    }
}

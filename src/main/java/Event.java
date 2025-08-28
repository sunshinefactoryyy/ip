
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
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
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        } catch (DateTimeParseException e1) {
            try {
                // Try parsing display format "MMM dd yyyy HHmm" (when loading from file)
                return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("MMM dd yyyy HHmm"));
            } catch (DateTimeParseException e2) {
                // If both parsing attempts fail, return null and keep original string
                return null;
            }
        }
    }

    @Override
    public String toString() {
        String fromDisplay;
        String toDisplay;
        
        if (from != null) {
            fromDisplay = from.format(DateTimeFormatter.ofPattern("MMM dd yyyy HHmm"));
        } else {
            // use og if parse fail
            fromDisplay = originalFromInput;
        }
        
        if (to != null) {
            toDisplay = to.format(DateTimeFormatter.ofPattern("MMM dd yyyy HHmm"));
        } else {
            // use og if parse fail
            toDisplay = originalToInput;
        }

        return "[E]" + super.toString() + " (from: " + fromDisplay + " to: " + toDisplay + ")";
    }
}

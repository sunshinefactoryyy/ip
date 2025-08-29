package bobbot.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    
    protected LocalDate by;
    protected String originalInput;

    public Deadline(String desc, String by) {
        super(desc);
        this.originalInput = by;
        this.by = parseDate(by);
    }

    private LocalDate parseDate(String date) {
        try {
            // parse input format
            return LocalDate.parse(date, INPUT_FORMAT);
        } catch (DateTimeParseException e1) {
            try {
                // parse when loading from file
                return LocalDate.parse(date, DISPLAY_FORMAT);
            } catch (DateTimeParseException e2) {
                return null;
            }
        }
    }

    @Override
    public String toString() {
        String dateDisplay = (by != null) ? by.format(DISPLAY_FORMAT) : originalInput;
        return "[D]" + super.toString() + " (by: " + dateDisplay + ")";
    }
}

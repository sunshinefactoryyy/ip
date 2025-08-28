import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
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

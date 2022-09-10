package duke;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Parses Date from String
 */
public class DateParser {
    /**
     * Takes the date string and parses it into {@link LocalDate} object
     * @param date
     * @return {@link LocalDate} object for the date string
     */
    public static LocalDate parseDate(String date) {
        assert !Objects.equals(date, "") : "Date cannot be null";
        LocalDate parsedDate = null;
        try {
            parsedDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }
}

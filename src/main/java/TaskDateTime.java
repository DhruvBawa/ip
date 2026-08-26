import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents date and time information attached to a task.
 * Parses task dates from Larry's command format and presents them in a
 * user-friendly format.
 */
public final class TaskDateTime {
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm", Locale.ENGLISH)
                    .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM d uuuu, h:mm a", Locale.ENGLISH);
    private static final DateTimeFormatter STORAGE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final String sourceText;
    private final Optional<LocalDateTime> value;

    /**
     * Creates a date-time value from Larry's command format.
     *
     * @param input Date and time in {@code yyyy-MM-dd HHmm} format.
     * @throws DateTimeParseException If the input is not a valid date and time.
     */
    public TaskDateTime(String input) {
        Objects.requireNonNull(input, "input");
        LocalDateTime parsedValue = LocalDateTime.parse(input, INPUT_FORMATTER);
        this.sourceText = input;
        this.value = Optional.of(parsedValue);
    }

    /**
     * Creates a date-time value backed by a parsed {@link LocalDateTime}.
     *
     * @param value Parsed date and time.
     */
    public TaskDateTime(LocalDateTime value) {
        LocalDateTime nonNullValue = Objects.requireNonNull(value, "value");
        this.sourceText = nonNullValue.format(STORAGE_FORMATTER);
        this.value = Optional.of(nonNullValue);
    }

    /**
     * Creates a task date and time with a specified parsed state.
     *
     * @param sourceText Original saved text.
     * @param value Parsed value, if the saved text uses the current format.
     */
    private TaskDateTime(String sourceText, Optional<LocalDateTime> value) {
        this.sourceText = sourceText;
        this.value = value;
    }

    /**
     * Creates a date-time value from saved text.
     * Current ISO values are parsed, while legacy free-form values are retained
     * so that tasks saved by an earlier Larry version are not lost.
     *
     * @param storedValue Saved date and time text.
     * @return Parsed task date and time, or a legacy display-only value.
     */
    public static TaskDateTime fromStorageString(String storedValue) {
        Objects.requireNonNull(storedValue, "storedValue");
        try {
            return new TaskDateTime(LocalDateTime.parse(storedValue, STORAGE_FORMATTER));
        } catch (DateTimeParseException e) {
            return new TaskDateTime(storedValue, Optional.empty());
        }
    }

    /**
     * Returns the parsed date and time when the value uses the current format.
     *
     * @return Parsed date and time, or an empty value for legacy saved text.
     */
    public Optional<LocalDateTime> getValue() {
        return value;
    }

    /**
     * Returns the stable representation used in the task data file.
     *
     * @return Date and time text suitable for storage.
     */
    public String toStorageString() {
        return value.map(dateTime -> dateTime.format(STORAGE_FORMATTER))
                .orElse(sourceText);
    }

    /**
     * Returns a user-friendly date and time.
     *
     * @return Date and time in a format such as {@code Dec 2 2019, 6:00 PM}.
     */
    @Override
    public String toString() {
        return value.map(dateTime -> dateTime.format(DISPLAY_FORMATTER))
                .orElse(sourceText);
    }
}

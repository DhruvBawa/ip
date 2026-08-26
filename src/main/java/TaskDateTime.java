import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents date and time information attached to a task.
 * A value can retain legacy text until date parsing is enabled, or contain a
 * parsed {@link LocalDateTime} supplied by future parsing code.
 */
public final class TaskDateTime {
    private final String sourceText;
    private final Optional<LocalDateTime> value;

    /**
     * Creates an unparsed date-time value while preserving its original text.
     *
     * @param sourceText Original date and time text.
     * @throws IllegalArgumentException If the text is blank.
     */
    public TaskDateTime(String sourceText) {
        Objects.requireNonNull(sourceText, "sourceText");
        if (sourceText.isBlank()) {
            throw new IllegalArgumentException("Date and time text cannot be blank.");
        }
        this.sourceText = sourceText;
        this.value = Optional.empty();
    }

    /**
     * Creates a date-time value backed by a parsed {@link LocalDateTime}.
     *
     * @param value Parsed date and time.
     */
    public TaskDateTime(LocalDateTime value) {
        LocalDateTime nonNullValue = Objects.requireNonNull(value, "value");
        this.sourceText = nonNullValue.toString();
        this.value = Optional.of(nonNullValue);
    }

    /**
     * Returns the parsed value when date parsing has been performed.
     *
     * @return Parsed date and time, or an empty value for legacy text.
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
        return sourceText;
    }

    /**
     * Returns the current user-facing date and time text.
     *
     * @return Original text or the ISO representation of a parsed value.
     */
    @Override
    public String toString() {
        return sourceText;
    }
}

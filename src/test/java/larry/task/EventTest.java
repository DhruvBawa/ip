package larry.task;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Tests date matching for events.
 */
class EventTest {
    @Test
    void occursOn_multiDayEvent_startMiddleAndEndDatesTrue() {
        Event event = new Event("conference",
                LocalDateTime.of(2026, 9, 5, 9, 0),
                LocalDateTime.of(2026, 9, 7, 17, 0));

        assertAll(
                () -> assertTrue(event.occursOn(LocalDate.of(2026, 9, 5))),
                () -> assertTrue(event.occursOn(LocalDate.of(2026, 9, 6))),
                () -> assertTrue(event.occursOn(LocalDate.of(2026, 9, 7)))
        );
    }

    @Test
    void occursOn_dateOutsideEventRange_false() {
        Event event = new Event("conference",
                LocalDateTime.of(2026, 9, 5, 9, 0),
                LocalDateTime.of(2026, 9, 7, 17, 0));

        assertAll(
                () -> assertFalse(event.occursOn(LocalDate.of(2026, 9, 4))),
                () -> assertFalse(event.occursOn(LocalDate.of(2026, 9, 8)))
        );
    }

    @Test
    void occursOn_singleDayEvent_onlyEventDateTrue() {
        Event event = new Event("meeting",
                LocalDateTime.of(2026, 9, 6, 9, 0),
                LocalDateTime.of(2026, 9, 6, 10, 0));

        assertAll(
                () -> assertFalse(event.occursOn(LocalDate.of(2026, 9, 5))),
                () -> assertTrue(event.occursOn(LocalDate.of(2026, 9, 6))),
                () -> assertFalse(event.occursOn(LocalDate.of(2026, 9, 7)))
        );
    }

    @Test
    void occursOn_legacyStartOrEndDate_false() {
        TaskDateTime parsedDateTime = new TaskDateTime(LocalDateTime.of(2026, 9, 6, 9, 0));
        TaskDateTime legacyDateTime = TaskDateTime.fromStorageString("Sunday morning");
        Event legacyStartEvent = new Event("meeting", legacyDateTime, parsedDateTime);
        Event legacyEndEvent = new Event("meeting", parsedDateTime, legacyDateTime);

        assertAll(
                () -> assertFalse(legacyStartEvent.occursOn(LocalDate.of(2026, 9, 6))),
                () -> assertFalse(legacyEndEvent.occursOn(LocalDate.of(2026, 9, 6)))
        );
    }

    @Test
    void occursOn_nullDate_nullPointerExceptionThrown() {
        Event event = new Event("meeting",
                LocalDateTime.of(2026, 9, 6, 9, 0),
                LocalDateTime.of(2026, 9, 6, 10, 0));

        assertThrows(NullPointerException.class, () -> event.occursOn(null));
    }
}

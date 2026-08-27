package larry.parser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import larry.command.AddCommand;
import larry.command.DateQueryCommand;
import larry.command.DeleteCommand;
import larry.command.ExitCommand;
import larry.command.FindCommand;
import larry.command.ListCommand;
import larry.command.MarkCommand;
import larry.command.UnmarkCommand;
import larry.exception.LarryException;

/**
 * Tests conversion of user input into Larry commands.
 */
class ParserTest {
    @Test
    void parseCommand_exitAndListCommands_correctCommandReturned() throws LarryException {
        assertAll(
                () -> assertInstanceOf(ExitCommand.class, Parser.parseCommand("bye")),
                () -> assertInstanceOf(ListCommand.class, Parser.parseCommand("list"))
        );
    }

    @Test
    void parseCommand_validTaskCommands_addCommandReturned() throws LarryException {
        assertAll(
                () -> assertInstanceOf(AddCommand.class,
                        Parser.parseCommand("todo read book")),
                () -> assertInstanceOf(AddCommand.class,
                        Parser.parseCommand("deadline return book /by 6-6-2026 1800")),
                () -> assertInstanceOf(AddCommand.class,
                        Parser.parseCommand("event meeting /from 6-8-2026 1400 /to 6-8-2026 1600"))
        );
    }

    @Test
    void parseCommand_validTaskActionCommands_correctCommandReturned() throws LarryException {
        assertAll(
                () -> assertInstanceOf(MarkCommand.class, Parser.parseCommand("mark 1")),
                () -> assertInstanceOf(UnmarkCommand.class, Parser.parseCommand("unmark 2")),
                () -> assertInstanceOf(DeleteCommand.class, Parser.parseCommand("delete 3"))
        );
    }

    @Test
    void parseCommand_validDateQuery_dateQueryCommandReturned() throws LarryException {
        assertInstanceOf(DateQueryCommand.class, Parser.parseCommand("on 6-9-2026"));
    }

    @Test
    void parseCommand_validFindQuery_findCommandReturned() throws LarryException {
        assertInstanceOf(FindCommand.class, Parser.parseCommand("find book"));
    }

    @Test
    void parseCommand_unknownOrNearMatchCommand_exceptionThrown() {
        assertAll(
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("unknown")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("Bye")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("list tasks")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("finder book")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("todoList task"))
        );
    }

    @Test
    void parseCommand_missingArgument_exceptionThrown() {
        assertAll(
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("todo")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("deadline")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("event")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("on")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("find")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("mark")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("unmark")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("delete"))
        );
    }

    @Test
    void parseCommand_invalidTaskIndex_exceptionThrown() {
        assertAll(
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("mark zero")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("mark 0")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("unmark -1")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("delete 1.5")),
                () -> assertThrows(LarryException.class, () -> Parser.parseCommand("delete 1 extra"))
        );
    }

    @Test
    void parseCommand_malformedDeadline_exceptionThrown() {
        assertAll(
                () -> assertThrows(LarryException.class,
                        () -> Parser.parseCommand("deadline return book")),
                () -> assertThrows(LarryException.class,
                        () -> Parser.parseCommand("deadline /by 6-6-2026 1800")),
                () -> assertThrows(LarryException.class,
                        () -> Parser.parseCommand("deadline return book /by")),
                () -> assertThrows(LarryException.class,
                        () -> Parser.parseCommand("deadline return book /by 31-2-2026 1800"))
        );
    }

    @Test
    void parseCommand_malformedEvent_exceptionThrown() {
        assertAll(
                () -> assertThrows(LarryException.class,
                        () -> Parser.parseCommand("event meeting")),
                () -> assertThrows(LarryException.class,
                        () -> Parser.parseCommand("event /from 6-8-2026 1400 /to 6-8-2026 1600")),
                () -> assertThrows(LarryException.class,
                        () -> Parser.parseCommand("event meeting /from 6-8-2026 1400")),
                () -> assertThrows(LarryException.class,
                        () -> Parser.parseCommand("event meeting /to 6-8-2026 1600")),
                () -> assertThrows(LarryException.class,
                        () -> Parser.parseCommand("event meeting /from 25:00 /to 6-8-2026 1600"))
        );
    }

    @Test
    void parseCommand_nullInput_nullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Parser.parseCommand(null));
    }
}

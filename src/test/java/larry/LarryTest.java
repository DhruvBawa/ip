package larry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests the command-response bridge shared with Larry's graphical interface.
 */
class LarryTest {
    @TempDir
    private Path temporaryDirectory;

    @Test
    void getResponse_validTaskCommands_stateAndCommandTypeUpdated() {
        Larry larry = new Larry(temporaryDirectory.resolve("tasks.txt"));

        String addResponse = larry.getResponse("todo read book");
        assertTrue(addResponse.contains("EVIL LARRY has added this task for you:"));
        assertTrue(addResponse.contains("[T][ ] read book"));
        assertEquals("AddCommand", larry.getCommandType());

        String markResponse = larry.getResponse("mark 1");
        assertTrue(markResponse.contains("[T][X] read book"));
        assertEquals("MarkCommand", larry.getCommandType());

        String listResponse = larry.getResponse("list");
        assertTrue(listResponse.contains("1.[T][X] read book"));
        assertEquals("ListCommand", larry.getCommandType());
    }

    @Test
    void getResponse_invalidCommand_errorResponseAndTypeReturned() {
        Larry larry = new Larry(temporaryDirectory.resolve("tasks.txt"));

        String response = larry.getResponse("not a command");

        assertTrue(response.contains("ERROR!! Fix your inputs Before EVIL LARRY comes after you!"));
        assertEquals("Error", larry.getCommandType());
    }

    @Test
    void getResponse_taskAdded_newLarryInstanceLoadsSavedTask() {
        Path dataFile = temporaryDirectory.resolve("tasks.txt");
        Larry originalLarry = new Larry(dataFile);
        originalLarry.getResponse("todo persist this task");

        Larry reloadedLarry = new Larry(dataFile);
        String response = reloadedLarry.getResponse("list");

        assertTrue(response.contains("1.[T][ ] persist this task"));
    }
}

import java.util.Scanner;

/**
 * Runs the Larry chatbot application.
 */
public class Larry {
    private static final String SEPARATOR = "    __________________________________________________________";
    private static final int MAX_TASKS = 100;

    /**
     * Starts Larry and responds to commands until the user enters {@code bye}.
     *
     * @param args command-line arguments; not used
     */
    public static void main(String[] args) {
        System.out.println(SEPARATOR + "\n");

        String banner = "██╗      █████╗ ██████╗ ██████╗ ██╗   ██╗      ██╗                   ╔██╗\n"
                      + "██║     ██╔══██╗██╔══██╗██╔══██╗╚██╗ ██╔╝      ████╗               ╔████║\n"
                      + "██║     ███████║██████╔╝██████╔╝ ╚████╔╝       █████████████████████████║\n"
                      + "██║     ██╔══██║██╔══██╗██╔══██╗  ╚██╔╝        ██║    ██        ██    ██║\n"
                      + "███████╗██║  ██║██║  ██║██║  ██║   ██║         ██║         ▲          ██║\n"
                      + "╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝         ██║       ╚═══╝        ██║\n"
                      + "                                               ╚════════════════════════╝\n";
        System.out.println(banner);

        System.out.println("Hello! I'm Larry.\nWhat can I do for you?");
        System.out.println(SEPARATOR);

        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(SEPARATOR);

            if (command.equals("bye")) {
                System.out.println("     Bye. Hope to see you again soon!");
                System.out.println(SEPARATOR);
                break;
            }

            if (command.equals("list")) {
                System.out.println("     Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("     " + (i + 1) + "." + tasks[i]);
                }
            } else if (command.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(command.substring(5)) - 1;
                tasks[taskIndex].markAsDone();
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       " + tasks[taskIndex]);
            } else if (command.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(command.substring(7)) - 1;
                tasks[taskIndex].markAsNotDone();
                System.out.println("     OK, I've marked this task as not done yet:");
                System.out.println("       " + tasks[taskIndex]);
            } else {
                Task newTask;
                if (command.startsWith("todo ")) {
                    String description = command.substring(5);
                    newTask = new Todo(description);
                } else if (command.startsWith("deadline ")) {
                    String[] parts = command.substring(9).split(" /by ", 2);
                    newTask = new Deadline(parts[0], parts[1]);
                } else if (command.startsWith("event ")) {
                    String[] descriptionAndTimes = command.substring(6).split(" /from ", 2);
                    String[] startAndEnd = descriptionAndTimes[1].split(" /to ", 2);
                    newTask = new Event(descriptionAndTimes[0], startAndEnd[0], startAndEnd[1]);
                } else {
                    newTask = new Task(command);
                }

                tasks[taskCount] = newTask;
                taskCount++;
                System.out.println("     Got it. I've added this task:");
                System.out.println("       " + newTask);
                String taskWord = taskCount == 1 ? "task" : "tasks";
                System.out.println("     Now you have " + taskCount + " " + taskWord + " in the list.");
            }

            System.out.println(SEPARATOR);
        }
        scanner.close();
    }
}

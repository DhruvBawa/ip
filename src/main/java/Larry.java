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
     * @param args Command-line arguments; not used.
     */
    public static void main(String[] args) {
        System.out.println(SEPARATOR + "\n");

        String banner = "██╗      █████╗ ██████╗ ██████╗ ██╗   ██╗\n"
                + "██║     ██╔══██╗██╔══██╗██╔══██╗╚██╗ ██╔╝\n"
                + "██║     ███████║██████╔╝██████╔╝ ╚████╔╝ \n"
                + "██║     ██╔══██║██╔══██╗██╔══██╗  ╚██╔╝  \n"
                + "███████╗██║  ██║██║  ██║██║  ██║   ██║   \n"
                + "╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   \n";
        System.out.println(banner);

        System.out.println("Hello! I'm Larry.\nWhat can I do for you?");
        System.out.println(SEPARATOR);

        String[] tasks = new String[MAX_TASKS];
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
                    System.out.println("     " + (i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println("     added: " + command);
            }

            System.out.println(SEPARATOR);
        }
        scanner.close();
    }
}

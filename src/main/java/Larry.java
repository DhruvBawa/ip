import java.util.Scanner;

/**
 * Runs the Larry chatbot application.
 */
public class Larry {
    private static final String SEPARATOR = "    __________________________________________________________";

    /**
     * Starts Larry and echoes commands until the user enters {@code bye}.
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

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(SEPARATOR);

            if (command.equals("bye")) {
                System.out.println("     Bye. Hope to see you again soon!");
                System.out.println(SEPARATOR);
                break;
            }

            System.out.println("     " + command);
            System.out.println(SEPARATOR);
        }
        scanner.close();
    }
}

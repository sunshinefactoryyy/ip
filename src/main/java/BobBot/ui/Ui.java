package bobbot.ui;
import java.util.Scanner;

import bobbot.task.Task;
import bobbot.tasklist.TaskList;

/**
 * Handles all user interface operations for BobBot.
 * Manages input/output interactions, displays messages, and formats output
 * in a consistent style with BobBot's personality.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Constructs a new Ui instance.
     * Initializes the Scanner for reading user input from System.in.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message and BobBot banner.
     * Shows the ASCII art banner and greeting message when the application starts.
     */
    public void showWelcome() {
        String BANNER =
            "██████╗  ██████╗ ██████╗ ██████╗  ██████╗ ████████╗\n" +
            "██╔══██╗██╔═══██╗██╔══██╗██╔══██╗██╔═══██╗╚══██╔══╝\n" +
            "██████╔╝██║   ██║██████╔╝██████╔╝██║   ██║   ██║   \n" +
            "██╔══██╗██║   ██║██╔══██╗██╔══██╗██║   ██║   ██║   \n" +
            "██████╔╝╚██████╔╝██████╔╝██████╔╝╚██████╔╝   ██║   \n" +
            "╚═════╝  ╚═════╝ ╚═════╝ ╚═════╝  ╚═════╝    ╚═╝   \n";

        System.out.println(BANNER);
        showLine();
        System.out.println("Hello! I'm BobBot");
        System.out.println("What can I do for you bobz?");
        showLine();
    }

    /**
     * Displays the goodbye message when the user exits the application.
     */
    public void showBye() {
        System.out.println("Bye bobz. Hope to see you again soon bobz!");
    }

    /**
     * Displays a horizontal line separator for visual formatting.
     * Used to separate different sections of output for better readability.
     */
    public void showLine() {
        System.out.println("--------------------------------------------------");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays a confirmation message when a task is successfully added.
     * Shows the added task and the updated total count of tasks.
     *
     * @param task the Task that was added
     * @param totalTasks the total number of tasks in the list after addition
     */
    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println("Got it bobz. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list bobz.");
    }

    /**
     * Displays a confirmation message when a task is marked as completed.
     * Shows the task that was marked with appropriate formatting.
     *
     * @param task the Task that was marked as done
     */
    public void showTaskMarked(Task task) {
        showLine();
        System.out.println("Nice bobz! I've marked this task as done bobz:");
        System.out.println("  " + task);
        showLine();
    }

    /**
     * Displays a confirmation message when a task is marked as not completed.
     * Shows the task that was unmarked with appropriate formatting.
     *
     * @param task the Task that was marked as not done
     */
    public void showTaskUnmarked(Task task) {
        showLine();
        System.out.println("OK bobz, I've marked this task as not done yet bobz:");
        System.out.println("  " + task);
        showLine();
    }

    /**
     * Displays a confirmation message when a task is deleted.
     * Shows the deleted task and the updated total count of remaining tasks.
     *
     * @param task the Task that was deleted
     * @param totalTasks the total number of tasks remaining in the list after deletion
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.println("Noted bobz. I've removed this task bobz:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list bobz.");
    }

    /**
     * Displays the complete list of tasks to the user.
     * Shows each task with its index number, or a message if the list is empty.
     *
     * @param tasks the TaskList containing all tasks to display
     */
    public void showTaskList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No items in the list bobz.");
        } else {
            System.out.println("Here are the items in your list bobz:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    /**
     * Reads a command from user input.
     * Waits for the user to enter a line of text and returns it.
     *
     * @return the user's input as a String
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Closes the Scanner and releases system resources.
     * Should be called when the application is shutting down.
     */
    public void close() {
        scanner.close();
    }
}
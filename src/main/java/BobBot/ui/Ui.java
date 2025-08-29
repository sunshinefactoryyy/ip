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

    private static final String BANNER =
        "██████╗  ██████╗ ██████╗ ██████╗  ██████╗ ████████╗\n" +
        "██╔══██╗██╔═══██╗██╔══██╗██╔══██╗██╔═══██╗╚══██╔══╝\n" +
        "██████╔╝██║   ██║██████╔╝██████╔╝██║   ██║   ██║   \n" +
        "██╔══██╗██║   ██║██╔══██╗██╔══██╗██║   ██║   ██║   \n" +
        "██████╔╝╚██████╔╝██████╔╝██████╔╝╚██████╔╝   ██║   \n" +
        "╚═════╝  ╚═════╝ ╚═════╝ ╚═════╝  ╚═════╝    ╚═╝   \n";
    
    private static final String LINE_SEPARATOR = "--------------------------------------------------";
    private static final String GREETING_MESSAGE = "Hello! I'm BobBot";
    private static final String PROMPT_MESSAGE = "What can I do for you bobz?";
    private static final String GOODBYE_MESSAGE = "Bye bobz. Hope to see you again soon bobz!";
    private static final String TASK_ADDED_MESSAGE = "Got it bobz. I've added this task:";
    private static final String TASK_COUNT_MESSAGE = "Now you have %d tasks in the list bobz.";
    private static final String TASK_MARKED_MESSAGE = "Nice bobz! I've marked this task as done bobz:";
    private static final String TASK_UNMARKED_MESSAGE = "OK bobz, I've marked this task as not done yet bobz:";
    private static final String TASK_DELETED_MESSAGE = "Noted bobz. I've removed this task bobz:";
    private static final String EMPTY_LIST_MESSAGE = "No items in the list bobz.";
    private static final String LIST_HEADER_MESSAGE = "Here are the items in your list bobz:";

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
        System.out.println(BANNER);
        showLine();
        System.out.println(GREETING_MESSAGE);
        System.out.println(PROMPT_MESSAGE);
        showLine();
    }

    /**
     * Displays the goodbye message when the user exits the application.
     */
    public void showBye() {
        System.out.println(GOODBYE_MESSAGE);
    }

    /**
     * Displays a horizontal line separator for visual formatting.
     * Used to separate different sections of output for better readability.
     */
    public void showLine() {
        System.out.println(LINE_SEPARATOR);
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
        System.out.println(TASK_ADDED_MESSAGE);
        printTaskWithIndent(task);
        System.out.println(String.format(TASK_COUNT_MESSAGE, totalTasks));
    }

    /**
     * Displays a confirmation message when a task is marked as completed.
     * Shows the task that was marked with appropriate formatting.
     *
     * @param task the Task that was marked as done
     */
    public void showTaskMarked(Task task) {
        showLine();
        System.out.println(TASK_MARKED_MESSAGE);
        printTaskWithIndent(task);
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
        System.out.println(TASK_UNMARKED_MESSAGE);
        printTaskWithIndent(task);
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
        System.out.println(TASK_DELETED_MESSAGE);
        printTaskWithIndent(task);
        System.out.println(String.format(TASK_COUNT_MESSAGE, totalTasks));
    }

    /**
     * Displays the complete list of tasks to the user.
     * Shows each task with its index number, or a message if the list is empty.
     *
     * @param tasks the TaskList containing all tasks to display
     */
    public void showTaskList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println(EMPTY_LIST_MESSAGE);
        } else {
            System.out.println(LIST_HEADER_MESSAGE);
            printNumberedTaskList(tasks);
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

    private void printTaskWithIndent(Task task) {
        System.out.println("  " + task);
    }

    private void printNumberedTaskList(TaskList tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
}
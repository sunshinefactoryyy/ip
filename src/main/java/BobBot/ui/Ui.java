package bobbot.ui;
import java.util.Scanner;

import bobbot.task.Task;
import bobbot.tasklist.TaskList;

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

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(BANNER);
        showLine();
        System.out.println(GREETING_MESSAGE);
        System.out.println(PROMPT_MESSAGE);
        showLine();
    }

    public void showBye() {
        System.out.println(GOODBYE_MESSAGE);
    }

    public void showLine() {
        System.out.println(LINE_SEPARATOR);
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println(TASK_ADDED_MESSAGE);
        printTaskWithIndent(task);
        System.out.println(String.format(TASK_COUNT_MESSAGE, totalTasks));
    }

    public void showTaskMarked(Task task) {
        showLine();
        System.out.println(TASK_MARKED_MESSAGE);
        printTaskWithIndent(task);
        showLine();
    }

    public void showTaskUnmarked(Task task) {
        showLine();
        System.out.println(TASK_UNMARKED_MESSAGE);
        printTaskWithIndent(task);
        showLine();
    }

    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.println(TASK_DELETED_MESSAGE);
        printTaskWithIndent(task);
        System.out.println(String.format(TASK_COUNT_MESSAGE, totalTasks));
    }

    public void showTaskList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println(EMPTY_LIST_MESSAGE);
        } else {
            System.out.println(LIST_HEADER_MESSAGE);
            printNumberedTaskList(tasks);
        }
    }

    public String readCommand() {
        return scanner.nextLine();
    }

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
import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

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

    public void showBye() {
        System.out.println("Bye bobz. Hope to see you again soon bobz!");
    }

    public void showLine() {
        System.out.println("--------------------------------------------------");
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println("Got it bobz. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list bobz.");
    }

    public void showTaskMarked(Task task) {
        showLine();
        System.out.println("Nice bobz! I've marked this task as done bobz:");
        System.out.println("  " + task);
        showLine();
    }

    public void showTaskUnmarked(Task task) {
        showLine();
        System.out.println("OK bobz, I've marked this task as not done yet bobz:");
        System.out.println("  " + task);
        showLine();
    }

    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.println("Noted bobz. I've removed this task bobz:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list bobz.");
    }

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

    public String readCommand() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}
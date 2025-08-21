import java.util.ArrayList;
import java.util.Scanner;

public class BobBot {
    public static void main(String[] args) {
        String BOT_NAME = "BobBot";
        String BANNER =
            "██████╗  ██████╗ ██████╗ ██████╗  ██████╗ ████████╗\n" +
            "██╔══██╗██╔═══██╗██╔══██╗██╔══██╗██╔═══██╗╚══██╔══╝\n" +
            "██████╔╝██║   ██║██████╔╝██████╔╝██║   ██║   ██║   \n" +
            "██╔══██╗██║   ██║██╔══██╗██╔══██╗██║   ██║   ██║   \n" +
            "██████╔╝╚██████╔╝██████╔╝██████╔╝╚██████╔╝   ██║   \n" +
            "╚═════╝  ╚═════╝ ╚═════╝ ╚═════╝  ╚═════╝    ╚═╝   \n"
            ;

        System.out.println(BANNER);
        printSeparator();
        System.out.println("Hello! I'm " + BOT_NAME);
        System.out.println("What can I do for you bobz?");
        printSeparator();

        boolean start = true;
        ArrayList<Task> tasks = new ArrayList<>();
        while (start) {
            Scanner userInput = new Scanner(System.in);
            String inputString = userInput.nextLine();
            if (inputString.equals("bye")) {
                start = false;
                System.out.println("Bye bobz. Hope to see you again soon bobz!");
            } else if (inputString.equals("list")) {
                 if (tasks.isEmpty()) {
                        System.out.println("No items in the list bobz.");
                    } else {
                        System.out.println("Here are the items in your list bobz:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                    }   
                
                printSeparator();
            } else if (inputString.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(inputString.split(" ")[1]) - 1;
                Task task = tasks.get(taskIndex);
                task.markAsDone();
                printSeparator();
                System.out.println("Nice bobz! I've marked this task as done bobz:");
                System.out.println("  " + task);
                printSeparator();
            } else if (inputString.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(inputString.split(" ")[1]) - 1;
                Task task = tasks.get(taskIndex);
                task.markAsNotDone();
                printSeparator();
                System.out.println("OK bobz, I've marked this task as not done yet bobz:");
                System.out.println("  " + task);
                printSeparator();
            } else {
                tasks.add(new Task(inputString));
                System.out.println("Added: " + inputString);
                printSeparator();
            }
        }
    }

    private static void printSeparator() {
        System.out.println("--------------------------------------------------");
    }
}

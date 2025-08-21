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
        Scanner userInput = new Scanner(System.in);

        while (start) {
            
            String inputString = userInput.nextLine();
            
            try {
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

                } else if (inputString.startsWith("todo ")) {
                    String desc = inputString.substring(5).trim();
                    if (desc.isEmpty()) {
                        throw new BobException("BOBZ!!! The description of a todo cannot be empty bobz.");
                    }
                    Task task = new Todo(desc);
                    tasks.add(task);
                    System.out.println("Got it bobz. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list bobz.");

                } else if (inputString.startsWith("deadline ")) {
                    String[] parts = inputString.substring(9).split(" /by ");
                    if (parts.length == 2) {
                        Task task = new Deadline(parts[0].trim(), parts[1].trim());
                        tasks.add(task);
                        System.out.println("Got it bobz. I've added this task:");
                        System.out.println("  " + task);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list bobz.");
                    } else {
                        System.out.println("BOBZ!!! Invalid format for deadline bobz. Try: deadline <desc> /by <time>");
                    }

                } else if (inputString.startsWith("event ")) {
                    String[] parts = inputString.substring(6).split(" /from | /to ");
                    if (parts.length == 3) {
                        Task task = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
                        tasks.add(task);
                        System.out.println("Got it bobz. I've added this task:");
                        System.out.println("  " + task);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list bobz.");
                    } else {
                        System.out.println("BOBZ!!! Invalid format for event bobz. Try: event <desc> /from <start> /to <end>");
                    }

                } else if (inputString.startsWith("delete ")) {
                    int index = Integer.parseInt(inputString.split(" ")[1]) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new BobException("BOBZ!!! That task number does not exist.");
                    }
                    Task removedTask = tasks.remove(index);
                    System.out.println("Noted bobz. I've removed this task bobz:");
                    System.out.println("  " + removedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list bobz.");

                } else {
                    throw new BobException("BOBZ!!! what are you saying bobz. Only use 'todo', 'deadline', 'event' and 'delete' bobz.");
                    
                }
                printSeparator();
            } catch (BobException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("BOBZ!!!Something went wrong, please check your command format bobz.");
            }
        }
        userInput.close(); // resourse leak prevention
    }

    private static void printSeparator() {
        System.out.println("--------------------------------------------------");
    }
}

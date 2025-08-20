import java.util.ArrayList;
import java.util.Scanner;

public class BobBot {
    public static void main(String[] args) {
        String BOT_NAME = "BobBot";
        String BANNER =
            " ██████   ██████   ██████   ██████   ██████   █████████\n" +
            "██    ██ ██    ██ ██    ██ ██    ██ ██    ██     ██    \n" +
            "██    ██ ██    ██ ██    ██ ██    ██ ██    ██     ██    \n" +
            "███████  ██    ██ ███████  ███████  ██    ██     ██    \n" +
            "██    ██ ██    ██ ██    ██ ██    ██ ██    ██     ██    \n" +
            "██    ██ ██    ██ ██    ██ ██    ██ ██    ██     ██    \n" +
            " ███████   ██████  ██████   ██████   ██████      ██    \n" +
            "\n" +
            "        [  (•_•)   B O B B O T   (•_•)  ]\n" +
            "             Your friendly chatbot bobz\n";
        System.out.println(BANNER);
        printSeparator();
        System.out.println("Hello! I'm " + BOT_NAME);
        System.out.println("What can I do for you bobz?");
        printSeparator();

        boolean start = true;
        ArrayList<String> items = new ArrayList<>();
        while (start) {
            Scanner userInput = new Scanner(System.in);
            String inputString = userInput.nextLine();
            if (inputString.equals("bye")) {
                start = false;
                System.out.println("Bye bobz. Hope to see you again soon bobz!");
            } else {
                // System.out.println(inputString); no need to echo anymore
                if (inputString.equals("list")) {
                    printSeparator();
                    if (items.isEmpty()) {
                        System.out.println("No items in the list bobz.");
                    } else {
                        System.out.println("Here are the items in your list bobz:");
                        for (int i = 0; i < items.size(); i++) {
                            System.out.println((i + 1) + ". " + items.get(i));
                        }
                    }
                } else {
                    items.add(inputString);
                    System.out.println("Added: " + inputString);
                }
                printSeparator();
                System.err.println("\n");
            }
        }
    }

    private static void printSeparator() {
        System.out.println("--------------------------------------------------");
    }
}

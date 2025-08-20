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
            "             Your friendly chatbot\n";
        System.out.println(BANNER);
        System.out.println("Hello! I'm " + BOT_NAME);
        System.out.println("What can I do for you bobz?");

        boolean start = true;
        while (start) {
            Scanner userInput = new Scanner(System.in);
            String inputString = userInput.nextLine();
            if (inputString.equals("bye")) {
                start = false;
                System.out.println("Bye bobz. Hope to see you again soon bobz!");
            } else {System.out.println(inputString);}
        }
    }
}

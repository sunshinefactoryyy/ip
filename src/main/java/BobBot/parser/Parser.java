package BobBot.parser;

public class Parser {

    public enum CommandType {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, INVALID
    }

    public static class Command {
        private CommandType type;
        private String[] arguments;

        public Command(CommandType type, String[] arguments) {
            this.type = type;
            this.arguments = arguments;
        }

        public CommandType getType() {
            return type;
        }

        public String[] getArguments() {
            return arguments;
        }
    }

    public static Command parseCommand(String input) {
        String trimmed = input.trim();

        if (trimmed.equals("bye")) {
            return new Command(CommandType.BYE, new String[0]);
        } else if (trimmed.equals("list")) {
            return new Command(CommandType.LIST, new String[0]);
        } else if (trimmed.startsWith("mark ")) {
            String indexStr = trimmed.substring(5).trim();
            return new Command(CommandType.MARK, new String[]{indexStr});
        } else if (trimmed.startsWith("unmark ")) {
            String indexStr = trimmed.substring(7).trim();
            return new Command(CommandType.UNMARK, new String[]{indexStr});
        } else if (trimmed.startsWith("todo ")) {
            String desc = trimmed.substring(5).trim();
            return new Command(CommandType.TODO, new String[]{desc});
        } else if (trimmed.startsWith("deadline ")) {
            String[] parts = trimmed.substring(9).split(" /by ", 2);
            return new Command(CommandType.DEADLINE, parts);
        } else if (trimmed.startsWith("event ")) {
            String remaining = trimmed.substring(6);
            String[] parts = remaining.split(" /from | /to ");
            return new Command(CommandType.EVENT, parts);
        } else if (trimmed.startsWith("delete ")) {
            String indexStr = trimmed.substring(7).trim();
            return new Command(CommandType.DELETE, new String[]{indexStr});
        } else {
            return new Command(CommandType.INVALID, new String[0]);
        }
    }
}
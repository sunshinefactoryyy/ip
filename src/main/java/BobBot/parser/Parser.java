package bobbot.parser;

public class Parser {

    public enum CommandType {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, INVALID
    }

    public static class Command {
        private final CommandType type;
        private final String[] arguments;

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
            return parseMarkCommand(trimmed);
        } else if (trimmed.startsWith("unmark ")) {
            return parseUnmarkCommand(trimmed);
        } else if (trimmed.startsWith("todo ")) {
            return parseTodoCommand(trimmed);
        } else if (trimmed.startsWith("deadline ")) {
            return parseDeadlineCommand(trimmed);
        } else if (trimmed.startsWith("event ")) {
            return parseEventCommand(trimmed);
        } else if (trimmed.startsWith("delete ")) {
            return parseDeleteCommand(trimmed);
        } else {
            return new Command(CommandType.INVALID, new String[0]);
        }
    }

    private static Command parseMarkCommand(String trimmed) {
        String indexStr = trimmed.substring(5).trim();
        return new Command(CommandType.MARK, new String[]{indexStr});
    }

    private static Command parseUnmarkCommand(String trimmed) {
        String indexStr = trimmed.substring(7).trim();
        return new Command(CommandType.UNMARK, new String[]{indexStr});
    }

    private static Command parseTodoCommand(String trimmed) {
        String desc = trimmed.substring(5).trim();
        return new Command(CommandType.TODO, new String[]{desc});
    }

    private static Command parseDeadlineCommand(String trimmed) {
        String[] parts = trimmed.substring(9).split(" /by ", 2);
        return new Command(CommandType.DEADLINE, parts);
    }

    private static Command parseEventCommand(String trimmed) {
        String remaining = trimmed.substring(6);
        String[] parts = remaining.split(" /from | /to ");
        return new Command(CommandType.EVENT, parts);
    }

    private static Command parseDeleteCommand(String trimmed) {
        String indexStr = trimmed.substring(7).trim();
        return new Command(CommandType.DELETE, new String[]{indexStr});
    }
}
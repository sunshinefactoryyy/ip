package bobbot.parser;

/**
 * Parses user input commands and converts them into structured Command objects.
 * Supports various command types including task creation, modification, and deletion.
 */
public class Parser {
    private static final int MARK_COMMAND_PREFIX_LENGTH = 5;
    private static final int UNMARK_COMMAND_PREFIX_LENGTH = 7;
    private static final int TODO_COMMAND_PREFIX_LENGTH = 5;
    private static final int DEADLINE_COMMAND_PREFIX_LENGTH = 9;
    private static final int EVENT_COMMAND_PREFIX_LENGTH = 6;
    private static final int DELETE_COMMAND_PREFIX_LENGTH = 7;
    private static final int FIND_COMMAND_PREFIX_LENGTH = 5;
        /**
     * Enumeration of all supported command types in BobBot.
     * Each type corresponds to a specific user action or operation.
     */
    public enum CommandType {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, INVALID
    }

    /**
     * Represents a parsed command with its type and associated arguments.
     * Encapsulates the command type and any parameters needed for execution.
     */
    public static class Command {
        private final CommandType type;
        private final String[] arguments;

        /**
         * Constructs a new Command with the specified type and arguments.
         *
         * @param type the type of command
         * @param arguments the arguments associated with the command
         */
        public Command(CommandType type, String[] arguments) {
            this.type = type;
            this.arguments = arguments;
        }

        /**
         * Returns the type of this command.
         *
         * @return the CommandType of this command
         */
        public CommandType getType() {
            return type;
        }

        /**
         * Returns the arguments associated with this command.
         *
         * @return an array of string arguments for this command
         */
        public String[] getArguments() {
            return arguments;
        }
    }

    /**
     * Parses a user input string and returns the corresponding Command object.
     * Analyzes the input to determine command type and extracts relevant arguments.
     * 
     * <p>Supported command formats:
     * <ul>
     *   <li>bye - exit the application</li>
     *   <li>list - show all tasks</li>
     *   <li>mark &lt;index&gt; - mark task as done</li>
     *   <li>unmark &lt;index&gt; - mark task as not done</li>
     *   <li>todo &lt;description&gt; - create todo task</li>
     *   <li>deadline &lt;description&gt; /by &lt;time&gt; - create deadline task</li>
     *   <li>event &lt;description&gt; /from &lt;start&gt; /to &lt;end&gt; - create event task</li>
     *   <li>delete &lt;index&gt; - delete task</li>
     * </ul>
     *
     * @param input the user input string to parse
     * @return a Command object representing the parsed command and its arguments
     */
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
        } else if (trimmed.startsWith("find ")) {
            String keyword = trimmed.substring(FIND_COMMAND_PREFIX_LENGTH).trim();
            return new Command(CommandType.FIND, new String[]{keyword});
        }
        else if (trimmed.startsWith("delete ")) {
            return parseDeleteCommand(trimmed);
        } else {
            return new Command(CommandType.INVALID, new String[0]);
        }
    }

    private static Command parseMarkCommand(String trimmed) {
        String indexStr = trimmed.substring(MARK_COMMAND_PREFIX_LENGTH).trim();
        return new Command(CommandType.MARK, new String[]{indexStr});
    }

    private static Command parseUnmarkCommand(String trimmed) {
        String indexStr = trimmed.substring(UNMARK_COMMAND_PREFIX_LENGTH).trim();
        return new Command(CommandType.UNMARK, new String[]{indexStr});
    }

    private static Command parseTodoCommand(String trimmed) {
        String desc = trimmed.substring(TODO_COMMAND_PREFIX_LENGTH).trim();
        return new Command(CommandType.TODO, new String[]{desc});
    }

    private static Command parseDeadlineCommand(String trimmed) {
        String[] parts = trimmed.substring(DEADLINE_COMMAND_PREFIX_LENGTH).split(" /by ", 2);
        return new Command(CommandType.DEADLINE, parts);
    }

    private static Command parseEventCommand(String trimmed) {
        String remaining = trimmed.substring(EVENT_COMMAND_PREFIX_LENGTH);
        String[] parts = remaining.split(" /from | /to ");
        return new Command(CommandType.EVENT, parts);
    }

    private static Command parseDeleteCommand(String trimmed) {
        String indexStr = trimmed.substring(DELETE_COMMAND_PREFIX_LENGTH).trim();
        return new Command(CommandType.DELETE, new String[]{indexStr});
    }
}
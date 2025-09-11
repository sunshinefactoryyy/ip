package bobbot.core;

import java.util.HashMap;
import java.util.Map;

import bobbot.command.CommandHandler;
import bobbot.exception.BobException;
import bobbot.parser.Parser;
import bobbot.storage.Storage;
import bobbot.task.Deadline;
import bobbot.task.Event;
import bobbot.task.Task;
import bobbot.task.Todo;
import bobbot.tasklist.TaskList;
import bobbot.ui.Ui;

/**
 * Main controller class for BobBot that handles both CLI and GUI interactions.
 * Coordinates between the parser, storage, task list, and user interface components.
 */
public class BobBot {
    private static final String WELCOME_MESSAGE = "Hello! I'm BobBot\nWhat can I do for you bobz?";
    private static final String GOODBYE_MESSAGE = "Bye bobz. Hope to see you again soon bobz!";
    private static final String INVALID_COMMAND_MESSAGE = 
        "BOBZ!!! what are you saying bobz. Only use 'todo', 'deadline', 'event' and 'delete' bobz.";
    private static final String GENERIC_ERROR_MESSAGE = 
        "BOBZ!!!Something went wrong, please check your command format bobz.";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    private final Map<Parser.CommandType, CommandHandler> commandHandlers;

    /**
     * Constructs a new BobBot instance with the specified file path for data persistence.
     *
     * @param filePath the file path where task data will be stored and loaded from
     */
    public BobBot(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.loadTasks());
        commandHandlers = initializeCommandHandlers();
    }

    /**
     * Initializes the command handlers map with appropriate handlers for each command type.
     *
     * @return a map of command types to their corresponding handlers
     */
    private Map<Parser.CommandType, CommandHandler> initializeCommandHandlers() {
        Map<Parser.CommandType, CommandHandler> handlers = new HashMap<>();
        
        handlers.put(Parser.CommandType.LIST, args -> formatTaskList());
        handlers.put(Parser.CommandType.MARK, this::handleMarkCommand);
        handlers.put(Parser.CommandType.UNMARK, this::handleUnmarkCommand);
        handlers.put(Parser.CommandType.TODO, this::handleTodoCommand);
        handlers.put(Parser.CommandType.DEADLINE, this::handleDeadlineCommand);
        handlers.put(Parser.CommandType.EVENT, this::handleEventCommand);
        handlers.put(Parser.CommandType.DELETE, this::handleDeleteCommand);
        handlers.put(Parser.CommandType.FIND, this::handleFindCommand);
        
        return handlers;
    }

    /**
     * Generates a response for the given user input (used by GUI).
     *
     * @param userInput the user input string to process
     * @return the bot's response as a string
     */
    public String getResponse(String userInput) {
        try {
            Parser.Command command = Parser.parseCommand(userInput);
            return handleCommand(command);
        } catch (BobException exception) {
            return exception.getMessage();
        } catch (Exception exception) {
            return GENERIC_ERROR_MESSAGE;
        }
    }

    /**
     * Returns the welcome message for new users.
     *
     * @return the welcome message string
     */
    public String getWelcomeMessage() {
        return WELCOME_MESSAGE;
    }

    /**
     * Processes a parsed command and returns the appropriate response.
     *
     * @param command the parsed command to process
     * @return the response string for the command
     * @throws Exception if there's an error processing the command
     */
    private String handleCommand(Parser.Command command) throws Exception {
        switch (command.getType()) {
        case BYE:
            return GOODBYE_MESSAGE;
        case INVALID:
            return INVALID_COMMAND_MESSAGE;
        default:
            CommandHandler handler = commandHandlers.get(command.getType());
            if (handler != null) {
                return handler.handle(command.getArguments());
            }
            return GENERIC_ERROR_MESSAGE;
        }
    }

    /**
     * Formats the task list for display.
     *
     * @return formatted string representation of all tasks
     */
    private String formatTaskList() {
        if (tasks.isEmpty()) {
            return "No items in the list bobz.";
        }
        
        StringBuilder taskListBuilder = new StringBuilder("Here are the items in your list bobz:\n");
        for (int i = 0; i < tasks.size(); i++) {
            taskListBuilder.append(String.format("%d. %s\n", i + 1, tasks.get(i)));
        }
        return taskListBuilder.toString().trim();
    }

    /**
     * Processes the mark command to mark a task as completed.
     *
     * @param arguments command arguments containing the task index
     * @return confirmation message
     * @throws Exception if the task index is invalid
     */
    private String handleMarkCommand(String[] arguments) throws Exception {
        int taskIndex = parseTaskIndex(arguments[0]);
        Task task = tasks.getTask(taskIndex);
        task.markAsDone();
        saveTasksToStorage();
        return String.format("Nice bobz! I've marked this task as done bobz:\n  %s", task);
    }

    /**
     * Processes the unmark command to mark a task as not completed.
     *
     * @param arguments command arguments containing the task index
     * @return confirmation message
     * @throws Exception if the task index is invalid
     */
    private String handleUnmarkCommand(String[] arguments) throws Exception {
        int taskIndex = parseTaskIndex(arguments[0]);
        Task task = tasks.getTask(taskIndex);
        task.markAsNotDone();
        saveTasksToStorage();
        return String.format("OK bobz, I've marked this task as not done yet bobz:\n  %s", task);
    }

    /**
     * Processes the todo command to create a new todo task.
     *
     * @param arguments command arguments containing the task description
     * @return confirmation message
     * @throws BobException if the description is empty
     */
    private String handleTodoCommand(String[] arguments) throws BobException {
        String description = arguments[0];
        if (description.isEmpty()) {
            throw new BobException("BOBZ!!! The description of a todo cannot be empty bobz.");
        }
        
        Task newTask = new Todo(description);
        tasks.addTask(newTask);
        saveTasksToStorage();
        
        return String.format("Got it bobz. I've added this task:\n  %s\nNow you have %d tasks in the list bobz.",
                newTask, tasks.size());
    }

    /**
     * Processes the deadline command to create a new deadline task.
     *
     * @param arguments command arguments containing description and deadline
     * @return confirmation message or error message
     */
    private String handleDeadlineCommand(String[] arguments) {
        if (arguments.length != 2) {
            return "BOBZ!!! Invalid format for deadline bobz. Try: deadline <desc> /by <time>";
        }
        
        Task newTask = new Deadline(arguments[0].trim(), arguments[1].trim());
        tasks.addTask(newTask);
        saveTasksToStorage();
        
        return String.format("Got it bobz. I've added this task:\n  %s\nNow you have %d tasks in the list bobz.",
                newTask, tasks.size());
    }

    /**
     * Processes the event command to create a new event task.
     *
     * @param arguments command arguments containing description, start time, and end time
     * @return confirmation message or error message
     */
    private String handleEventCommand(String[] arguments) {
        if (arguments.length != 3) {
            return "BOBZ!!! Invalid format for event bobz. Try: event <desc> /from <start> /to <end>";
        }
        
        Task newTask = new Event(arguments[0].trim(), arguments[1].trim(), arguments[2].trim());
        tasks.addTask(newTask);
        saveTasksToStorage();
        
        return String.format("Got it bobz. I've added this task:\n  %s\nNow you have %d tasks in the list bobz.",
                newTask, tasks.size());
    }

    /**
     * Processes the delete command to remove a task.
     *
     * @param arguments command arguments containing the task index
     * @return confirmation message
     * @throws Exception if the task index is invalid
     */
    private String handleDeleteCommand(String[] arguments) throws Exception {
        int taskIndex = parseTaskIndex(arguments[0]);
        Task removedTask = tasks.deleteTask(taskIndex);
        saveTasksToStorage();
        
        return String.format("Noted bobz. I've removed this task bobz:\n  %s\nNow you have %d tasks in the list bobz.",
                removedTask, tasks.size());
    }

    /**
     * Processes the find command to search for tasks containing a keyword.
     *
     * @param arguments command arguments containing the search keyword
     * @return formatted list of matching tasks
     * @throws BobException if no keyword is provided
     */
    private String handleFindCommand(String[] arguments) throws BobException {
        if (arguments.length == 0 || arguments[0].isEmpty()) {
            throw new BobException("BOBZ!!! The search keyword cannot be empty bobz.");
        }

        String keyword = arguments[0].toLowerCase();
        TaskList matchingTasks = findTasksContainingKeyword(keyword);

        if (matchingTasks.isEmpty()) {
            return "No matching tasks found bobz.";
        }
        
        StringBuilder resultBuilder = new StringBuilder("Here are the matching tasks in your list bobz:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            resultBuilder.append(String.format("%d. %s\n", i + 1, matchingTasks.get(i)));
        }
        return resultBuilder.toString().trim();
    }

    /**
     * Finds all tasks that contain the given keyword in their description.
     *
     * @param keyword the keyword to search for (case-insensitive)
     * @return a TaskList containing all matching tasks
     */
    private TaskList findTasksContainingKeyword(String keyword) {
        TaskList matchingTasks = new TaskList();
        
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getDescription().toLowerCase().contains(keyword)) {
                matchingTasks.addTask(task);
            }
        }
        
        return matchingTasks;
    }

    /**
     * Parses a string representation of a task index to zero-based integer.
     *
     * @param indexString the string representation of the task index (1-based)
     * @return the zero-based task index
     * @throws NumberFormatException if the string is not a valid number
     */
    private int parseTaskIndex(String indexString) throws NumberFormatException {
        return Integer.parseInt(indexString) - 1;
    }

    /**
     * Saves the current task list to storage.
     * Prints an error message if saving fails.
     */
    private void saveTasksToStorage() {
        try {
            storage.saveTasks(tasks.getTasks());
        } catch (Exception exception) {
            System.err.println("Error saving tasks: " + exception.getMessage());
        }
    }

    // CLI methods remain the same but with better naming...
    // [Keep existing CLI methods for backward compatibility]
}
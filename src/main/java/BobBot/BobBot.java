package bobbot;

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
 * Starting point of BobBot. Wires together I/O, parsing, storage and the task model.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Reads user input in a loop</li>
 *   <li>Delegates parsing to {@link bobbot.parser.Parser}</li>
 *   <li>Mutates the task list and persists via {@link bobbot.storage.Storage}</li>
 *   <li>Prints responses via {@link bobbot.ui.Ui}</li>
 * </ul>
 */
public class BobBot {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a new BobBot instance with the specified file path for data persistence.
     * Initializes the user interface, storage system, and loads existing tasks from file.
     *
     * @param filePath the file path where task data will be stored and loaded from
     */
    public BobBot(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.loadTasks());
    }

    /**
     * Launches the application and starts the main command loop.
     * Displays welcome message, processes user commands until exit, and handles errors gracefully.
     */
    public void run() {
        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Parser.Command command = Parser.parseCommand(fullCommand);
                
                switch (command.getType()) {
                    case BYE:
                        isExit = true;
                        ui.showBye();
                        break;
                    case LIST:
                        ui.showTaskList(tasks);
                        break;
                    case MARK:
                        handleMark(command.getArguments());
                        break;
                    case UNMARK:
                        handleUnmark(command.getArguments());
                        break;
                    case TODO:
                        handleTodo(command.getArguments());
                        break;
                    case DEADLINE:
                        handleDeadline(command.getArguments());
                        break;
                    case EVENT:
                        handleEvent(command.getArguments());
                        break;
                    case DELETE:
                        handleDelete(command.getArguments());
                        break;
                    case FIND:
                        handleFind(command.getArguments());
                        break;
                    case INVALID:
                        throw new BobException("BOBZ!!! what are you saying bobz. Only use 'todo', 'deadline', 'event' and 'delete' bobz.");
                }
                
                if (!isExit) {
                    ui.showLine();
                }
            } catch (BobException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("BOBZ!!!Something went wrong, please check your command format bobz.");
            }
        }
        ui.close();
    }

    /**
     * Handles the mark command to mark a task as completed.
     * Retrieves the task by index, marks it as done, displays confirmation, and saves changes.
     *
     * @param args command arguments where args[0] is the 1-based task index
     * @throws Exception if the task index is invalid or parsing fails
     */
    private void handleMark(String[] args) throws Exception {
        int taskIndex = Integer.parseInt(args[0]) - 1;
        Task task = tasks.getTask(taskIndex);
        task.markAsDone();
        ui.showTaskMarked(task);
        saveToFile();
    }

    /**
     * Handles the unmark command to mark a task as not completed.
     * Retrieves the task by index, marks it as not done, displays confirmation, and saves changes.
     *
     * @param args command arguments where args[0] is the 1-based task index
     * @throws Exception if the task index is invalid or parsing fails
     */
    private void handleUnmark(String[] args) throws Exception {
        int taskIndex = Integer.parseInt(args[0]) - 1;
        Task task = tasks.getTask(taskIndex);
        task.markAsNotDone();
        ui.showTaskUnmarked(task);
        saveToFile();
    }

    /**
     * Handles the todo command to create a new todo task.
     * Creates a new Todo task with the given description, adds it to the task list,
     * displays confirmation, and saves changes.
     *
     * @param args command arguments where args[0] is the task description
     * @throws Exception if the description is empty or invalid
     */
    private void handleTodo(String[] args) throws Exception {
        String desc = args[0];
        if (desc.isEmpty()) {
            throw new BobException("BOBZ!!! The description of a todo cannot be empty bobz.");
        }
        Task task = new Todo(desc);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.size());
        saveToFile();
    }

    /**
     * Handles the deadline command to create a new deadline task.
     * Creates a new Deadline task with description and due date, adds it to the task list,
     * displays confirmation, and saves changes.
     *
     * @param args command arguments where args[0] is the description and args[1] is the deadline
     * @throws Exception if the format is invalid or required arguments are missing
     */
    private void handleDeadline(String[] args) throws Exception {
        if (args.length != 2) {
            ui.showError("BOBZ!!! Invalid format for deadline bobz. Try: deadline <desc> /by <time>");
            return;
        }
        Task task = new Deadline(args[0].trim(), args[1].trim());
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.size());
        saveToFile();
    }

    /**
     * Handles the event command to create a new event task.
     * Creates a new Event task with description, start time, and end time,
     * adds it to the task list, displays confirmation, and saves changes.
     *
     * @param args command arguments where args[0] is description, args[1] is start time, args[2] is end time
     * @throws Exception if the format is invalid or required arguments are missing
     */
    private void handleEvent(String[] args) throws Exception {
        if (args.length != 3) {
            ui.showError("BOBZ!!! Invalid format for event bobz. Try: event <desc> /from <start> /to <end>");
            return;
        }
        Task task = new Event(args[0].trim(), args[1].trim(), args[2].trim());
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.size());
        saveToFile();
    }

    /**
     * Handles the delete command to remove a task from the list.
     * Removes the task at the specified index, displays confirmation, and saves changes.
     *
     * @param args command arguments where args[0] is the 1-based task index
     * @throws Exception if the task index is invalid or parsing fails
     */
    private void handleDelete(String[] args) throws Exception {
        int index = Integer.parseInt(args[0]) - 1;
        Task removedTask = tasks.deleteTask(index);
        ui.showTaskDeleted(removedTask, tasks.size());
        saveToFile();
    }

    /**
     * Handles the find command to search for tasks containing a keyword.
     * Performs case-insensitive search through all task descriptions.
     *
     * @param args Array containing the search keyword as the first element.
     * @throws BobException If no keyword is provided or keyword is empty.
     */
    private void handleFind(String[] args) throws BobException {
        if (args.length == 0 || args[0].isEmpty()) {
            throw new BobException("BOBZ!!! The search keyword cannot be empty bobz.");
        }

        String keyword = args[0].toLowerCase();
        TaskList matchingTasks = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getDescription().toLowerCase().contains(keyword)) {
                matchingTasks.addTask(task);
            }
        }

        ui.showMatchingTasks(matchingTasks);
    }

    /**
     * Saves the current task list to the storage file.
     * Displays an error message if saving fails.
     */
    private void saveToFile() {
        try {
            storage.saveTasks(tasks.getTasks());
        } catch (Exception e) {
            ui.showError("Something went wrong while savin bobz: " + e.getMessage());
        }
    }

    /**
     * Main entry point for the BobBot application.
     * Creates a new BobBot instance with default file path and starts the application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        new BobBot("../../../data/bobbotTask.txt").run();
    }
}
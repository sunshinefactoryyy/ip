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

public class BobBot {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public BobBot(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.loadTasks());
    }

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

    private void handleMark(String[] args) throws Exception {
        int taskIndex = Integer.parseInt(args[0]) - 1;
        Task task = tasks.getTask(taskIndex);
        task.markAsDone();
        ui.showTaskMarked(task);
        saveToFile();
    }

    private void handleUnmark(String[] args) throws Exception {
        int taskIndex = Integer.parseInt(args[0]) - 1;
        Task task = tasks.getTask(taskIndex);
        task.markAsNotDone();
        ui.showTaskUnmarked(task);
        saveToFile();
    }

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

    private void saveToFile() {
        try {
            storage.saveTasks(tasks.getTasks());
        } catch (Exception e) {
            ui.showError("Something went wrong while saving: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new BobBot("../../../data/bobbotTask.txt").run();
    }
}
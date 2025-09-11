package bobbot.tasklist;

import java.util.ArrayList;

import bobbot.exception.BobException;
import bobbot.task.Task;

/**
 * Manages a collection of tasks with operations to add, delete, and retrieve tasks.
 * Provides a wrapper around ArrayList with additional validation and error handling
 * specific to task management operations.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     * Initializes the internal task collection as an empty ArrayList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        assert tasks != null;
    }

    /**
     * Constructs a TaskList with the given collection of tasks.
     * Used primarily for loading existing tasks from storage.
     *
     * @param tasks the initial collection of tasks to populate this TaskList
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null;
        this.tasks = tasks;
        assert this.tasks != null;
    }

    /**
     * Adds a new task to the end of the task list.
     *
     * @param task the Task to be added to the list
     */
    public void addTask(Task task) {
        assert task != null;
        tasks.add(task);
    }

    /**
     * Deletes and returns the task at the specified index.
     * Removes the task from the list permanently.
     *
     * @param index the 0-based index of the task to delete
     * @return the Task that was removed from the list
     * @throws BobException if the index is out of bounds
     */
    public Task deleteTask(int index) throws BobException {
        validateIndex(index);

        assert index >= 0 && index < tasks.size();

        return tasks.remove(index);
    }

    /**
     * Retrieves the task at the specified index without removing it.
     * Provides bounds checking and throws appropriate exceptions for invalid indices.
     *
     * @param index the 0-based index of the task to retrieve
     * @return the Task at the specified index
     * @throws BobException if the index is out of bounds
     */
    public Task getTask(int index) throws BobException {
        validateIndex(index);

        assert index >= 0 && index < tasks.size();

        return tasks.get(index);
    }

    /**
     * Retrieves the task at the specified index without bounds checking.
     * Direct access to the underlying ArrayList's get method.
     *
     * @param index the 0-based index of the task to retrieve
     * @return the Task at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public Task get(int index) {
        assert index >= 0 && index < tasks.size();

        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in this TaskList.
     *
     * @return the size of the task collection
     */
    public int size() {
        int size = tasks.size();
        assert size >= 0;

        return size;
    }

    /**
     * Checks if this TaskList contains no tasks.
     *
     * @return true if the TaskList is empty, false otherwise
     */
    public boolean isEmpty() {
        boolean isEmpty = tasks.isEmpty();
        assert (isEmpty && tasks.size() == 0) || (!isEmpty && tasks.size() > 0);

        return isEmpty;
    }

    /**
     * Returns the underlying ArrayList of tasks.
     * Used primarily for storage operations and iteration.
     *
     * @return the ArrayList containing all tasks
     */
    public ArrayList<Task> getTasks() {
        assert tasks != null;

        return tasks;
    }

    private void validateIndex(int index) throws BobException {
        assert tasks != null;
        
        if (index < 0 || index >= tasks.size()) {
            throw new BobException("BOBZ!!! That task number does not exist.");
        }
    }
}
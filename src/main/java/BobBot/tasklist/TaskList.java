package bobbot.tasklist;

import java.util.ArrayList;

import bobbot.exception.BobException;
import bobbot.task.Task;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) throws BobException {
        validateIndex(index);
        return tasks.remove(index);
    }

    public Task getTask(int index) throws BobException {
        validateIndex(index);
        return tasks.get(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    private void validateIndex(int index) throws BobException {
        if (index < 0 || index >= tasks.size()) {
            throw new BobException("BOBZ!!! That task number does not exist.");
        }
    }
}
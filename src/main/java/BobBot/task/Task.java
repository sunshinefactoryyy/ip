package bobbot.task;

/**
 * Represents a generic task with a description and completion status.
 * This is the base class for all task types.
 */
public class Task {
    private final String desc;
    private boolean isDone;

    /**
     * Creates a new Task with the specified description.
     * The task is initially marked as not done.
     *
     * @param desc Description of the task.
     */
    public Task(String desc) {
        this.desc = desc;
        this.isDone = false;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }
    
    public String getDescription() {
        return this.desc;
    }

    /**
     * Returns a string representation of this task.
     * The format is [X] description if done, or [ ] description if not done.
     *
     * @return String representation of this task.
     */
    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + desc;
    }
}
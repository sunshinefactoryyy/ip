package bobbot.task;

/**
 * Represents a basic todo task.
 * This is the simplest type of task with just a description and completion status.
 */
public class Todo extends Task {

    /**
     * Creates a new Todo task with the specified description.
     *
     * @param desc Description of the todo task.
     */
    public Todo(String desc) {
        super(desc);
    }

    /**
     * Returns a string representation of this todo task.
     * The format is [T][X] description if done, or [T][ ] description if not done.
     *
     * @return String representation of this todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

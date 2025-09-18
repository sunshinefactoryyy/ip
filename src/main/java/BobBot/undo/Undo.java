package bobbot.undo;

import bobbot.task.Task;

/**
 * Represents an action that can be undone in BobBot.
 * Stores the type of action and the necessary data to reverse it.
 */
public class Undo {
    
    /**
     * Enumeration of action types that can be undone.
     */
    public enum ActionType {
        ADD_TASK, DELETE_TASK, MARK_TASK, UNMARK_TASK
    }
    
    private final ActionType actionType;
    private final Task task;
    private final int index; // Used for delete operations to restore at correct position
    
    /**
     * Creates an UndoableAction for add/mark/unmark operations.
     *
     * @param actionType the type of action performed
     * @param task the task that was affected
     */
    public Undo(ActionType actionType, Task task) {
        this.actionType = actionType;
        this.task = task;
        this.index = -1; // Not needed for add/mark/unmark
    }
    
    /**
     * Creates an UndoableAction for delete operations.
     *
     * @param actionType the type of action performed (should be DELETE_TASK)
     * @param task the task that was deleted
     * @param index the original index where the task was located
     */
    public Undo(ActionType actionType, Task task, int index) {
        this.actionType = actionType;
        this.task = task;
        this.index = index;
    }
    
    /**
     * Gets the type of action that was performed.
     *
     * @return the ActionType
     */
    public ActionType getActionType() {
        return actionType;
    }
    
    /**
     * Gets the task that was affected by the action.
     *
     * @return the Task object
     */
    public Task getTask() {
        return task;
    }
    
    /**
     * Gets the index where the task was originally located (for delete operations).
     *
     * @return the original index, or -1 if not applicable
     */
    public int getIndex() {
        return index;
    }
}
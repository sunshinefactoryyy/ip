package bobbot.tasklist;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import bobbot.tasklist.TaskList;
import bobbot.task.Todo;
import bobbot.task.Task;
import bobbot.exception.BobException;

public class TaskListTest {
    @Test
    public void testTaskOperations() {
        TaskList taskList = new TaskList();
        Task todo = new Todo("test task");
        
        // test add task
        taskList.addTask(todo);
        assertEquals(1, taskList.size());
        
        // test get task
        try {
            Task retrieved = taskList.getTask(0);
            assertEquals(todo, retrieved);
        } catch (BobException e) {
            fail("Should not throw exception for valid index");
        }
        
        // test delete task
        try {
            Task deleted = taskList.deleteTask(0);
            assertEquals(todo, deleted);
            assertEquals(0, taskList.size());
        } catch (BobException e) {
            fail("Should not throw exception for valid index");
        }
        
        // test exception
        assertThrows(BobException.class, () -> {
            taskList.getTask(0);
        });
    }
}
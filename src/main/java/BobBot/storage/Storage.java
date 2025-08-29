package bobbot.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import bobbot.task.Deadline;
import bobbot.task.Event;
import bobbot.task.Task;
import bobbot.task.Todo;

public class Storage {
     private static final String DATA_DIRECTORY = "../../../data";
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        createDataDirectoryIfNotExists();
        
        FileWriter fw = new FileWriter(filePath);
        for (int i = 0; i < tasks.size(); i++) {
            fw.write((i + 1) + ". " + tasks.get(i).toString() + System.lineSeparator());
        }
        fw.close();
    }

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File f = new File(filePath);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine().trim();
                if (!line.isEmpty()) {
                    Task task = parseTaskFromDisplayFormat(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, start with empty list
            System.out.println("Error loading tasks bobz: File not found bobz");
        } catch (Exception e) {
            System.out.println("Error loading tasks bobz: " + e.getMessage());
        }
        return tasks;
    }

    private void createDataDirectoryIfNotExists() {
        File dataDir = new File(DATA_DIRECTORY);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    private Task parseTaskFromDisplayFormat(String line) {
        try {
            String taskString = extractTaskString(line);
            if (taskString == null) {
                return null;
            }
            
            char taskType = taskString.charAt(1);
            boolean isDone = taskString.charAt(3) == 'X';
            String content = taskString.substring(6);
            
            Task task = createTaskByType(taskType, content);
            
            if (task != null && isDone) {
                task.markAsDone();
            }
            
            return task;
        } catch (Exception e) {
            return null;
        }
    }

    private String extractTaskString(String line) {
        int dotIndex = line.indexOf(". ");
        if (dotIndex == -1) {
            return null;
        }
        String taskString = line.substring(dotIndex + 2);
        
        if (!taskString.startsWith("[")) {
            return null;
        }
        
        return taskString;
    }

    private Task createTaskByType(char taskType, String content) {
        switch (taskType) {
        case 'T':
            return new Todo(content);
        case 'D':
            return parseDeadlineTask(content);
        case 'E':
            return parseEventTask(content);
        default:
            return null;
        }
    }

    /**
     * Parses a deadline task from its stored content format.
     * Extracts the description and deadline information from the stored format.
     *
     * @param content the content string for the deadline task
     * @return a Deadline task object, or null if parsing fails
     */
    private Task parseDeadlineTask(String content) {
        // Find the " (by: " part
        int byIndex = content.lastIndexOf(" (by: ");
        if (byIndex != -1) {
            String description = content.substring(0, byIndex);
            String deadline = content.substring(byIndex + 6, content.length() - 1); // Remove the closing ")"
            return new Deadline(description, deadline);
        }
        return null;
    }

    /**
     * Parses an event task from its stored content format.
     * Extracts the description, start time, and end time from the stored format.
     *
     * @param content the content string for the event task
     * @return an Event task object, or null if parsing fails
     */
    private Task parseEventTask(String content) {
        // Find the " (from: " part
        int fromIndex = content.lastIndexOf(" (from: ");
        if (fromIndex != -1) {
            String description = content.substring(0, fromIndex);
            String timeInfo = content.substring(fromIndex + 8, content.length() - 1); // Remove the closing ")"
            String[] timeParts = timeInfo.split(" to: ");
            if (timeParts.length == 2) {
                return new Event(description, timeParts[0], timeParts[1]);
            }
        }
        return null;
    }
}
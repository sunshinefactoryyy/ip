import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        File dataDir = new File("./data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        
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
        } catch (Exception e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    private Task parseTaskFromDisplayFormat(String line) {
        try {
            // Remove the number prefix (e.g., "1. ")
            int dotIndex = line.indexOf(". ");
            if (dotIndex == -1) {
                return null;
            }
            String taskString = line.substring(dotIndex + 2);
            
            // Extract task type
            if (!taskString.startsWith("[")) {
                return null;
            }
            
            char taskType = taskString.charAt(1);
            
            // Extract done status
            boolean isDone = taskString.charAt(3) == 'X';
            
            // Extract description and additional info
            String content = taskString.substring(6); // Skip "[T][ ] " or "[T][X] "
            
            Task task = null;
            
            switch (taskType) {
                case 'T':
                    task = new Todo(content);
                    break;
                case 'D':
                    // Find the " (by: " part
                    int byIndex = content.lastIndexOf(" (by: ");
                    if (byIndex != -1) {
                        String description = content.substring(0, byIndex);
                        String deadline = content.substring(byIndex + 6, content.length() - 1); // Remove the closing ")"
                        task = new Deadline(description, deadline);
                    }
                    break;
                case 'E':
                    // Find the " (from: " part
                    int fromIndex = content.lastIndexOf(" (from: ");
                    if (fromIndex != -1) {
                        String description = content.substring(0, fromIndex);
                        String timeInfo = content.substring(fromIndex + 8, content.length() - 1); // Remove the closing ")"
                        String[] timeParts = timeInfo.split(" to: ");
                        if (timeParts.length == 2) {
                            task = new Event(description, timeParts[0], timeParts[1]);
                        }
                    }
                    break;
            }
            
            if (task != null && isDone) {
                task.markAsDone();
            }
            
            return task;
        } catch (Exception e) {
            return null;
        }
    }
}
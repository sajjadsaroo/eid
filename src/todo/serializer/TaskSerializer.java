package todo.serializer;
import db.*;
import todo.entity.Task;
import java.util.Date;

public class TaskSerializer implements Serializer {

    @Override
    public String serialize(Entity e) {
        Task task = (Task) e;
        return task.id + "|" + task.title + "|" + task.description + "|" + task.dueDate + "|" + task.status.name();
    }

    @Override
    public Entity deserialize(String s) {
        String[] parts = s.split("\\|");
        if (parts.length != 5) throw new IllegalArgumentException("Invalid Task data format");

        Task task = new Task(parts[1], parts[2], new Date(parts[3]));  // Assuming valid Date format
        task.id = Integer.parseInt(parts[0]);
        task.status = Task.Status.valueOf(parts[4]);

        return task;
    }
}

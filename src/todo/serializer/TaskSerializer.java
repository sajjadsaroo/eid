package todo.serializer;

import db.Serializer;
import db.Entity;
import todo.entity.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskSerializer implements Serializer {

    @Override
    public String serialize(Entity e) {
        Task task = (Task) e;
        return task.getEntityCode() + "|" + task.id + "|" + task.title + "|" + task.description + "|" +
                new SimpleDateFormat("yyyy-MM-dd").format(task.dueDate) + "|" + task.status.name();
    }

    @Override
    public Entity deserialize(String s) {
        String[] parts = s.split("\\|");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid Task data format");
        }

        try {
            int entityCode = Integer.parseInt(parts[0]);
            Task task = new Task(parts[2], parts[3], new SimpleDateFormat("yyyy-MM-dd").parse(parts[4]));  // Parse dueDate from string
            task.id = Integer.parseInt(parts[1]);
            task.status = Task.Status.valueOf(parts[5]);

            return task;
        } catch (Exception e) {
            System.out.println("Error deserializing Task: " + e.getMessage());
            return null;
        }
    }
}

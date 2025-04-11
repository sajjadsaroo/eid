package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;


public class TaskService {

    public static void changeStatus(int taskId, Task.Status newStatus) {
        try {
            Entity entity = Database.get(taskId);

            if (!(entity instanceof Task task)) {
                System.out.println("Entity with ID=" + taskId + " is not a Task.");
                return;
            }

            if (task.status == newStatus) {
                System.out.println("Task is already marked as " + newStatus.name());
                return;
            }

            task.status = newStatus;

            Database.update(task);
            System.out.println("Task with ID=" + taskId + " marked as " + newStatus.name());
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot find task with ID=" + taskId);
        } catch (InvalidEntityException e) {
            System.out.println("Cannot update task.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void updateTaskField(int id, String field, String newValue) {
        try {
            Entity entity = Database.get(id);

            if (!(entity instanceof Task task)) {
                System.out.println("Entity with ID=" + id + " is not a Task.");
                return;
            }

            String oldValue = "";
            if (field.equalsIgnoreCase("title")) {
                oldValue = task.title;
                task.title = newValue;

            } else if (field.equalsIgnoreCase("description")) {
                oldValue = task.description;
                task.description = newValue;

            } else if (field.equalsIgnoreCase("due date")) {
                oldValue = task.dueDate != null ? task.dueDate.toString() : "null";
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(newValue);
                    task.dueDate = date;
                } catch (Exception e) {
                    System.out.println("Invalid date format. Use yyyy-MM-dd");
                    return;
                }

            } else if (field.equalsIgnoreCase("status")) {
                oldValue = task.status.name();
                try {
                    task.status = Task.Status.valueOf(newValue);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid status value.");
                    return;
                }

            } else {
                System.out.println("Unknown field: " + field);
                return;
            }

            Database.update(task);

            System.out.println("Successfully updated the task.");
            System.out.println("Field: " + field);
            System.out.println("Old Value: " + oldValue);
            System.out.println("New Value: " + newValue);
            System.out.println("Modification Date: " + task.getLastModificationDate());

        } catch (EntityNotFoundException e) {
            System.out.println("Cannot update task with ID=" + id);
        } catch (InvalidEntityException e) {
            System.out.println("Cannot update task.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void getTaskById(int id) {
        try {
            Entity entity = Database.get(id);

            if (!(entity instanceof Task task)) {
                System.out.println("Entity with ID=" + id + " is not a Task.");
                return;
            }

            System.out.println("ID: " + task.id);
            System.out.println("Title: " + task.title);
            System.out.println("Due Date: " + task.dueDate);
            System.out.println("Status: " + task.status);

            ArrayList<Entity> allSteps = Database.getAll(todo.entity.Step.STEP_ENTITY_CODE);
            boolean hasSteps = false;

            for (Entity e : allSteps) {
                todo.entity.Step step = (todo.entity.Step) e;
                if (step.taskRef == task.id) {
                    if (!hasSteps) {
                        System.out.println("Steps:");
                        hasSteps = true;
                    }
                    System.out.println("    + " + step.title + ":");
                    System.out.println("        ID: " + step.id);
                    System.out.println("        Status: " + step.status);
                }
            }

        } catch (EntityNotFoundException e) {
            System.out.println("Cannot find task with ID=" + id);
        }
    }

    public static void getAllTasks() {
        ArrayList<Entity> allTasks = Database.getAll(Task.TASK_ENTITY_CODE);

        allTasks.sort(Comparator.comparing(e -> ((Task) e).dueDate));

        for (Entity entity : allTasks) {
            getTaskById(entity.id);
            System.out.println();
        }
    }

    public static void getIncompleteTasks() {
        ArrayList<Entity> allTasks = Database.getAll(Task.TASK_ENTITY_CODE);

        allTasks.sort(Comparator.comparing(e -> ((Task) e).dueDate));

        for (Entity entity : allTasks) {
            Task task = (Task) entity;
            if (task.status != Task.Status.Completed) {
                getTaskById(task.id);
                System.out.println();
            }
        }
    }





}

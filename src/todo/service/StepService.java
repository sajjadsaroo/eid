package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import java.util.ArrayList;

public class StepService {

    public static void saveStep(int taskRef, String title) {
        try {
            Step step = new Step(title, taskRef);
            Database.add(step);

            System.out.println("Step saved successfully.");
            System.out.println("ID: " + step.id);
        } catch (InvalidEntityException e) {
            System.out.println("Cannot save step.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void updateStepField(int id, String field, String newValue) {
        try {
            Entity entity = Database.get(id);
            if (!(entity instanceof Step step)) {
                System.out.println("Entity with ID=" + id + " is not a Step.");
                return;
            }

            String oldValue = "";
            if (field.equalsIgnoreCase("title")) {
                oldValue = step.title;
                step.title = newValue;

            } else if (field.equalsIgnoreCase("status")) {
                oldValue = step.status.name();
                try {
                    for (Step.Status s : Step.Status.values()) {
                        if (s.name().equalsIgnoreCase(newValue)) {
                            step.status = s;
                            break;
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid status value.");
                    return;
                }

            } else {
                System.out.println("Unknown field: " + field);
                return;
            }

            Database.update(step);

            System.out.println("Successfully updated the step.");
            System.out.println("Field: " + field);
            System.out.println("Old Value: " + oldValue);
            System.out.println("New Value: " + newValue);

            checkAndUpdateTaskStatus(step.taskRef);

        } catch (EntityNotFoundException e) {
            System.out.println("Cannot update step with ID=" + id);
            System.out.println("Error: " + e.getMessage());
        } catch (InvalidEntityException e) {
            System.out.println("Cannot update step.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void checkAndUpdateTaskStatus(int taskId) {
        try {
            Entity entity = Database.get(taskId);
            if (!(entity instanceof Task task)) return;

            ArrayList<Entity> allSteps = Database.getAll(Step.STEP_ENTITY_CODE);
            int completed = 0;
            int total = 0;

            for (Entity e : allSteps) {
                Step step = (Step) e;
                if (step.taskRef == taskId) {
                    total++;
                    if (step.status == Step.Status.Completed) completed++;
                }
            }

            if (total == 0) return;

            if (completed == total && task.status != Task.Status.Completed) {
                task.status = Task.Status.Completed;
                Database.update(task);
            } else if (completed > 0 && task.status == Task.Status.NotStarted) {
                task.status = Task.Status.InProgress;
                Database.update(task);
            }
        } catch (Exception ignored) {}
    }
}

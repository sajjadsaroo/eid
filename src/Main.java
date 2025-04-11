import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import todo.serializer.StepSerializer;
import todo.serializer.TaskSerializer;
import todo.service.StepService;
import todo.service.TaskService;
import todo.validator.StepValidator;
import todo.validator.TaskValidator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static final String[] MENU_ITEMS = {
            "Add task",
            "Add step",
            "Update task",
            "Update step",
            "Delete task",
            "Get task by ID",
            "Get all tasks",
            "Get incomplete tasks",
            "Save database",
            "Show menu",
            "Exit"
    };

    public static void showMenu() {
        System.out.println("\n===== TO-DO LIST MENU =====");
        for (String item : MENU_ITEMS) {
            System.out.println("- " + item);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Database.registerValidator(Task.TASK_ENTITY_CODE, new TaskValidator());
        Database.registerValidator(Step.STEP_ENTITY_CODE, new StepValidator());

        Database.registerSerializer(Task.TASK_ENTITY_CODE, new TaskSerializer());
        Database.registerSerializer(Step.STEP_ENTITY_CODE, new StepSerializer());


        Database.load();

        showMenu();

        while (true) {
            System.out.print("\nEnter choice (or 'show menu' for options): ");
            String input = scanner.nextLine().trim().toLowerCase();

            try {
                switch (input) {
                    case "add task" -> {
                        System.out.println("\n--- Add New Task ---");
                        System.out.print("Title: ");
                        String title = scanner.nextLine().trim();
                        System.out.print("Description: ");
                        String description = scanner.nextLine().trim();
                        System.out.print("Due date (yyyy-MM-dd): ");
                        String dueDateStr = scanner.nextLine().trim();
                        Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateStr);
                        TaskService.createTask(title, description, dueDate);
                    }
                    case "add step" -> {
                        System.out.println("\n--- Add New Step ---");
                        System.out.print("TaskID: ");
                        int taskRef = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Title: ");
                        String title = scanner.nextLine().trim();
                        StepService.saveStep(taskRef, title);
                    }
                    case "update task" -> {
                        System.out.println("\n--- Update Task ---");
                        System.out.print("ID: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Field: ");
                        String field = scanner.nextLine().trim();
                        System.out.print("New Value: ");
                        String newValue = scanner.nextLine().trim();
                        TaskService.updateTaskField(id, field, newValue);
                    }
                    case "update step" -> {
                        System.out.println("\n--- Update Step ---");
                        System.out.print("ID: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Field: ");
                        String field = scanner.nextLine().trim();
                        System.out.print("New Value: ");
                        String newValue = scanner.nextLine().trim();
                        StepService.updateStepField(id, field, newValue);
                    }
                    case "delete task" -> {
                        System.out.println("\n--- Delete Task ---");
                        System.out.print("ID: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        try {
                            TaskService.deleteTask(id);
                        } catch (EntityNotFoundException e) {
                            System.out.println("Cannot delete entity with ID=" + id);
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case "get task by id" -> {
                        System.out.println("\n--- View Task By ID ---");
                        System.out.print("ID: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        TaskService.getTaskById(id);
                    }
                    case "get all tasks" -> {
                        System.out.println("\n--- All Tasks ---");
                        TaskService.getAllTasks();
                    }
                    case "get incomplete tasks" -> {
                        System.out.println("\n--- Incomplete Tasks ---");
                        TaskService.getIncompleteTasks();
                    }
                    case "save database" -> {
                        Database.save();
                    }
                    case "show menu" -> showMenu();
                    case "exit" -> {
                        Database.save();
                        System.out.println("Exiting program. Goodbye!");
                        return;
                    }
                    default -> System.out.println("Unknown choice. Type 'show menu' to see available commands.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }

            System.out.println("\nReturning to main menu...\n");
        }
    }
}

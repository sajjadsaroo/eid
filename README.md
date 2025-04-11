# 📝 Java To-Do List with In-Memory Database

This project is a simple yet powerful **To-Do List CLI application** built in Java. At its core lies a custom **in-memory database** that supports validation, timestamping, and file persistence.

---

## 📆 Features

- ✅ Store entities with unique auto-incremented IDs
- ✅ Validator system for entity integrity checks
- ✅ Trackable entities with creation and modification timestamps
- ✅ Tasks and their related Steps as manageable entities
- ✅ Smart status updates across tasks and steps
- ✅ File-based persistence using `db.txt`
- ✅ Command-line interface (CLI) for user interaction

---

## 📂 Project Structure

```
src/
├── Main.java
├── db/
│   ├── Database.java
│   ├── Entity.java
│   ├── Trackable.java
│   ├── Validator.java
│   ├── Serializer.java
│   └── exception/
│       ├── EntityNotFoundException.java
│       └── InvalidEntityException.java
├── todo/
│   ├── entity/
│   │   ├── Task.java
│   │   └── Step.java
│   ├── validator/
│   │   ├── TaskValidator.java
│   │   └── StepValidator.java
│   ├── serializer/
│   │   ├── TaskSerializer.java
│   │   └── StepSerializer.java
│   └── service/
│       ├── TaskService.java
│       └── StepService.java
```

---

## ⚙️ How to Run

> Prerequisite: Java SDK 8 or later installed

1. Open the project in an IDE (IntelliJ IDEA or VS Code).
2. Run `Main.java`.
3. Use CLI commands to interact with the application.

---

## 🔊 Available CLI Commands

| Command | Action |
|---------|--------|
| `add task` | Add a new task |
| `add step` | Add a step to a specific task |
| `update task` | Modify task fields (title, status, etc.) |
| `update step` | Modify step status |
| `delete` | Delete a task or step by ID |
| `get task-by-id` | View details of a specific task |
| `get all-tasks` | Show all tasks, sorted by due date |
| `get incomplete-tasks` | Show only incomplete tasks |
| `save` | Save all data to `db.txt` |
| `exit` | Exit the program |

---

## 📃 Persistence

- All data is saved to a local text file `db.txt`
- On next program start, this file is read and the database is restored

---

## ✨ Future Improvements

- GUI interface using JavaFX or Swing
- Task categorization (e.g. Personal, Work, Study)
- Task prioritization (High / Medium / Low)
- Reminders for upcoming deadlines

---

## 👨‍💼 Author

**Sajjad Sarookhani**  
Java Developer, WordPress Designer, and Tech YouTuber  
[GitHub Profile](https://github.com/sajjadsaroo)

---

## 📄 License

This project is intended for educational purposes and open for public use.

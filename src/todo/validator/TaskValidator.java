package todo.validator;

import db.*;
import db.exception.InvalidEntityException;
import todo.entity.Task;

public class TaskValidator implements Validator {

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Task task))
            throw new IllegalArgumentException("Invalid entity type. Expected Task.");


        if (task.title == null || task.title.trim().isEmpty())
            throw new InvalidEntityException("Task title cannot be empty.");

    }

}

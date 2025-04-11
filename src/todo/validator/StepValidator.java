package todo.validator;

import db.*;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;


public class StepValidator implements Validator {

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Step step))
            throw new IllegalArgumentException("Invalid entity type. Expected Step.");


        if (step.title == null || step.title.trim().isEmpty())
            throw new InvalidEntityException("Step title cannot be empty.");

        try {
            Entity taskEntity = Database.get(step.taskRef);
            if (!(taskEntity instanceof Task))
                throw new InvalidEntityException("Step must reference a valid Task.");


        } catch (EntityNotFoundException e) {
            throw new InvalidEntityException("Cannot find task with ID=" + step.taskRef);
        }

    }
}

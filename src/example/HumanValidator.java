package example;

import db.*;
import db.exception.*;

public class HumanValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Human))
            throw new IllegalArgumentException("Invalid entity type. Expected Human.");


        validateHuman((Human) entity);
    }

    private void validateHuman(Human human) throws InvalidEntityException {
        validateName(human.name);
        validateAge(human.age);
    }

    private void validateName(String name) throws InvalidEntityException {
        if (name == null || name.isBlank())
            throw new InvalidEntityException("Name cannot be null or empty");

    }

    private void validateAge(int age) throws InvalidEntityException {
        if (age <= 0)
            throw new InvalidEntityException("Age must be a positive integer");

    }
}

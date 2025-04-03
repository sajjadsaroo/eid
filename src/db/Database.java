package db;

import java.util.*;
import db.exception.*;

public class Database {

    protected static int nextID = 1;
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    private Database() {}

    private static int findEntityIndexById(int id) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == id) {
                return i;
            }
        }
        throw new EntityNotFoundException(id);
    }

    private static void validateEntity(Entity entity) throws InvalidEntityException {
        Validator validator = validators.get(entity.getEntityCode());

        if (validator == null) {
            throw new IllegalArgumentException(
                    "No validator registered for entity code: " + entity.getEntityCode()
            );
        }

        validator.validate(entity);
    }


    public static void add(Entity entity) throws InvalidEntityException {
        validateEntity(entity);
        entity.id = nextID++;
        entities.add(entity.copy());
    }


    public static Entity get(int id) {
        for (Entity entity : entities) {
            if (entity.id == id) {
                return entity.copy();
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == id) {
                entities.remove(i);
                return;
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void update(Entity entity) throws InvalidEntityException {

        validateEntity(entity);
        int index = findEntityIndexById(entity.id);
        entities.set(index, entity.copy());

    }


    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode))
            throw new IllegalArgumentException("Validator for this entity code already exists.");

        validators.put(entityCode, validator);
    }


}

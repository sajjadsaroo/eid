package db;

import java.util.*;
import java.io.*;
import java.util.List;

import db.exception.*;

public class Database {

    protected static int nextID = 1;
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();
    private static HashMap<Integer, Serializer> serializers = new HashMap<>();

    private Database() {
    }

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
        Validator validator = validators.get(entity.getEntityCode());
        if (validator != null)
            validateEntity(entity);
        entity.id = nextID++;

        if (entity instanceof Trackable trackableEntity) {
            Date now = new Date();
            trackableEntity.setCreationDate(now);
            trackableEntity.setLastModificationDate(now);
        }

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
        Validator validator = validators.get(entity.getEntityCode());
        if (validator != null)
            validateEntity(entity);

        int index = findEntityIndexById(entity.id);

        if (entity instanceof Trackable trackableEntity) {
            trackableEntity.setLastModificationDate(new Date());
        }

        entities.set(index, entity.copy());
    }

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode))
            throw new IllegalArgumentException("Validator for this entity code already exists.");

        validators.put(entityCode, validator);
    }

    public static void registerSerializer(int entityCode, Serializer serializer) {
        if (serializers.containsKey(entityCode))
            throw new IllegalArgumentException("Serializer for this entity code already exists.");

        serializers.put(entityCode, serializer);
    }

    public static void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("db.txt"))) {
            for (Entity entity : entities) {
                Serializer serializer = serializers.get(entity.getEntityCode());
                if (serializer != null) {
                    String serializedEntity = serializer.serialize(entity);
                    writer.write(serializedEntity);
                    writer.newLine();
                }
            }
            System.out.println("Database saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving database: " + e.getMessage());
        }
    }

    public static void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader("db.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 2);
                if (parts.length < 2) continue;

                int entityCode = Integer.parseInt(parts[0]);
                String serializedData = parts[1];

                Serializer serializer = serializers.get(entityCode);
                if (serializer != null) {
                    Entity entity = serializer.deserialize(serializedData);
                    add(entity);
                }
            }
            System.out.println("Database loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading database: " + e.getMessage());
        } catch (InvalidEntityException e) {
            System.out.println("invalid entity " + e.getMessage());
        }
    }


    public static ArrayList<Entity> getAll(int entityCode) {
        ArrayList<Entity> result = new ArrayList<>();
        for (Entity e : entities) {
            if (e.getEntityCode() == entityCode) {
                result.add(e.copy());
            }
        }
        return result;
    }
}

package db;
import java.util.ArrayList;
import db.exception.*;

public class Database {

    protected static int nextID = 1;
    private static ArrayList<Entity> entities = new ArrayList<>();

    private Database() {}

    public static void add(Entity e) {
        e.id = nextID++;
        entities.add(e.copy());
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

    public static void update(Entity newEntity) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == newEntity.id) {
                entities.set(i, newEntity.copy());
                return;
            }
        }
        throw new EntityNotFoundException(newEntity.id);
    }

}

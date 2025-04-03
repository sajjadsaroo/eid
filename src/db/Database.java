package db;
import java.util.ArrayList;
import db.exception.*;

public class Database {

    protected static int nextID = 1;
    private static ArrayList<Entity> entities = new ArrayList<>();

    private Database() {
    }

    public static void add(Entity e) {
        e.id = nextID++;
        entities.add(e);
    }

    public static Entity get(int id) {
        for (Entity entity : entities) {
            if (entity.id == id) {
                return entity;
            }
        }
        throw new EntityNotFoundException();
    }

    public static void delete(int id) {
        for (Entity e : entities) {
            if (e.id == id) {
                entities.remove(e);
                return;
            }
        }
        throw new EntityNotFoundException();
    }

    public static void update(Entity newEntity) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == newEntity.id) {
                entities.set(i, newEntity);
                return;
            }
        }
        throw new EntityNotFoundException();
    }

}

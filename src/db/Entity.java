package db;

public abstract class Entity {

    public int id;

    /**
     * Creates and returns a deep copy of the current entity.
     * This method ensures that the returned copy is a completely new object,
     * independent of the original. This is critical in an in-memory database
     * to prevent external modifications from affecting the data stored in the database.
     * All classes that extend Entity must implement this method by returning a new
     * instance of their own type with the same field values, but located separately
     * in memory (deep copy).
     *
     * @return a new instance of the same type as this entity, with copied field values.
     */

    public abstract Entity copy();

    /**
     * Returns the unique entity code that represents the type of this entity.
     * This integer code is used by the database to identify the type of entity,
     * so it can retrieve and apply the appropriate validator for it.
     * Each entity class must return a **distinct, constant** value as its entity code,
     * which should never collide with codes from other entity classes.
     * For example, class Human might return 14, and another class like Lion
     * would return a completely different number.
     *
     * @return a unique integer code representing the type of this entity.
     */

    public abstract int getEntityCode();

}
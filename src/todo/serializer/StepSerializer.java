package todo.serializer;

import db.Serializer;
import db.Entity;
import todo.entity.Step;

public class StepSerializer implements Serializer {

    @Override
    public String serialize(Entity e) {
        Step step = (Step) e;
        return step.getEntityCode() + "," + step.id + "," + step.title + "," + step.status.name() + "," + step.taskRef;
    }

    @Override
    public Entity deserialize(String s) {
        String[] parts = s.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid Step data format");
        }

        try {
            int entityCode = Integer.parseInt(parts[0]);
            Step step = new Step(parts[2], Integer.parseInt(parts[4]));
            step.id = Integer.parseInt(parts[1]);
            step.status = Step.Status.valueOf(parts[3]);

            return step;
        } catch (Exception e) {
            System.out.println("Error deserializing Step: " + e.getMessage());
            return null;
        }
    }
}

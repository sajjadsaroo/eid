package todo.serializer;
import db.*;
import todo.entity.Step;

public class StepSerializer implements Serializer {

    @Override
    public String serialize(Entity e) {
        Step step = (Step) e;
        return step.id + "|" + step.title + "|" + step.status.name() + "|" + step.taskRef;
    }

    @Override
    public Entity deserialize(String s) {
        String[] parts = s.split("\\|");
        if (parts.length != 4) throw new IllegalArgumentException("Invalid Step data format");

        Step step = new Step(parts[1], Integer.parseInt(parts[3]));
        step.id = Integer.parseInt(parts[0]);
        step.status = Step.Status.valueOf(parts[2]);

        return step;
    }
}

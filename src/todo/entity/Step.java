package todo.entity;
import db.*;

public class Step extends Entity {

    public static final int STEP_ENTITY_CODE = 17;

    public String title;
    public Status status;
    public int taskRef;

    public enum Status {
        NotStarted,
        Completed
    }

    public Step(String title, int taskRef) {
        this.title = title;
        this.taskRef = taskRef;
        this.status = Status.NotStarted;
    }


    @Override
    public Step copy() {
        Step copy = new Step(this.title, this.taskRef);
        copy.id = this.id;
        copy.status = this.status;
        return copy;
    }



    @Override
    public int getEntityCode(){
        return STEP_ENTITY_CODE;
    }

}

package todo.entity;
import db.Trackable;
import java.util.*;
import db.Entity;
import db.exception.*;

public class Task extends Entity implements Trackable {

    public static final int TASK_ENTITY_CODE = 16;


    public enum Status {
        NotStarted,
        InProgress,
        Completed,
    }

    public String title;
    public String description;
    public Date dueDate;
    public Status status;
    private Date creationDate;
    private Date lastModificationDate;

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return this.creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return this.lastModificationDate;
    }

    public Task(String title, String description, Date dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = Status.NotStarted;
    }



    @Override
    public Task copy() {
        Task copy = new Task(
                this.title,
                this.description,
                this.dueDate != null ? new Date(this.dueDate.getTime()) : null
        );
        copy.id = this.id;
        copy.status = this.status;
        copy.setCreationDate(this.creationDate != null ? new Date(this.creationDate.getTime()) : null);
        copy.setLastModificationDate(this.lastModificationDate != null ? new Date(this.lastModificationDate.getTime()) : null);
        return copy;
    }




    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
    }


}

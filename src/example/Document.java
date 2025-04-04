package example;

import db.*;
import db.exception.*;

import javax.swing.plaf.PanelUI;
import java.util.Date;


public class Document extends Entity implements Trackable {

    public String content;
    public static final int DOCUMENT_ENTITY_CODE = 15;

    private Date creationDate;
    private Date lastModificationDate;

    public Document(String content) {
        this.content = content;
    }

    @Override
    public void setCreationDate(Date date){
        this.creationDate = new Date(date.getTime());
    }

    @Override
    public Date getCreationDate(){
        return new Date(this.creationDate.getTime());
    }

    @Override
    public void setLastModificationDate(Date date){
        this.lastModificationDate = new Date(date.getTime());
    }

    @Override
    public Date getLastModificationDate(){
        return new Date(this.lastModificationDate.getTime());
    }


    @Override
    public Document copy() {
        Document docCopy = new Document(this.content);
        docCopy.id = this.id;
        docCopy.setCreationDate(this.getCreationDate());
        docCopy.setLastModificationDate(this.getLastModificationDate());
        return docCopy;
    }

    @Override
    public int getEntityCode() {
        return DOCUMENT_ENTITY_CODE;
    }
}

package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import services.DBConnection;

public abstract class BaseModel {
    @Id
    private ObjectId id;

    public String getId() {
        return id.toString();
    }

    @JsonIgnore
    public ObjectId getRawId(){
        return id;
    }

    public void save(){
        DBConnection.getDatastore().save(this);
    }

    public void remove(){
        DBConnection.getDatastore().delete(this);
    }

    public void removeIter() {
        DBConnection.getDatastore().delete(this);
    }
}

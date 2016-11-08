package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import services.DBConnection;

public class BaseModel {
    @Id
    private ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void save(){
        DBConnection.getDatastore().save(this);
    }

    public void remove(){
        DBConnection.getDatastore().delete(this);
    }
}

package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Id;
import services.DBConnection;

public class BaseModel {
    @Id
    private ObjectId id;

    public String getId() {
        return id.toString();
    }

    public void save(){
        DBConnection.getDatastore().save(this);
    }

    public void remove(){
        DBConnection.getDatastore().delete(this);
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + id +
                '}';
    }
}

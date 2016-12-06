package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.deser.Deserializers;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import services.DBConnection;
import sun.reflect.Reflection;

import java.util.List;

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
        remove();
    }

    public static long getSize(){
        return DBConnection.getDatastore().createQuery(Reflection.getCallerClass())
                .getCollection().count();
    }
}

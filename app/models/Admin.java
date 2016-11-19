package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import services.DBConnection;

import java.util.List;

@Entity
public class Admin extends User {

    public static List<Admin> getAll(){
        return DBConnection.getDatastore()
                .createQuery(Admin.class)
                .asList();
    }

    public static List<Admin> getAllWithField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Admin.class)
                .field(field)
                .equal(value)
                .asList();
    }

    public static Admin findByID(String id){
        return DBConnection.getDatastore()
                .get(Admin.class, new ObjectId(id));
    }

    public static Admin findByID(ObjectId id){
        return DBConnection.getDatastore()
                .get(Admin.class, id);
    }

    public static Admin findByField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Admin.class)
                .field(field).equal(value)
                .get();
    }
}

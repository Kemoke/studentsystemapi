package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import services.DBConnection;

import java.util.List;

@Entity
public class Instructor extends User{
    @Indexed(unique = true)
    private String instructorID;
    private String firstName;
    private String lastName;

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static Instructor findByID(String id){
        return DBConnection.getDatastore()
                .get(Instructor.class, new ObjectId(id));
    }

    public static Instructor findByID(ObjectId id){
        return DBConnection.getDatastore()
                .get(Instructor.class, id);
    }

    public static Instructor findByField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Instructor.class)
                .field(field).equal(value)
                .get();
    }
}

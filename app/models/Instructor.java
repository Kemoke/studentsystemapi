package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.List;

@Entity
@JsonIgnoreProperties({"instructor", "instructors"})
public class Instructor extends User{
    @Indexed(unique = true)
    private String instructorID;
    @Reference
    private List<Section> sections;

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

    public static List<Instructor> getAll(){
        return DBConnection.getDatastore()
                .createQuery(Instructor.class)
                .asList();
    }

    public static List<Instructor> getAllWithField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Instructor.class)
                .field(field)
                .equal(value)
                .asList();
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

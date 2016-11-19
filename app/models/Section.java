package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.List;

@Entity
public class Section extends BaseModel {
    private int number;
    @Reference
    private Course course;
    private int capacity;
    @Reference
    private List<Student> students;
    @Reference
    private Instructor instructor;

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @JsonIgnore
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public static List<Section> getAll(){
        return DBConnection.getDatastore()
                .createQuery(Section.class)
                .asList();
    }

    public static List<Section> getAllWithField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Section.class)
                .field(field)
                .equal(value)
                .asList();
    }

    public static Section findByID(String id){
        return DBConnection.getDatastore()
                .get(Section.class, new ObjectId(id));
    }

    public static Section findByID(ObjectId id){
        return DBConnection.getDatastore()
                .get(Section.class, id);
    }

    public static Section findByField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Section.class)
                .field(field).equal(value)
                .get();
    }
}

package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.List;

@Entity
@JsonIgnoreProperties({"section", "sections"})
public class Section extends BaseModel {
    private int number;
    @Reference
    private Course course;
    @Reference
    private List<Student> students;
    @Reference
    private Instructor instructor;
    private int capacity;
    private int day;
    private int startTime;
    private int endTime;
    @Reference
    private List<GradeType> gradeTypes;

    public List<GradeType> getGradeTypes() {
        return gradeTypes;
    }

    public void setGradeTypes(List<GradeType> gradeTypes) {
        this.gradeTypes = gradeTypes;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

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

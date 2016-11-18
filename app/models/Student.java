package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.List;

@Entity
public class Student extends User{
    @Indexed(unique = true)
    private String studentID;
    private String firstName;
    private String lastName;
    private int semester;
    private int year;
    private double cgpa;
    @Reference
    private List<Section> sections;

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
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

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public static Student findByID(String id){
        return DBConnection.getDatastore()
                .get(Student.class, new ObjectId(id));
    }

    public static Student findByID(ObjectId id){
        return DBConnection.getDatastore()
                .get(Student.class, id);
    }

    public static Student findByField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Student.class)
                .field(field).equal(value)
                .get();
    }
}

package models;

import com.fasterxml.jackson.annotation.*;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.List;
import java.util.Map;

@Entity
public class Student extends User {
    @Indexed()
    private String studentID;
    private int semester;
    private int year;
    private double cgpa;
    @Reference
    @JsonIgnoreProperties({"student", "students"})
    private List<Section> sections;
    @Embedded
    private Map<GradeType, Integer> grades;
    @Reference
    @JsonIgnoreProperties({"student", "students"})
    private Department department;
    private Department oldDepartment;

    public Map<GradeType, Integer> getGrades() {
        return grades;
    }

    public void setGrades(Map<GradeType, Integer> grades) {
        this.grades = grades;
    }

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

    public static List<Student> getAll() {
        return DBConnection.getDatastore()
                .createQuery(Student.class)
                .asList();
    }

    public static List<Student> getAllWithField(String field, String value) {
        return DBConnection.getDatastore()
                .createQuery(Student.class)
                .field(field)
                .equal(value)
                .asList();
    }

    public static Student findByID(String id) {
        return DBConnection.getDatastore()
                .get(Student.class, new ObjectId(id));
    }

    public static Student findByID(ObjectId id) {
        return DBConnection.getDatastore()
                .get(Student.class, id);
    }

    public static Student findByField(String field, String value) {
        return DBConnection.getDatastore()
                .createQuery(Student.class)
                .field(field).equal(value)
                .get();
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        if (department == null) {
            this.department.getStudents().remove(this);
            oldDepartment = this.department;
            this.department = null;
        } else if (this.department == null) {
            this.department = department;
            department.getStudents().add(this);
        } else if (!this.department.getId().equals(department.getId())) {
            oldDepartment = this.department;
            this.department = department;
        } else {
            oldDepartment = null;
        }
    }

    @Override
    public void save() {
        if (oldDepartment != null) {
            oldDepartment.getStudents().remove(this);
            oldDepartment.save();
            if (department != null) {
                department.getStudents().add(this);
            }
            oldDepartment = null;
        }
        super.save();
        if (this.department != null) {
            this.department.save();
        }
    }

    @Override
    public void remove() {
        department.getStudents().remove(this);
        department.save();
        for (Section section : sections) {
            section.getStudents().remove(this);
            section.save();
        }
        super.remove();
    }

    public boolean register(Section section){
        if(section.getStudents().size() >= section.getCapacity()){
            return false;
        }
        sections.add(section);
        section.getStudents().add(this);
        return true;
    }

    public void unregister(Section section){
        sections.remove(section);
        section.getStudents().remove(this);
    }

}

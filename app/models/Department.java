package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.Iterator;
import java.util.List;

@Entity
public class Department extends BaseModel {
    private String name;
    @Reference
    @JsonIgnoreProperties({"department", "departments"})
    private List<Program> programs;
    @Reference
    @JsonIgnoreProperties({"department", "departments"})
    private List<Instructor> instructors;
    @Reference
    @JsonIgnoreProperties({"department", "departments"})
    private List<Student> students;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public static List<Department> getAll(){
        return DBConnection.getDatastore()
                .createQuery(Department.class)
                .asList();
    }

    public static List<Department> getAllWithField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Department.class)
                .field(field)
                .equal(value)
                .asList();
    }

    public static Department findByID(String id){
        return DBConnection.getDatastore()
                .get(Department.class, new ObjectId(id));
    }

    public static Department findByID(ObjectId id){
        return DBConnection.getDatastore()
                .get(Department.class, id);
    }

    public static Department findByField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Department.class)
                .field(field).equal(value)
                .get();
    }

    @Override
    public void remove() {
        for (Iterator<Program> iterator = programs.iterator(); iterator.hasNext();) {
            iterator.next().removeIter();
            iterator.remove();
        }
        for(Student student : students){
            student.setDepartment(null);
            student.save();
        }
        for (Instructor instructor : instructors) {
            instructor.setDepartment(null);
            instructor.save();
        }
        super.remove();
    }
}

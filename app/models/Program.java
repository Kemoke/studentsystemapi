package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.List;

@Entity
public class Program extends BaseModel{
    private String name;
    @Reference
    private Department department;
    @Reference
    private List<Course> courses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public static List<Program> getAll(){
        return DBConnection.getDatastore()
                .createQuery(Program.class)
                .asList();
    }

    public static List<Program> getAllWithField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Program.class)
                .field(field)
                .equal(value)
                .asList();
    }

    public static Program findByID(String id){
        return DBConnection.getDatastore()
                .get(Program.class, new ObjectId(id));
    }

    public static Program findByID(ObjectId id){
        return DBConnection.getDatastore()
                .get(Program.class, id);
    }

    public static Program findByField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Program.class)
                .field(field).equal(value)
                .get();
    }
}

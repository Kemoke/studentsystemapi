package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.Iterator;
import java.util.List;

@Entity
public class Program extends BaseModel{
    private String name;
    @Reference
    @JsonIgnoreProperties({"program", "programs"})
    private Department department;
    @Reference
    @JsonIgnoreProperties({"program", "programs"})
    private List<Course> courses;
    private String curriculumUri;
    private Department oldDepartment;

    public String getCurriculumUri() {
        return curriculumUri;
    }

    public void setCurriculumUri(String curriculumUri) {
        this.curriculumUri = curriculumUri;
    }

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
        if(this.department == null){
            this.department = department;
            department.getPrograms().add(this);
        }
        if(!this.department.getId().equals(department.getId())){
            oldDepartment = this.department;
            this.department = department;
        } else {
            oldDepartment = null;
        }
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

    @Override
    public void save() {
        if(oldDepartment != null){
            oldDepartment.getPrograms().remove(this);
            oldDepartment.save();
            department.getPrograms().add(this);
            oldDepartment = null;
        }
        super.save();
        department.save();
    }

    @Override
    public void remove() {
        department.getPrograms().remove(this);
        department.save();
        for (Course course : courses) {
            course.remove();
        }
        super.remove();
    }

    @Override
    public void removeIter(){
        for (Iterator<Course> iterator = courses.iterator(); iterator.hasNext();) {
            iterator.next().removeIter();
            iterator.remove();
        }
        super.removeIter();
    }
}

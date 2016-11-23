package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.List;

@Entity
public class Instructor extends User{
    @Indexed()
    private String instructorID;
    @Reference
    @JsonIgnoreProperties({"instructor", "instructors"})
    private List<Section> sections;
    @Reference
    @JsonIgnoreProperties({"instructor", "instructors"})
    private Department department;
    private Department oldDepartment;

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        if(department == null){
            this.department.getInstructors().remove(this);
            oldDepartment = this.department;
            this.department = null;
        }else if(this.department == null){
            this.department = department;
            department.getInstructors().add(this);
        }else if(!this.department.getId().equals(department.getId())){
            oldDepartment = this.department;
            this.department = department;
        } else {
            oldDepartment = null;
        }
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

    @Override
    public void save() {
        if(oldDepartment != null){
            oldDepartment.getInstructors().remove(this);
            oldDepartment.save();
            if(department != null){
                department.getInstructors().add(this);
            }
            oldDepartment = null;
        }
        super.save();
        if(this.department != null){
            this.department.save();
        }
    }

    @Override
    public void remove() {
        department.getInstructors().remove(this);
        department.save();
        for (Section section : sections) {
            section.setInstructor(null);
            section.save();
        }
        super.remove();
    }
}

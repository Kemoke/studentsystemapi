package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.List;

@Entity
@JsonIgnoreProperties({"department", "departments"})
public class Department extends BaseModel {
    private String name;
    @Reference
    private List<Program> programs;

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
}

package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.List;

@Entity
public class Course extends BaseModel {
    private String name;
    private String code;
    private int ects;
    @Reference
    private Program program;
    @Reference
    private List<Section> sections;

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public static List<Course> getAll(){
        return DBConnection.getDatastore()
                .createQuery(Course.class)
                .asList();
    }

    public static List<Course> getAllWithField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Course.class)
                .field(field)
                .equal(value)
                .asList();
    }

    public static Course findByID(String id){
        return DBConnection.getDatastore()
                .get(Course.class, new ObjectId(id));
    }

    public static Course findByID(ObjectId id){
        return DBConnection.getDatastore()
                .get(Course.class, id);
    }

    public static Course findByField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Course.class)
                .field(field).equal(value)
                .get();
    }
}

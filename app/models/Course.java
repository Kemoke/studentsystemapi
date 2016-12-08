package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;
import sun.reflect.Reflection;

import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.List;

@Entity
public class Course extends BaseModel {
    private String name;
    private String code;
    private int ects;
    @Reference
    @JsonIgnoreProperties({"course", "courses"})
    private Program program;
    @Reference
    @JsonIgnoreProperties({"course", "courses"})
    private List<Section> sections;
    private Program oldprogram;

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
        if(this.program == null){
            this.program = program;
            program.getCourses().add(this);
        };
        if(!this.program.getId().equals(program.getId())){
            oldprogram = this.program;
            this.program = program;
        } else {
            oldprogram = null;
        }
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

    @Override
    public void save() {
        if(oldprogram != null){
            oldprogram.getCourses().remove(this);
            oldprogram.save();
            program.getCourses().add(this);
            oldprogram = null;
        }
        super.save();
        program.save();
    }

    @Override
    public void remove() {
        program.getCourses().remove(this);
        program.save();
        for (Section section : sections) {
            section.removeIter();
        }
        super.remove();
    }

    @Override
    public void removeIter() {
        for (Iterator<Section> iterator = sections.iterator(); iterator.hasNext();) {
            iterator.next().removeIter();
            iterator.remove();
        }
        super.removeIter();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return (name != null ? name.equals(course.name) : course.name == null) && (code != null ? code.equals(course.code) : course.code == null) && (program != null ? program.equals(course.program) : course.program == null);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (program != null ? program.hashCode() : 0);
        return result;
    }
}

package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.List;

@Entity
@JsonIgnoreProperties({"gradeType", "gradeTypes"})
public class GradeType extends BaseModel{
    @Reference
    private Section section;
    private String name;
    private int weight;

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public static List<GradeType> getAll(){
        return DBConnection.getDatastore()
                .createQuery(GradeType.class)
                .asList();
    }

    public static List<GradeType> getAllWithField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(GradeType.class)
                .field(field)
                .equal(value)
                .asList();
    }

    public static GradeType findByID(String id){
        return DBConnection.getDatastore()
                .get(GradeType.class, new ObjectId(id));
    }

    public static GradeType findByID(ObjectId id){
        return DBConnection.getDatastore()
                .get(GradeType.class, id);
    }

    public static GradeType findByField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(GradeType.class)
                .field(field).equal(value)
                .get();
    }
}

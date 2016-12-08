package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.List;

@Entity
public class GradeType extends BaseModel{
    @Reference
    @JsonIgnoreProperties({"gradeType", "gradeTypes"})
    private Section section;
    private String name;
    private int weight;
    private Section oldSection;

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        if(!this.section.getId().equals(section.getId())){
            oldSection = this.section;
        } else {
            oldSection = null;
        }
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

    @Override
    public void save() {
        if(oldSection != null){
            oldSection.getGradeTypes().remove(this);
            oldSection.save();
            section.getGradeTypes().add(this);
            section.save();
            oldSection = null;
        }
        super.save();
    }

    @Override
    public void remove() {
        section.getGradeTypes().remove(this);
        section.save();
        super.remove();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GradeType gradeType = (GradeType) o;

        return weight == gradeType.weight && (section != null ? section.equals(gradeType.section) : gradeType.section == null) && (name != null ? name.equals(gradeType.name) : gradeType.name == null);
    }

    @Override
    public int hashCode() {
        int result = section != null ? section.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + weight;
        return result;
    }
}

package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.List;

@Entity
public class Section extends BaseModel {
    private int number;
    @Reference
    @JsonIgnoreProperties({"section", "sections"})
    private Course course;
    private Course oldCourse;
    @Reference
    @JsonIgnoreProperties({"section", "sections"})
    private List<Student> students;
    @Reference
    @JsonIgnoreProperties({"section", "sections"})
    private Instructor instructor;
    private Instructor oldInstructor;
    private int capacity;
    private int day;
    private int startTime;
    private int endTime;
    @Reference
    @JsonIgnoreProperties({"section", "sections"})
    private List<GradeType> gradeTypes;
    private String syllabusUri;

    public List<GradeType> getGradeTypes() {
        return gradeTypes;
    }

    public void setGradeTypes(List<GradeType> gradeTypes) {
        this.gradeTypes = gradeTypes;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        if(this.instructor == null) {
            this.instructor = instructor;
            instructor.getSections().add(this);
        };
        if(instructor == null || !this.instructor.getId().equals(instructor.getId())){
            oldInstructor = this.instructor;
            this.instructor = instructor;
        } else {
            oldInstructor = null;
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        if(this.course == null){
            this.course = course;
            course.getSections().add(this);
        }
        if(!this.course.getId().equals(course.getId())){
            oldCourse = this.course;
            this.course = course;
        } else {
            oldCourse = null;
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public static List<Section> getAll(){
        return DBConnection.getDatastore()
                .createQuery(Section.class)
                .asList();
    }

    public static List<Section> getAllWithField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Section.class)
                .field(field)
                .equal(value)
                .asList();
    }

    public static Section findByID(String id){
        return DBConnection.getDatastore()
                .get(Section.class, new ObjectId(id));
    }

    public static Section findByID(ObjectId id){
        return DBConnection.getDatastore()
                .get(Section.class, id);
    }

    public static Section findByField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(Section.class)
                .field(field).equal(value)
                .get();
    }

    @Override
    public void save() {
        if(oldCourse != null){
            oldCourse.getSections().remove(this);
            oldCourse.save();
            course.getSections().add(this);
        }
        if(oldInstructor != null){
            oldInstructor.getSections().remove(this);
            oldInstructor.save();
            if(instructor != null){
                instructor.getSections().add(this);
            }
        }
        oldInstructor = null;
        oldCourse = null;
        super.save();
        course.save();
        if(instructor != null){
            instructor.save();
        }
    }

    @Override
    public void remove() {
        if(course != null){
            course.getSections().remove(this);
            course.save();
        }
        if(instructor != null){
            instructor.getSections().remove(this);
            instructor.save();
        }
        for (Student student : students) {
            student.getSections().remove(this);
            student.save();
        }
        super.remove();
    }

    @Override
    public void removeIter() {
        if(instructor != null){
            instructor.getSections().remove(this);
            instructor.save();
        }
        for (Student student : students) {
            student.getSections().remove(this);
            student.save();
        }
        super.removeIter();
    }

    public String getSyllabusUri() {
        return syllabusUri;
    }

    public void setSyllabusUri(String syllabusUri) {
        this.syllabusUri = syllabusUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section section = (Section) o;

        if (number != section.number) return false;
        return course != null ? course.equals(section.course) : section.course == null;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (course != null ? course.hashCode() : 0);
        return result;
    }
}

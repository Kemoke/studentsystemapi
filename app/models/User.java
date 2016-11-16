package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;
import services.DBConnection;

import java.util.List;

@Entity
public class User extends BaseModel {
    @Indexed(unique = true)
    private String username;
    private String password;
    @Indexed(unique = true)
    private String email;
    private int userType;
    @Reference
    private Student student;
    @Reference
    private Instructor instructor;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public static List<User> getAll(){
        return DBConnection.getDatastore()
                .createQuery(User.class)
                .asList();
    }

    public static List<User> getAllWithField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(User.class)
                .field(field)
                .equal(value)
                .asList();
    }

    public static User findByID(String id){
        return DBConnection.getDatastore()
                .get(User.class, new ObjectId(id));
    }

    public static User findByID(ObjectId id){
        return DBConnection.getDatastore()
                .get(User.class, id);
    }

    public static User findByField(String field, String value){
        return DBConnection.getDatastore()
                .createQuery(User.class)
                .field(field).equal(value)
                .get();
    }

}

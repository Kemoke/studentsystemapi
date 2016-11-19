package controllers.crud;

import filters.AdminAuth;
import filters.StudentAuth;
import models.Student;
import org.mindrot.jbcrypt.BCrypt;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;
import java.util.Map;

public class StudentController extends Controller {

    @Security.Authenticated(AdminAuth.class)
    public Result list(){
        List<Student> students = Student.getAll();
        return ok(Json.toJson(students));
    }

    @Security.Authenticated(AdminAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result add(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Student newStudent = new Student();
        String rawPass = params.get("password")[0];
        String hashPass = BCrypt.hashpw(rawPass, BCrypt.gensalt());
        newStudent.setPassword(hashPass);
        newStudent.setUsername(params.get("username")[0]);
        newStudent.setEmail(params.get("email")[0]);
        newStudent.setStudentID(params.get("studentId")[0]);
        newStudent.setFirstName(params.get("firstname")[0]);
        newStudent.setLastName(params.get("lastname")[0]);
        newStudent.setSemester(Integer.valueOf(params.get("semester")[0]));
        newStudent.setYear(Integer.valueOf(params.get("year")[0]));
        newStudent.setCgpa(Double.valueOf(params.get("cgpa")[0]));
        newStudent.save();
        return ok(Json.toJson(newStudent));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result get(String id){
        Student student = Student.findByID(id);
        return ok(Json.toJson(student));
    }

    @Security.Authenticated(StudentAuth.class)
    public Result getSelf(){
        String username = request().username();
        Student student = Student.findByField("email", username);
        return ok(Json.toJson(student));
    }

    @Security.Authenticated(AdminAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result edit(String id){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Student student = Student.findByID(id);
        student.setUsername(params.get("username")[0]);
        student.setEmail(params.get("email")[0]);
        student.setStudentID(params.get("studentId")[0]);
        student.setFirstName(params.get("firstname")[0]);
        student.setLastName(params.get("lastname")[0]);
        student.setSemester(Integer.valueOf(params.get("semester")[0]));
        student.setYear(Integer.valueOf(params.get("year")[0]));
        student.setCgpa(Double.valueOf(params.get("cgpa")[0]));
        student.save();
        return ok(Json.toJson(student));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result delete(String id){
        Student student = Student.findByID(id);
        student.remove();
        return ok("deleted");
    }
}

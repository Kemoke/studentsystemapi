package controllers.crud;

import filters.AdminAuth;
import filters.InstructorAuth;
import models.Instructor;
import org.mindrot.jbcrypt.BCrypt;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;
import java.util.Map;

public class InstructorController extends Controller {

    @Security.Authenticated(AdminAuth.class)
    public Result list(){
        List<Instructor> instructors = Instructor.getAll();
        return ok(Json.toJson(instructors));
    }

    @Security.Authenticated(AdminAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result add(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Instructor newInstructor = new Instructor();
        String rawPass = params.get("password")[0];
        String hashPass = BCrypt.hashpw(rawPass, BCrypt.gensalt());
        newInstructor.setPassword(hashPass);
        newInstructor.setUsername(params.get("username")[0]);
        newInstructor.setEmail(params.get("email")[0]);
        newInstructor.setInstructorID(params.get("instructorId")[0]);
        newInstructor.setFirstName(params.get("firstname")[0]);
        newInstructor.setLastName(params.get("lastname")[0]);
        newInstructor.save();
        return ok(Json.toJson(newInstructor));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result get(String id){
        Instructor instructor = Instructor.findByID(id);
        return ok(Json.toJson(instructor));
    }

    @Security.Authenticated(InstructorAuth.class)
    public Result getSelf(){
        String username = request().username();
        Instructor instructor = Instructor.findByField("email", username);
        return ok(Json.toJson(instructor));
    }

    @Security.Authenticated(AdminAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result edit(String id){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Instructor instructor = Instructor.findByID(id);
        instructor.setUsername(params.get("username")[0]);
        instructor.setEmail(params.get("email")[0]);
        instructor.setInstructorID(params.get("instructorId")[0]);
        instructor.setFirstName(params.get("firstname")[0]);
        instructor.setLastName(params.get("lastname")[0]);
        instructor.save();
        return ok(Json.toJson(instructor));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result delete(String id){
        Instructor instructor = Instructor.findByID(id);
        instructor.remove();
        return ok("deleted");
    }
}

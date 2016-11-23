package controllers.crud;

import filters.AdminAuth;
import models.Course;
import models.Log;
import models.Program;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;
import java.util.Map;

public class CourseController extends Controller {

    @Security.Authenticated(AdminAuth.class)
    public Result list(){
        List<Course> courses = Course.getAll();
        return ok(Json.toJson(courses));
    }

    @Security.Authenticated(AdminAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result add(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Course course = new Course();
        Program courseProgram = Program.findByID(params.get("programId")[0]);
        course.setName(params.get("name")[0]);
        course.setCode(params.get("code")[0]);
        course.setEcts(Integer.valueOf(params.get("ects")[0]));
        course.setProgram(courseProgram);
        course.save();
        Log.LogAction(request().username(), "Add course id:"+course.getId());
        return ok(Json.toJson(course));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result get(String id){
        Course course = Course.findByID(id);
        return ok(Json.toJson(course));
    }

    @Security.Authenticated(AdminAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result edit(String id){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Course course = Course.findByID(id);
        Program newProgram = Program.findByID(params.get("programId")[0]);
        course.setName(params.get("name")[0]);
        course.setCode(params.get("code")[0]);
        course.setEcts(Integer.valueOf(params.get("ects")[0]));
        course.setProgram(newProgram);
        course.save();
        Log.LogAction(request().username(), "Edit course id:"+course.getId());
        return ok(Json.toJson(course));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result delete(String id){
        Course course = Course.findByID(id);
        Log.LogAction(request().username(), "Remove course id:"+course.getId());
        course.remove();
        return ok("deleted");
    }
}
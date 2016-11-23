package controllers.crud;

import filters.AdminAuth;
import models.Course;
import models.Instructor;
import models.Log;
import models.Section;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;
import java.util.Map;

public class SectionController extends Controller {

    @Security.Authenticated(AdminAuth.class)
    public Result list(){
        List<Section> sections = Section.getAll();
        return ok(Json.toJson(sections));
    }

    @Security.Authenticated(AdminAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result add(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Section section = new Section();
        Course sectionCourse = Course.findByID(params.get("courseId")[0]);
        Instructor instructor = Instructor.findByID(params.get("instructorId")[0]);
        section.setCapacity(Integer.valueOf(params.get("capacity")[0]));
        section.setCourse(sectionCourse);
        section.setInstructor(instructor);
        section.save();
        Log.LogAction(request().username(), "Add section id:"+section.getId());
        return ok(Json.toJson(section));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result get(String id){
        Section section = Section.findByID(id);
        return ok(Json.toJson(section));
    }

    @Security.Authenticated(AdminAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result edit(String id){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Section section = Section.findByID(id);
        Course newCourse = Course.findByID(params.get("courseId")[0]);
        Instructor newInstructor = Instructor.findByID(params.get("instructorId")[0]);
        section.setCapacity(Integer.valueOf(params.get("capacity")[0]));
        section.setCourse(newCourse);
        section.setInstructor(newInstructor);
        section.save();
        Log.LogAction(request().username(), "Edit section id:"+section.getId());
        return ok(Json.toJson(section));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result delete(String id){
        Section section = Section.findByID(id);
        Log.LogAction(request().username(), "Remove section id:"+section.getId());
        section.remove();
        return ok("deleted");
    }
}

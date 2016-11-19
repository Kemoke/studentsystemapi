package controllers.crud;

import filters.AdminAuth;
import models.Course;
import models.Instructor;
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
        sectionCourse.getSections().add(section);
        instructor.getSections().add(section);
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
        Course sectionCourse = Course.findByID(params.get("courseId")[0]);
        Instructor instructor = Instructor.findByID(params.get("instructorId")[0]);
        sectionCourse.getSections().remove(section);
        section.setCapacity(Integer.valueOf(params.get("capacity")[0]));
        section.setCourse(sectionCourse);
        section.setInstructor(instructor);
        section.save();
        sectionCourse.getSections().add(section);
        instructor.getSections().add(section);
        return ok(Json.toJson(section));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result delete(String id){
        Section section = Section.findByID(id);
        section.remove();
        return ok("deleted");
    }
}

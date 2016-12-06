package controllers;

import filters.StudentAuth;
import models.Section;
import models.Student;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Map;

public class StudentActionController extends Controller {
    @Security.Authenticated(StudentAuth.class)
    public Result register(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Student student = Student.findByField("email", request().username());
        Section section = Section.findByID(params.get("sectionId")[0]);
        if(student.register(section)){
            return ok(Json.toJson(section));
        } else {
            return internalServerError("section is full");
        }
    }

    @Security.Authenticated(StudentAuth.class)
    public Result unregister(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Student student = Student.findByField("email", request().username());
        Section section = Section.findByID(params.get("sectionId")[0]);
        if(student.getSections().contains(section)){
            student.unregister(section);
            return ok(Json.toJson(section));
        } else {
            return internalServerError("That section is not registered");
        }
    }
}

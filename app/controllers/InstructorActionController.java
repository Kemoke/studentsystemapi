package controllers;

import filters.InstructorAuth;
import models.GradeType;
import models.Section;
import models.Student;
import org.h2.store.fs.FileUtils;
import play.Environment;
import play.api.Play;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.MultipartFormData.FilePart;

import java.io.File;
import java.util.Map;

public class InstructorActionController extends Controller {

    @Security.Authenticated(InstructorAuth.class)
    @BodyParser.Of(BodyParser.MultipartFormData.class)
    public Result setCurriculum(){
        Http.MultipartFormData<File> data = request().body().asMultipartFormData();
        Map<String, String[]> params = data.asFormUrlEncoded();
        FilePart<File> file = data.getFile("curriculum");
        if(file != null){
            String name = file.getFilename();
            String type = file.getContentType();
            File curr = file.getFile();
            String fileUri = "https://srv.kemoke.net/"+name;
            FileUtils.move(curr.getAbsolutePath(), Play.current().path().getAbsolutePath()+"/public/"+name);
        }
        return ok("uploaded");
    }

    @Security.Authenticated(InstructorAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result setGradeTypes(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        String[] sectionIds = params.get("sectionId");
        String[] names = params.get("name");
        String[] weights = params.get("weight");
        GradeType[] gradeTypes = new GradeType[sectionIds.length];
        for (int i = 0; i < gradeTypes.length; i++) {
            gradeTypes[i].setName(names[i]);
            gradeTypes[i].setWeight(Integer.valueOf(weights[i]));
            gradeTypes[i].setSection(Section.findByID(sectionIds[i]));
            gradeTypes[i].save();
        }
        return ok(Json.toJson(gradeTypes));
    }

    @Security.Authenticated(InstructorAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result setGrade(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Student student = Student.findByID(params.get("studentId")[0]);
        GradeType type = GradeType.findByID(params.get("gradeTypeId")[0]);
        student.getGrades().put(type, Integer.valueOf(params.get("score")[0]));
        student.save();
        return ok(Json.toJson(student));
    }
}

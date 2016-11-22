package controllers.crud;

import filters.AdminAuth;
import models.Department;
import models.Program;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;
import java.util.Map;

public class ProgramController extends Controller {

    @Security.Authenticated(AdminAuth.class)
    public Result list(){
        List<Program> programs = Program.getAll();
        return ok(Json.toJson(programs));
    }

    @Security.Authenticated(AdminAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result add(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Program program = new Program();
        Department progDepartment = Department.findByID(params.get("departmentId")[0]);
        program.setName(params.get("name")[0]);
        program.setDepartment(progDepartment);
        program.save();
        return ok(Json.toJson(program));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result get(String id){
        Program program = Program.findByID(id);
        return ok(Json.toJson(program));
    }

    @Security.Authenticated(AdminAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result edit(String id){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Program program = Program.findByID(id);
        Department newDepartment = Department.findByID(params.get("departmentId")[0]);
        program.setName(params.get("name")[0]);
        program.setDepartment(newDepartment);
        program.save();
        return ok(Json.toJson(program));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result delete(String id){
        Program program = Program.findByID(id);
        program.getDepartment().getPrograms().remove(program);
        program.getDepartment().save();
        program.remove();
        return ok("deleted");
    }
}

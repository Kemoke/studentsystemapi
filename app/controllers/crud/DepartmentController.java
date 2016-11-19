package controllers.crud;

import filters.AdminAuth;
import models.Department;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;
import java.util.Map;

public class DepartmentController extends Controller {

    @Security.Authenticated(AdminAuth.class)
    public Result list(){
        List<Department> departments = Department.getAll();
        return ok(Json.toJson(departments));
    }

    @Security.Authenticated(AdminAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result add(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Department department = new Department();
        department.setName(params.get("name")[0]);
        department.save();
        return ok(Json.toJson(department));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result get(String id){
        Department department = Department.findByID(id);
        return ok(Json.toJson(department));
    }

    @Security.Authenticated(AdminAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result edit(String id){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Department department = Department.findByID(id);
        department.setName(params.get("name")[0]);
        department.save();
        return ok(Json.toJson(department));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result delete(String id){
        Department department = Department.findByID(id);
        department.remove();
        return ok("deleted");
    }
}

package controllers;

import filters.AdminAuth;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;
import java.util.Map;

public class UserController extends Controller {

    @Security.Authenticated(AdminAuth.class)
    public Result list(){
        List<User> users = User.getAll();
        return ok(Json.toJson(users));
    }

    @Security.Authenticated(AdminAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result add(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        User newUser = new User();
        String rawPass = params.get("password")[0];
        String hashPass = BCrypt.hashpw(rawPass, BCrypt.gensalt());
        newUser.setPassword(hashPass);
        newUser.setUsername(params.get("username")[0]);
        newUser.setEmail(params.get("email")[0]);
        newUser.save();
        return ok(Json.toJson(newUser));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result get(String id){
        User user = User.findByID(id);
        return ok(Json.toJson(user));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result getSelf(){
        String username = request().username();
        User user = User.findByField("email", username);
        return ok(Json.toJson(user));
    }

    @Security.Authenticated(AdminAuth.class)
    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result edit(String id){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        User user = User.findByID(id);
        user.setUsername(params.get("username")[0]);
        user.setEmail(params.get("email")[0]);
        user.save();
        return ok(Json.toJson(user));
    }

    @Security.Authenticated(AdminAuth.class)
    public Result delete(String id){
        User user = User.findByID(id);
        user.remove();
        return ok("deleted");
    }
}

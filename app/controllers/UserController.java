package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import filters.Auth;
import models.User;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.Datastore;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.DBConnection;

import java.util.List;

/**
 * Created by kemoke on 11/15/16.
 */
public class UserController extends Controller {

    @Security.Authenticated(Auth.class)
    public Result listUsers(){
        Datastore datastore = DBConnection.getDatastore();
        List<User> users = datastore.createQuery(User.class).asList();
        JsonNode usersJson = Json.toJson(users);
        return ok(usersJson);
    }

    @Security.Authenticated(Auth.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result addUser(){
        JsonNode request = request().body().asJson();
        User newUser = new User();
        String rawPass = request.findPath("password").textValue();
        String hashPass = BCrypt.hashpw(rawPass, BCrypt.gensalt());
        newUser.setPassword(hashPass);
        newUser.setUsername(request.findPath("username").textValue());
        newUser.setEmail(request.findPath("email").textValue());
        newUser.save();
        JsonNode user = Json.toJson(newUser);
        return ok(user);
    }

    @Security.Authenticated(Auth.class)
    public Result getUser(String id){
        User user = DBConnection.getDatastore().get(User.class, new ObjectId(id));
        JsonNode userJson = Json.toJson(user);
        return ok(userJson);
    }

    @Security.Authenticated(Auth.class)
    public Result getSelf(){
        User user = DBConnection.getDatastore().createQuery(User.class).field("email").equal(request().username()).get();
        JsonNode userJson = Json.toJson(user);
        return ok(userJson);
    }

    @Security.Authenticated(Auth.class)
    public Result editUser(String id){
        JsonNode request = request().body().asJson();
        User user = DBConnection.getDatastore().get(User.class, new ObjectId(id));
        user.setUsername(request.findPath("username").textValue());
        user.setEmail(request.findPath("email").textValue());
        user.save();
        JsonNode userJson = Json.toJson(user);
        return ok(userJson);
    }

    @Security.Authenticated(Auth.class)
    public Result deleteUser(String id){
        User user = DBConnection.getDatastore().get(User.class, new ObjectId(id));
        user.remove();
        return ok("deleted");
    }
}

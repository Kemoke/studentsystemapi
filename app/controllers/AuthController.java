package controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.Admin;
import models.Instructor;
import models.Student;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.AppConfig;
import util.TokenJson;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class AuthController extends Controller {

    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result login(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        String email = params.get("email")[0];
        String password = params.get("password")[0];
        User user = Student.findByField("email", email);
        if(user != null && BCrypt.checkpw(password, user.getPassword())){
            TokenJson token = new TokenJson(makeToken(user), "student");
            return ok(Json.toJson(token));
        }
        user = Instructor.findByField("email", email);
        if(user != null && BCrypt.checkpw(password, user.getPassword())){
            TokenJson token = new TokenJson(makeToken(user), "instructor");
            return ok(Json.toJson(token));
        }
        user = Admin.findByField("email", email);
        if(user != null && BCrypt.checkpw(password, user.getPassword())){
            TokenJson token = new TokenJson(makeToken(user), "admin");
            return ok(Json.toJson(token));
        }
        return unauthorized("Invalid credentials");
    }

    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result register(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        Admin admin = new Admin();
        admin.setEmail(params.get("email")[0]);
        admin.setUsername(params.get("username")[0]);
        String rawPass = params.get("password")[0];
        String hashPass = BCrypt.hashpw(rawPass, BCrypt.gensalt());
        admin.setPassword(hashPass);
        admin.save();
        return ok(Json.toJson(admin));
    }

    private String makeToken(User user){
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        Date expDate = calendar.getTime();
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setIssuer("kemoke.net")
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS256, AppConfig.JWTKey)
                .compact();
    }
}
package controllers;

import cache.LoginCache;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.*;
import org.mindrot.jbcrypt.BCrypt;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.AppConfig;
import services.DBConnection;
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
        LoginCache cache = DBConnection.getLoginCache();
        String userType = cache.getUserType(email);
        if(userType != null && BCrypt.checkpw(password, cache.getUserPassword(email))){
            TokenJson token = new TokenJson(makeToken(email), userType);
            return ok(Json.toJson(token));
        }
        /*User user = Student.findByField("email", email);
        if(user != null && BCrypt.checkpw(password, user.getPassword())){
            TokenJson token = new TokenJson(makeToken(user), "Student");
            return ok(Json.toJson(token));
        }
        user = Instructor.findByField("email", email);
        if(user != null && BCrypt.checkpw(password, user.getPassword())){
            TokenJson token = new TokenJson(makeToken(user), "Instructor");
            return ok(Json.toJson(token));
        }
        user = Admin.findByField("email", email);
        if(user != null && BCrypt.checkpw(password, user.getPassword())){
            TokenJson token = new TokenJson(makeToken(user), "Admin");
            return ok(Json.toJson(token));
        }*/
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
        DBConnection.getLoginCache().put(admin);
        return ok(Json.toJson(admin));
    }

    private String makeToken(String email){
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        Date expDate = calendar.getTime();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setIssuer("kemoke.net")
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS256, AppConfig.JWTKey)
                .compact();
    }
}
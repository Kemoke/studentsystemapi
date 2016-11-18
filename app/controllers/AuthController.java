package controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.AppConfig;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class AuthController extends Controller {

    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result login(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();

        String email = params.get("email")[0];
        String password = params.get("password")[0];
        User user = User.findByField("email", email);
        if(user != null && BCrypt.checkpw(password, user.getPassword())){
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            Date expDate = calendar.getTime();
            String jwt = Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(now)
                    .setIssuer("kemoke.net")
                    .setExpiration(expDate)
                    .signWith(SignatureAlgorithm.HS256, AppConfig.JWTKey)
                    .compact();
            return ok(jwt);
        }
        return unauthorized();
    }

    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result register(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        User user = new User();
        user.setEmail(params.get("email")[0]);
        user.setUsername(params.get("username")[0]);
        String rawPass = params.get("password")[0];
        String hashPass = BCrypt.hashpw(rawPass, BCrypt.gensalt());
        user.setPassword(hashPass);
        user.save();
        return ok(Json.toJson(user));
    }
}
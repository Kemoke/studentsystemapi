package controllers;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.User;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;
import play.api.db.DBComponents;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.AppConfig;
import services.DBConnection;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class AuthController extends Controller {

    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result login(){
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        String email = params.get("email")[0];
        String password = params.get("password")[0];
        User user = DBConnection.getDatastore().createQuery(User.class).field("email").equal(email).get();
        if(user != null && BCrypt.checkpw(password, user.getPassword())){
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            calendar.add(Calendar.HOUR, 1);
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
}
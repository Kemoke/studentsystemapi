package filters;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import models.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.AppConfig;
import services.DBConnection;

import java.sql.Struct;

/**
 * Created by kemoke on 11/15/16.
 */
public class Auth extends Security.Authenticator{

    @Override
    public String getUsername(Http.Context ctx) {
        String tokenString = getToken(ctx);
        try{
            String email = Jwts.parser().setSigningKey(AppConfig.JWTKey).parseClaimsJws(tokenString).getBody().getSubject();
            User user = DBConnection.getDatastore().createQuery(User.class).field("email").equal(email).get();
            return user.getEmail();
        }catch (SignatureException | IllegalArgumentException | NullPointerException e){
            return null;
        }
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return unauthorized("Invalid or expired token");
    }

    private String getToken(Http.Context context){
        String[] authToken = context.request().headers().get("X-Auth-Token");
        if(authToken != null){
            return authToken[0];
        }
        return null;
    }
}

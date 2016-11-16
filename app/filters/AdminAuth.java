package filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import models.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.AppConfig;

import java.time.Instant;
import java.util.Date;

public class AdminAuth extends Security.Authenticator{

    @Override
    public String getUsername(Http.Context ctx) {
        String tokenString = getToken(ctx);
        try{
            Claims jwt = Jwts.parser().setSigningKey(AppConfig.JWTKey)
                    .parseClaimsJws(tokenString).getBody();
            if(jwt.getExpiration().getTime() < Date.from(Instant.now()).getTime()){
                return null;
            }
            String email = jwt.getSubject();
            User user = User.findByField("email", email);
            if(user.getUserType() != 0){
                return null;
            }
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

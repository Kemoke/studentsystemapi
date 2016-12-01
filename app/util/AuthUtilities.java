package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import play.mvc.Http;
import services.AppConfig;

import java.time.Instant;
import java.util.Date;

public class AuthUtilities {

    public static Claims getToken(Http.Context context){
        String[] authToken = context.request().headers().get("X-Auth-Token");
        if(authToken != null){
            try{
                Claims jwt = Jwts.parser().setSigningKey(AppConfig.JWTKey)
                        .parseClaimsJws(authToken[0]).getBody();
                if(jwt.getExpiration().getTime() < Date.from(Instant.now()).getTime()){
                    return null;
                }
                return jwt;
            }catch (Exception e){
                return null;
            }
        }
        return null;
    }
}

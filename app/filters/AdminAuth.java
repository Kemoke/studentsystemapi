package filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import models.Admin;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.DBConnection;
import util.AuthUtilities;

public class AdminAuth extends Security.Authenticator{

    @Override
    public String getUsername(Http.Context ctx) {
        try{
            Claims jwt = AuthUtilities.getToken(ctx);
            String email = jwt.getSubject();
            String type = DBConnection.getLoginCache().getUser(email).getType();
            if(!type.equals("Admin")){
                return null;
            }
            return email;
        }catch (SignatureException | IllegalArgumentException | NullPointerException e){
            return null;
        }
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return unauthorized("Invalid or expired token");
    }
}

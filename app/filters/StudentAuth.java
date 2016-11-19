package filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import models.Admin;
import models.Student;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import utility.AuthUtilities;

public class StudentAuth extends Security.Authenticator{

    @Override
    public String getUsername(Http.Context ctx) {
        try{
            Claims jwt = AuthUtilities.getToken(ctx);
            String email = jwt.getSubject();
            Student admin = Student.findByField("email", email);
            return admin.getEmail();
        }catch (SignatureException | IllegalArgumentException | NullPointerException e){
            return null;
        }
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return unauthorized("Invalid or expired token");
    }
}

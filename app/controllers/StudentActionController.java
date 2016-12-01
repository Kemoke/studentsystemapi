package controllers;

import filters.StudentAuth;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class StudentActionController extends Controller {
    @Security.Authenticated(StudentAuth.class)
    public Result register(){
        return internalServerError("Not implemented");
    }

    @Security.Authenticated(StudentAuth.class)
    public Result unregister(){
        return internalServerError("Not implemented");
    }
}

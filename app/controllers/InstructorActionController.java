package controllers;

import filters.InstructorAuth;
import play.mvc.Result;
import play.mvc.Controller;
import play.mvc.Security;

public class InstructorActionController extends Controller {

    @Security.Authenticated(InstructorAuth.class)
    public Result setCurriculum(){
        return internalServerError("Not implemented");
    }

    @Security.Authenticated(InstructorAuth.class)
    public Result setGradeTypes(){
        return internalServerError("Not implemented");
    }

    @Security.Authenticated(InstructorAuth.class)
    public Result setGrade(){
        return internalServerError("Not implemented");
    }
}

package controllers;

import java.util.Map;
import models.Student;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * The controller for the home page of this application.
 * 
 * @author Philip Johnson
 */
public class Application extends Controller {

  public static Result getIndex(long id) {
    Student student = (id == 0) ? new Student() : Student.findStudent(id);
    return ok(index.render(student));
  }
  
  public static Result postIndex() {
    // Retrieve the submitted form data from the request object.
    Map<String, String[]> formValues = request().body().asFormUrlEncoded();
    // Convert the form data into a Student model instance. 
    Student student = Student.makeInstance(formValues);
    if (student.hasErrors()) {
      flash("error", "Invalid student: " + student.toString());
      return ok(index.render(student));  
    }
    else {
      flash("success", "Valid student: " + student.toString());
      return badRequest(index.render(student));  
    }
  }
}

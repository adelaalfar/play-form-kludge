package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple model class used for form data retrieval.
 * Note that to implement persistence, this class should extend play.db.ebean.Model.
 * @author Philip Johnson
 */
public class Student {
  private Map<String, String> errorMap = new HashMap<>(); 
  private String name = "";
  private String password = "";
  private List<Hobby> hobbies = new ArrayList<>();  //Hobbies are optional.
  private GradeLevel level = GradeLevel.getDefaultLevel();
  private GradePointAverage gpa = GradePointAverage.getDefaultGPA();
  private List<Major> majors = new ArrayList<>(); //Majors are optional.
  
  public Student(){}
  
  /**
   * Creates a returns a new Student instance initialized from formValues.
   * If problems occur during binding, the errorMap field reports the problem(s).
   * Use hasErrors() to check if this instance is valid or not.  
   * @param formValues The values retrieved from the form.
   * @return A student instance. 
   */
  public static Student makeInstance(Map<String, String[]> formValues) {
    System.out.println("Form values: " + formValues);
    Student student = new Student();
    // Process Name field
    if (formValues.containsKey("Name")) {
      student.setName(formValues.get("Name")[0]);
    }
    else {
      student.getErrorMap().put("Name", "Required field name is missing.");
    }
    // Process Password field
    if (formValues.containsKey("Password")) {
      student.setName(formValues.get("Password")[0]);
    }
    else {
      student.getErrorMap().put("Password", "Required field name is missing.");
    }
    // Process Hobbies.
    student.hobbies = new ArrayList<Hobby>();
    if (formValues.containsKey("Hobbies[]")) {
      for (String hobbyName : formValues.get("Hobbies[]")) {
        System.out.println("Adding hobby: " + hobbyName);
        student.hobbies.add(new Hobby(0L, hobbyName));
      }
    }
    return student;
  }
  
  /**
   * @return True if this instance has an invalid state. 
   */
  public boolean hasErrors() {
    return !this.getErrorMap().isEmpty();
  }
  
  public boolean hasHobby(String hobbyName) {
    for (Hobby hobby : this.hobbies) {
      if (hobbyName.equals(hobby.getName()))
        return true;
    }
    return false;
  }
  
  public boolean hasMajor(String majorName) {
    for (Major major : this.getMajors()) {
      if (majorName.equals(major.getName()))
        return true;
    }
    return false;
  }
  
  public String toString() {
    return String.format("[Student %s, password: %s Hobbies: %s %s %s Majors: %s]", 
        this.getName(), this.getPassword(), this.hobbies, this.level, this.gpa, this.getMajors());
  }

  /**
   * @return the errorMap
   */
  public Map<String, String> getErrorMap() {
    return errorMap;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the level
   */
  public GradeLevel getLevel() {
    return level;
  }

  /**
   * @param level the level to set
   */
  public void setLevel(GradeLevel level) {
    this.level = level;
  }

  /**
   * @return the gpa
   */
  public GradePointAverage getGpa() {
    return gpa;
  }

  /**
   * @param gpa the gpa to set
   */
  public void setGpa(GradePointAverage gpa) {
    this.gpa = gpa;
  }

  /**
   * @return the majors
   */
  public List<Major> getMajors() {
    return majors;
  }

  /**
   * @param majors the majors to set
   */
  public void setMajors(List<Major> majors) {
    this.majors = majors;
  }
  
  public void addMajor(Major major) {
    this.majors.add(major);
  }
  
}

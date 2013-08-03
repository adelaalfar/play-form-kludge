package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple model class used for form data manipulation.
 */
public class Student {
  private long id;
  private Map<String, String> errorMap = new HashMap<>();
  private String name = "";
  private String password = "";
  private List<Hobby> hobbies = new ArrayList<>(); // Hobbies are optional.
  private GradeLevel level = null;
  private GradePointAverage gpa = null;
  private List<Major> majors = new ArrayList<>(); // Majors are optional.

  public Student() {
  }

  public Student(long id, String name, String password, GradeLevel level, GradePointAverage gpa) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.level = level;
    this.gpa = gpa;
  }

  /**
   * Creates a returns a new Student instance initialized from formValues. If problems occur during
   * binding, the errorMap field reports the problem(s). Use hasErrors() to check if this instance
   * is valid or not.
   * 
   * @param formValues The values retrieved from the form.
   * @return A student instance.
   */
  public static Student makeInstance(Map<String, String[]> formValues) {
    //System.out.println("Form values: " + formValues);
    Student student = new Student();

    // Process Name field: required
    if (formValues.containsKey("Name") && formValues.get("Name")[0].length() > 0) {
      student.setName(formValues.get("Name")[0]);
    }
    else {
      student.getErrorMap().put("Name", "A name is required.");
    }

    // Process Password field: required and length >= 5
    if (formValues.containsKey("Password") && formValues.get("Password")[0].length() >= 5) {
      student.setPassword(formValues.get("Password")[0]);
    }
    else {
      student.getErrorMap().put("Password", "A password of at least five characters is required.");
    }

    // Process Hobbies. Optional, but if supplied must exist in database.
    student.hobbies = new ArrayList<Hobby>();
    if (formValues.containsKey("Hobbies[]")) {
      for (String hobbyName : formValues.get("Hobbies[]")) {
        Hobby hobby = Hobby.findHobby(hobbyName);
        if (hobby == null) {
          student.getErrorMap().put("Hobbies", "Supplied hobby is not defined.");
        }
        else {
          student.hobbies.add(hobby);
        }
      }
    }

    // Process Grade Level. Required and must exist in database.
    if (!formValues.containsKey("Level")) {
      student.getErrorMap().put("Level", "Grade Level must be supplied.");
    }
    else {
      String levelName = formValues.get("Level")[0];
      GradeLevel level = GradeLevel.findLevel(levelName);
      if (level == null) {
        student.getErrorMap().put("Level", "Supplied grade level is not defined.");
      }
      else {
        student.level = level;
      }
    }
    
    // Process GPA. Required and must exist in database.
    if (!formValues.containsKey("GPA")) {
      student.getErrorMap().put("GPA", "GPA must be supplied.");
    }
    else {
      String gpaName = formValues.get("GPA")[0];
      GradePointAverage gpa = GradePointAverage.findGPA(gpaName);
      if (gpa == null) {
        student.getErrorMap().put("GPA", "Supplied GPA is not defined.");
      }
      else {
        student.gpa = gpa;
      }
    }
    
    // Process Majors. Optional, but if supplied must exist in database.
    student.majors = new ArrayList<Major>();
    if (formValues.containsKey("Majors")) {
      for (String majorName : formValues.get("Majors")) {
        Major major = Major.findMajor(majorName);
        if (major == null) {
          student.getErrorMap().put("Major", "Supplied major is not defined.");
        }
        else {
          student.majors.add(major);
        }
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
  
  public void addHobby(Hobby hobby) {
    this.hobbies.add(hobby);
  }

  public boolean hasMajor(String majorName) {
    for (Major major : this.getMajors()) {
      if (majorName.equals(major.getName()))
        return true;
    }
    return false;
  }

  public String toString() {
    return String.format("[Student %s, password: %s Hobbies: %s %s %s Majors: %s]", this.getName(),
        this.getPassword(), this.hobbies, this.level, this.gpa, this.getMajors());
  }

  /**
   * @return the errorMap
   */
  public Map<String, String> getErrorMap() {
    return errorMap;
  }

  public String getError(String errorName) {
    String msg = this.errorMap.get(errorName);
    return (msg == null) ? "" : msg;
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
  
  // Fake a database of students. 
  private static List<Student> allStudents = new ArrayList<>();
  
  public static Student findStudent(long id) {
    for (Student student : allStudents) {
      if (student.id == id) {
        return student;
      }
    }
    throw new RuntimeException("Couldn't find student");
  }
  
  static {
    // Valid student. No optional data supplied.
    allStudents.add(new Student(1L, "Joe Good", "mypassword", GradeLevel.findLevel("Freshman"), GradePointAverage.findGPA("4.0")));
    // Valid student with optional data.
    allStudents.add(new Student(2L, "Alice Good", "mypassword", GradeLevel.findLevel("Sophomore"), GradePointAverage.findGPA("4.0")));
    findStudent(2L).addHobby(Hobby.findHobby("Biking"));
    findStudent(2L).addHobby(Hobby.findHobby("Surfing"));
    findStudent(2L).addMajor(Major.findMajor("Chemistry"));
    findStudent(2L).addMajor(Major.findMajor("Physics"));
    // Invalid student. Password is too short.
    allStudents.add(new Student(3L, "Frank Bad", "pass", GradeLevel.findLevel("Freshman"), GradePointAverage.findGPA("4.0")));
  }

}

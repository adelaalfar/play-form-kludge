package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represent a student grade level.
 */
public class GradeLevel {
  private long id;
  private String name;
  
  public GradeLevel(long id, String name) {
    this.id = id;
    this.name = name;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public long getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }

  /**
   * @return A list of level names in sorted order. 
   */
  public static List<String> getNameList() {
    String[] nameArray = {"Freshman", "Sophomore", "Junior", "Senior", "Other"};
    return Arrays.asList(nameArray);    
  }

  /**
   * Return the Level instance in the database with name 'LevelName' or null if not found.
   * @param LevelName The Level name.
   * @return The Level instance, or null.
   */
  public static GradeLevel findLevel(String LevelName) {
    for (GradeLevel Level : allLevels) {
      if (LevelName.equals(Level.getName())) {
        return Level;
      }
    }
    return null;
  }
  
  public static GradeLevel getDefaultLevel() {
    return findLevel("Freshman");
  }
  
  @Override
  public String toString() {
    return String.format("[GradeLevel %s]", this.name);
  }
  
  // Fake a database of Grade Levels. 
  private static List<GradeLevel> allLevels = new ArrayList<>();
  
  static {
    allLevels.add(new GradeLevel(1L, "Freshman"));
    allLevels.add(new GradeLevel(2L, "Sophomore"));
    allLevels.add(new GradeLevel(3L, "Junior"));
    allLevels.add(new GradeLevel(4L, "Senior"));
    allLevels.add(new GradeLevel(5L, "Other"));
  }
  

}

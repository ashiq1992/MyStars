package Model;

import java.util.List;
/**
 * 
 * @author Ameen
 * @author Waqas
 * @author Ashiq
 * @author Will
 * @author Reuben
 * @version 1.0
 * @since 2017-04-01
 */
public class Schedule {

	/**
	 * The venue of where the course is held (LT22)
	 */
	private String Venue;
	
	/**
	 * The day of the course.E.g,Tuesday,Wednesday
	 */
	private String day;
	
	/**
	 * The type refers to whether it is a lab,lecture,tutorial
	 */
	private String type;
	
	/**
	 * Each schedule is based on the course index.E.G CE2003_1.
	 * Index 1 will have its own schedule
	 */
	private int index;
	
	/**
	 * The course code affiliated to each schedule
	 */
	private String courseCode;
	
	/**
	 * The times to be placed for the schedule
	 */
	private String startTime, endTime;
	
	/**
	 * A schedule constructor that creates a schedule object for each course
	 * 
	 * @param courseCode  courseCode of a course
	 * @param index  index of course
	 * @param type  The type of course,LAB,TUTORIAL OR LECTURE
	 * @param day  The day of the course,E.GMonday or Tuesday
	 * @param venue  Where the LEC,LAB OR TUT is held.E.G LT22
	 * @param startTime  The time the lesson starts 
	 * @param endTime  The time the lesson ends
	 */
	public Schedule(String courseCode, int index, String type, String day, String venue, String startTime,
			String endTime) {
		this.courseCode = courseCode;
		this.day = day;
		this.type = type;
		this.index = index;
		this.Venue = venue;
		this.startTime = startTime;
		this.endTime = endTime;

	}
	
	/**
	 * Gets the index of the course
	 * @return the index of the course
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the index of the course to the method.E.G:CE2003_1
	 * Where 1 is the index
	 * @param index of the course
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * Gets the course code
	 * @return course code
	 */
	public String getCourseCode() {
		return courseCode;
	}
	
	/**
	 * 
	 * Sets the courseCode
	 * @param courseCode of a particular course
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	/**
	 * gets the start time of the lesson
	 * @return the start time as String
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time of the lesson
	 * @param startTime input taken as a String
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * 
	 * gets the end time of the lesson
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * Sets the end time of the lesson
	 * @param endTime as a String
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * gets the venue 
	 * @return the location of the venue
	 */
	public String getVenue() {
		return Venue;
	}
	
	/**
	 * Sets the venue
	 * @param venue taken as a String
	 */
	public void setVenue(String venue) {
		Venue = venue;
	}
	
	/**
	 * Gets the day of the course
	 * @return the day as a String
	 */
	public String getDay() {
		return day;
	}
	
	/**
	 * Sets the day of the course
	 * @param day takes the day as String
	 */
	public void setDay(String day) {
		this.day = day;
	}
	
	/**
	 * Gets the type of Lesson
	 * Lab,Lecture or Tutorial
	 * @return the type of lesson as a String
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the type of Lesson
	 * Lab,Lecture or Tutorial
	 * @param type of lesson as String
	 */
	public void setType(String type) {
		this.type = type;
	}

}

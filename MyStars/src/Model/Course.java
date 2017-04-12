package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
public class Course {

	/**
	 * School: The faculty in a school E.G SPMS CourseCode: The course code for
	 * a particular course CourseName: The name of the course
	 */
	private String school, courseCode, courseName;

	/**
	 * nOfIndex: Total number of indexes in a course Capacity: Total capacity in
	 * the course
	 */
	private int nOfIndex, capacity;

	/**
	 * Indices array stores the total capacity for each course index EG:
	 * CE2003_1 -->CE2003 refers to the course code and 1 refers to the index.
	 * For Index "1", the total capacity is stored indices
	 * 
	 * Vacancy array stores the vacancies for each Index
	 */
	private int[] indices, vacancy;

	/**
	 * Start Date is the start date for a particular course
	 * 
	 * End Date is the end data for a particular course
	 */
	private String startDate, endDate;

	/**
	 * A LinkedList array take in a list of students who are on a waitlist for a
	 * particular course
	 */
	private LinkedList[] waitlist;

	/**
	 * 
	 * This constructor creates a course object given the,
	 * courseCode,CourseName,school,startDate,endDate,vacancy,indices
	 * 
	 * 
	 * @param courseCode
	 *            for a particular course
	 * @param courseName
	 *            --> refers to the name of the coursee
	 * @param school
	 *            --> refers to the faculty
	 * @param startDate
	 *            --> refers to the start date of a course
	 * @param endDate
	 *            --> refers to the end of a course
	 * @param vacancy
	 *            --> the vacancy for the different indexes in a course
	 * @param indices
	 *            --> refers to the total capacity for each index
	 */
	public Course(String courseCode, String courseName, String school, String startDate, String endDate, int[] vacancy,
			int[] indices) {
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.school = school;
		this.indices = indices;
		this.vacancy = vacancy;
		this.startDate = startDate;
		this.endDate = endDate;

		int nOfIndex = 0;
		for (int i = 0; i < indices.length; i++)
			nOfIndex++;
	}

	/**
	 * 
	 * @return the startDate for each course
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * sets the startDate to the set method in DD/MM/YYYY
	 * 
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * 
	 * @return the endDate in the format DD/MM/YYYY
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 
	 * @param endDate
	 *            --> sets the end date of the course in the format DD/MM/YYYY
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 
	 * @return the faculty as a String->> e.g:SCE
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * 
	 * @param school
	 *            set the faculty where the course is held
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * 
	 * @return -->returns the course code
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * 
	 * @param courseCode
	 *            --> sets the courseCode
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	/**
	 * 
	 * 
	 * @return the course name for a course
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * 
	 * @param courseName
	 *            --> sets the coursename for a course
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Gets the capacity for each Index in a course
	 * 
	 * @return
	 */
	public int[] getIndices() {
		return indices;
	}

	/**
	 * Sets the capacity for each Index in a course
	 * 
	 * @param indices
	 */
	public void setIndices(int[] indices) {
		this.indices = indices;
	}

	/**
	 * Gets the vacancies for each Index in a course
	 * 
	 * @return
	 */
	public int[] getVacancy() {
		return vacancy;
	}

	/**
	 * Sets the vacancies for each Index in a course
	 * 
	 * @param vacancy
	 */
	public void setVacancy(int[] vacancy) {
		this.vacancy = vacancy;
	}

	/**
	 * This method decreases the vacancy given a particular index of a course
	 * when invoked
	 * 
	 * @param indexNo
	 */
	public void decreaseVacancy(int indexNo) {
		vacancy[indexNo - 1]--;
	}

	/**
	 * This method increases the vacancy given a particular index of a course
	 * when invoked
	 * 
	 * @param indexNo
	 */
	public void increaseVacancy(int indexNo) {
		vacancy[indexNo - 1]++;
	}

}

package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//hello

public class Course {
	//Properties
	private String       school, courseCode, courseName;
	private int          nOfIndex, capacity;
	private int[] 	     indices, vacancy;
	private String       startDate, endDate; //to be changed to Date type
	private Schedule     schedule;
	private LinkedList[] waitlist;
	
	
	//Constructor
	public Course(){}
	
	public Course(String courseCode, String courseName, String school, int[] indices, String startDate, String endDate){
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.school 	= school;
		this.indices 	= indices;
		this.vacancy	= indices;
		this.startDate 	= startDate;
		this.endDate 	= endDate;
		
		int nOfIndex = 0;
		for(int i=0; i<indices.length;i++)
			nOfIndex++;
		
		this.waitlist = new LinkedList[nOfIndex];
		
		for(int i=0; i<nOfIndex; i++){
			this.waitlist[i] = new LinkedList();
		
		}
	}
	
	//Methods	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getSchool() {
		return school;
	}
	
	public void setSchool(String school) {
		this.school = school;
	}
	
	public String getCourseCode() {
		return courseCode;
	}
	
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
//	public int getCapacity() {
//		return capacity;
//	}
//	public void setCapacity(int capacity) {
//		this.capacity = capacity;
//	}
	
	public int[] getIndices() {
		return indices;
	}
	public void setIndices(int[] indices) {
		this.indices = indices;
	}
	
	public int[] getVacancy() {
		return vacancy;
	}
	
	public void setVacancy(int[] vacancy) {
		this.vacancy = vacancy;
	}
	
	public void decreaseVacancy(int indexNo){
		vacancy[indexNo-1] --;
	}
	
	public void increaseVacancy(int indexNo){
		vacancy[indexNo-1] ++;
	}
	
	//public Person waitlist()
	//{
		   
	//}
	
	
	

}

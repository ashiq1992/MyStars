package Model;
//hello

public class Course {
	//Properties
	private String   school, courseCode, courseName;
	private int      capacity, vacancy;
	private String   startDate, endDate; //to be changed to Date type
	private Schedule schedule;
	
	
	//Constructor
	public Course(){}
	
	public Course(String courseCode, String courseName, String school, int capacity, String startDate, String endDate){
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.school 	= school;
		this.capacity 	= capacity;
		this.vacancy 	= capacity;
		this.startDate 	= startDate;
		this.endDate 	= endDate;
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
	
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int getVacancy() {
		return vacancy;
	}
	
	public void setVacancy(int vacancy) {
		this.vacancy = vacancy;
	}
	
	public void decreaseVacancy(){
		vacancy--;
	}
	
	public void increaseVacancy(){
		vacancy++;
	}
	
	//public Person waitlist()
	//{
		   
	//}
	
	
	

}

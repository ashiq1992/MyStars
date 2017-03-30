package Control;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.Course;
import Model.Student;

public class CourseController {
	//Properties
	public static final String SEPARATOR = "|"; 
	
	
	//Constructor
	public CourseController(){}
	
	//Methods
	public boolean addCourse(){
		  Scanner sc = new Scanner(System.in);
		  List courseSave = new ArrayList();
		  System.out.println("No of courses to be added");
		  int input = sc.nextInt();
		  String Temp=sc.nextLine();//to clear the \n buffer
		  for (int i=0;i<input;i++)
		  {
			  
			  String code, name, school, startDate, endDate;
			  int 	 capacity;
			  				 
			  System.out.println("Please enter course code: ");
			  code = sc.nextLine();
			  
			  System.out.println("Please enter course name: ");
			  name = sc.nextLine();
			  
			  System.out.println("Please enter course school : ");
			  school = sc.nextLine();
			  
			  System.out.println("Please enter the capacity of the course: ");
			  capacity = sc.nextInt();
			  Temp=sc.nextLine();//To eliminate the buffer
			  System.out.println("Please enter the start date of course: ");
			  startDate = sc.next();
			  
			  System.out.println("Please enter the end date of course: ");
			  endDate = sc.next();
			  
			  Course course = new Course(code, name, school, capacity, startDate, endDate);
			  
			  courseSave.add(course);
		  }
		  
		  
		  try {
			  saveCourse("src/course.txt", courseSave);
			  return true;
		} catch (IOException e) {
			  e.printStackTrace();
			  return false;
		}
		  
	}
	
	public static void saveCourse(String filename, List list) throws IOException {
  		List tempList = new ArrayList() ;// to store students data

          for (int i = 0 ; i < list.size() ; i++) {
  				Course course = (Course)list.get(i);
  				StringBuilder st =  new StringBuilder() ;
  				st.append(course.getCourseCode().trim());
  				st.append(SEPARATOR);
  				st.append(course.getCourseName().trim());
  				st.append(SEPARATOR);
  				st.append(course.getSchool().trim());
  				st.append(SEPARATOR);
  				String temp = "" + course.getCapacity();
  				st.append(temp.trim()); 
  				st.append(SEPARATOR);
  				st.append(course.getStartDate().trim());
  				st.append(SEPARATOR);
  				st.append(course.getEndDate().trim());
  				
  				tempList.add(st.toString());
  			}
  			write(filename,tempList);
  	}

	public static void write(String fileName, List data) throws IOException  {
	    PrintWriter out = new PrintWriter(new FileWriter(fileName));

	    try {
			for (int i =0; i < data.size() ; i++) {
	      		out.println((String)data.get(i));
			}
	    }
	    finally {
	      out.close();
	    }
	  }

	
	
}

package Control;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import Model.Course;
import Model.Student;

public class CourseController {
	//Properties
	public static final String SEPARATOR = "|"; 
	
	
	//Constructor
	public CourseController(){}
	
	//Methods
	public static ArrayList readAllCourse(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList alr = new ArrayList() ;// to store Admins data
		for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","
				
				String courseCode=star.nextToken().trim();
				String  courseName = star.nextToken().trim();
				String  school = star.nextToken().trim();
				int  capacity =Integer.parseInt(star.nextToken().trim()); //(int) star.nextToken().trim(); capacity is int 
				String  startDate = star.nextToken().trim();
				String  endDate= star.nextToken().trim();
				
				
				Course course = new Course(courseCode,courseName,school,capacity,startDate,endDate);
				
				// add student objects to alr
				alr.add(course) ;
			}
			return alr ;
	}
	
	/** Read the contents of the given file. */
	  public static List read(String fileName) throws IOException {
		List data = new ArrayList() ;
	    Scanner scanner = new Scanner(new FileInputStream(fileName));
	    try {
	      while (scanner.hasNextLine()){
	        data.add(scanner.nextLine());
	      }
	    }
	    finally{
	      scanner.close();
	    }
	    return data;
	  }
	 
	
	
	
	
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
	    PrintWriter out = new PrintWriter(new FileWriter(fileName,true));//to ensure that the previous data is still intact

	    try {
			for (int i =0; i < data.size() ; i++) {
	      		out.println((String)data.get(i));
			}
	    }
	    finally {
	      out.close();
	    }
	  }

	
	
	  public boolean deleteCourse(String file,String courseCode)
	  {
		  try {
			ArrayList<Course> a1=readAllCourse(file);
			
			List l2 = new ArrayList();
			for(int i=0;i<a1.size();i++)
			{
				Course course = (Course)a1.get(i);
				
				String mat= course.getCourseCode();
				
				
				if(mat.equals(courseCode))
				{
					
					a1.remove(i);
				}
				
								
			}
			
			for(int i=0;i<a1.size();i++)
			{
				Course course=(Course)a1.get(i);
				//System.out.println("Gender: "+student.getGender());
				//System.out.println("MatriculationNumber: "+student.getMatriculationNumber());
				l2.add(a1.get(i));
			}
			
			saveCourseAmend(file,l2);
			
			
		} catch (IOException e) {
			
			return false;
		}
		  
		  return true;
	  }
	
	
	
	
	
	public static void newWrite(String fileName, List data) throws IOException  {
	    PrintWriter out = new PrintWriter(new FileWriter(fileName));//clears the data in the file before writing it back to the file

	    try {
			for (int i =0; i < data.size() ; i++) {
	      		out.println((String)data.get(i));
			}
	    }
	    finally {
	      out.close();
	    }
	  }
	
	/*The below method is to save course after the amendments are done*/
	public static void saveCourseAmend(String filename, List list) throws IOException {
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
  				// 
  				tempList.add(st.toString());
  			}
  			newWrite(filename,tempList);
  	}
}

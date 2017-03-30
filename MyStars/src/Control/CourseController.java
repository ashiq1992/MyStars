package Control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.Student;

public class CourseController {
	//Properties
	public static final String SEPARATOR = "|"; 
	
	
	//Constructor
	public CourseController(){}
	
	//Methods
	public boolean addCourse(){
		 Scanner sc = new Scanner(System.in);
		  List studentSave= new ArrayList();
		  System.out.println("No of students to be added");
		  int input=sc.nextInt();
		  
		  for (int i=0;i<input;i++)
		  {
			  
			  String code, name, school,
			  				 
			  System.out.println("Please enter student name: ");
			  String name=sc.next();
			  student.setName(name);
			  
			  System.out.println("Please enter student Matriculation Number: ");
			  String matriculationNumber=sc.next();
			  student.setMatriculationNumber(matriculationNumber);
			  
			  
			  
			  System.out.println("Please enter student user id : ");
			  student.setUserId(sc.next());
			  
			  System.out.println("Please enter student password: ");
			  student.setPassword(sc.next());
			  
			  System.out.println("Please enter student nationality: ");
			  student.setNationality(sc.next());
			  
			  System.out.println("Please enter student gender: ");
			  student.setGender(sc.next());
			  
			  Course course = new Course();
			  
			  studentSave.add(student);
		  }
		  
		  
		  try {
			 
			StudentController.saveStudent("src/student.txt", studentSave);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		  
	}
	
	
}

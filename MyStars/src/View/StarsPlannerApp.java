package View;

import java.util.InputMismatchException;
import java.util.Scanner;

import Control.AdminController;
import Control.CourseController;
import Control.StudentController;

public class StarsPlannerApp {
	static AdminController Ad  = new AdminController();
	static CourseController Cd = new CourseController();
	static boolean adddropEnable;
	
	public static void main(String args[]){
		adddropEnable = true;
		
		int input=-1; //Takes in user input;
		int valid=1;
		boolean result;
		String password,userId;
		do {
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Type of user: ");
		System.out.println("1) Student ");
		System.out.println("2) Admin ");
		
		
		try
		{
			input=sc.nextInt();
		}catch(InputMismatchException ex)
		{
			System.out.println("Input choice is not an integer");
		}
		if(input==1)
		{
			
		}
		
		else if (input==2)
		{
			System.out.println("Enter userId:");
			userId=sc.next();
			System.out.println("Enter Password:");
			password=sc.next();
			AdminController ad=new AdminController();
			result=ad.checkAccount(userId, password);
			if(result==true){
				System.out.println("Sucess");
				AdminMenu();
				
			}
			else{
				System.out.println("Failed");
			}
		}
		
		else 
		{
			System.out.println("Invalid Choice/Input! Re-enter");
			
		}
		}
		
		while(valid !=0);
			
		
	}
	
	// =================================================================================
	// =================================================================================
	// 									ADMIN MENU
	// =================================================================================
	// =================================================================================
	
	public static void AdminMenu()
	{
		Scanner sc=new Scanner(System.in);
		int input;
		System.out.println("1)Add student.");
		System.out.println("2)Add Course.");
		System.out.println("3)RemoveCourse.");
		System.out.println("4)Print by index.");
		System.out.println("5)Print by Course.");
		System.out.println("6) Remove Student");
		System.out.println("7) Enable/Disable Add/Drop");
		System.out.println("10)LogOut.");
		
		//System.out.println("Enter options:");
		//input=sc.nextInt();
		
		do{
			System.out.println("Enter options:");
			input=sc.nextInt();
			
		switch(input){
		case 1: AddStudent();
				break;

		case 2: addCourse();
				break;
				
		case 3: removeCourse();
				break;
				
		case 6: removeStudent();
				break;
		
		case 7: adddrop();
				break;

			
			default: System.out.println("Input error");
		}
		}while(input !=10);
		
	}
	public static void AddStudent(){
		boolean result;
		
		
		result=Ad.AddStudent();
		if(result==true){
			System.out.println("Success");
		}
		else{
			System.err.println("Fail");
		}
		
	}
	
	public static void addCourse(){
		boolean result;
		
		result = Cd.addCourse();
		if(result==true){
			System.out.println("Success");
		}
		else{
			System.err.println("Fail");
		}
		
	}
	
	public static void removeCourse(){
		boolean result;
		Scanner sc=new Scanner(System.in);
		CourseController cd =new CourseController();
		System.out.println("Enter CourseCode:");
		String id=sc.nextLine();
		result=cd.deleteCourse("src/course.txt",id);
		if(result==true){
			System.out.println("Success");
		}
		else{
			System.err.println("Fail");
		}
		
	}
	
	
	
	public static void removeStudent(){
		boolean result;
		Scanner sc=new Scanner(System.in);
		StudentController s1 = new StudentController();
		System.out.println("Enter Student MatricID:");
		String id=sc.nextLine();
		result=s1.deleteStudent("src/student.txt",id);
		if(result==true){
			System.out.println("Success");
		}
		else{
			System.err.println("Fail");
		}
				
		
	}

	// =================================================================================
	// =================================================================================
	// 									STUDENT MENU
	// =================================================================================
	// =================================================================================
	
	public static void StudentMenu()
	{
		Scanner sc=new Scanner(System.in);
		int input;
		System.out.println("1)View Courses.");
		System.out.println("2)Add/Drop.");
		System.out.println("0)LogOut.");
		
		//System.out.println("Enter options:");
		//input=sc.nextInt();
		
		do{
			System.out.println("Enter options:");
			input=sc.nextInt();
			
		switch(input){
		case 1:AddStudent();
				break;

		case 2:addCourse();
				break;
				
		case 3: removeCourse();
				break;
				
		case 6:removeStudent();
				break;
			
			default: System.out.println("Input error");
		}
		}while(input !=10);
		
	}
	
	

}
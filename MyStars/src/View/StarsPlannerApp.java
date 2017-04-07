package View;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import Model.AddDrop;
import Control.AccessController;
import Control.AddDropController;
import Control.AdminController;
import Control.CourseController;
import Control.ScheduleController;
import Control.StudentController;
import Model.Student;
import Miscellaneous.ClearScreen;
import Miscellaneous.MaskPassword;

public class StarsPlannerApp {
	static AdminController Ad=null;
	static CourseController Cd=null;
	static AccessController access=null;
	static StudentController Sd = new StudentController();
	static AddDropController addDrop = new AddDropController();
	static ScheduleController SCD=new ScheduleController();
	static Student student = new Student();
	static MaskPassword mask = new MaskPassword();
	final static DateFormat dateFormat = new SimpleDateFormat("HH:mm");
	
	static boolean adddropEnable;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String args[]) {
		adddropEnable = true;
		int input = -1; // Takes in user input;
		int valid = 1;
		boolean result=false;
		boolean accessResult=false;
		String password, userId;
		do {
			accessResult=false;
			access = new AccessController();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate localDate = LocalDate.now();
			String currentDate=dtf.format(localDate);
			Date cDate=new Date();
			cDate.parse(currentDate);

			
			DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
			DateFormat format1 = new SimpleDateFormat("HH:mm");
			Calendar now = Calendar.getInstance();
			
			Date date=null;
			Date date1=new Date();
			try {
				date = format.parse(dtf.format(localDate));
				String test2=dateFormat.format(now.getTime());
				date1=format1.parse(test2);

			} catch (ParseException e) {
		
				e.printStackTrace();
			}

			AccessController a1 = new AccessController();
			try {
				accessResult=a1.checkStudentAccess(cDate,date1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Scanner sc = new Scanner(System.in);
			System.out.println("============================");
		    System.out.println("|       Stars Planner      |");
		    System.out.println("============================");
		    System.out.println("| Welcome!                 |");
		    System.out.println("| Please Identify yourself,|");
		    System.out.println("|                          |");
		    System.out.println("|        1. Student        |");
		    System.out.println("|        2. Admin          |");
		    System.out.println("|        3. Exit           |");
		    System.out.println("============================");
		    
			
			try {
				input = sc.nextInt();
			} catch (InputMismatchException ex) {
				System.out.println("Input choice is not an integer");
			}
			if (input == 1) {
				
				System.out.println("Student Login,     ");
				System.out.println("                   ");
				System.out.println("Please enter your UserId:|");
			    userId = sc.next();
			    System.out.println("|Please enter your password:|");
				password = mask.readPassword("Enter passwordd");
				
				result = Sd.checkAccount(userId, password);
				if (result == true) {
					
					if(accessResult==true)
					{
						System.out.println("Sucess");
						student.setMatriculationNumber(userId);
						StudentMenu();
						
					}
					
					else
					{
						System.out.println("Access denied");
					}
					
					

				} else {
					System.out.println("Failed");
				}
			}
			

			else if (input == 2) {
				System.out.println("Enter userId:");
				userId = sc.next();
//				System.out.println("Enter Password:");
				password = mask.readPassword("Enter Passwordd");
				System.out.println(password);
				Ad = new AdminController();
				result = Ad.checkAccount(userId, password);
				if (result == true) {
					System.out.println("Welcome "+userId);
					System.out.println("         ");
					AdminMenu();

				} else {
					System.out.println("User Authentication Failure");
				}
			}
			
			else if(input==3)
			{
				System.out.println("Thank you for using our system");
				System.out.println("Have a great day,goodbye!");
				break;
			}

			else {
				System.out.println("You have entered an invalid choice,please try again!");

			}
		}

		while (valid != 0);

	}

	// =================================================================================
	// =================================================================================
	// ADMIN MENU
	// =================================================================================
	// =================================================================================

	public static void AdminMenu() {
		Scanner sc = new Scanner(System.in);
		int input;
		System.out.println("1)Add student.");
		System.out.println("2)Add Course.");
		System.out.println("3)RemoveCourse.");
		System.out.println("4)Print by index.");
		System.out.println("5)Print by Course.");
		System.out.println("6) Remove Student");
		System.out.println("7) Student Access Period");
		System.out.println("8)Show available vacancy for course/index.");
		System.out.println("10)LogOut.");

		// System.out.println("Enter options:");
		// input=sc.nextInt();

		do {
			System.out.println("Enter options:");
			input = sc.nextInt();

			switch (input) {
			case 1:
				AddStudent();
				break;

			case 2:
				addCourse();
				break;

			case 3:
				removeCourse();
				break;
			case 4:
				printByIndex();
				break;
			case 5:
				printByCourseCode();
				break;

			case 6:
				removeStudent();
				break;

			case 7:
				studentAccessPeriod();
				break;

			case 8:
				displayVacancy();
				break;

			default:
				System.out.println("Input error");
			}
		} while (input != 10);

	}

	public static void AddStudent() {
		boolean result;
		result = Ad.AddStudent();
		if (result == true) {
			System.out.println("Success");
		} else {
			System.err.println("Fail");
		}
	}

	public static void addCourse() {
		boolean result;

		result = Cd.addCourse();
		if (result == true) {
			System.out.println("Success");
		} else {
			System.err.println("Fail");
		}
	}

	public static void removeCourse() {
		boolean result=false;
		Scanner sc = new Scanner(System.in);
		Cd = new CourseController();
		System.out.println("Enter CourseCode:");
		String id = sc.next();
		result = Cd.deleteCourse("DataBase/courses.txt",id);
		if (result == true) {
			System.out.println("Success");
		} else {
			System.err.println("Fail");
		}
	}

	public static void removeStudent() {
		boolean result;
		Sd = new StudentController();
		System.out.println("Enter Student MatricID:");
		String id = sc.nextLine();
		result = Sd.deleteStudent("DataBase/student.txt", id);
		if (result == true) {
			System.out.println("Success");
		} else {
			System.err.println("Fail");
		}
	}

	public static void studentAccessPeriod() {
		access=new AccessController();
		boolean result=false;
		
		char input = 0;
		do{
		
		result=access.accessPeriod();
		if(result==false)
		{
			input=0;
			System.out.println("Do you want to continue? Y/N ");
			input=sc.next().toLowerCase().charAt(0);
		
		}
		
		else
		{
			System.out.println("Successfully made the amendments");
		}
		
		}while(input =='y' || result==false );
		
	}

	public static void displayVacancy(){
		String courseCode;
		char check;
		Ad=new AdminController();
		
		boolean result=false;
		do{
			System.out.println("Enter the course code to view the vacancy:");
			courseCode=sc.next();
			result=Ad.vacancy(courseCode);
			if(result ==false){
				System.out.println("Do you wish to exit (y/n)?");
				check=sc.next().toLowerCase().charAt(0);
				if(check=='y'){
					result=false;
				}
				else if(check=='n'){
					result=true;
				}
				else{
					System.out.println("You have entered a wrong input value");
					System.out.println("Key in courseCode again");
				}
				
			}
			
		}while(result==false);
		System.out.println("You have exited the loop");
		
	}
		public static void printByCourseCode(){
				
				String courseCode;
				boolean state=false;
				char input=0;
				
				while(state==false || input=='y')
				{
					input=0;
				
				System.out.println("Enter the course code to view the student:");
				courseCode=sc.next();
				
				state=Ad.printByCourseCode(courseCode);
				
				if(state==false)
				{
					System.out.println("Do you want to continue? Y/N");
					input=sc.next().toLowerCase().charAt(0);
				}
				
				if(input=='n'){
					state=true;
				}
				
				}
		}
		
		
	public static void printByIndex(){
		boolean state=false;
		String courseCode;
		int index=0;
		char input=0;
		
		while(state==false || input=='y')
		{
			input=0;
			System.out.println("Enter the course code to view the student:");
			courseCode=sc.next();
			System.out.println("Enter the index of the course:");
			try{
				
			index=sc.nextInt();
			state=Ad.printByIndex(courseCode, index);
			}
			
			catch(InputMismatchException e)
			{
				System.out.println("Please enter a valid input");
			}
			
			
			if(state==false)
			{
				System.out.println("Do you want to continue? Y/N");
				
				input=sc.next().toLowerCase().charAt(0);
//				sc.nextLine();
			}
			
			if(input=='n'){
				state=true;
			}
		}
		
		
	}
	
	
	
	
	// =================================================================================
	// =================================================================================
	// STUDENT MENU
	// =================================================================================
	// =================================================================================

	public static void StudentMenu() {
		int input;
		do {
			System.out.println("                                         ");
			System.out.println("*****************************************");
			System.out.println("Student Menu");
			System.out.println("1)View all Courses.");
			System.out.println("2)View my Courses.");
			System.out.println("3)Add/Drop.");
			System.out.println("0)LogOut.");
			System.out.println("*****************************************");
			System.out.print("Enter options: ");
			input = sc.nextInt();

			switch (input) {
			case 1:
				viewAllCourses();
				break;

			case 2:
				viewMyCourses();
				break;

			case 3:
				addDropMenu();
				break;

			default:
				System.err.println("Input error");
			}

		} while (input != 0);
	}

	public static void viewAllCourses() {
		Cd=new CourseController();
		Cd.showAllCourses();
	}

	public static void viewMyCourses() {

		Sd.displayCourse(student.getMatriculationNumber());

	}

	// =================================================================================
	// =================================================================================
	// AddDrop Menu
	// =================================================================================
	// =================================================================================

	public static void addDropMenu() {
		int input;
		System.out.println("1)Add a course.");
		System.out.println("2)Drop a course.");
		System.out.println("3)Change index number.");
		System.out.println("0)Back.");

		System.out.println("Enter options:");
		input = sc.nextInt();
		// test
		switch (input) {
		case 1:
			addACourse();
			break;

		case 2:
			dropACourse();
			break;

		case 3:
			changeIndex();
			break;

		default:
			System.err.println("Input error");
		}
	}

	public static void addACourse() {
		String courseCode;
		int index;
		boolean result=false;
		boolean clashCheck;
		char value = 0;
		
		do{
			value=0;
		System.out.println("Enter Course Code: ");
		courseCode = sc.next().toLowerCase();
		// add
		Cd.showIndexByCourse(courseCode);
		System.out.println("Enter index: ");
		index = sc.nextInt();
		
		clashCheck=SCD.clashcheck(courseCode, index, student.getMatriculationNumber());//clash check implementation
		if(clashCheck){
		
		result=addDrop.AddMasterCheck(student.getMatriculationNumber().toLowerCase(), courseCode.toLowerCase(), index);
		}
		else{
			System.out.println("TimeTable Clash Choose another index for the course!!");
		}
			
		if (result == false) {
			System.out.println("You are not enrolled in the course! ");
			System.out.println("Do you want to continue ? ");
			value = sc.next().charAt(0);
		}
		}
		while (result != true || value == 'Y' || value == 'y');
			

	}

	public static void dropACourse() {
		String courseCode;
		int index;
		System.out.println("Enter CourseCode:");
		courseCode = sc.next();
		index = addDrop.returnIndex(student.getMatriculationNumber(), courseCode);// get
																					// the
																					// index
																					// from
																					// the
																					// file

		addDrop.dropMasterCheck(student.getMatriculationNumber(), courseCode, index);

	}

	public static void changeIndex() {
		String courseCode = null;
		int newIndex = 0;
		int oldIndex = 0;
		boolean result = false;
		char value = 0;

		do {
			System.out.print("Please enter the course code");
			courseCode = sc.next();
			courseCode = courseCode.toLowerCase();

			System.out.println("Please enter the current index");
			oldIndex = sc.nextInt();

			System.out.println(" ");

			System.out.println("Please enter the index you wish to change to");
			newIndex = sc.nextInt();

			result = Cd.checkCourseCode(courseCode, student.getMatriculationNumber(), oldIndex);

			if (result == false) {
				System.out.println("You are not enrolled in the course! ");
				System.out.println("Do you want to continue ? ");
				value = sc.next().charAt(0);
			}

		} while (result != true || value == 'Y' || value == 'y');

		if (result) {

			Sd.changeIndex(newIndex, oldIndex, courseCode, student.getMatriculationNumber());

		}

	}
}

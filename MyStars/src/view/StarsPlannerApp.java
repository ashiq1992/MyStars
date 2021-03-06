package view;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import Miscellaneous.MaskPassword;
/**
 * The main class of StarsPlanner System
 *   
 * @author Ameen
 * @author Ashiq
 * @author Will
 * @author Reuben
 * @author Waqas
 * @since 2017-04-01
 * @version 1.0
 */
public class StarsPlannerApp {
	
	/**
	 * A static AdminController to handle admin functions
	 */
	static AdminController Ad = null;
	
	/**
	 * A static CourseController to handle course functions
	 */
	static CourseController Cd = null;
	
	/**
	 * A static AccessController to handle course functions
	 */
	static AccessController access = null;
	
	/**
	 * A static StudentController to handle student functions
	 */
	static StudentController Sd = new StudentController();
	
	/**
	 * A static AddDropController to handle Adding and Dropping of courses
	 */
	static AddDropController addDrop = new AddDropController();
	
	/**
	 * A static ScheduleController to handle the schedule functions
	 */
	static ScheduleController SCD = new ScheduleController();
	
	/**
	 * A static student object
	 */
	static Student student = new Student();
	
	/**
	 * A static Mask password to handle masking of passwords when entered
	 */
	static MaskPassword mask = new MaskPassword();
	
	/**
	 * A static DateFormat to handle Time related calls
	 */
	final static DateFormat dateFormat = new SimpleDateFormat("HH:mm");
	
	/**
	 * A static adddropEnable
	 */
	static boolean adddropEnable;
	
	/**
	 * A static scanner object
	 */
	static Scanner sc = new Scanner(System.in);
	
	/**
	 * The main method of the program
	 * @param args By Default
	 */
	public static void main(String args[]) {
	try{
		adddropEnable = true;
	
		int input = -1; // Takes in user input;
		int valid = 1;
		boolean result=false;
		boolean accessResult=false;
		String password=null, userId=null;
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
				System.out.println("System message:Input choice is not an integer");
			}
			if (input == 1) {
				
				System.out.println("|Student Login:    ");
				System.out.println("                   ");
				System.out.println("|Please enter your UserId:");
			    userId = sc.next();
			    System.out.println("|Please enter your password:");
				password = mask.readPassword("Enter passwordd");
				
				result = Sd.checkAccount(userId.toLowerCase(), password);
				if (result == true) {
					
					if(accessResult==true)
					{
						student.setMatriculationNumber(userId);
						StudentMenu();
						
					}
					
					else
					{
						System.out.println("System message:You are not allowed to access in this period");
						access.displayAccessPeriod();
						
					}
					
					

				} else {
					System.out.println("System message:User authentication failure.Please check your password/userId ");
				}
			}
			

			else if (input == 2) {
				System.out.println("| Admin Login:    ");
				System.out.println("| Enter userId:");
				userId = sc.next();
//				System.out.println("Enter Password:");
				password = mask.readPassword("Enter Passwordd");
				Ad = new AdminController();
				result = Ad.checkAccount(userId, password);
				if (result == true) {
					System.out.println("Welcome Admin: "+userId);
					System.out.println("         ");
					AdminMenu();

				} else {
					System.out.println("System message:User authentication failure.Please check your password/userId ");
				}
			}
			
			else if(input==3)
			{
				System.out.println("System message:Thank you for using our system");
				System.out.println("System message:Have a great day,goodbye!");
				break;
			}

			else {
				System.out.println("System message:You have entered an invalid choice,please try again!");

			}
		}

		while (valid != 0);
	}
	catch(Exception e){
		System.out.println("Program terminating");
		
		
	}
	}

	// =================================================================================
	// =================================================================================
	// ADMIN MENU
	// =================================================================================
	// =================================================================================
	
	/**
	 * An Admin menu to display functions for Admin
	 */
	public static void AdminMenu() {
		Scanner sc = new Scanner(System.in);
		int input;

		// System.out.println("Enter options:");
		// input=sc.nextInt();

		do {
			System.out.println("                                                ");
			System.out.println("================================================");
			System.out.println("| [Menu]:Admin>>Admin                          |");
			System.out.println("|                                              |");
			System.out.println("================================================");
			System.out.println("| 1)Add student                                |");
			System.out.println("| 2)Add Course                                 |");
			System.out.println("| 3)RemoveCourse                               |");
			System.out.println("| 4)Print by index                             |");
			System.out.println("| 5)Print by Course                            |");
			System.out.println("| 6)Remove Student                             |");
			System.out.println("| 7)Student Access Period                      |");
			System.out.println("| 8)Show available vacancy for course/index    |");
			System.out.println("| 10)Logout                                    |");
			
			System.out.println("================================================");
			System.out.println(" ");
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
			case 0:
				System.out.println("System message:Logging off");
				System.out.println("System message:Goodbye!!!!");
				break;
			default:
				System.out.println("System message:Input error");
			}
		} while (input != 10);

	}
	
	/**
	 * This is method is for the Admin to add a student	
	 * 
	 */
	public static void AddStudent() {
		boolean result;
		result = Ad.AddStudent();
		if (result == true) {
			System.out.println("System message:Student has been successfully added");
		} else {
			System.err.println("System messsage:Unable to add student.Please try again");
		}
	}
	
	/**
	 * This method is for the Admin to add a course
	 */
	public static void addCourse() {
		boolean result;
		Cd = new CourseController();
		result = Cd.addCourse();
		if (result == true) {
			System.out.println("System message:The course has been successfully added");
		} else {
			System.err.println("System message:Unable to add course .Please try again");
		}
	}
	
	
	/**
	 * This method is for the Admin to remove a course
	 */
	public static void removeCourse() {
		boolean result = false;
		Scanner sc = new Scanner(System.in);
		Cd = new CourseController();
		System.out.println("Enter CourseCode:");
		String id = sc.next().toLowerCase();
		result = Cd.deleteCourse("DataBase/courses.txt", id);
		if (result == true) {
			System.out.println("System message:The course has been successfully removed");
		} else {
			System.err.println("System message:Unable to remove course .Please try again");
		}
	}
	
	/**
	 * This function is for the admin to remove a student
	 */
	public static void removeStudent() {
		boolean result;
		Sd = new StudentController();
		System.out.println("Enter Student MatricID:");
		String id = sc.nextLine();
		result = Sd.deleteStudent("DataBase/student.txt", id);
		if (result == true) {
			System.out.println("System message:Student has been successfully droppped");
		} else {
			System.err.println("System message:Unable to remove the student");
		}
	}
	
	/**
	 * This function is for the Admin to set the student access period
	 */
	public static void studentAccessPeriod() {
		access = new AccessController();
		boolean result = false;

		char input = 0;

		result = access.accessPeriod();
		if (result == false) {

			System.out.println("System message:An error occured when making the chages please try again.");

		}

		else {
			System.out.println("System message:Successfully made changes");
		}

	}
	
	/**
	 * This method is for the Admin to view the vacancy for each course
	 */
	public static void displayVacancy() {
		String courseCode;
		Ad = new AdminController();

		boolean result = false;

		System.out.println("Enter the course code to view the vacancy:");
		courseCode = sc.next();
		result = Ad.vacancy(courseCode);
		if (result == false) {
			System.out.println("System message:An error occured .Check your input values and try again.");
		}

	}
	
	/**
	 * This method prints all courses by course code
	 */
	public static void printByCourseCode() {

		String courseCode;
		boolean state = false;
		char input = 0;
		System.out.println("Enter the course code to view the student:");
		courseCode = sc.next();

		state = Ad.printByCourseCode(courseCode);

		if (state == false) {
			System.out.println("System message:An error occured .Check your input values and try again.");

		}

	}
	
	/**
	 * This method prints all the indexes based on the course code
	 */
	public static void printByIndex() {
		boolean state = false;
		String courseCode;
		int index = 0;

		System.out.println("Enter the course code to view the student:");
		courseCode = sc.next();
		System.out.println("Enter the index of the course:");
		try {

			index = sc.nextInt();
			state = Ad.printByIndex(courseCode, index);
		}

		catch (InputMismatchException e) {
			System.out.println("System message:Please enter a valid course code");
		}

		if (state == false) {
			System.out.println("System message:Please enter a valid course code / course index and try again");
		}

	}

	// =================================================================================
	// =================================================================================
	// STUDENT MENU
	// =================================================================================
	// =================================================================================
	
	/**
	 * 
	 * This is a menu that consists of all functions accessible for a student
	 */
	public static void StudentMenu() {
		int input;
		do {
			System.out.println("                                         ");
			System.out.println("==================================");
			System.out.println("| [Current Menu]:Student>>Student|");
			System.out.println("|                                |");
			System.out.println("==================================");
			System.out.println("| Student Menu                   |");
			System.out.println("| 1)View all Courses.            |");
			System.out.println("| 2)View my Courses.             |");
			System.out.println("| 3)Add/Drop Menu                |");
			System.out.println("| 0)LogOut.                      |");
			System.out.println("==================================");
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

			case 0:
				System.out.println("System message:Logging off");
				System.out.println("System message:Goodbye!!!!");
				break;

			default:
				System.err.println("System message:Input mismatch.");
			}

		} while (input != 0);
	}
	
	/**
	 * This method shows all courses
	 */
	public static void viewAllCourses() {
		Cd = new CourseController();
		Cd.showAllCourses();
	}
	
	/**
	 * This method allow the student to view his courses
	 */
	public static void viewMyCourses() {

		Sd.displayCourse(student.getMatriculationNumber());

	}

	// =================================================================================
	// =================================================================================
	// AddDrop Menu
	// =================================================================================
	// =================================================================================
	
	/**
	 * This method allows the add/drop of courses for a student
	 */
	public static void addDropMenu() {
		int input;
		do {
			System.out.println("                                         ");
			System.out.println("=============================================");
			System.out.println("| [Current Menu]:Student>>Add/Drop          |");
			System.out.println("|                                           |");
			System.out.println("=============================================");
			System.out.println("|                                           |");
			System.out.println("| 1)Add a course.                           |");
			System.out.println("| 2)Drop a course.                          |");
			System.out.println("| 3)Change of Course Index                  |");
			System.out.println("| 4)Swappping of Index with another student |");
			System.out.println("| 0)Back.                                   |");
			System.out.println("=============================================");
			System.out.println();
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
				System.out.println("(Note: Index change can only be done from the same course)");
				changeIndex();
				break;
			case 4:
				System.out.println("(Note: Swapping of Index can only be done of the same course)");
				changeIndexWithAnotherStudent();
				break;
			case 0:
				System.out.println("System message:You are now at the previous Menu");
				break;
			default:
				System.err.println("System message:Input mismatch.");
			}
		} while (input != 0);
	}
	
	/**
	 * This method allows a student to add a course
	 */
	public static void addACourse() {
		String courseCode;
		int index;
		boolean result = false;
		boolean clashCheck;
		char value = 0;
		Cd = new CourseController();

		value = 0;
		System.out.println("Enter Course Code: ");
		courseCode = sc.next().toLowerCase();
		// add
		SCD.displaySchedule(courseCode);
		Cd.showIndexByCourse(courseCode);
		System.out.println("Enter index: ");
		index = sc.nextInt();

		clashCheck = SCD.clashcheck(courseCode, index, student.getMatriculationNumber());// clash
																							// check
																							// implementation
		if (clashCheck == false) {

			result = addDrop.AddMasterCheck(student.getMatriculationNumber().toLowerCase(), courseCode.toLowerCase(),
					index);
			// Add the method inside the addMaster()
		} else {
			System.out.println("System message:TimeTable Clash Choose another index for the course!!");
		}

		if (result == false) {
			System.out.println("System message:You are directed to the Add/Drop menu choose your options again ");

		}

	}
	
	/**
	 * This method allows a student drop a course
	 */
	public static void dropACourse() {
		String courseCode;
		int index;
		System.out.println("Enter CourseCode: ");
		courseCode = sc.next();
		index = addDrop.returnIndex(student.getMatriculationNumber(), courseCode);// get
																					// the
																					// index
																					// from
																					// the
																					// file

		addDrop.dropMasterCheck(student.getMatriculationNumber(), courseCode, index);
		
	}
	
	/**
	 * This method allows the change of Index done by a Student
	 */
	public static void changeIndex() {
		String courseCode = null;
		int newIndex = 0;
		int oldIndex = 0;
		boolean result = false;
		char value = 0;
		Cd = new CourseController();
		System.out.print("Please enter the course code: ");
		courseCode = sc.next();
		courseCode = courseCode.toLowerCase();

		SCD.displaySchedule(courseCode);
		System.out.println("Please enter the current index: ");
		oldIndex = sc.nextInt();

		System.out.println(" ");

		System.out.println("Please enter the index you wish to change to: ");
		newIndex = sc.nextInt();

		result = Cd.checkCourseCode(courseCode, student.getMatriculationNumber().toLowerCase(), oldIndex);

		if (result == false) {
			System.out.println("System message:You are not enrolled in the course! ");
			System.out.println("System message:You are directed to the Add/Drop menu choose your options again");
			
		}

		if (result) {
			StudentController SS= new StudentController();

			SS.changeIndex(newIndex, oldIndex, courseCode, student.getMatriculationNumber().toLowerCase());

		}

	}
	
	/**
	 * This method allows a student to change Index with another student
	 */
	public static void changeIndexWithAnotherStudent() {
		String courseCode = null;
		int newIndex = 0;
		String newMatricId;
		boolean result = false;
		char value = 0;
		Cd = new CourseController();

		System.out.print("Please enter the course code :");
		courseCode = sc.next();
		courseCode = courseCode.toLowerCase();

		System.out.println("Please enter the other student MatricID:");
		newMatricId = sc.next();

		result = Cd.checkCourseCode(courseCode, student.getMatriculationNumber(), newMatricId);

		if (result == false) {
			System.out.println(
					"System message:You / the other student is not enrolled in the course .Do check input values and try again ");
		}

		if (result) {
			Sd.changeMatricId(courseCode, student.getMatriculationNumber(), newMatricId);

		}

	}

}

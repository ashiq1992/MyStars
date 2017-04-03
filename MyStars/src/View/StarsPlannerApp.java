package View;

import java.util.InputMismatchException;
import java.util.Scanner;

import Model.AddDrop;
import Control.AddDropController;
import Control.AdminController;
import Control.CourseController;
import Control.StudentController;
import Model.Student;

public class StarsPlannerApp {
	static AdminController Ad = new AdminController();
	static CourseController Cd = new CourseController();
	static StudentController Sd = new StudentController();
	static AddDropController addDrop = new AddDropController();
	static Student student = new Student();

	static boolean adddropEnable;
	static Scanner sc = new Scanner(System.in);

	public static void main(String args[]) {
		adddropEnable = true;

		int input = -1; // Takes in user input;
		int valid = 1;
		boolean result;
		String password, userId;
		do {

			Scanner sc = new Scanner(System.in);
			System.out.println("Type of user: ");
			System.out.println("1) Student ");
			System.out.println("2) Admin ");

			try {
				input = sc.nextInt();
			} catch (InputMismatchException ex) {
				System.out.println("Input choice is not an integer");
			}
			if (input == 1) {
				System.out.println("Enter userId:");
				userId = sc.next();
				System.out.println("Enter Password:");
				password = sc.next();

				result = Sd.checkAccount(userId, password);
				if (result == true) {
					System.out.println("Sucess");
					student.setMatriculationNumber(userId);
					StudentMenu();

				} else {
					System.out.println("Failed");
				}
			}

			else if (input == 2) {
				System.out.println("Enter userId:");
				userId = sc.next();
				System.out.println("Enter Password:");
				password = sc.next();
				AdminController ad = new AdminController();
				result = ad.checkAccount(userId, password);
				if (result == true) {
					System.out.println("Sucess");
					AdminMenu();

				} else {
					System.out.println("Failed");
				}
			}

			else {
				System.out.println("Invalid Choice/Input! Re-enter");

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
		System.out.println("7) Enable/Disable Add/Drop");
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

			case 6:
				removeStudent();
				break;

			case 7:
				adddrop();
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
		boolean result;
		Scanner sc = new Scanner(System.in);
		CourseController cd = new CourseController();
		System.out.println("Enter CourseCode:");
		String id = sc.nextLine();
		result = cd.deleteCourse("src/course.txt", id);
		if (result == true) {
			System.out.println("Success");
		} else {
			System.err.println("Fail");
		}
	}

	public static void removeStudent() {
		boolean result;
		StudentController s1 = new StudentController();
		System.out.println("Enter Student MatricID:");
		String id = sc.nextLine();
		result = s1.deleteStudent("src/student.txt", id);
		if (result == true) {
			System.out.println("Success");
		} else {
			System.err.println("Fail");
		}
	}

	public static void adddrop() {
		int input;
		System.out.println("Current Add/Drop Status, Enabled: " + adddropEnable);

		if (adddropEnable) {
			System.out.println("Do you want to disable Add/Drop?\n1. Yes\n2. No");
			input = sc.nextInt();
			if (input == 1) {
				adddropEnable = false;
			}
		} else {
			System.out.println("Do you want to enable Add/Drop?\n1. Yes\n2. No");
			input = sc.nextInt();
			if (input == 1) {
				adddropEnable = true;
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
//test
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
		boolean result;
		System.out.println("Enter Course Code: ");
		courseCode = sc.next();
		// add
		Cd.showIndexByCourse(courseCode);
		System.out.println("Enter index: ");
		index = sc.nextInt();
		result = addDrop.addMethod(courseCode, index, student.getMatriculationNumber());
		// Cd.decreaseCourseIndexVacancy(courseCode, index);

		if (result) {
			System.out.println("Success");
		} else {
			System.err.println("Fail");
		}

	}

	public static void dropACourse() {
		String courseCode;
		int index;
		System.out.println("Enter CourseCode:");
		courseCode=sc.next();
		index=addDrop.returnIndex(student.getMatriculationNumber(), courseCode);//get the index from the file 
	
		addDrop.dropMethod(courseCode, index, student.getMatriculationNumber());
		
		

	}

	public static void changeIndex() {
		String matriculation;
		System.out.println("Enter the Students Matriculation Id:");
		matriculation=sc.next();
		
		

	}
}

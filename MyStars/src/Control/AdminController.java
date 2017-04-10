package Control;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Miscellaneous.Hash;
import Model.Access;
import Model.Admin;
import Model.Student;

/**
 * @author Ameen
 * @author Ashiq
 * @author Will
 * @author Reuben
 * @author Waqas
 * @since 2017-04-01
 * @version 1.0
 */
public class AdminController {
	
	/**
	 * A FileManager object is instantiated
	 */
	
	private static FileManager manage =new FileManager();
	
	/**
	 * A separator is used to separate the elements
	 */
	public static final String SEPARATOR = "|";
	
	/**
	 * A hash object instantiated
	 */
	private Hash h1 = new Hash();
	
	/**
	 * Given a file name, the method reads all the list of admins and saves it into an arraylist
	 * 
	 * @param filename  The filename where the contents should be read from
	 * @return An ArrayList consisting of Admin objects
	 * @throws IOException Throws an exception to the method which called this method.
	 */
	public static ArrayList readAllAdmins(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)manage.read(filename);
		ArrayList alr = new ArrayList();// to store Admins data

		for (int i = 0; i < stringArray.size(); i++) {
			String st = (String) stringArray.get(i);
			StringTokenizer star = new StringTokenizer(st, SEPARATOR); 

			String gender = star.nextToken().trim();
			String nationality = star.nextToken().trim();
			String name = star.nextToken().trim();
			String userId = star.nextToken().trim();
			String password = star.nextToken().trim(); // second token
			Admin admin = new Admin(gender, nationality, name, userId, password);

			
			alr.add(admin);
		}
		return alr;
	}

	/**
	 * This method allows a student to be added by the Admin
	 * 
	 * 
	 * @return returns a true if the student has been successfully added or false if otherwise
	 */
	public boolean AddStudent() {
		boolean status;
	
		Scanner sc = new Scanner(System.in);
		List studentSave = new ArrayList();
		System.out.println("No of students to be added");
		int input = sc.nextInt();

		for (int i = 0; i < input; i++) {
			String temp = sc.nextLine();
			Student student = new Student();

			System.out.println("Please enter student name: ");

		
			student.setName(sc.nextLine());
			String userId=null;
			do{
			System.out.println("Please enter student Matriculation Number(formart:U1234567Z): ");
			userId = sc.next();
			status=this.validateMatricNum(userId);
			}while(status!=true);
		
			userId = userId.toLowerCase();
			student.setMatriculationNumber(userId.toLowerCase());

			System.out.println("Please enter student email : ");
			String emailId = sc.next();
			student.setUserId(emailId);

			System.out.println("Please enter student password: ");
			String clearPass = sc.next();
			String hashedPass = null;
			try {
				hashedPass = h1.hashString(clearPass, userId);
			} catch (Exception e) {

				e.printStackTrace();
			}
			student.setPassword(hashedPass);

			System.out.println("Please enter student nationality: ");
			student.setNationality(sc.next());

			System.out.println("Please enter student gender: ");
			student.setGender(sc.next());

			studentSave.add(student);
		}

		try {
			boolean clash = false;
			List student = StudentController.readAllStudents("DataBase/student.txt");
			if (student.size() != 0) {
				for (int x = 0; x < student.size(); x++) {
					Student s = (Student) student.get(x);
					for (int k = 0; k < studentSave.size(); k++) {
						Student s1 = (Student) studentSave.get(k);
						if (s.getMatriculationNumber().toLowerCase()
								.equals(s1.getMatriculationNumber().toLowerCase())) {
							System.out.println("Student Exist.Cannot add Again!!!!!");
							clash = true;

						}

					}

				}
				if (clash == false) {
					StudentController.saveStudent("DataBase/student.txt", studentSave);
				}
				List student1 = StudentController.readAllStudents("DataBase/student.txt");
					System.out.println("*******************************************");
				for (int x = 0; x < student1.size(); x++) {
					Student s = (Student) student1.get(x);
					System.out.println("Student Number: "+(x+1));
					System.out.println("Name: "+s.getName());
					System.out.println("Gender: "+s.getGender());
					System.out.println("Nationality: "+s.getNationality());
					System.out.println("MatricNumer: "+s.getMatriculationNumber());
					System.out.println("emal: "+s.getUserId());
					System.out.println("*******************************************");
				}

			}

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public ArrayList<Admin> retriveAllAdmins() {
		ArrayList<Admin> a2 = new ArrayList<Admin>();

		AdminController txtDB = new AdminController();
		String filename = "DataBase/admin.txt";
		try {
			// read file containing Professor records.
			ArrayList al = AdminController.readAllAdmins(filename);
			for (int i = 0; i < al.size(); i++) {
				Admin admin = (Admin) al.get(i);
				a2.add(admin);
			}
		} catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}

		return a2;
	}
	
	/**
	 * 
	 * A method to check whether the Admins userId and password is valid
	 * 
	 * @param userId The userid of the administrator
	 * @param password The password of the administrator
	 * @return True, if the method is valid and false if otherwise
	 */
	public boolean checkAccount(String userId, String password) {
		int valid = 0;
		ArrayList<Admin> a1 = retriveAllAdmins();

		for (int i = 0; i < a1.size(); i++) {
			String hashedValue = null;

			try {
				hashedValue = h1.hashString(password, userId);
			} catch (Exception e) {

				e.printStackTrace();
			}

			int userVal = a1.get(i).getUserId().toLowerCase().compareTo(userId.toLowerCase());
			int passVal = a1.get(i).getPassword().compareTo(hashedValue);

			
			if ((userVal == passVal)) {
				valid = 1;
				System.out.println("Account accessed");
				System.out.println("Gender: " + a1.get(i).getGender());
				System.out.println("Name: " + a1.get(i).getName());
				break;

			}

			else {
				valid = 0;

			}
		}

		if (valid == 1) {
			return true;
		}

		else {
			return false;
		}

	}
	
	/**
	 * This method checks whether there is vacancy given a
	 * 
	 * @param courseCode
	 * @return
	 */
	public boolean vacancy(String courseCode) {

		CourseController display = new CourseController();
		return display.adminShowCourse(courseCode);

	}

	public boolean printByCourseCode(String courseCode) {

		CourseController display = new CourseController();
		boolean state = display.adminPrintByCourseCode(courseCode);

		return state;
	}

	public boolean printByIndex(String courseCode, int index) {
		CourseController display = new CourseController();
		boolean state = display.adminPrintByCourseCodeAndIndex(courseCode, index);
		return state;
	}

	public boolean validateMatricNum(String txt) {
		//String txt="U1234567K";

	    String re1="(U)";	// Any Single Character 1
	    String re2="(\\d)";	// Any Single Digit 1
	    String re3="(\\d)";	// Any Single Digit 2
	    String re4="(\\d)";	// Any Single Digit 3
	    String re5="(\\d)";	// Any Single Digit 4
	    String re6="(\\d)";	// Any Single Digit 5
	    String re7="(\\d)";	// Any Single Digit 6
	    String re8="(\\d)";	// Any Single Digit 7
	    String re9="([A-Z])";	// Any Single Word Character (Not Whitespace) 1

	    Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7+re8+re9, Pattern.DOTALL);
	    Matcher m = p.matcher(txt);
	    if (m.find())
	    {
	    	
	    	System.out.println("valid");
	    	return true;
//	        String c1=m.group(1);
//	        String d1=m.group(2);
//	        String d2=m.group(3);
//	        String d3=m.group(4);
//	        String d4=m.group(5);
//	        String d5=m.group(6);
//	        String d6=m.group(7);
//	        String d7=m.group(8);
//	        String w1=m.group(9);
//	        System.out.print("("+c1.toString()+")"+"("+d1.toString()+")"+"("+d2.toString()+")"+"("+d3.toString()+")"+"("+d4.toString()+")"+"("+d5.toString()+")"+"("+d6.toString()+")"+"("+d7.toString()+")"+"("+w1.toString()+")"+"\n");
	    }
	    
	    else
	    {
	    	
	    	System.out.println("invalid input key in correct inputs");
	    	return false;
	    }
	}

	
//	public static void main(String args[]) {
//		Scanner sc = new Scanner(System.in);
//		String courseCode;
//		int index;
//		char s;
//		AdminController ad = new AdminController();
//		boolean result = false;
//	ad.AddStudent();
//
//	}

}

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

import Miscellaneous.Hash;
import Model.Access;
import Model.Admin;
import Model.Student;

public class AdminController {
	public static final String SEPARATOR = "|";
	private Hash h1 = new Hash();

	public static ArrayList readAllAdmins(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList) read(filename);
		ArrayList alr = new ArrayList();// to store Admins data

		for (int i = 0; i < stringArray.size(); i++) {
			String st = (String) stringArray.get(i);
			// get individual 'fields' of the string separated by SEPARATOR
			StringTokenizer star = new StringTokenizer(st, SEPARATOR); // pass
																		// in
																		// the
																		// string
																		// to
																		// the
																		// string
																		// tokenizer
																		// using
																		// delimiter
																		// ","

			String gender = star.nextToken().trim();
			String nationality = star.nextToken().trim();
			String name = star.nextToken().trim();
			String userId = star.nextToken().trim();
			String password = star.nextToken().trim(); // second token
			// int contact = Integer.parseInt(star.nextToken().trim()); // third
			// token
			// create Admin object from file data
			Admin admin = new Admin(gender, nationality, name, userId, password);

			// add to Professors list
			alr.add(admin);
		}
		return alr;
	}

	/** Read the contents of the given file. */
	public static List read(String fileName) throws IOException {
		List data = new ArrayList();
		Scanner scanner = new Scanner(new FileInputStream(fileName));
		try {
			while (scanner.hasNextLine()) {
				data.add(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}
		return data;
	}

	/** Write fixed content to the given file. */
	public static void write(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName, true));// true
																			// to
																			// ensure
																			// the
																			// previous
																			// data
																			// is
																			// not
																			// deleted

		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}

	public boolean AddStudent() {
		Scanner sc = new Scanner(System.in);
		List studentSave = new ArrayList();
		System.out.println("No of students to be added");
		int input = sc.nextInt();

		for (int i = 0; i < input; i++) {
			String temp = sc.nextLine();// to eliminate the buffer /n
			Student student = new Student();

			System.out.println("Please enter student name: ");

			// String name=sc.nextLine();
			student.setName(sc.nextLine());
			System.out.println("Please enter student Matriculation Number(formart:U1234567Z): ");
			// String matriculationNumber=sc.next();
			String userId = sc.next();
			this.validateMatricNum(userId);
		
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

			// System.out.println("database"+a1.get(i).getName());
			int userVal = a1.get(i).getUserId().toLowerCase().compareTo(userId.toLowerCase());
			int passVal = a1.get(i).getPassword().compareTo(hashedValue);

			System.out.println(userVal);
			if ((userVal == passVal)) {
				//System.out.println("yes");
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

	public boolean validateMatricNum(String ip) {
		boolean result = true;

		if (ip == null || !ip.matches("^([0-2][0-9]||3[0-1])(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$")) {
			System.out.println("MatricNum Inout error");
			result = false;
		}
	
		else {
			result = true;;
		}

		return result;
	}

	
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		String courseCode;
		int index;
		char s;
		AdminController ad = new AdminController();
		boolean result = false;
	ad.AddStudent();

	}

}

package Control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import Miscellaneous.Hash;
import Model.AddDrop;
import Model.Admin;
import Model.Course;
import Model.Schedule;
import Model.Student;

/**
 * Student Controller class handles student functions and calls to the data file
 * 
 * @author Ameen
 * @author Ashiq
 * @author Will
 * @author Reuben
 * @author Waqas
 * @since 2017-04-01
 * @version 1.0
 */
public class StudentController {
	/**
	 * Instantiated a FileManager object to read and write data to the text files.
	 */
	public static FileManager manage =new FileManager();
	
	/**
	 * A separator which is used to separate the elements
	 */
	public static final String SEPARATOR = "|";
	
	/**
	 * Instantiated an AddDropController object
	 */
	private AddDropController addDrop = new AddDropController();
	
	/**
	 * Instantiated a CourseController object
	 */
	private CourseController Cc1 = new CourseController();
	
	/**
	 * Instantiated a ScheduleController object
	 */
	private ScheduleController schedule = new ScheduleController();
	
	/**
	 * Instantiated a Hash object
	 */
	private Hash h1 = new Hash();
	
	/**
	 * This method reads all the students 
	 * 
	 * @param filename of where the students should be read from
	 * @return An arraylist with student objects in it
	 * @throws IOException throws an exception when there is a problem reading contents
	 * 
	 */
	public static ArrayList readAllStudents(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList) manage.read(filename);
		ArrayList alr = new ArrayList();// to store Admins data
		for (int i = 0; i < stringArray.size(); i++) {
			String st = (String) stringArray.get(i);
			StringTokenizer star = new StringTokenizer(st, SEPARATOR); 

			String matriculationNumber = star.nextToken().trim();
			String name = star.nextToken().trim();
			String userId = star.nextToken().trim();
			String password = star.nextToken().trim();
			String nationality = star.nextToken().trim();
			String gender = star.nextToken().trim();

			Student student = new Student(matriculationNumber, name, userId, password, nationality, gender);

			// add student objects to alr
			alr.add(student);
		}
		return alr;
	}

	
	/**
	 * 
	 * This method creates a string builder with student objects
	 * 
	 * @param filename where the contents should be written onto
	 * @param al is the List of contents
	 * @throws IOException if there is an issue building the string
	 */
	public static void saveStudent(String filename, List al) throws IOException {
		List alw = new ArrayList();

		for (int i = 0; i < al.size(); i++) {
			Student student = (Student) al.get(i);
			StringBuilder st = new StringBuilder();
			st.append(student.getMatriculationNumber().trim());
			st.append(SEPARATOR);
			st.append(student.getName().trim());
			st.append(SEPARATOR);
			st.append(student.getUserId().trim());
			st.append(SEPARATOR);
			st.append(student.getPassword().trim());
			st.append(SEPARATOR);
			st.append(student.getNationality().trim());
			st.append(SEPARATOR);
			st.append(student.getGender().trim());
			alw.add(st.toString());
		}
		manage.write(filename, alw);
	}
	
	/**
	 * This method deletes the student from a text file
	 * 
	 * @param file refers to the name of the file
	 * @param matriculationNumber refers to Student's matric number 
	 * @return True if the deletion is successful.False,if deletion is unsuccessful
	 */
	public boolean deleteStudent(String file, String matriculationNumber) {
		boolean check =false;
		try {
			ArrayList<Student> a1 = readAllStudents(file);

			List l2 = new ArrayList();
			for (int i = 0; i < a1.size(); i++) {
				Student student = (Student) a1.get(i);

				String mat = student.getMatriculationNumber();

				if (mat.toLowerCase().equals(matriculationNumber.toLowerCase())) {
					check=true;
					a1.remove(i);
				}

			}

			for (int i = 0; i < a1.size(); i++) {
				Student student = (Student) a1.get(i);
				l2.add(a1.get(i));
			}

			saveStudentAmmend(file, l2);

			ArrayList<Student> a2 = readAllStudents(file);
			System.out.println("*******************************************");
			for (int i = 0; i < a1.size(); i++) {
				Student s = (Student) a1.get(i);
				System.out.println("Student Number: " + (i + 1));
				System.out.println("Name: " + s.getName());
				System.out.println("Gender: " + s.getGender());
				System.out.println("Nationality: " + s.getNationality());
				System.out.println("MatricNumer: " + s.getMatriculationNumber());
				System.out.println("emal: " + s.getUserId());
				System.out.println("*******************************************");
			}

		} catch (IOException e) {

			return false;
		}

		return check;
	}

	
	
	/**
	 * A method to save the amended content onto the file after deleting the student from the file
	 * 
	 * @param filename the name of the file
	 * @param al refers to a List of content
	 * @throws IOException is thrown if an exception is thrown
	 */
	public static void saveStudentAmmend(String filename, List al) throws IOException {
		List alw = new ArrayList();// to store students data

		for (int i = 0; i < al.size(); i++) {
			Student student = (Student) al.get(i);
			StringBuilder st = new StringBuilder();
			st.append(student.getMatriculationNumber().trim());
			st.append(SEPARATOR);
			st.append(student.getName().trim());
			st.append(SEPARATOR);
			st.append(student.getUserId().trim());
			st.append(SEPARATOR);
			st.append(student.getPassword().trim());
			st.append(SEPARATOR);
			st.append(student.getNationality().trim());
			st.append(SEPARATOR);
			st.append(student.getGender().trim());
			alw.add(st.toString());
		}
		manage.writeNew(filename, alw);
	}
	
	/**
	 * A method which retrieves all students
	 * 
	 * 
	 * @return An ArrayList of Student objects
	 */
	public ArrayList<Student> retriveAllStudent() {
		ArrayList<Student> a2 = new ArrayList<Student>();
		
		String filename = "DataBase/student.txt";
		try {
			ArrayList al = StudentController.readAllStudents(filename);
			for (int i = 0; i < al.size(); i++) {
				Student student = (Student) al.get(i);
				a2.add(student);

			}

		} catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}

		return a2;
	}
	
	/**
	 * A method to check the validity of a student account
	 * 
	 * @param matriculationNumber of a student
	 * @param password of the user
	 * @return True if the account credentials are valid and False if otherwise
	 */
	public boolean checkAccount(String matriculationNumber, String password) {
		int valid = 0;
		ArrayList<Student> a1 = retriveAllStudent();

		for (int i = 0; i < a1.size(); i++) {

			String hashedValue = null;
						
			try {
				hashedValue = h1.hashString(password, matriculationNumber);
			} catch (Exception e) {

				e.printStackTrace();
			}

			int userVal = a1.get(i).getMatriculationNumber().compareTo(matriculationNumber);
			int passVal = a1.get(i).getPassword().compareTo(hashedValue);

			if (userVal == 0 && passVal == 0) {

				valid = 1;
				System.out.println("Welcome to the Stars Planner system "+a1.get(i).getName());
				System.out.println("                                             ");
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
	 * A method to display all courses given a matriculation number
	 * 
	 * 
	 * @param matriculationNum of a student
	 */
	public void displayCourse(String matriculationNum) {
		boolean check = false;
		try {
			String courseCode = null;
			List StringArray = addDrop.readAllCourseAndStudent("DataBase/courseAndStudent.txt");
			int index = 0;

			for (int x = 0; x < StringArray.size(); x++) {
				AddDrop addDrop = (AddDrop) StringArray.get(x);
				for (int k = 0; k < addDrop.getList().size(); k++) {
					if (addDrop.getList().get(k).toLowerCase().equals(matriculationNum.toLowerCase())) {
						courseCode = addDrop.getCourseCode();
						check = true;
						index = addDrop.getIndex();
						System.out.println("Course Code: " + addDrop.getCourseCode() + " Index: " + addDrop.getIndex());

						List StringArray2 = schedule.readSchedule(courseCode);
						for (int q = 0; q < StringArray2.size(); q++) {
							Schedule s = (Schedule) StringArray2.get(q);
							if (courseCode.toLowerCase().equals(s.getCourseCode().toLowerCase())
									&& s.getIndex() == index) {
								System.out.println("Day: " + s.getDay() + " venue: " + s.getVenue());
								System.out.println("Type: " + s.getType() + " StartTime: " + s.getStartTime()
										+ " EndTime: " + s.getEndTime());

							}

						}

						System.out.println(
								"========================end of line============================================");

					}
				}

			}

			if (check == false) {
				System.out.println("                                                    ");
				System.out.println("System message: You have not registered for a course");
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A method to swap index
	 * 
	 * @param index to be the new index
	 * @param oldindex to be swapped
	 * @param courseCode of the course
	 * @param matricNum matriculation number of the student
	 */
	public void changeIndex(int index, int oldindex, String courseCode, String matricNum) {
		try {
				boolean status=false;
			int temp;
			List list = new ArrayList();
			list.add(matricNum);
			int courseIndex = 0;
			String courseID = null;
			int retIndex = 0;
			List readCourse = new ArrayList();
			 readCourse = Cc1.readAllCourse("DataBase/courses.txt");
			List StringArray = addDrop.readAllCourseAndStudent("DataBase/courseAndStudent.txt");
			for (int x = 0; x < StringArray.size(); x++) {
				AddDrop changeIndex = (AddDrop) StringArray.get(x);
				/* To remove the matric number from old index number */
				if (changeIndex.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())
						&& changeIndex.getIndex() == oldindex) {
					for (int k = 0; k < changeIndex.getList().size(); k++) {
						if (changeIndex.getList().get(k).toLowerCase().equals(matricNum.toLowerCase())) {
							changeIndex.getList().remove(k);
							courseIndex = changeIndex.getIndex();
							courseID = changeIndex.getCourseCode();
						}
					}

				}
			}
	status=schedule.clashcheck(courseCode, index, matricNum);
				if(status==true){
					for (int x = 0; x < StringArray.size(); x++) {
						AddDrop changeIndex = (AddDrop) StringArray.get(x);
						/* To remove the matric number from old index number */
						if (changeIndex.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())
								&& changeIndex.getIndex() == oldindex) {
							for (int k = 0; k < changeIndex.getList().size(); k++) {
								if (changeIndex.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())&& changeIndex.getIndex()==courseIndex) {
								changeIndex.getList().add(matricNum);
								}
							}

						}
					}
					System.out.println("System message:Clash with timeTable.Index was not changed!!");
					
				}
				else{
					
					

					for (int i = 0; i < readCourse.size(); i++) {
						Course course = (Course) readCourse.get(i);
						

						for (int j = 0; j < course.getVacancy().length; j++) {

							if ((j + 1) == courseIndex && courseID.toLowerCase().equals(course.getCourseCode().toLowerCase())) {
								course.increaseVacancy(courseIndex);
							}

							if ((j + 1) == index && (courseID.toLowerCase().equals(course.getCourseCode().toLowerCase()))) {
								course.decreaseVacancy(index);
							}

						}

					}

					if (addDrop.validateIndexOfCourseAndStudent(index, courseCode)) {
						List newArray = new ArrayList();
						// newArray.add(StringArray.get(retIndex));
						List matric = new ArrayList();
						matric.add(matricNum);
						AddDrop a1 = new AddDrop(courseCode, index, matric);
						StringArray.add(a1);
						addDrop.saveAmend(StringArray);
						System.out.println("System message:You index for the course is changed to :" + index);
						addDrop.saveAmend(StringArray);
						
					}

					
					
				}
				
		

			Cc1.saveCourseAmend("DataBase/courses.txt", readCourse);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * A method to swap index by matriculation id
 * 	
 * @param courseCode of a particular course
 * @param matricNum of a student A
 * @param newMatricId of a student B
 */
	public void changeMatricId(String courseCode, String matricNum, String newMatricId) {
		try {
			int newIndex = 1, oldIndex = 1;
			int temp;
			boolean check,check2;
			ScheduleController SDC = new ScheduleController();
			List StringArray = addDrop.readAllCourseAndStudent("DataBase/courseAndStudent.txt");
			for (int x = 0; x < StringArray.size(); x++) {
				AddDrop changeIndex = (AddDrop) StringArray.get(x);
				/* To remove the matric number from old index number */
				if (changeIndex.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())) {
					for (int k = 0; k < changeIndex.getList().size(); k++) {
						if (changeIndex.getList().get(k).toLowerCase().equals(newMatricId.toLowerCase())) {

							newIndex = changeIndex.getIndex();
							check = SDC.clashcheck(courseCode, newIndex, matricNum);
							if (check == true) {
								newIndex = 0;
								System.out.println(
										"System message:TimeTable Clash cannot swap with the student you have clash of timetable");
							}
						}
						if (changeIndex.getList().get(k).toLowerCase().equals(matricNum.toLowerCase())) {

							oldIndex = changeIndex.getIndex();
							check2 = SDC.clashcheck(courseCode, oldIndex, newMatricId);
							if (check2 == true) {
								oldIndex = 0;
								System.out.println(
										"System message:TimeTable Clash cannot swap with the student ,the other student has clash with timeTable");
							}
						}

					}
				}

			}

			if (newIndex != 0 && oldIndex != 0) {
				for (int x = 0; x < StringArray.size(); x++) {

					AddDrop changeIndex = (AddDrop) StringArray.get(x);
					if (changeIndex.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())) {
						for (int k = 0; k < changeIndex.getList().size(); k++) {
							if (changeIndex.getList().get(k).toLowerCase().equals(newMatricId.toLowerCase())
									&& changeIndex.getIndex() == newIndex) {
								changeIndex.getList().remove(k);
								changeIndex.getList().add(k, matricNum);

							}
							if (changeIndex.getList().get(k).toLowerCase().equals(matricNum.toLowerCase())
									&& changeIndex.getIndex() == oldIndex) {
								changeIndex.getList().remove(k);
								changeIndex.getList().add(k, newMatricId);

							}
						}
//
					}
				}
				
				System.out.println("System message:You have swap your index with the other student");
			}

			addDrop.saveAmend(StringArray);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
//public static void main(String args[]){
//	StudentController c=new StudentController();
//	c.changeIndex(1, 2, "ee3015", "n1602952b");
//}
//}
//}

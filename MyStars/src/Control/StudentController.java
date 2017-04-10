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

public class StudentController {
	/**
	 * Instantiated a FileManager object to read and write data to the text files.
	 */
	public static FileManager manage =new FileManager();
	public static final String SEPARATOR = "|";
	private AddDropController addDrop = new AddDropController();
	private CourseController Cc1 = new CourseController();
	private ScheduleController schedule = new ScheduleController();
	private Hash h1 = new Hash();

	public static ArrayList readAllStudents(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList) manage.read(filename);
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

	
	// an example of saving
	public static void saveStudent(String filename, List al) throws IOException {
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
		manage.write(filename, alw);
	}

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
				// System.out.println("Gender: "+student.getGender());
				// System.out.println("MatriculationNumber:
				// "+student.getMatriculationNumber());
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

	

		/*
	 * Implement another save to amend the content in the file after deleting
	 * the student from the file
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

	public ArrayList<Student> retriveAllStudent() {
		ArrayList<Student> a2 = new ArrayList<Student>();
		// String filename = new File("src/student.txt").g
		String filename = "DataBase/student.txt";
		try {
			// read file containing Professor records.
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
				System.out.println("You have yet to register for a course!!!");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changeIndex(int index, int oldindex, String courseCode, String matricNum) {
		try {

			int temp;
			List list = new ArrayList();
			list.add(matricNum);

			int courseIndex = 0;
			String courseID = null;
			int retIndex = 0;

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
							// System.out.println("triggered");
						}
					}

				}
				if (changeIndex.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())
						&& changeIndex.getIndex() == index) {
					retIndex = x;
					changeIndex.setList(list);
				}

			}

			List readCourse = Cc1.readAllCourse("DataBase/courses.txt");

			for (int i = 0; i < readCourse.size(); i++) {
				Course course = (Course) readCourse.get(i);
				// System.out.println("Course Id: " + course.getCourseCode());
				// System.out.println(courseIndex);

				for (int j = 0; j < course.getVacancy().length; j++) {
					// System.out.println("Index " + (j + 1));

					if ((j + 1) == courseIndex && courseID.toLowerCase().equals(course.getCourseCode().toLowerCase())) {
						course.increaseVacancy(courseIndex);
						// System.out.println("INcreased");
					}

					if ((j + 1) == index && (courseID.toLowerCase().equals(course.getCourseCode().toLowerCase()))) {
						course.decreaseVacancy(index);
						// System.out.println("Decreased");
					}

				}

			}

			if (addDrop.validateIndexOfCourseAndStudent(index, courseCode)) {
				addDrop.saveAmend(StringArray);
				System.out.println("You index for the course is changed to :" + index);
//				List newArray = new ArrayList();
//				// newArray.add(StringArray.get(retIndex));
//				List matric = new ArrayList();
//				matric.add(matricNum);
//				AddDrop a1 = new AddDrop(courseCode, index, matric);
//				StringArray.add(a1);
//				addDrop.saveAmend(StringArray);
			}

			else {
				
				List newArray = new ArrayList();
				// newArray.add(StringArray.get(retIndex));
				List matric = new ArrayList();
				matric.add(matricNum);
				AddDrop a1 = new AddDrop(courseCode, index, matric);
				StringArray.add(a1);
				addDrop.saveAmend(StringArray);
			}

			Cc1.saveCourseAmend("DataBase/courses.txt", readCourse);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changeMatricId(String courseCode, String matricNum, String newMatricId) {
		try {
			int newIndex = 1, oldIndex = 1;
			int temp;
			boolean check;
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
										"TimeTable Clash cannot swap with the student you have clash of timetable");
							}
							// System.out.println("triggered");
						}
						if (changeIndex.getList().get(k).toLowerCase().equals(matricNum.toLowerCase())) {

							oldIndex = changeIndex.getIndex();
							check = SDC.clashcheck(courseCode, oldIndex, newMatricId);
							if (check == true) {
								oldIndex = 0;
								System.out.println(
										"TimeTable Clash cannot swap with the student ,the other student has clash with timeTable");
							}
						}

					}
					// test
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

					}
				}
				
				System.out.println("You have swap your index with the other student");
			}

			addDrop.saveAmend(StringArray);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* for testing display course by student */

	// public static void main(String args[]){
	// StudentController n=new StudentController();
	// n.changeMatricId("mh1812", "u165", "u163");
	// }

}

package Control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.AddDrop;
import Model.Course;
import Model.Schedule;
import Model.Student;

/**
 * This course controllers manages the creation,modification and deletion of courses
 * @author Ameen
 * @author Ashiq
 * @author Will
 * @author Reuben
 * @author Waqas
 * @since 2017-04-01
 * @version 1.0
 */
public class CourseController {
	
	/**
	 * Instantiated a FileManager object to read and write data to the text files
	 */
	private static FileManager manage =new FileManager();
	
	/**
	 * A separator that is used to separate elements read from a file
	 */
	public static final String SEPARATOR = "|";
	
	/**
	 *An index_separator that is used to separate elements reads from a file
	 */
	public static final String INDEX_SEPARATOR = "_";
	
	/**
	 * Declared a ScheduleController object
	 */
	private ScheduleController schedulecontroller = null;;
	
	/**
	 * An enum which consists of the type of course
	 * 
	 * 
	 * @author Ameen
	 *
	 */
	private enum type {
		LECTURE, LABORATORY, TUTORIAL;
	}
	
	/**
	 * 
	 * An enum which consists of all the days of a week
	 * @author Ameen
	 *
	 */
	private enum weekdays {
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY;
	}

	/**
	 * An empty course controller instructor
	 */
	public CourseController() {
	}

	/**
	 * 
	 * This method reads all the courses given a file name
	 * 
	 * @param filename Read the filename where the contents should be read from
	 * @return An Arraylist of course objects
	 * @throws IOException Exception is thrown if there is a problem reading the file
	 */
	public static ArrayList readAllCourse(String filename) throws IOException {
		ArrayList stringArray = (ArrayList) manage.read(filename);
		ArrayList alr = new ArrayList();// to store Admins data

		for (int i = 0; i < stringArray.size(); i++) {

			String st = (String) stringArray.get(i);
			StringTokenizer star = new StringTokenizer(st, SEPARATOR); 
			String courseCode = star.nextToken().trim();
			String courseName = star.nextToken().trim();
			String school = star.nextToken().trim();
			String startDate = star.nextToken().trim();
			String endDate = star.nextToken().trim();
			int counter = 0;
			counter = star.countTokens();

			int[] retrievedIndex = new int[counter];
			int[] retrievedTotal = new int[counter];

			for (int j = 0; j < counter; j++) {

				String index = star.nextToken("_").trim();
				StringBuilder modifyIndex = new StringBuilder(index);
				index = modifyIndex.deleteCharAt(0).toString();

				String vacancy = star.nextToken("_").trim();

				StringBuilder modifyVacancy = new StringBuilder(vacancy);
				vacancy = modifyVacancy.toString();

				String total = star.nextToken("|").trim();

				StringBuilder modifyTotal = new StringBuilder(total);
				total = modifyTotal.deleteCharAt(0).toString();

				retrievedTotal[j] = Integer.parseInt(total);
				retrievedIndex[j] = Integer.parseInt(vacancy);

			}


			Course course = new Course(courseCode, courseName, school, startDate, endDate, retrievedIndex,
					retrievedTotal);
			alr.add(course);
		}
		return alr;
	}

	
	/**
	 * This methods is to add a course into the system
	 * 
	 * 
	 * @return True, if the course has been successfully added and false if not
	 */
	public boolean addCourse() {
		boolean check = true;
		boolean status;
		Scanner sc = new Scanner(System.in);
		List courseSave = new ArrayList();
		List scheduleList = new ArrayList();
		String startTime = null, venue, day = null, endTime = null;
		String currentIndex;

		System.out.println("No of courses to be added");
		int input = sc.nextInt();
		int lec;
		char lab = 0, tut = 0;
		int totalIndex = 0;
		String Temp = sc.nextLine();
		for (int i = 0; i < input; i++) {

			String code, name, school, startDate, endDate;
			int capacity, noOfSections, temp;

			do {
				System.out.println("Please enter course code format[CE1234]: ");
				code = sc.nextLine();
				status = validateCourseCode(code);
				code = code.toLowerCase();
			} while (status != true);

			
			try {
				List course1 = readAllCourse("DataBase/courses.txt");
				for (int x = 0; x < course1.size(); x++) {

					Course n = (Course) course1.get(x);
					if (n.getCourseCode().toLowerCase().equals(code.toLowerCase())) {
						System.out.println("Course is already added cannot add repeated course re-enter options !!!!!");
						check = false;
						break;
					}

				}
			} catch (IOException e1) {
			
				e1.printStackTrace();
			}

			if (check == false) {
				return false;

			}
			

			System.out.println("Please enter course name: ");
			name = sc.nextLine();

			System.out.println("Please enter course school : ");
			school = sc.nextLine();

			System.out.println("Please enter the number of index in the course: ");
			noOfSections = sc.nextInt();
			capacity = 0;
			int[] indices = new int[noOfSections];

			for (int g = 0; g < noOfSections; g++) {
				System.out.println("Please enter the capacity of index " + (g + 1) + ": ");
				temp = sc.nextInt();
				indices[g] = temp;
				totalIndex = g + 1;
			}
			Temp = sc.nextLine();  

			for (int q = 0; q < totalIndex; q++) {
				System.out.println("Index: " + (q + 1));
				System.out.println("How many lectures does it have in a week:");
				lec = sc.nextInt();
				for (int p = 0; p < lec; p++) {
					System.out.println("Enter fileds for: " + (p + 1) + " Lecture");

					do {
						System.out.println("The intended day to have the Lecture?");

						System.out.println("============Options===========");
						System.out.println("| 1) Monday                  |");
						System.out.println("| 2) Tuesday                 |");
						System.out.println("| 3) Wednesday               |");
						System.out.println("| 4) Thursday                |");
						System.out.println("| 5) Friday                  |");
						System.out.println("========End Of Options========");
						int inputDay = 0;
						try {

							inputDay = sc.nextInt();

						} catch (Exception ex) {
							inputDay = 0;
						}

						switch (inputDay)

						{
						case 1:
							day = weekdays.MONDAY.toString();
							break;

						case 2:
							day = weekdays.TUESDAY.toString();
							break;

						case 3:
							day = weekdays.WEDNESDAY.toString();
							break;

						case 4:
							day = weekdays.THURSDAY.toString();
							break;

						case 5:
							day = weekdays.FRIDAY.toString();
							break;

						default:
							System.out.println("You have entered the wrong input key in again");
						}
					} while (input == 0 || input >= 6);

					System.out.println("Where is the location?:");
					venue = sc.next();
					boolean result = false;
					do {
						result = false;

						String ip = "";
						String pattern = "^([01]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d)$";

						System.out.println("Enter the start time in the format HH:MM:SS");
						ip = sc.next();
						Pattern r = Pattern.compile(pattern);
						Matcher m = r.matcher(ip);
						if (m.find()) {

							result = true;
							startTime = ip;
						} else {
							System.out.println("Start time entered is in the wrong format");
							result = false;
						}
					} while (result == false);

					do {
						result = false;

						String ip = "";
						String pattern = "^([01]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d)$";

						System.out.println("Enter the end time in the format HH:MM:SS");
						ip = sc.next();
						Pattern r = Pattern.compile(pattern);
						Matcher m = r.matcher(ip);
						if (m.find()) {
							// System.out.println("Time" + m.group(0));
							// System.out.println("hh " + m.group(1));
							// System.out.println("mm " + m.group(2));
							// System.out.println("ss " + m.group(3));
							result = true;
							endTime = ip;
						} else {
							System.out.println("End time entered is in a wrong format ");
							result = false;
						}
					} while (result == false);

					Schedule schedule = new Schedule(code, (q + 1), type.LECTURE.toString(), day, venue, startTime,
							endTime);
					scheduleList.add(schedule);

				}
				do {
					System.out.println("Does it have a Lab:? (y/n)");
					lab = sc.next().toLowerCase().charAt(0);
					System.out.println("Does it have a Tut:?(y/n)");
					tut = sc.next().toLowerCase().charAt(0);
				} while (!((lab != 'y' && tut != 'n') || (lab == 'y' && tut != 'n') || (lab != 'y' && tut == 'n')
						|| (lab == 'y' && tut == 'n')));
				// Schedule schedule =new Schedule(code,(q+1),);
				if (lab == 'y') {

					do {
						System.out.println("The intended day to have the LAB?");

						System.out.println("===========Options==============");
						System.out.println(" 1) Monday                     |");
						System.out.println(" 2) Tuesday                    |");
						System.out.println(" 3) Wednesday                  |");
						System.out.println(" 4) Thursday                   |");
						System.out.println(" 5) Friday                     |");
						System.out.println("========End Of Options==========");
						int inputDay = 0;
						try {

							inputDay = sc.nextInt();

						} catch (Exception ex) {
							inputDay = 0;
						}

						switch (inputDay)

						{
						case 1:
							day = weekdays.MONDAY.toString();
							break;

						case 2:
							day = weekdays.TUESDAY.toString();
							break;

						case 3:
							day = weekdays.WEDNESDAY.toString();
							break;

						case 4:
							day = weekdays.THURSDAY.toString();
							break;

						case 5:
							day = weekdays.FRIDAY.toString();
							break;

						default:
							System.out.println("You have entered the wrong input");
						}
					} while (input == 0 || input >= 6);

					System.out.println("Where is the location?");
					venue = sc.next();
					boolean result = false;
					do {
						result = false;

						String ip = "";
						String pattern = "^([01]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d)$";
						//String pattern = "^([01]?\\d|2[0-3]):([0-5]?\\d)$";
						System.out.println("Enter the start time in the format HH:MM:SS");
						ip = sc.next();
						Pattern r = Pattern.compile(pattern);
						Matcher m = r.matcher(ip);
						if (m.find()) {

							result = true;
							startTime = ip;
						} else {
							System.out.println("Invalid format! Please try again");
							result = false;
						}
					} while (result == false);

					do {
						result = false;

						String ip = "";
						//String pattern = "^([01]?\\d|2[0-3]):([0-5]?\\d)$";
						String pattern = "^([01]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d)$";
						System.out.println("Enter the end time in the format HH:MM:SS");
						ip = sc.next();
						Pattern r = Pattern.compile(pattern);
						Matcher m = r.matcher(ip);
						if (m.find()) {
							result = true;
							endTime = ip;
						} else {
							System.out.println("Invalid format! Please try again");
							result = false;
						}
					} while (result == false);

					Schedule schedule = new Schedule(code, (q + 1), type.LABORATORY.toString(), day, venue, startTime,
							endTime);
					scheduleList.add(schedule);
				}
				if (tut == 'y') {

					do {
						System.out.println("The intended day to have the Tutorial?");
						System.out.println(" ");

						System.out.println("===========Options==============");
						System.out.println(" 1) Monday                     |");
						System.out.println(" 2) Tuesday                    |");
						System.out.println(" 3) Wednesday                  |");
						System.out.println(" 4) Thursday                   |");
						System.out.println(" 5) Friday                     |");
						System.out.println("========End Of Options==========");
						int inputDay = 0;
						try {

							inputDay = sc.nextInt();

						} catch (Exception ex) {
							inputDay = 0;
						}

						switch (inputDay)

						{
						case 1:
							day = weekdays.MONDAY.toString();
							break;

						case 2:
							day = weekdays.TUESDAY.toString();
							break;

						case 3:
							day = weekdays.WEDNESDAY.toString();
							break;

						case 4:
							day = weekdays.THURSDAY.toString();
							break;

						case 5:
							day = weekdays.FRIDAY.toString();
							break;

						default:
							System.out.println("You have entered the wrong input");
						}
					} while (input == 0 || input >= 6);
					System.out.println("Where is the location?");
					venue = sc.next();
					boolean result = false;
					do {
						result = false;

						String ip = "";
						String pattern = "^([01]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d)$";

						System.out.println("Enter the start time in the format HH:MM:SS");
						ip = sc.next();
						Pattern r = Pattern.compile(pattern);
						Matcher m = r.matcher(ip);
						if (m.find()) {
							result = true;
							startTime = ip;
						} else {
							System.out.println("Invalid format! Please try again");
							result = false;
						}
					} while (result == false);

					do {
						result = false;

						String ip = "";
						String pattern = "^([01]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d)$";

						System.out.println("Enter the end time in the format HH:MM:SS");
						ip = sc.next();
						Pattern r = Pattern.compile(pattern);
						Matcher m = r.matcher(ip);
						if (m.find()) {
							// System.out.println("Time" + m.group(0));
							// System.out.println("hh " + m.group(1));
							// System.out.println("mm " + m.group(2));
							// System.out.println("ss " + m.group(3));
							result = true;
							endTime = ip;
						} else {
							System.out.println("Invalid format.Please try again!");
							result = false;
						}
					} while (result == false);
					
					Schedule schedule = new Schedule(code, (q + 1), type.TUTORIAL.toString(), day, venue, startTime,
							endTime);
					scheduleList.add(schedule);
				}

			}
			
			boolean dateResult = false;

			do {
				dateResult = false;

				System.out.println("Please enter the start date of course in this format dd/mm/yyyy: ");
				startDate = sc.next();
				dateResult = validateDate(startDate);
			} while (dateResult != true);

			do {
				dateResult = false;

				System.out.println("Please enter the end date of course in this format dd/mm/yyyy: ");
				endDate = sc.next();
				dateResult = validateDate(endDate);
				System.out.println(dateResult);
			} while (dateResult != true);

			
			Course course = new Course(code, name, school, startDate, endDate, indices, indices); // here

			if (check == true) {
				schedulecontroller = new ScheduleController();
				schedulecontroller.saveSchedule(scheduleList, code);
				courseSave.add(course);
				Temp = sc.nextLine();// To eliminate the buffer
				File f = new File("DataBase/waitlists/" + code + ".txt");

				try {
					f.createNewFile();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

		}

		try {
			saveCourse("DataBase/courses.txt", courseSave);
			List courses = readAllCourse("DataBase/courses.txt");
			for (int k = 0; k < courses.size(); k++) {
				System.out.println("================================**=========================================");
				Course C = (Course) courses.get(k);
				System.out.println("Course name: " + C.getCourseName());
				System.out.println("Course code: " + C.getCourseCode());
				for (int h = 0; h < C.getIndices().length; h++) {
					System.out.println("Course index: " + (h + 1));
					System.out.println("Course vacancy: " + C.getIndices()[h]);
				}
				System.out.println("***************************************************************************");

			}

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 
	 * This method writes the course details onto a file
	 * 
	 * @param filename The filename where the contents should be written onto
	 * @param list Takes a list to be written
	 * @throws IOException throws an exception if there is problems saving the course
	 */
	public static void saveCourse(String filename, List list) throws IOException {
		List tempList = new ArrayList();// to store course

		for (int i = 0; i < list.size(); i++) {
			Course course = (Course) list.get(i);
			StringBuilder st = new StringBuilder();
			st.append(course.getCourseCode().trim());
			st.append(SEPARATOR);
			st.append(course.getCourseName().trim());
			st.append(SEPARATOR);
			st.append(course.getSchool().trim());
			st.append(SEPARATOR);

			st.append(course.getStartDate().trim());
			st.append(SEPARATOR);
			st.append(course.getEndDate().trim());
			st.append(SEPARATOR);

			for (int j = 0; j < course.getIndices().length; j++) {
				st.append(j + 1);
				st.append(INDEX_SEPARATOR);
				st.append(course.getVacancy()[j]);
				st.append(INDEX_SEPARATOR);
				st.append(course.getIndices()[j]);
				st.append(SEPARATOR);
				if (j == course.getIndices().length)

				{
					st.append(j + 1);
					st.append(INDEX_SEPARATOR);
					st.append(course.getVacancy()[j]);
					st.append(INDEX_SEPARATOR);
					st.append(course.getIndices()[j]);
				}

			}

			tempList.add(st.toString());
		}
		manage.write(filename, tempList);
	}


	/**
	 * This method allows a course to be deleted
	 * 
	 * @param file of the file where the deletion process should take place
	 * @param courseCode course code of a particular course
	 * @return True,if the deletion is successful and false if not.
	 */
	public boolean deleteCourse(String file, String courseCode) {
		boolean deleted = false;

		try {

			ArrayList<Course> a1 = readAllCourse(file);
			List l2 = new ArrayList();
			for (int i = 0; i < a1.size(); i++) {

				Course course = (Course) a1.get(i);

				String mat = course.getCourseCode().toLowerCase();

				if (mat.equals(courseCode.toLowerCase())) {

					a1.remove(i);
					deleted = true;

					File f = new File("DataBase/waitlists/" + mat + ".txt");
					f.delete();

					File d = new File("DataBase/schedule/" + mat + ".txt");
					d.delete();
				}

			}

			for (int i = 0; i < a1.size(); i++) {
				Course course = (Course) a1.get(i);
				l2.add(a1.get(i));
			}

			saveCourseAmend(file, l2);

			ArrayList<Course> a2 = readAllCourse(file);

			for (int i = 0; i < a1.size(); i++) {
				System.out.println("================================**=========================================");
				Course C = (Course) a1.get(i);
				System.out.println("Course name: " + C.getCourseName());
				System.out.println("Course code: " + C.getCourseCode());
				for (int h = 0; h < C.getIndices().length; h++) {
					System.out.println("Course index: " + (h + 1));
					System.out.println("Course vacancy: " + C.getIndices()[h]);
				}
				System.out.println("***************************************************************************");
			}

		} catch (IOException e) {

			return false;
		}

		return deleted;
	}
	
	
	/**
	 * This method saves an amended course
	 * 
	 * @param filename filename of the course
	 * @param list The list of course to be amended
	 * @throws IOException if there is a problem saving the file
	 */
	public static void saveCourseAmend(String filename, List list) throws IOException {
		List tempList = new ArrayList();

		for (int i = 0; i < list.size(); i++) {
			Course course = (Course) list.get(i);
			StringBuilder st = new StringBuilder();
			st.append(course.getCourseCode().trim());
			st.append(SEPARATOR);
			st.append(course.getCourseName().trim());
			st.append(SEPARATOR);
			st.append(course.getSchool().trim());
			st.append(SEPARATOR);

			st.append(course.getStartDate().trim());
			st.append(SEPARATOR);
			st.append(course.getEndDate().trim());
			st.append(SEPARATOR);
			for (int j = 0; j < course.getIndices().length; j++) {
				st.append(j + 1);
				st.append(INDEX_SEPARATOR);
				st.append(course.getVacancy()[j]);
				st.append(INDEX_SEPARATOR);
				st.append(course.getIndices()[j]);
				st.append(SEPARATOR);
				if (j == course.getIndices().length)

				{
					st.append(j + 1);
					st.append(INDEX_SEPARATOR);
					st.append(course.getVacancy()[j]);
					st.append(INDEX_SEPARATOR);
					st.append(course.getIndices()[j]);
				}

			}

			tempList.add(st.toString());
		}
		manage.writeNew(filename, tempList);
	}
	
	/**
	 * This is method displays all the courses
	 * 
	 * 
	 */
	public void showAllCourses() {
		try {
			ArrayList<Course> a1 = readAllCourse("DataBase/courses.txt");
			for (int x = 0; x < a1.size(); x++) {
				System.out.println("| Course code: " + a1.get(x).getCourseCode());
				System.out.println("| Course name: " + a1.get(x).getCourseName());
				System.out.println("|                                                ");
				for (int j = 0; j < a1.get(x).getVacancy().length; j++) {
					System.out.println("| Available index: " + (j + 1) + " Vacancy: " + a1.get(x).getVacancy()[j] + "/"
							+ a1.get(x).getIndices()[j]);

				}
				System.out.println("===================================================");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method displays all courses by the index
	 * 
	 * @param courseCode is based on the course
	 */
	public void showIndexByCourse(String courseCode) {
		try {

			ArrayList a1 = readAllCourse("DataBase/courses.txt");
			ArrayList a2 = new ArrayList();

			courseCode = courseCode.toLowerCase();

			for (int i = 0; i < a1.size(); i++) {
				Course course = (Course) a1.get(i);
				if (course.getCourseCode().toLowerCase().equals(courseCode)) {
					a2.add(a1.get(i));
				}

			}

			for (int x = 0; x < a2.size(); x++) {
				Course course = (Course) a2.get(x);
				System.out.println("Course code: " + course.getCourseCode());
				System.out.println("Course name: " + course.getCourseName());
				System.out.println("**************************************************");
				for (int j = 0; j < course.getVacancy().length; j++) {
					System.out.println(" Available index: " + (j + 1) + " Vacancy: " + course.getVacancy()[j] + " "
							+ "Total size of " + course.getIndices()[j]);
					System.out.println("                                                ");
				}
				System.out.println("===================================================");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method allows the Admin to display all the courses based on a course code
	 * 
	 * @param courseCode of a particular course
	 * @return True if there is no course to be displayed
	 */
	public boolean adminShowCourse(String courseCode) {
		boolean check = false;
		try {

			ArrayList a1 = readAllCourse("DataBase/courses.txt");
			ArrayList a2 = new ArrayList();

			courseCode = courseCode.toLowerCase();

			for (int i = 0; i < a1.size(); i++) {
				Course course = (Course) a1.get(i);
				if (course.getCourseCode().toLowerCase().equals(courseCode)) {
					a2.add(a1.get(i));
					check = true;
				}

			}
			if (check) {
				for (int x = 0; x < a2.size(); x++) {
					Course course = (Course) a2.get(x);
					System.out.println("Course code: " + course.getCourseCode());
					System.out.println("Course name: " + course.getCourseName());
					System.out.println("**************************************************");
					for (int j = 0; j < course.getVacancy().length; j++) {
						System.out.println(" Available index: " + (j + 1) + " Vacancy: " + course.getVacancy()[j]
								+ " / " + course.getIndices()[j]);
						System.out.println("                                                ");
					}
					System.out.println("===================================================");
				}
			} else {
				System.out.println("There is no such course .Re-enter the coursecode!!");

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return check;
	}
	
	/**
	 * This method prints all the students enrolled in a course given a course code
	 * @param courseCode of a particular course
	 * @return True, if there is courses to print and false if otherwise
	 */
	public boolean adminPrintByCourseCode(String courseCode) {
		boolean check = false;
		boolean status = false;
		boolean checkCourse = false;
		try {
			AddDropController checkStudent = new AddDropController();
			StudentController s = new StudentController();
			ArrayList student = checkStudent.readAllCourseAndStudent("DataBase/courseAndStudent.txt");
			ArrayList studentCheck = s.readAllStudents("DataBase/student.txt");
			ArrayList courseCheck = this.readAllCourse("DataBase/courses.txt");
			ArrayList a2 = new ArrayList();
			ArrayList a3 = new ArrayList();

			courseCode = courseCode.toLowerCase();

			for (int q = 0; q < courseCheck.size(); q++) {
				Course c = (Course) courseCheck.get(q);
				if (c.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())) {
					checkCourse = true;
				}
			}

			if (checkCourse) {

				for (int i = 0; i < student.size(); i++) {
					AddDrop Add = (AddDrop) student.get(i);
					if (Add.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())) {
						a2.add(student.get(i));
						check = true;
					}

				}
				if (check) {
					for (int x = 0; x < a2.size(); x++) {
						AddDrop Add = (AddDrop) a2.get(x);
						System.out.println("                                               ");
						System.out.println("                                               ");
						System.out.println("===============================================");
						System.out.println("  Courses for Index       :" + Add.getIndex());
						System.out.println("  Course Code             :" + Add.getCourseCode());
						System.out.println("***********************************************");
						// System.out.println("CourseCode: " +
						// Add.getCourseCode() +
						// " Index: " + Add.getIndex());
						for (int z = 0; z < Add.getList().size(); z++) {
							for (int k = 0; k < studentCheck.size(); k++) {
								Student s1 = (Student) studentCheck.get(k);

								if (Add.getList().get(z).toLowerCase()
										.equals(s1.getMatriculationNumber().toLowerCase())) {
									System.out.println("                           ");
									System.out.println("  Student Name            :" + s1.getName());
									System.out.println("  Gender                  :" + s1.getGender());
									System.out.println("  Nationality             :" + s1.getNationality());
									// System.out.println(s1.getName() + " " +
									// s1.getGender() + " " +
									// s1.getNationality());
								}
							}
						}
						System.out.println("===============================================");

					}
					status = true;
				} else {
					System.out.println("System message:There is no student registered for the course");

				}
			}

			else {
				checkCourse = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return checkCourse;
	}
	
	/**
	 * This allows the admin to print the students enrolled into a course based on a coursecode and index
	 * 
	 * @param courseCode of a particular course
	 * @param index of a particular course code.E.g CE2003_1 where 1 refers to the index
	 * @return True,if there are courses to be printed and false if it is otherwise
	 */
	public boolean adminPrintByCourseCodeAndIndex(String courseCode, int index) {
		boolean check = false;

		boolean checkCourse = false;
		int status;
		try {
			AddDropController checkStudent = new AddDropController();

			StudentController s = new StudentController();
			ArrayList student = checkStudent.readAllCourseAndStudent("DataBase/courseAndStudent.txt");
			ArrayList studentCheck = s.readAllStudents("DataBase/student.txt");
			ArrayList courseCheck = this.readAllCourse("DataBase/courses.txt");
			ArrayList a2 = new ArrayList();
			ArrayList a3 = new ArrayList();

			courseCode = courseCode.toLowerCase();

			for (int q = 0; q < courseCheck.size(); q++) {
				Course c = (Course) courseCheck.get(q);
				if (c.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())) {
					checkCourse = true;
				}
			}
			if (checkCourse) {
				for (int i = 0; i < student.size(); i++) {
					AddDrop Add = (AddDrop) student.get(i);
					if (Add.getCourseCode().toLowerCase().equals(courseCode) && Add.getIndex() == index) {
						a2.add(student.get(i));
						check = true;
					}

				}
				if (check) {
					for (int x = 0; x < a2.size(); x++) {
						AddDrop Add = (AddDrop) a2.get(x);
						System.out.println("===============================================");
						System.out.println("  Courses for Index       :" + Add.getIndex());
						System.out.println("  Course Code             :" + Add.getCourseCode());
						System.out.println("***********************************************");

						for (int z = 0; z < Add.getList().size(); z++) {
							for (int k = 0; k < studentCheck.size(); k++) {
								Student s1 = (Student) studentCheck.get(k);

								if (Add.getList().get(z).toLowerCase()
										.equals(s1.getMatriculationNumber().toLowerCase())) {
									System.out.println("                           ");
									System.out.println("  Student Name            :" + s1.getName());
									System.out.println("  Gender                  :" + s1.getGender());
									System.out.println("  Nationality             :" + s1.getNationality());

								}
							}

						}
						System.out.println("===============================================");

					}
				} else {
					System.out.println("There is no student registerd for the course yet !!");

				}
			} else {
				checkCourse = false;
			}

		} catch (IOException e) {
			
			e.printStackTrace();
		}

		catch (InputMismatchException e) {

			System.out.println("Enter correct inputs");
		}

		return checkCourse;
	}
	
	/**
	 * This method checks for a valid course code and index
	 * 
	 * @param index of a course.E.G,CE2003_2 where 2 refers to the index
	 * @param courseCode refers to the course code of a particular course
	 * @return true if the index is valid and false if it is otherwise
	 */
	public boolean checkIndexeAndCourseCode(int index, String courseCode) {

		boolean courseCheck = false;
		boolean indexCheck = false;
		boolean finalCheck = false;
		try {
			List data = readAllCourse("DataBase/courses.txt");
			List indexData = new ArrayList();

			for (int i = 0; i < data.size(); i++) {
				Course course = (Course) data.get(i);
				if (course.getCourseCode().toLowerCase().equals(courseCode)) {
					courseCheck = true;
					indexData.add(data.get(i));
					break;
				}

			}

			if (courseCheck == true) {
				for (int j = 0; j < indexData.size(); j++) {
					Course course = (Course) indexData.get(j);
					for (int z = 0; z < course.getIndices().length; z++) {
						if ((z + 1) == index) {
							indexCheck = true;
						}
					}
				}
			}

			if (courseCheck == false) {
				System.out.println("Please re-check your courseId");
			}

			if (indexCheck == false) {
				System.out.println("Please re-check your index ");
			}

			else {
				finalCheck = true;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return finalCheck;
	}
	
	/**
	 * This method checks whether the new index and old index is valid for a given course code
	 * 
	 * @param newIndex E.G,CE2003_2 where 2 refers to the index
	 * @param oldIndex E.G,CE2003_2 where 2 refers to the index
	 * @param courseCode of a particular course
	 * @return True if the indexes are valid and false if it is otherwise
	 */
	public boolean checkIndexes(int newIndex, int oldIndex, String courseCode) {
		int firstCheck = 0;
		int secondCheck = 0;
		boolean state = false;
		try {
			List data = readAllCourse("DataBase/courses.txt");

			for (int i = 0; i < data.size(); i++) {
				Course course = (Course) data.get(i);
				if (course.getCourseCode().equals(courseCode)) {

					for (int j = 0; j < course.getIndices().length; j++) {

						if ((j + 1) == oldIndex) {
							firstCheck = 1;
						}

					}

					for (int j = 0; j < course.getIndices().length; j++) {

						if ((j + 1) == newIndex) {
							secondCheck = 1;
						}

					}

				}
			}

			if (firstCheck != 1) {
				System.out.println("Please re-check your old index");
			}

			if (secondCheck != 1) {
				System.out.println("Please re-check your new index");
			}

			if (firstCheck == 1 && secondCheck == 1) {
				state = true;
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		return state;

	}
	
	/**
	 * Checks whether the student is enrolling himself into the same course
	 * 
	 * @param courseCode of a particular course
	 * @param matricNum refers to the matriculation code of a student
	 * @param index E.G,CE2003_2 where 2 refers to the index
	 * @return True if if the student is not enrolled in to the course,and False if otherwise
	 */
	public boolean checkCourseCode(String courseCode, String matricNum, int index) {

		boolean state = false;
		boolean result = false;

		try {

			List data = readAllCourse("DataBase/courses.txt");

			for (int i = 0; i < data.size(); i++) {

				Course course = (Course) data.get(i);

				if (course.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())) {

					state = true;

				}

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		if (state) {
			AddDropController invoke = new AddDropController();
			result = invoke.validateStudentAgainstCourseEnrolled(matricNum, courseCode, index);

		}
		return result;
	}
	
	/**
	 * This method is invoked when two students who wish to swap index with each other
	 * 
	 * This method checks whether the two students are enrolled in the same course in order
	 * for the swap to take place
	 * 
	 * @param courseCode of a particular course
	 * @param matricNum of the student A
	 * @param newMatricId of the student B
	 * @return True if the course code is correct and false if otherwise
	 */
	public boolean checkCourseCode(String courseCode, String matricNum, String newMatricId) {

		boolean state = false;
		boolean result = false;

		try {

			List data = readAllCourse("DataBase/courses.txt");

			for (int i = 0; i < data.size(); i++) {

				Course course = (Course) data.get(i);

				if (course.getCourseCode().toLowerCase().equals(courseCode)) {

					//
					state = true;

				}

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		if (state) {
			AddDropController invoke = new AddDropController();
			result = invoke.validateStudentAgainstCourse(matricNum, courseCode);
			result = invoke.validateStudentAgainstCourse(newMatricId, courseCode);

		}
		return result;
	}
	
	/**
	 * This method validates the date
	 * 
	 * @param date as a string in dd/mm/yyyy
	 * @return True if the date is valid and false if not
	 */
	public boolean validateDate(String date) {
		boolean result = true;

		if (date == null || !date.matches("^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$")) {
			System.out.println("date input error");
			result = false;
		}
		if (result == true) {
			SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
			df.setLenient(false);
			try {
				df.parse(date);
				result = true;
			} catch (ParseException ex) {
				result = false;
			}
		}

		else {
			result = false;
		}

		return result;
	}
	
	/**
	 * This method validates a course to check if it conforms to a certain format
	 * 
	 * @param courseCode of a particular course
	 * @return True if the course code format is valid and false if not
	 */
	public boolean validateCourseCode(String courseCode) {


		String re1 = "([A-Z])"; // Any Single Character 1
		String re2 = "([A-Z])"; // Any Single Digit 1
		String re3 = "(\\d)"; // Any Single Digit 2
		String re4 = "(\\d)"; // Any Single Digit 3
		String re5 = "(\\d)"; // Any Single Digit 4
		String re6 = "(\\d)"; // Any Single Digit 5

		Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6, Pattern.DOTALL);
		Matcher m = p.matcher(courseCode);
		if (m.find()) {

			System.out.println("valid");
			return true;
			
		}

		else {

			System.out.println("invalid input please key in again");
			return false;
		}

	}

	
}

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

public class CourseController {
	// Properties
	public static final String SEPARATOR = "|";
	public static final String INDEX_SEPARATOR = "_";
	private ScheduleController schedulecontroller = null;;

	private enum type {
		LECTURE, LABORATORY, TUTORIAL;
	}

	private enum weekdays {
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY;
	}

	// Constructor
	public CourseController() {
	}

	// Methods
	public static ArrayList readAllCourse(String filename) throws IOException {
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
			String courseCode = star.nextToken().trim();
			String courseName = star.nextToken().trim();
			String school = star.nextToken().trim();
			String startDate = star.nextToken().trim();
			String endDate = star.nextToken().trim();
			int counter = 0;
			counter = star.countTokens();

			int[] retrievedIndex = new int[counter];
			int[] retrievedTotal = new int[counter];

			// Algorithm to breakdown indexes and vacancies separately
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

			// for(int j=0;j<retrievedIndex.length;j++)
			// {
			//
			// System.out.println("Index "+(j+1)+": "+retrievedIndex[j]);
			// }

			Course course = new Course(courseCode, courseName, school, startDate, endDate, retrievedIndex,
					retrievedTotal);
			// Course course = new
			// Course(courseCode,courseName,school,capacity,startDate,endDate);

			// add student objects to alr
			alr.add(course);
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

	public boolean addCourse() {
		Scanner sc = new Scanner(System.in);
		List courseSave = new ArrayList();
		List scheduleList = new ArrayList();
		String startTime = null, venue, day = null, endTime = null;// for the
																	// schedule
		String currentIndex;

		System.out.println("No of courses to be added");
		int input = sc.nextInt();
		int lec;
		char lab = 0, tut = 0;
		int totalIndex = 0;
		String Temp = sc.nextLine();// to clear the \n buffer
		for (int i = 0; i < input; i++) {

			String code, name, school, startDate, endDate;
			int capacity, noOfSections, temp;

			System.out.println("Please enter course code: ");
			code = sc.nextLine().toLowerCase();

			System.out.println("Please enter course name: ");
			name = sc.nextLine();

			System.out.println("Please enter course school : ");
			school = sc.nextLine();

			System.out.println("Please enter the number of sections in the course: ");
			noOfSections = sc.nextInt();
			capacity = 0;
			int[] indices = new int[noOfSections];

			for (int g = 0; g < noOfSections; g++) {
				System.out.println("Please enter the capacity of index " + (g + 1) + ": ");
				temp = sc.nextInt();
				indices[g] = temp;
				totalIndex = g + 1;
			}
			Temp = sc.nextLine();// To eliminate the buffer

			// implement the schedule for each index and save into a txt file
			for (int q = 0; q < totalIndex; q++) {
				System.out.println("Index " + (q + 1));
				System.out.println("How many lectures does it have in a week:");
				lec = sc.nextInt();
				for (int p = 0; p < lec; p++) {
					System.out.println("Enter fileds for" + (p + 1) + "Lecture");

					do {
						System.out.println("The intended day to have the Lecture?");

						System.out.println("*********Options***********************");
						System.out.println(" 1) Monday");
						System.out.println(" 2) Tuesday");
						System.out.println(" 3) Wednesday");
						System.out.println(" 4) Thursday");
						System.out.println(" 5) Friday");
						System.out.println("*********End Of Options****************");
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
							System.out.println("Time" + m.group(0));
							System.out.println("hh " + m.group(1));
							System.out.println("mm " + m.group(2));
							System.out.println("ss " + m.group(3));
							result = true;
							startTime = ip;
						} else {
							System.out.println("NO MATCH! format mismatch");
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
							System.out.println("Time" + m.group(0));
							System.out.println("hh " + m.group(1));
							System.out.println("mm " + m.group(2));
							System.out.println("ss " + m.group(3));
							result = true;
							endTime = ip;
						} else {
							System.out.println("NO MATCH! format mismatch");
							result = false;
						}
					} while (result == false);

					// currentIndex=""+q+1;
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

						System.out.println("*********Options***********************");
						System.out.println(" 1) Monday");
						System.out.println(" 2) Tuesday");
						System.out.println(" 3) Wednesday");
						System.out.println(" 4) Thursday");
						System.out.println(" 5) Friday");
						System.out.println("*********End Of Options****************");
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
							System.out.println("Time" + m.group(0));
							System.out.println("hh " + m.group(1));
							System.out.println("mm " + m.group(2));
							System.out.println("ss " + m.group(3));
							result = true;
							startTime = ip;
						} else {
							System.out.println("NO MATCH! format mismatch");
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
							System.out.println("Time" + m.group(0));
							System.out.println("hh " + m.group(1));
							System.out.println("mm " + m.group(2));
							System.out.println("ss " + m.group(3));
							result = true;
							endTime = ip;
						} else {
							System.out.println("NO MATCH! format mismatch");
							result = false;
						}
					} while (result == false);

					// currentIndex=""+q+1;
					Schedule schedule = new Schedule(code, (q + 1), type.LABORATORY.toString(), day, venue, startTime,
							endTime);
					scheduleList.add(schedule);
				}
				if (tut == 'y') {

					do {
						System.out.println("The intended day to have the Tutorial?");

						System.out.println("*********Options***********************");
						System.out.println(" 1) Monday");
						System.out.println(" 2) Tuesday");
						System.out.println(" 3) Wednesday");
						System.out.println(" 4) Thursday");
						System.out.println(" 5) Friday");
						System.out.println("*********End Of Options****************");
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
							System.out.println("Time" + m.group(0));
							System.out.println("hh " + m.group(1));
							System.out.println("mm " + m.group(2));
							System.out.println("ss " + m.group(3));
							result = true;
							startTime = ip;
						} else {
							System.out.println("NO MATCH! format mismatch");
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
							System.out.println("Time" + m.group(0));
							System.out.println("hh " + m.group(1));
							System.out.println("mm " + m.group(2));
							System.out.println("ss " + m.group(3));
							result = true;
							endTime = ip;
						} else {
							System.out.println("NO MATCH! format mismatch");
							result = false;
						}
					} while (result == false);
					// currentIndex=""+q+1;
					Schedule schedule = new Schedule(code, (q + 1), type.TUTORIAL.toString(), day, venue, startTime,
							endTime);
					scheduleList.add(schedule);
				}

			}
			/* end of implementtion for the schedule */
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

			schedulecontroller = new ScheduleController();
			schedulecontroller.saveSchedule(scheduleList, code);// add the
																// schedule
																// implementation
																// here

			Course course = new Course(code, name, school, startDate, endDate, indices, indices);

			courseSave.add(course);
			Temp = sc.nextLine();// To eliminate the buffer
			File f = new File("DataBase/waitlists/" + code + ".txt");
			try {
				f.createNewFile();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		try {
			saveCourse("DataBase/courses.txt", courseSave);

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static void saveCourse(String filename, List list) throws IOException {
		List tempList = new ArrayList();// to store students data

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
			/* to store the vacancy and the total space in the course */
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
			// String temp = "" + course.getCapacity();
			// st.append(temp.trim());
			// st.append(SEPARATOR);

			tempList.add(st.toString());
		}
		write(filename, tempList);
	}

	public static void write(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName, true));// to
																			// ensure
																			// that
																			// the
																			// previous
																			// data
																			// is
																			// still
																			// intact

		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}

	public boolean deleteCourse(String file, String courseCode) {
		boolean deleted = false;

		try {

			ArrayList<Course> a1 = readAllCourse(file);
			System.out.println(file);
			System.out.println(courseCode);
			System.out.println(a1.size());
			List l2 = new ArrayList();
			for (int i = 0; i < a1.size(); i++) {

				Course course = (Course) a1.get(i);

				String mat = course.getCourseCode().toLowerCase();

				if (mat.equals(courseCode.toLowerCase())) {

					a1.remove(i);
					deleted = true;

					File f = new File("DataBase/waitlists/" + mat + ".txt");
					f.delete();

					File d = new File("DataBase/schedule/" + mat + ".txt");// to
																		// delete
																		// the
																		// schedule
					d.delete();
				}

			}

			for (int i = 0; i < a1.size(); i++) {
				Course course = (Course) a1.get(i);
				// System.out.println("Gender: "+student.getGender());
				// System.out.println("MatriculationNumber:
				// "+student.getMatriculationNumber());
				l2.add(a1.get(i));
			}

			saveCourseAmend(file, l2);

		} catch (IOException e) {

			return false;
		}

		return deleted;
	}

	public static void newWrite(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName));// clears
																	// the data
																	// in the
																	// file
																	// before
																	// writing
																	// it back
																	// to the
																	// file

		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}

	/* The below method is to save course after the amendments are done */
	public static void saveCourseAmend(String filename, List list) throws IOException {
		List tempList = new ArrayList();// to store students data

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
			// String temp = "" + course.getCapacity();
			// st.append(temp.trim());
			// st.append(SEPARATOR);

			tempList.add(st.toString());
		}
		newWrite(filename, tempList);
	}

	public void showAllCourses() {
		try {
			ArrayList<Course> a1 = readAllCourse("DataBase/courses.txt");
			for (int x = 0; x < a1.size(); x++) {
				System.out.println("Course code: " + a1.get(x).getCourseCode());
				System.out.println("Course name: " + a1.get(x).getCourseName());
				for (int j = 0; j < a1.get(x).getVacancy().length; j++) {
					System.out.println(" Available index: " + (j + 1) + " Vacancy: " + a1.get(x).getIndices()[j]);
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
				// System.out.println("spots: "+a2.get(x).getVacancy().length);
				// System.out.println("spots: "+a2.size());
				for (int j = 0; j < course.getVacancy().length; j++) {
					System.out.println(" Available index: " + (j + 1) + " Vacancy: " + course.getVacancy()[j]
							+ "Total size of " + course.getIndices()[j]);
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
					// System.out.println("spots:
					// "+a2.get(x).getVacancy().length);
					// System.out.println("spots: "+a2.size());
					for (int j = 0; j < course.getVacancy().length; j++) {
						System.out.println(" Available index: " + (j + 1) + " Vacancy: " + course.getVacancy()[j]
								+ " / " + course.getIndices()[j]);
					}

				}
			} else {
				System.out.println("There is no such course .Re-enter the coursecode!!");

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return check;
	}

	public boolean adminPrintByCourseCode(String courseCode) {
		boolean check = false;
		try {
			AddDropController checkStudent = new AddDropController();
			StudentController s = new StudentController();
			ArrayList student = checkStudent.readAllCourseAndStudent("DataBase/courseAndStudent.txt");
			ArrayList studentCheck = s.readAllStudents("DataBase/student.txt");

			ArrayList a2 = new ArrayList();
			ArrayList a3 = new ArrayList();

			courseCode = courseCode.toLowerCase();

			for (int i = 0; i < student.size(); i++) {
				AddDrop Add = (AddDrop) student.get(i);
				if (Add.getCourseCode().toLowerCase().equals(courseCode)) {
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
				    System.out.println("  Courses for Index       :"+Add.getIndex());
				    System.out.println("  Course Code             :"+Add.getCourseCode());
				    System.out.println("***********************************************");
					//System.out.println("CourseCode: " + Add.getCourseCode() + " Index: " + Add.getIndex());
					for (int z = 0; z < Add.getList().size(); z++) {
						for (int k = 0; k < studentCheck.size(); k++) {
							Student s1 = (Student) studentCheck.get(k);

							if (Add.getList().get(z).toLowerCase().equals(s1.getMatriculationNumber().toLowerCase())) {
								System.out.println("                           ");
							    System.out.println("  Student Name            :"+s1.getName());
							    System.out.println("  Gender                  :"+s1.getGender());
							    System.out.println("  Nationality             :"+s1.getNationality());
								//System.out.println(s1.getName() + " " + s1.getGender() + " " + s1.getNationality());
							}
						}
					}
					System.out.println("===============================================");

				}
			} else {
				System.out.println("There is no student registered for the course/check your course code entered!!");

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return check;
	}
	
	public boolean adminPrintByCourseCodeAndIndex(String courseCode,int index) {
		boolean check = false;
		int status;
		try {
			AddDropController checkStudent = new AddDropController();
			StudentController s = new StudentController();
			ArrayList student = checkStudent.readAllCourseAndStudent("DataBase/courseAndStudent.txt");
			ArrayList studentCheck = s.readAllStudents("DataBase/student.txt");

			ArrayList a2 = new ArrayList();
			ArrayList a3 = new ArrayList();

			courseCode = courseCode.toLowerCase();

			for (int i = 0; i < student.size(); i++) {
				AddDrop Add = (AddDrop) student.get(i);
				if (Add.getCourseCode().toLowerCase().equals(courseCode)&& Add.getIndex()==index) {
					a2.add(student.get(i));
					check = true;
				}

			}
			if (check) {
				for (int x = 0; x < a2.size(); x++) {
					AddDrop Add = (AddDrop) a2.get(x);
					System.out.println("===============================================");
				    System.out.println("  Courses for Index       :"+Add.getIndex());
				    System.out.println("  Course Code             :"+Add.getCourseCode());
				    System.out.println("***********************************************");
				   
				    
				    
					
					//System.out.println("CourseCode: " + Add.getCourseCode() + " Index: " + Add.getIndex());
					for (int z = 0; z < Add.getList().size(); z++) {
						for (int k = 0; k < studentCheck.size(); k++) {
							Student s1 = (Student) studentCheck.get(k);

							if (Add.getList().get(z).toLowerCase().equals(s1.getMatriculationNumber().toLowerCase())) {
								//System.out.println(s1.getName() + " " + s1.getGender() + " " + s1.getNationality());
									System.out.println("                           ");
								    System.out.println("  Student Name            :"+s1.getName());
								    System.out.println("  Gender                  :"+s1.getGender());
								    System.out.println("  Nationality             :"+s1.getNationality());
								    
							}
						}
						
					}
					System.out.println("===============================================");
				}
			} else {
				System.out.println("There is no student registered for the spcecific index of the given course/check entered Index !!");

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 catch (InputMismatchException e) {
				
			 System.out.println("Enter correct inputs");
				//e.printStackTrace();
			}

		return check;
	}
	
	

	public boolean decreaseCourseIndexVacancy(String courseCode, int index) {
		boolean endResult = false;
		try {

			ArrayList<Course> a1 = readAllCourse("DataBase/courses.txt");
			List data = new ArrayList();

			for (int x = 0; x < a1.size(); x++) {
				if (courseCode.equals(a1.get(x).getCourseCode())) {
					if (a1.get(x).getVacancy()[index - 1] <= 0) {
						// waitList();
						endResult = false;
					} else {
						a1.get(x).decreaseVacancy(index);
						System.out.println("Index:" + index + " " + a1.get(x).getVacancy()[index - 1]);

						endResult = true;
					}

				}
				data.add(a1.get(x));
			}

			CourseController.saveCourseAmend("DataBase/courses.txt", data);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return endResult;
	}

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
				// System.out.println("i am in");
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
				System.out.println("Please re-check your courseid");
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

	public boolean checkCourseCode(String courseCode, String matricNum, int index) {

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
			result = invoke.validateStudentAgainstCourseEnrolled(matricNum, courseCode, index);

		}
		return result;
	}

	public boolean validateDate(String ip) {
		boolean result = true;

		if (ip == null || !ip.matches("^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$")) {
			System.out.println("date input error");
			result = false;
		}
		if (result == true) {
			SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
			df.setLenient(false);
			try {
				df.parse(ip);
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

}

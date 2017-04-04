package Control;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import Model.Admin;
import Model.Course;
import Control.CourseController;
import Control.FileManager;
import Model.Student;
import Model.AddDrop;

public class AddDropController {
	public static final String SEPARATOR = "|";
	public static final String INDEX_SEPARATOR = "_";

	// private Course c1;
	// private Admin a1;
	// private Student s1;
	// private FileManager f1;
	private CourseController Cc1 = new CourseController();
	
	public boolean addMethod(String courseCode, int index, String matricNum) {
		boolean endResult = false;
		List student;
		AddDrop Add;
		// Cc1.decreaseCourseIndexVacancy(courseCode, index);
		try {
			// ArrayList<AddDrop> a1 =Cc1.
			// readAll("src/courseAndStudent.txt");//To add the student with a
			// course then save it to a txt file
			List studentData = new ArrayList();

			ArrayList a1 = Cc1.readAllCourse("src/courses.txt");
			// Comment: Reads all the data from courses.txt file and stores it
			// into an ArrayList

			List data = new ArrayList();
			courseCode = courseCode.toLowerCase();
			for (int x = 0; x < a1.size(); x++) {
				Course course = (Course) a1.get(x);

				if (course.getCourseCode().toLowerCase().equals(courseCode)) {

					if (course.getVacancy()[index - 1] <= 0) {
						// waitList();
						endResult = false;
					} else {

						course.decreaseVacancy(index);
						Add = new AddDrop(courseCode, index, matricNum);
						studentData.add(Add);
						// "+a1.get(x).getVacancy()[index-1]);

						endResult = true;
					}

				}
				data.add(a1.get(x));
			}

			// for(int i=0;i<studentData.size();i++)
			// {
			// AddDrop course = (AddDrop)studentData.get(i);
			//
			// }
			AddDropController addd = new AddDropController();
			addd.studentAddsCourse("src/courseAndStudent.txt", studentData, courseCode, index);

			// AddDropController.studentAddsCourse();
			Cc1.saveCourseAmend("src/courses.txt", data);
			endResult = true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return endResult;

	}

	public void sendEmail() {
	}

	public void changeIndex() {

	}

	public void swapIndex() {

	}

	public void checkClash() {

	}

	public void studentAddsCourse(String filename, List list, String courseCode, int index) throws IOException {

		List tempList = new ArrayList();
		ArrayList listOfEnrolledStudents = readAllCourseAndStudent("src/courseAndStudent.txt");

		// ArrayList<Course> a1 = readAllCourse(file);

		if (listOfEnrolledStudents.isEmpty()) {

			AddDrop addCourse = (AddDrop) list.get(0);
			StringBuilder newBuild = new StringBuilder();

			newBuild.append(addCourse.getCourseCode());
			newBuild.append(INDEX_SEPARATOR);
			newBuild.append(addCourse.getIndex());

			newBuild.append(SEPARATOR);
			newBuild.append(addCourse.getMatricNum());
			newBuild.append(SEPARATOR);
			tempList.add(newBuild.toString());

		}

		else {

			ArrayList updated = new ArrayList();
			int flag = 0;

			for (int x = 0; x < listOfEnrolledStudents.size(); x++) {
				AddDrop singleMatricNum = (AddDrop) list.get(0);

				AddDrop listOfMatricNum = (AddDrop) listOfEnrolledStudents.get(x);

				AddDrop addMatricNum = new AddDrop(listOfMatricNum.getCourseCode(), listOfMatricNum.getIndex(),
						listOfMatricNum.getList());

				if (listOfMatricNum.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())
						&& listOfMatricNum.getIndex() == index)

				{
					List<String> matricList = new ArrayList<String>();
					matricList.add(singleMatricNum.getMatricNum());
					addMatricNum.setList(matricList);

					flag = 1;

				}

				updated.add(addMatricNum);
			}

			if (flag != 1) {
				AddDrop a1 = (AddDrop) list.get(0);
				String matriculationNumber = a1.getMatricNum();
				String courseCode1 = a1.getCourseCode();
				int index1 = a1.getIndex();

				List<String> matricNum = new ArrayList<String>();
				matricNum.add(a1.getMatricNum());
				AddDrop a2 = new AddDrop(a1.getCourseCode(), a1.getIndex(), matricNum);

				updated.add(a2);

				for (int j = 0; j < updated.size(); j++) {
					AddDrop test = (AddDrop) updated.get(j);
				}
			}

			for (int i = 0; i < updated.size(); i++) {
				StringBuilder st1 = new StringBuilder();
				AddDrop a1 = (AddDrop) updated.get(i);

				st1.append(a1.getCourseCode());
				st1.append(INDEX_SEPARATOR);
				st1.append(a1.getIndex());
				st1.append(SEPARATOR);
				for (int j = 0; j < a1.getList().size(); j++) {

					st1.append(a1.getList().get(j));
					st1.append(SEPARATOR);

				}
				tempList.add(st1.toString());
			}

		}
		write(filename, tempList);
	}

	public static void write(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName));// to
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

	public static ArrayList readAllCourseAndStudent(String filename) throws IOException {
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

			String courseCode = star.nextToken("_").trim();

			String index = star.nextToken("|").trim();

			StringBuilder modifyIndex = new StringBuilder(index);
			index = modifyIndex.deleteCharAt(0).toString();

			int counter = 0;
			counter = star.countTokens();

			ArrayList retrievedMatricNum = new ArrayList();

			// Algorithm to breakdown indexes and vacancies separately
			for (int j = 0; j < counter; j++) {

				String matricNum = star.nextToken().trim();
				retrievedMatricNum.add(matricNum);

			}

			AddDrop add = new AddDrop(courseCode, Integer.parseInt(index), retrievedMatricNum);
			// Course course = new
			// Course(courseCode,courseName,school,capacity,startDate,endDate);

			// add student objects to alr
			alr.add(add);
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

	public void dropMethod(String courseCode, int index, String matricNum) {
		try {
			List a2 = Cc1.readAllCourse("src/courses.txt");// read the data from
															// the course txt.
			List tempList = new ArrayList();
			List stringArray = readAllCourseAndStudent("src/courseAndStudent.txt");

			for (int i = 0; i < stringArray.size(); i++) {
				AddDrop check = (AddDrop) stringArray.get(i);
				if (check.getCourseCode().toLowerCase().equals(courseCode.toLowerCase()) && check.getIndex() == index) {
					for (int x = 0; x < check.getList().size(); x++) {
						if (check.getList().get(x).toLowerCase().equals(matricNum.toLowerCase())) {
							System.out.print("test");
							check.getList().remove(x);
							/*
							 * triverse through the course and add back the
							 * vacancy
							 */
							for (int k = 0; k < a2.size(); k++) {
								Course course = (Course) a2.get(k);
								if (course.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())) {
									course.increaseVacancy(index);
								}

							}

						}
					}
				}
			}
			Cc1.saveCourseAmend("src/courses.txt", a2);

			for (int i = 0; i < stringArray.size(); i++) {
				StringBuilder st1 = new StringBuilder();
				AddDrop a1 = (AddDrop) stringArray.get(i);

				st1.append(a1.getCourseCode());
				st1.append(INDEX_SEPARATOR);
				st1.append(a1.getIndex());
				st1.append(SEPARATOR);
				for (int j = 0; j < a1.getList().size(); j++) {

					st1.append(a1.getList().get(j));
					st1.append(SEPARATOR);

				}
				tempList.add(st1.toString());
			}

			write("src/courseAndStudent.txt", tempList);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* This method return the user the index of thr course added base */
	public int returnIndex(String matriculationNum, String courseCode) {
		List stringArray;
		int index = 0;
		try {

			stringArray = readAllCourseAndStudent("src/courseAndStudent.txt");

			for (int i = 0; i < stringArray.size(); i++) {
				AddDrop check = (AddDrop) stringArray.get(i);
				if (check.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())) {
					for (int k = 0; k < check.getList().size(); k++) {
						if (check.getList().get(k).toLowerCase().equals(matriculationNum.toLowerCase())) {
							index = check.getIndex();
						}

					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return index;

	}
	
	/*Save the amended student and course file*/
	public void saveAmend(List stringArray){
		List tempList = new ArrayList();
		for (int i = 0; i < stringArray.size(); i++) {
			StringBuilder st1 = new StringBuilder();
			AddDrop a1 = (AddDrop) stringArray.get(i);

			st1.append(a1.getCourseCode());
			st1.append(INDEX_SEPARATOR);
			st1.append(a1.getIndex());
			st1.append(SEPARATOR);
			for (int j = 0; j < a1.getList().size(); j++) {

				st1.append(a1.getList().get(j));
				st1.append(SEPARATOR);

			}
			tempList.add(st1.toString());
		}

		try {
			write("src/courseAndStudent.txt", tempList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public boolean validateIndexOfCourseAndStudent(int index,String courseCode)
	{
		boolean state=false;
		try {
			List data = readAllCourseAndStudent("src/courseAndStudent.txt");
			
			
			for(int i=0;i<data.size();i++)
			{
				AddDrop validate= (AddDrop)data.get(i);
				
				if(validate.getIndex()==index && validate.getCourseCode().toLowerCase().equals(courseCode.toLowerCase()))
				{
					
					state=true;
				}
			}
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		return state;
		
	}
	
	public boolean validateStudentAgainstCourseEnrolled(String matricNum,String courseCode,int index)
	{
		boolean state=false;
		
		try {
			List read= readAllCourseAndStudent("src/courseAndStudent.txt");
			
			
			for(int i=0;i<read.size();i++)
			{
				AddDrop a1 = (AddDrop) read.get(i);
				if(a1.getCourseCode().toLowerCase().equals(courseCode.toLowerCase()) && (a1.getIndex()==index))
				{
					
					for(int j=0;j<a1.getList().size();j++)
					{
						if(a1.getList().get(j).toLowerCase().equals(matricNum.toLowerCase()))
						{
							state=true;
						}
					}
					
				}
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return state;
	}
	
	

}

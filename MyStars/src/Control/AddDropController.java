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

	private Course c1;
	private Admin a1;
	private Student s1;
	private FileManager f1;
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
						// System.out.println("Index:"+index+"
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
			// System.out.println("Test: "+course.getCourseCode());
			// }

			AddDropController.saveCourseWithStudent("src/courseAndStudent.txt", studentData, courseCode, index);
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

	public void dropMethod() {

	}

	public void changeIndex() {

	}

	public void swapIndex() {

	}

	public void checkClash() {

	}

	public static void saveCourseWithStudent(String filename, List list, String courseCode, int index)
			throws IOException {
		List tempList = new ArrayList();// to store students data
		ArrayList<AddDrop> StringArray = readAllCourseAndStudent("src/courseAndStudent.txt");
		
		if (StringArray.size() != 0) {
			for (int x = 0; x < StringArray.size(); x++) {
				AddDrop Add = (AddDrop) list.get(0);// Convert the data into
													// AddDrop
													// class then check for
													// condition before adding
													// them
													// together
				AddDrop a1 = new AddDrop(StringArray.get(x).getCourseCode(),StringArray.get(x).getIndex(),StringArray.get(x).getMatricNumArray());
				// AddDrop stArray = (AddDrop) StringArray.get(x);
				// System.out.println("test");
				if (StringArray.get(x).getCourseCode().toLowerCase().equals(courseCode.toLowerCase())
						&& StringArray.get(x).getIndex() == index) {
					System.out.println(Add.getMatricNum());
					ArrayList pass = new ArrayList();
					pass.add(Add.getMatricNum());
					//System.out.println(pass.size());
					StringArray.get(x).setmatricNumArray(pass);

				} else {
					StringArray.add(Add);

				}
			}

			for (int i = 0; i < StringArray.size(); i++) {
				// AddDrop Add = (AddDrop) list.get(i);
				StringBuilder st = new StringBuilder();
				AddDrop stArray = (AddDrop) list.get(i);
				st.append(StringArray.get(i).getCourseCode());
				// st.append(stArray.getCourseCode());
				st.append(INDEX_SEPARATOR);
				st.append(StringArray.get(i).getIndex());
				// System.out.println(stArray.getMatricNum());
				for (int j = 0; j < StringArray.get(i).getMatricNumArray().size(); j++) {
					st.append(SEPARATOR);
					st.append(StringArray.get(i).getMatricNumArray().get(j));
					st.append(SEPARATOR);
					// }

					// String temp = "" + course.getCapacity();
					// st.append(temp.trim());
					// st.append(SEPARATOR);

					tempList.add(st.toString());

				}
			}
		} else {

			for (int i = 0; i < StringArray.size(); i++) {
				// AddDrop Add = (AddDrop) list.get(i);
				StringBuilder st1 = new StringBuilder();
				// AddDrop stArray = (AddDrop) list.get(i);
				st1.append(StringArray.get(i).getCourseCode());
				// st.append(stArray.getCourseCode());
				st1.append(INDEX_SEPARATOR);
				st1.append(StringArray.get(i).getIndex());
				// System.out.println(stArray.getMatricNum());
				for (int j = 0; j < StringArray.get(i).getMatricNumArray().size(); j++) {
					st1.append(SEPARATOR);
					st1.append(StringArray.get(i).getMatricNumArray().get(j));
					st1.append(SEPARATOR);
					// }

					// String temp = "" + course.getCapacity();
					// st.append(temp.trim());
					// st.append(SEPARATOR);

					tempList.add(st1.toString());
				}
			}
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

	public static ArrayList readAllCourseAndStudent(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList) read(filename);
		//System.out.println(read(filename).size());
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
			// System.out.println(index);//for error checking
			int counter = 0;
			counter = star.countTokens();

			ArrayList retrievedMatricNum = new ArrayList();

			// Algorithm to breakdown indexes and vacancies separately
			for (int j = 0; j < counter; j++) {

				String matricNum = star.nextToken().trim();
				// System.out.println(matricNum);//for testing purpose
				retrievedMatricNum.add(matricNum);

			}

			AddDrop add = new AddDrop(courseCode, Integer.parseInt(index), retrievedMatricNum);
			// Course course = new
			// Course(courseCode,courseName,school,capacity,startDate,endDate);

			// add student objects to alr
			// System.out.println("hello");
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

	 public static void main(String[] args){
	 try {
	 ArrayList A1= readAllCourseAndStudent("src/courseAndStudent.txt");
	 //System.out.println(A1.size());
	 AddDropController a2 = new AddDropController();
	 a2.addMethod("Mh1812",1,"u162");
	 
	 for(int x=0;x<A1.size();x++){
	 AddDrop a1 = (AddDrop) A1.get(x);
	 for(int i=0;i<a1.getMatricNumArray().size();i++){
	 System.out.println(a1.getCourseCode()+" "+a1.getIndex() +" "+a1.getMatricNumArray().get(i));
	 }
	
	 //System.out.println(A1.get(x).getIndex() );
	
	 }
	 } catch (IOException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	
	 }

	// public static void main(String args[])
	
//	 {
//	 AddDropController a1 = new AddDropController();
//	 a1.addMethod("Mh1812",1,"u162");
//	 }

}

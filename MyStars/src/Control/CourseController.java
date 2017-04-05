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

import Model.Course;
import Model.Student;

public class CourseController {
	// Properties
	public static final String SEPARATOR = "|";
	public static final String INDEX_SEPARATOR = "_";

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

			// Algorithm to breakdown indexes and vacancies separately
			for (int j = 0; j < counter; j++) {

				String index = star.nextToken("_").trim();
				StringBuilder modifyIndex = new StringBuilder(index);
				index = modifyIndex.deleteCharAt(0).toString();

				String vacancy = star.nextToken("|").trim();

				StringBuilder modifyVacancy = new StringBuilder(vacancy);
				vacancy = modifyVacancy.deleteCharAt(0).toString();

				retrievedIndex[j] = Integer.parseInt(vacancy);

			}
			
//			for(int j=0;j<retrievedIndex.length;j++)
//			{
//				
//				System.out.println("Index "+(j+1)+": "+retrievedIndex[j]);
//			}
			

			Course course = new Course(courseCode, courseName, school, startDate, endDate, retrievedIndex);
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
		System.out.println("No of courses to be added");
		int input = sc.nextInt();
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
				System.out.println("Please enter the capacity of index" + (g + 1) + ": ");
				temp = sc.nextInt();
				indices[g] = temp;
			}
			Temp = sc.nextLine();// To eliminate the buffer
			System.out.println("Please enter the start date of course: ");
			startDate = sc.next();

			System.out.println("Please enter the end date of course: ");
			endDate = sc.next();

			Course course = new Course(code, name, school, startDate, endDate, indices);

			courseSave.add(course);
			Temp = sc.nextLine();// To eliminate the buffer
			File f = new File("src/waitlists/"+code+".txt");
			try {
				f.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
		

		try {
			saveCourse("src/courses.txt", courseSave);
		
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
			for (int j = 0; j < course.getIndices().length; j++) {
				st.append(j + 1);
				st.append(INDEX_SEPARATOR);
				st.append(course.getIndices()[j]);
				st.append(SEPARATOR);
				if (j == course.getIndices().length)

				{
					st.append(j + 1);
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
		
			List l2 = new ArrayList();
			for (int i = 0; i < a1.size(); i++) {
				
				Course course = (Course) a1.get(i);

				String mat = course.getCourseCode().toLowerCase();
				
				if (mat.equals(courseCode.toLowerCase())) {

					a1.remove(i);
					deleted = true;
					
					File f = new File("src/waitlists/"+mat+".txt");
					f.delete();
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
				st.append(course.getIndices()[j]);
				st.append(SEPARATOR);
				if (j == course.getIndices().length)

				{
					st.append(j + 1);
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
			ArrayList<Course> a1 = readAllCourse("src/courses.txt");
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

			ArrayList a1 = readAllCourse("src/courses.txt");
			ArrayList a2 = new ArrayList();
			
			
//			for(int j=0;j<a1.get(j).getIndices().length;j++)
//			{
//				System.out.println("Index "+(j+1)+": "+a1.get(j).getVacancy()[j]);
//			}
			
//			for(int z=0;z<a1.size();z++)
//			{
//				System.out.println("Display coursecode: "+a1.get(z).getCourseCode());
//				System.out.println("Display coursename: "+a1.get(z).getCourseName());
//				System.out.println("Display school: "+a1.get(z).getSchool());
//				System.out.println("Display start date: "+a1.get(z).getStartDate());
//				System.out.println("Display end date: "+a1.get(z).getEndDate());
//				
//				
//				
//				
//			}

			courseCode=courseCode.toLowerCase();

			for (int i = 0; i < a1.size(); i++) {
					Course course = (Course)a1.get(i);
				if (course.getCourseCode().toLowerCase().equals(courseCode)) {
					a2.add(a1.get(i));
				}

			}

			for (int x = 0; x < a2.size(); x++) {
				Course course = (Course)a2.get(x);
				System.out.println("Course code: " + course.getCourseCode());
				System.out.println("Course name: " + course.getCourseName());
				//System.out.println("spots: "+a2.get(x).getVacancy().length);
				//System.out.println("spots: "+a2.size());
				for (int j = 0; j < course.getVacancy().length; j++) {
					System.out.println(" Available index: " + (j + 1) + " Vacancy: " + course.getVacancy()[j]);
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean decreaseCourseIndexVacancy(String courseCode, int index) {
		boolean endResult = false;
		try {

			ArrayList<Course> a1 = readAllCourse("src/courses.txt");
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

			CourseController.saveCourseAmend("src/courses.txt", data);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return endResult;
	}
	
	
	
	public boolean checkIndexeAndCourseCode(int index,String courseCode)
	{
		
		boolean courseCheck=false;
		boolean indexCheck=false;
		boolean finalCheck=false;
		try {
			List data=readAllCourse("src/courses.txt");
			List indexData= new ArrayList();
			
			for(int i=0;i<data.size();i++)
			{
				Course course = (Course) data.get(i);
				if(course.getCourseCode().toLowerCase().equals(courseCode))
				{
					courseCheck=true;
					indexData.add(data.get(i));
					break;
			}
			
			}	
			
			if(courseCheck==true)
			{
				System.out.println("i am in");
				for(int j=0;j<indexData.size();j++)
				{
					Course course = (Course) indexData.get(j);
					for(int z=0;z<course.getIndices().length;z++)
					{
						if((z+1)==index)
						{
							indexCheck=true;
						}
					}
				}
			}
			
			
			if(courseCheck==false)
			{
				System.out.println("Please re-check your courseid");
			}
			
			if(indexCheck==false)
			{
				System.out.println("Please re-check your index ");
			}
			
			else
			{
				finalCheck=true;
			}
				
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return finalCheck;
	}
	
	
	public boolean checkIndexes(int newIndex,int oldIndex,String courseCode)
	{
		int firstCheck=0;
		int secondCheck=0;
		boolean state=false;
		try {
			List data=readAllCourse("src/courses.txt");
			
			for(int i=0;i<data.size();i++)
			{
				Course course = (Course) data.get(i);
				if(course.getCourseCode().equals(courseCode))
				{
					
					for(int j=0;j<course.getIndices().length;j++)
					{
						
						if((j+1)==oldIndex)
						{
							firstCheck=1;
						}
						
					}
					
					for(int j=0;j<course.getIndices().length;j++)
					{
						
						if((j+1)==newIndex)
						{
							secondCheck=1;
						}
						
					}
					
					
				}
			}
			
			if(firstCheck!=1)
			{
				System.out.println("Please re-check your old index");
			}
			
			if(secondCheck!=1)
			{
				System.out.println("Please re-check your new index");
			}
			
			if(firstCheck==1 && secondCheck==1)
			{
				state=true;
			}
			
		
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return state;
		
		
	}
	
	public boolean checkCourseCode(String courseCode,String matricNum,int index)
	{	
		
		boolean state=false;
		boolean result=false;
		
		try {
			
			List data=readAllCourse("src/courses.txt");
			
			for(int i=0;i<data.size();i++)
			{
				
				Course course = (Course)data.get(i);
				
				if(course.getCourseCode().toLowerCase().equals(courseCode))
				{
					
//				
					state=true;
					
				}
				
			}
			
			
			
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		if(state)
		{
			AddDropController invoke = new AddDropController();
			result=invoke.validateStudentAgainstCourseEnrolled(matricNum, courseCode, index);
			
		}
		return result;
	}
	
	
//	 public static void main(String args[])
//	 {
//		 CourseController c1 = new CourseController();
//		 c1.checkIndexes(10, 2, "Mh1812");
//	 }
//	
	
	
	


}

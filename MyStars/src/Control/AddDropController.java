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
import Miscellaneous.EmailSender;
import Model.Student;
import Model.AddDrop;

/**
 * An AddDropController class that facilitates with the Add and Drop of courses
 * 
 * @author Ameen
 * @author Ashiq
 * @author Will
 * @author Reuben
 * @author Waqas
 * @since 2017-04-01
 * @version 1.0
 */

public class AddDropController {
	/**
	 * A separator that is used to separate elements read from a file
	 */
	public static final String SEPARATOR = "|";
	
	/**
	 * 
	 * An index_separator that is used to separate elements reads from a file
	 */
	public static final String INDEX_SEPARATOR = "_";
	
	/**
	 * Instantiated a course controller object to communicate with the CourseController class
	 *
	 */
	private CourseController Cc1 = new CourseController();
	
	/**
	 * Declared a EmailSender object 
	 */
	private EmailSender email;
	
	/**
	 * A method for a student to add and drop method based on the CourseCode,Course Index and the students
	 * matriculation number.If the course is full,the person is automatically added into the waitlist.
	 * 
	 * 
	 * @param courseCode --> The course code the student which is to enroll into
	 * @param index --> The index of the course which the student prefers.E.g.CE2003_2 where 2 refers to the index
	 * @param matricNum --> The matriculation number of the student
	 * @return --> returns a boolean, either true when method is is able to add a course/add a person to a waitlist.False if otherwise.
	 */
	public boolean addMethod(String courseCode, int index, String matricNum) {
		boolean endResult = false;
		List student;
		AddDrop Add;
		List waitList = new ArrayList();
		try {
			List studentData = new ArrayList();
			ArrayList a1 = Cc1.readAllCourse("DataBase/courses.txt");
			List data = new ArrayList();
			courseCode = courseCode.toLowerCase();
			for (int x = 0; x < a1.size(); x++) {
				Course course = (Course) a1.get(x);

				if (course.getCourseCode().toLowerCase().equals(courseCode)) {

					if (course.getVacancy()[index - 1] <= 0) {
						Add = new AddDrop(courseCode, index, matricNum);
						waitList.add(Add);
						endResult = false;
					} else {

						course.decreaseVacancy(index);
						Add = new AddDrop(courseCode, index, matricNum);
						studentData.add(Add);

						endResult = true;
					}

				}
				data.add(a1.get(x));
			}
			
			AddDropController addd = new AddDropController();

			if (endResult == true) {
				addd.studentAddsCourse("DataBase/courseAndStudent.txt", studentData, courseCode, index);
				System.out.println("You have been added to the course");

			} else {
				addd.studentAndCourseWaitList("DataBase/waitlists/" + courseCode + ".txt", waitList, courseCode, index);
				System.out.println("You have been added to the wait list ");
			}

			Cc1.saveCourseAmend("DataBase/courses.txt", data);
			endResult = true;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return endResult;

	}


	/**
	 * A method that adds a student to a waitlist if the course is full
	 * 
	 * @param filename --> The file name of the waitlist for a particular course
	 * @param list --> Takes in a List which consists of the List
	 * @param courseCode -->  The course code of a course
	 * @param index --> The index of the course.E.g,CE2003_2 where 2 refers to the index
	 */
	public void studentAndCourseWaitList(String filename, List list, String courseCode, int index) {
		try {
			List tempList = new ArrayList();
			ArrayList listOfStudentInWait = readAllCourseAndStudent("DataBase/waitlists/" + courseCode + ".txt");

			if (listOfStudentInWait.isEmpty()) {

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

				for (int x = 0; x < listOfStudentInWait.size(); x++) {
					AddDrop singleMatricNum = (AddDrop) list.get(0);

					AddDrop listOfMatricNum = (AddDrop) listOfStudentInWait.get(x);

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

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * A method that takes in the matriculation number of the first student in the waiting list
	 * 
	 * @param courseCode --> The course code of a particular course
	 * @param index --> The index of the course.E.g,CE2003_2 where 2 refers to the index
	 * @return --> Returns the matriculation number
	 */
	public String getMatricNum(String courseCode, int index) {

		String matricNum = null;

		try {
			ArrayList listOfStudentInWait = readAllCourseAndStudent("DataBase/waitlists/" + courseCode + ".txt");
			for (int x = 0; x < listOfStudentInWait.size(); x++) {
				AddDrop Drop = (AddDrop) listOfStudentInWait.get(x);

				if (Drop.getCourseCode().toLowerCase().equals(courseCode.toLowerCase()) && Drop.getIndex() == index) {
					if (Drop.getList().isEmpty() != true) {
						matricNum = Drop.getList().get(0);
						
					}
				}
			}

			this.saveAmendWaitList(listOfStudentInWait, courseCode);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return matricNum;

	}
	
	/**
	 * A method that adds a student enrolled in a course into a file
	 * 
	 * @param filename --> The file name of where the data should be written
	 * @param list --> A list of student to be added into the course
	 * @param courseCode -->The course code of a particular course
	 * @param index --> The index of the course.E.g,CE2003_2 where 2 refers to the index
	 * @throws IOException -->Throws an exception to the method which calls this method in the event,there is a problem reading the file
	 */
	public void studentAddsCourse(String filename, List list, String courseCode, int index) throws IOException {

		List tempList = new ArrayList();
		ArrayList listOfEnrolledStudents = readAllCourseAndStudent("DataBase/courseAndStudent.txt");

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
	
	/**
	 * A method that writes content onto a text file
	 * 
	 * @param fileName --> The filename where the data should be written onto
	 * @param data --> Takes in a List of data to be written to the text file
	 * @throws IOException --> Exception when there is a problem writing data on the file
	 */
	public static void write(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName));

		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}
	
	/**
	 * A method that reads all students enrolled in the course given a filename
	 * 
	 * @param filename -->The name name of the file where data should be read from
	 * @return --> An ArrayList of students who are enrolled to the particular course
	 * @throws IOException -->Throws an exception to the method which calls this method where the reading fails
	 */
	public static ArrayList readAllCourseAndStudent(String filename) throws IOException {
		ArrayList stringArray = (ArrayList) read(filename);
		ArrayList alr = new ArrayList();

		for (int i = 0; i < stringArray.size(); i++) {

			String st = (String) stringArray.get(i);
			StringTokenizer star = new StringTokenizer(st, SEPARATOR); 

			String courseCode = star.nextToken("_").trim();

			String index = star.nextToken("|").trim();

			StringBuilder modifyIndex = new StringBuilder(index);
			index = modifyIndex.deleteCharAt(0).toString();

			int counter = 0;
			counter = star.countTokens();

			ArrayList retrievedMatricNum = new ArrayList();

			for (int j = 0; j < counter; j++) {

				String matricNum = star.nextToken().trim();
				retrievedMatricNum.add(matricNum);

			}

			AddDrop add = new AddDrop(courseCode, Integer.parseInt(index), retrievedMatricNum);
			alr.add(add);
		}

		return alr;
	}

	/** 
	 * A method that retrieves data given the file name of a text file
	 * @param fileName --> The file name of the text file where the contents should be read
	 * @return --> Returns a List of data read from a particular text file
	 * @throws IOException --> Exception thrown if there is problems reading from the file
	 */
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
	
	/**
	 * This method allows a student to drop a course and checks whether there are any students in the waitlist
	 * if there are students in the waitlist.The first student from the waitlist will be added to the course automatically
	 * and triggers an email instantly to the latter that he has been enrolled in the course.
	 * 
	 * @param courseCode -->Course of the particular course
	 * @param index --> The index of the course.E.g,CE2003_2 where 2 refers to the index
	 * @param matricNum --> The matriculation number of the student
	 */
	public void dropMethod(String courseCode, int index, String matricNum) {
		try {

			int vacancy = 0;
			List a2 = Cc1.readAllCourse("DataBase/courses.txt");
			List stringArray = readAllCourseAndStudent("DataBase/courseAndStudent.txt");
			String newMatricNum;
			for (int i = 0; i < stringArray.size(); i++) {
				AddDrop check = (AddDrop) stringArray.get(i);
				if (check.getCourseCode().toLowerCase().equals(courseCode.toLowerCase()) && check.getIndex() == index) {
					for (int x = 0; x < check.getList().size(); x++) {
						if (check.getList().get(x).toLowerCase().equals(matricNum.toLowerCase())) {
							check.getList().remove(x);
							for (int k = 0; k < a2.size(); k++) {
								Course course = (Course) a2.get(k);
								if (course.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())) {
									course.increaseVacancy(index);
									vacancy = course.getVacancy()[index - 1];

								}

							}

						}
					}
				}
			}
			this.saveAmend(stringArray);
			Cc1.saveCourseAmend("DataBase/courses.txt", a2);
			
			if (vacancy == 1) {
				newMatricNum = this.getMatricNum(courseCode, index);
				if (newMatricNum != null) {
					email = new EmailSender();
					this.addMethod(courseCode, index, newMatricNum);
					StudentController student=new StudentController();
					List studentEmailAddress = student.readAllStudents("DataBase/student.txt");
					String[] receipient= new String[1];
					String name=null;
					for(int i=0;i<studentEmailAddress.size();i++)
					{
						Student student1 = (Student)studentEmailAddress.get(i);
						if(student1.getMatriculationNumber().toLowerCase().equals(newMatricNum.toLowerCase()))
						{
							receipient[0]=student1.getUserId();
							name=student1.getName();
							break;
						}
					}
					String message="Dear "+name+","+"\n" +"We are pleased to inform you that you have been accepted into "+courseCode +"\n"+"Regards,"+"\n"+"StarsPlanner Administrator";
					
					email.sendFromGMail("starsplannerntu", "javaproject",receipient,"Notification: Acceptance into course",message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method returns the Index of a course a student is enrolled to based on
	 * the following parameters,
	 * 
	 * @param matriculationNum --> The matriculation number of the student
	 * @param courseCode --> The course code of a particular
	 * @return --> returns the index in the form of an integer
	 */
	public int returnIndex(String matriculationNum, String courseCode) {
		List stringArray;
		int index = 0;
		try {

			stringArray = readAllCourseAndStudent("DataBase/courseAndStudent.txt");

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
			e.printStackTrace();
		}

		return index;

	}

	
	/**
	 * This method saves the amended List of students enrolled into a course
	 * 
	 * @param stringArray -->Takes in a list to be written onto the file
	 */
	public void saveAmend(List stringArray) {
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
			write("DataBase/courseAndStudent.txt", tempList);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * This method saves the amended List of students who are in the wait list for a course
	 * 
	 * 
	 * @param stringArray -->Takes in a list to be written onto the file
	 * @param courseCode -->Takes the course code of a particular course
	 */
	public void saveAmendWaitList(List stringArray, String courseCode) {
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
			write("DataBase/waitLists/" + courseCode + ".txt", tempList);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * A method that checks whether there is an index for a particular courseCode
	 * 
	 * @param index --> The index of the course.E.g,CE2003_2 where 2 refers to the index
	 * @param courseCode --> The course code based on a particular course
	 * @return --> True, when such an index exists for a course else false if otherwise
	 */
	public boolean validateIndexOfCourseAndStudent(int index, String courseCode) {
		boolean state = false;
		try {
			List data = readAllCourseAndStudent("DataBase/courseAndStudent.txt");

			for (int i = 0; i < data.size(); i++) {
				AddDrop validate = (AddDrop) data.get(i);

				if (validate.getIndex() == index
						&& validate.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())) {

					state = true;
				}
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		return state;

	}
	
	/**
	 * This method when invoked allows the student to be dropped from the wait list.
	 * 
	 * 
	 * @param courseCode -->Takes in a course code of a particular course
	 * @param matricNum --> Matriculation number of the student
	 * @param Index --> The index of the course.E.g,CE2003_2 where 2 refers to the index
	 */
	public void dropWaitList(String courseCode,String matricNum,int Index)
	
	{
		try {
			List read = readAllCourseAndStudent("DataBase/waitLists/"+courseCode+".txt");
			
			for(int x=0;x<read.size();x++){
				AddDrop Drop=(AddDrop)read.get(x);
				if(Drop.getIndex()==Index){
					for(int k=0;k<Drop.getList().size();k++){
						if(Drop.getList().get(k).toLowerCase().equals(matricNum.toLowerCase())){
							Drop.getList().remove(k);
						}
					}
				}
				
				
				
			}
			
			this.saveAmendWaitList(read, courseCode);//saves the newly amended 
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method validates the student against a course to check if the student is enrolled in the course in a given index
	 * 
	 * @param matricNum -->Matriculation number of the student
	 * @param courseCode --> The course code of a particular course
	 * @param index --> The index of the course.E.g,CE2003_2 where 2 refers to the index
	 * @return --> True if the student is enrolled in the course,False if otherwise
	 */
	public boolean validateStudentAgainstCourseEnrolled(String matricNum, String courseCode, int index) {
		boolean state = false;

		try {
			List read = readAllCourseAndStudent("DataBase/courseAndStudent.txt");

			for (int i = 0; i < read.size(); i++) {
				AddDrop a1 = (AddDrop) read.get(i);
				if (a1.getCourseCode().toLowerCase().equals(courseCode.toLowerCase()) && (a1.getIndex() == index)) {

					for (int j = 0; j < a1.getList().size(); j++) {
						if (a1.getList().get(j).toLowerCase().equals(matricNum.toLowerCase())) {
							state = true;
						}
					}

				}
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		return state;
	}
	
	/**
	 * This method validates the student against a course to check if the student is enrolled in the course
	 * 
	 * @param matricNum --> Matriculation number of the student
	 * @param courseCode --> The course code of a particular course
	 * @return -->  True if the student is enrolled in the course,False if otherwise
	 */
	public boolean validateStudentAgainstCourse(String matricNum, String courseCode) {
		boolean state = false;

		try {
			List read = readAllCourseAndStudent("DataBase/courseAndStudent.txt");

			for (int i = 0; i < read.size(); i++) {
				AddDrop a1 = (AddDrop) read.get(i);
				if (a1.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())) {

					for (int j = 0; j < a1.getList().size(); j++) {
						if (a1.getList().get(j).toLowerCase().equals(matricNum.toLowerCase())) {
							state = true;
						}
					}

				}
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		return state;
	}
	
	
	/**
	 * This method allows to drop a student from a course
	 * 
	 * @param matricNum --> The matriculation number of the student
	 * @param courseCode -->The course code of the particular course
	 * @param index -->  The index of the course.E.g,CE2003_2 where 2 refers to the index
	 */
	public void dropMasterCheck(String matricNum,String courseCode,int index){
		
		boolean check=false;
		boolean waitCheck=false;
		try {
			List read = readAllCourseAndStudent("DataBase/courseAndStudent.txt");
			
			List read2 = readAllCourseAndStudent("DataBase/waitlists/"+courseCode+".txt");
			
			for(int k=0;k<read.size();k++){
				AddDrop Drop=(AddDrop)read.get(k);
				if(Drop.getCourseCode().toLowerCase().equals(courseCode.toLowerCase()) && Drop.getIndex()==index){
					for(int x=0;x<Drop.getList().size();x++){
						if(Drop.getList().get(x).toLowerCase().equals(matricNum.toLowerCase())){
							check=true;
						}
					}
					
				}
			}
			
			if(check==false){
				for(int k=0;k<read2.size();k++){
					AddDrop Drop=(AddDrop)read2.get(k);
					if(Drop.getIndex()==index){
						for(int x=0;x<Drop.getList().size();x++){
							if(Drop.getList().get(x).toLowerCase().equals(matricNum.toLowerCase())){
								waitCheck=true;
							}
						}
						
					}
				}
				
			}
			else{
				
				this.dropMethod(courseCode, index, matricNum);
				System.out.println("Course has been removed");
				
				
			}
			
			if(waitCheck==true){
				this.dropWaitList(courseCode, matricNum, index);
				System.out.println("Course has been removed from waitlist");
			}
			if(waitCheck==false && check==false){
				
				System.out.println("Error please register for the course first");
				
				
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method allows a student to be added into the course and a notification is triggered
	 * 
	 * @param matricNum --> Matriculation number of the student
	 * @param courseCode -->courseCode of a particular course
	 * @param index --> The index of the course.E.g,CE2003_2 where 2 refers to the index
	 * @return
	 */

	public boolean AddMasterCheck(String matricNum,String courseCode,int index){
		
		
		boolean check=false;
		boolean waitCheck=false;
		boolean courseCheck=true,indexCheck=true;
		
		boolean indexCourse=false;
		
		boolean finalCheck=false;
		try {
			
			
			indexCourse=Cc1.checkIndexeAndCourseCode(index, courseCode);
			
			if(indexCourse==true){
				List read = readAllCourseAndStudent("DataBase/courseAndStudent.txt");
				
				List read2 = readAllCourseAndStudent("DataBase/waitlists/"+courseCode+".txt");
				
				
			for(int x=0;x<read.size();x++){
				AddDrop Add=(AddDrop)read.get(x);
				if(Add.getCourseCode().toLowerCase().equals(courseCode.toString())){
					for(int k=0;k<Add.getList().size();k++){
						if(Add.getList().get(k).toLowerCase().equals(matricNum.toLowerCase())){
							check=true;
							//add the email send here
						
						}
					}
				
				
			}
	
			}
			
			
			if(check==true){
				System.out.println("You have registered for the stated course!!!!");
			}
			else{
				
				//implement the clash check here 
				for(int x=0;x<read2.size();x++){
					AddDrop Add=(AddDrop)read2.get(x);
					if(Add.getCourseCode().toLowerCase().equals(courseCode) && Add.getIndex()==index){
						for(int k=0;k<Add.getList().size();k++){
							if(Add.getList().get(k).toLowerCase().equals(matricNum.toLowerCase())){
								waitCheck=true;
							}
						}
					
					
				}
		
				}
				
				if(waitCheck==true){
					System.out.println("You have registered for the stated course and you have been put to waitlist and can't add the same course again!!!!");
				}
				
				else{
					this.addMethod(courseCode, index, matricNum);
					StudentController std=new StudentController();
					List studentEmailAddress = std.readAllStudents("DataBase/student.txt");
					String[] receipient= new String[1];
					String name=null;
					for(int i=0;i<studentEmailAddress.size();i++)
					{
						Student student = (Student)studentEmailAddress.get(i);
						if(student.getMatriculationNumber().toLowerCase().equals(matricNum.toLowerCase()))
						{
							receipient[0]=student.getUserId();
							name=student.getName();
							break;
						}
					}
					String message="Dear "+name+","+"\n"+"\n" +"We are pleased to inform you that your registration for the following course is successful "+courseCode+"\n" +"\n"+"Regards,"+"\n"+"Stars Planner Administrator";
					
					email.sendFromGMail("starsplannerntu", "javaproject",receipient,"Course Registration Successful",message);
				}
				///else condition for the clash check will be plced here
				
				
				
			}
			finalCheck=true;
		}
			
			else
			{
				finalCheck=false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return finalCheck;
	}
	
	
	
	public List returnCourseRegistered(String matricNum){
		List courseCode=new ArrayList();
		try {
			
			List StringArray = this.readAllCourseAndStudent("DataBase/courseAndStudent.txt");
			for (int x = 0; x < StringArray.size(); x++) {
				AddDrop addDrop = (AddDrop) StringArray.get(x);
				for (int k = 0; k < addDrop.getList().size(); k++) {
					if (addDrop.getList().get(k).toLowerCase().equals(matricNum.toLowerCase())) {
						courseCode.add(addDrop.getCourseCode());
						courseCode.add(addDrop.getIndex());
					}
				}

			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return courseCode;
		
	}


//	public static void main(String args[]) {
//		AddDropController Drop = new AddDropController();
//		Drop.dropMethod("ce2005", 1, "u162");
		// Drop.removeFromWaitList("ce2005",1);

//	
//		
//		AddDropController Drop = new AddDropController();
//		Drop.dropMasterCheck("u12345B", "Ce2003",1);
//		
//	}
}

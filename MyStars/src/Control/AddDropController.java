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
		List waitList = new ArrayList();
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
						Add = new AddDrop(courseCode, index, matricNum);
						waitList.add(Add);
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

			if (endResult == true) {
				addd.studentAddsCourse("src/courseAndStudent.txt", studentData, courseCode, index);

			} else {
				addd.studentAndCourseWaitList("src/waitlists/" + courseCode + ".txt", waitList, courseCode, index);
			}

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

	public void swapIndex() {

	}

	public void checkClash() {

	}

	public void studentAndCourseWaitList(String filename, List list, String courseCode, int index) {
		try {
			List tempList = new ArrayList();
			ArrayList listOfStudentInWait = readAllCourseAndStudent("src/waitlists/" + courseCode + ".txt");

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* This method is to check waitlist and amend the waitList to student */
	public String removeFromWaitList(String courseCode, int index) {

		String matricNum = null;

		try {
			ArrayList listOfStudentInWait = readAllCourseAndStudent("src/waitlists/" + courseCode + ".txt");
			for (int x = 0; x < listOfStudentInWait.size(); x++) {
				AddDrop Drop = (AddDrop) listOfStudentInWait.get(x);

				if (Drop.getCourseCode().toLowerCase().equals(courseCode.toLowerCase()) && Drop.getIndex() == index) {
					if (Drop.getList().isEmpty() != true) {
						matricNum = Drop.getList().get(0);// get the matric num
															// at index position
															// 0 to add to the
															// course
						Drop.getList().remove(0);
					}
				}
			}

			this.saveAmendWaitList(listOfStudentInWait, courseCode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return matricNum;

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

			int vacancy = 0;
			List a2 = Cc1.readAllCourse("src/courses.txt");// read the data from
															// courses
			List stringArray = readAllCourseAndStudent("src/courseAndStudent.txt");
			String newMatricNum;
			for (int i = 0; i < stringArray.size(); i++) {
				AddDrop check = (AddDrop) stringArray.get(i);
				if (check.getCourseCode().toLowerCase().equals(courseCode.toLowerCase()) && check.getIndex() == index) {
					for (int x = 0; x < check.getList().size(); x++) {
						if (check.getList().get(x).toLowerCase().equals(matricNum.toLowerCase())) {
							check.getList().remove(x);
							/*
							 * triverse through the course and add back the
							 * vacancy
							 */
							for (int k = 0; k < a2.size(); k++) {
								Course course = (Course) a2.get(k);
								if (course.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())) {
									course.increaseVacancy(index);
									vacancy = course.getVacancy()[index - 1];// need
																				// to
																				// minus
																				// the
																				// index
																				// position

								}

							}

						}
					}
				}
			}
			this.saveAmend(stringArray);
			Cc1.saveCourseAmend("src/courses.txt", a2);

			/*
			 * Only triggered when the vacany is 1 then the person form the wait
			 * list is removed then added to the course
			 */
			if (vacancy == 1) {
				newMatricNum = this.removeFromWaitList(courseCode, index);
				if (newMatricNum != null) {
					this.addMethod(courseCode, index, newMatricNum);
				}
			}
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

	/* Save the amended student and course file */
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
			write("src/courseAndStudent.txt", tempList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

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
			write("src/waitLists/" + courseCode + ".txt", tempList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean validateIndexOfCourseAndStudent(int index, String courseCode) {
		boolean state = false;
		try {
			List data = readAllCourseAndStudent("src/courseAndStudent.txt");

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
	
	public void dropWaitList(String courseCode,String matricNum,int Index)
	
	{
		try {
			List read = readAllCourseAndStudent("src/waitLists/"+courseCode+".txt");
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean validateStudentAgainstCourseEnrolled(String matricNum, String courseCode, int index) {
		boolean state = false;

		try {
			List read = readAllCourseAndStudent("src/courseAndStudent.txt");

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

	
	public void dropMasterCheck(String matricNum,String courseCode,int index){
		
		boolean check=false;
		boolean waitCheck=false;
		try {
			List read = readAllCourseAndStudent("src/courseAndStudent.txt");
			
			List read2 = readAllCourseAndStudent("src/waitlists/"+courseCode+".txt");
			
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
				System.out.println("Course hass been removed");
				
			}
			
			if(waitCheck==true){
				this.dropWaitList(courseCode, matricNum, index);
				System.out.println("Course hass been removed");
			}
			if(waitCheck==false && check==false){
				
				System.out.println("Error please register for the course first");
				
				
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean AddMasterCheck(String matricNum,String courseCode,int index){
		
		
		boolean check=false;
		boolean waitCheck=false;
		boolean courseCheck=true,indexCheck=true;
		
		boolean indexCourse=false;
		
		boolean finalCheck=false;
		try {
			
			
			indexCourse=Cc1.checkIndexeAndCourseCode(index, courseCode);
			
			if(indexCourse==true){
				List read = readAllCourseAndStudent("src/courseAndStudent.txt");
				
				List read2 = readAllCourseAndStudent("src/waitlists/"+courseCode+".txt");
				
				
			for(int x=0;x<read.size();x++){
				AddDrop Add=(AddDrop)read.get(x);
				if(Add.getCourseCode().toLowerCase().equals(courseCode.toString())){
					for(int k=0;k<Add.getList().size();k++){
						if(Add.getList().get(k).toLowerCase().equals(matricNum.toLowerCase())){
							check=true;
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
					System.out.println("You have registered for the stated course and you have been put to waitlist and caa't add the same course again!!!!");
				}
				
				else{//after checking it will add the course to the student either to wait list or the course itself
					this.addMethod(courseCode, index, matricNum);
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
			
			List StringArray = this.readAllCourseAndStudent("src/courseAndStudent.txt");
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


	public static void main(String args[]) {
//		AddDropController Drop = new AddDropController();
//		Drop.dropMethod("ce2005", 1, "u162");
		// Drop.removeFromWaitList("ce2005",1);

//	}
	}
}

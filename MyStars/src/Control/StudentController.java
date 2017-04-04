package Control;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import Model.AddDrop;
import Model.Admin;
import Model.Course;
import Model.Student;

public class StudentController {
	public static final String SEPARATOR = "|";
	private AddDropController addDrop = new AddDropController();
	private CourseController Cc1=new CourseController();

	public static ArrayList readAllStudents(String filename) throws IOException {
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
		write(filename, alw);
	}

	public boolean deleteStudent(String file, String matriculationNumber) {
		try {
			ArrayList<Student> a1 = readAllStudents(file);

			List l2 = new ArrayList();
			for (int i = 0; i < a1.size(); i++) {
				Student student = (Student) a1.get(i);

				String mat = student.getMatriculationNumber();

				if (mat.equals(matriculationNumber)) {

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

		} catch (IOException e) {

			return false;
		}

		return true;
	}

	/** Write fixed content to the given file. */
	public static void write(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName, true));// does
																			// not
																			// delete
																			// previous
																			// data

		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}

	/**
	 * Write fixed content to the given file.Ashiq implemented this to test the
	 * remove function
	 */
	public static void newWrite(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName));//Rewrites the dataa

		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
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
		newWrite(filename, alw);
	}

	public ArrayList<Student> retriveAllStudent() {
		ArrayList<Student> a2 = new ArrayList<Student>();

		String filename = "src/student.txt";
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
			int userVal = a1.get(i).getMatriculationNumber().compareTo(matriculationNumber);
			int passVal = a1.get(i).getPassword().compareTo(password);
			// System.out.println(userVal);
			// System.out.println(passVal);

			if (userVal==0 && passVal==0) {

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
		public void displayCourse(String matriculationNum){
			try {
				List StringArray=addDrop.readAllCourseAndStudent("src/courseAndStudent.txt");
				
				
				for(int x=0;x<StringArray.size();x++){
					AddDrop addDrop=(AddDrop)StringArray.get(x);
					for(int k=0;k<addDrop.getList().size();k++){
						if(addDrop.getList().get(k).toLowerCase().equals(matriculationNum.toLowerCase())){
							System.out.println("Course Code: "+ addDrop.getCourseCode() +" Index: "+addDrop.getIndex());
						}
					}
					
				}
				
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
		public void changeIndex(int index,int oldindex,String courseCode,String matricNum){
			try {
				
				int temp;
				List list=new ArrayList();
				list.add(matricNum);
				
				
				List readCourse = Cc1.readAllCourse("src/courses.txt");
				
				for(int i=0;i<readCourse.size();i++)
				{
					Course course = (Course) readCourse.get(i);
					System.out.println("Course Id: "+course.getCourseCode());
					
					for(int z=0;z<course.getVacancy().length;z++)
					{
						System.out.println("Vacancy: "+course.getVacancy()[z]);
					}
					
					
				}
				
				List StringArray=addDrop.readAllCourseAndStudent("src/courseAndStudent.txt");
				for(int x=0;x<StringArray.size();x++){
				AddDrop changeIndex=(AddDrop)StringArray.get(x);
				/*To remove the matric number from old index number*/
				if(changeIndex.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())&& changeIndex.getIndex()==oldindex){
					for(int k=0;k<changeIndex.getList().size();k++){
						if(changeIndex.getList().get(k).toLowerCase().equals(matricNum.toLowerCase())){
							changeIndex.getList().remove(k);
							
						}
					}
					
				}
				if(changeIndex.getCourseCode().toLowerCase().equals(courseCode.toLowerCase()) && changeIndex.getIndex()==index){
					
					changeIndex.setList(list);
				}
				
		
				
				}
				//addDrop.saveAmend(StringArray);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		/*for testing display course by student*/
		
		public static void main (String args[]){
			
			
			StudentController SS=new StudentController();
			//SS.displayCourse("u162");
			SS.changeIndex(4,2, "Mh1812", "u162");
			
			
		}
		
}

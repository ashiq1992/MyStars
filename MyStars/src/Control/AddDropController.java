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
	private CourseController Cc1= new CourseController();
	
	public boolean addMethod(String courseCode,int index,String matricNum)
	{
		boolean endResult=false;
		List student;
		AddDrop Add;
		//Cc1.decreaseCourseIndexVacancy(courseCode, index);
try {
			//ArrayList<AddDrop> a1 =Cc1. readAll("src/courseAndStudent.txt");//To add the student with a course then save it to a txt file
			List studentData = new ArrayList() ;
			
			ArrayList<Course> a1 =Cc1. readAllCourse("src/courses.txt");//read all data and store it in course format
			List data = new ArrayList() ;
			
			for (int x = 0; x < a1.size(); x++) {
				if (courseCode.equals(a1.get(x).getCourseCode())) {
					if (a1.get(x).getVacancy()[index-1] <= 0) {
						//waitList();
						endResult = false;
					} else {
						a1.get(x).decreaseVacancy(index);
						Add=new AddDrop(courseCode,index,matricNum);
						studentData.add(Add);
						//System.out.println("Index:"+index+" "+a1.get(x).getVacancy()[index-1]);
					
						endResult = true;
					}
					
				}
				data.add(a1.get(x));
			}
			
			
						
			AddDropController.saveCourseWithStudent("src/courseAndStudent.txt", studentData,courseCode,index);
			Cc1.saveCourseAmend("src/courses.txt",data);
			endResult=true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return endResult;
		
	}
	
	public void sendEmail()
	{
	
	}
	
	public void dropMethod()
	{
	
	}
	
	public void changeIndex()
	{
	
	}
	
	public void swapIndex()
	{
	
	}
	
	public void checkClash()
	{
	
	}
	
	
	public static void saveCourseWithStudent(String filename, List list,String courseCode,int index) throws IOException {
		List tempList = new ArrayList();// to store students data
		ArrayList<AddDrop> StringArray=readAllCourseAndStudent(filename);
		
		for(int x=0;x<StringArray.size();x++){
			AddDrop Add = (AddDrop) list.get(0);//Convert the data into AddDrop class then check for condition before adding them together
			if(StringArray.get(x).getCourseCode().equals(courseCode) && StringArray.get(x).getIndex()==index){
				StringArray.get(x).setmatricNumArray(Add.getMatricNum());
			}
		}
		
		
		
		
		for (int i = 0; i <StringArray.size(); i++) {
			//AddDrop Add = (AddDrop) list.get(i);
			StringBuilder st = new StringBuilder();
		
			st.append(StringArray.get(i).getCourseCode());
			st.append(INDEX_SEPARATOR);
			st.append(StringArray.get(i).getIndex());
			for(int j=0;j<StringArray.get(i).getMatricNumArray().length;j++){
				st.append(StringArray.get(i).getMatricNumArray()[j]);
				st.append(SEPARATOR);
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
			//System.out.println(index);//for error checking
			int counter = 0;
			counter = star.countTokens();
		

			String[] retrievedMatricNum = new String[counter];

			// Algorithm to breakdown indexes and vacancies separately
			for (int j = 0; j < counter; j++) {

				String matricNum = star.nextToken().trim();
				//System.out.println(matricNum);//for testing purpose
				retrievedMatricNum[j] =matricNum;

			}

			AddDrop add = new AddDrop(courseCode,Integer.parseInt(index), retrievedMatricNum);
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
	
	
	
//	public static void main(String[] args){
//		 try {
//			ArrayList<AddDrop> A1= readAllCourseAndStudent("src/courseAndStudent.txt");
//			for(int x=0;x<A1.size();x++){
//				for(int i=0;i<A1.get(x).getMatricNumArray().length;i++){
//					System.out.println(A1.get(x).getCourseCode()+" "+A1.get(x).getIndex() +" "+A1.get(x).getMatricNumArray()[i]);
//				}
//				
//				//System.out.println(A1.get(x).getIndex() );
//				
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 
//	}
	
	
	
	
	

}

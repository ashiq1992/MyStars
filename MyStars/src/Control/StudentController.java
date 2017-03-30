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
import Model.Student;

public class StudentController {
	public static final String SEPARATOR = "|";
	
	public static ArrayList readAllStudents(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList alr = new ArrayList() ;// to store Admins data
		for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","
				
				String matriculationNumber=star.nextToken().trim();
				String  name = star.nextToken().trim();
				String  userId = star.nextToken().trim();
				String  password = star.nextToken().trim();
				String  nationality = star.nextToken().trim();
				String  gender = star.nextToken().trim();
				
				
				Student student = new Student(matriculationNumber,name,userId,password,nationality,gender);
				
				// add student objects to alr
				alr.add(student) ;
			}
			return alr ;
	}
	
	/** Read the contents of the given file. */
	  public static List read(String fileName) throws IOException {
		List data = new ArrayList() ;
	    Scanner scanner = new Scanner(new FileInputStream(fileName));
	    try {
	      while (scanner.hasNextLine()){
	        data.add(scanner.nextLine());
	      }
	    }
	    finally{
	      scanner.close();
	    }
	    return data;
	  }
	 
	 
	  
	  // an example of saving
	  public static void saveStudent(String filename, List al) throws IOException {
	  		List alw = new ArrayList() ;// to store students data

	          for (int i = 0 ; i < al.size() ; i++) {
	  				Student student = (Student)al.get(i);
	  				StringBuilder st =  new StringBuilder() ;
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
	  				alw.add(st.toString()) ;
	  			}
	  			write(filename,alw);
	  	}
	  
	  public boolean deleteStudent(String file,String matriculationNumber)
	  {
		  try {
			ArrayList<Student> a1=readAllStudents(file);
			
			List l2 = new ArrayList();
			for(int i=0;i<a1.size();i++)
			{
				Student student = (Student)a1.get(i);
				
				String mat= student.getMatriculationNumber();
				
				
				if(mat.equals(matriculationNumber))
				{
					
					a1.remove(i);
				}
				
								
			}
			
			for(int i=0;i<a1.size();i++)
			{
				Student student=(Student)a1.get(i);
				System.out.println("Gender: "+student.getGender());
				System.out.println("MatriculationNumber: "+student.getMatriculationNumber());
				l2.add(a1.get(i));
			}
			
			saveStudent(file,l2);
			
			
		} catch (IOException e) {
			
			return false;
		}
		  
		  return true;
	  }

	  /** Write fixed content to the given file. */
	  public static void write(String fileName, List data) throws IOException  {
	    PrintWriter out = new PrintWriter(new FileWriter(fileName));

	    try {
			for (int i =0; i < data.size() ; i++) {
	      		out.println((String)data.get(i));
			}
	    }
	    finally {
	      out.close();
	    }
	  }
	  


}

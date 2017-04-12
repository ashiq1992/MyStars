package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import Model.Admin;
import Model.Course;
import Model.Student;
/**
 * An Entity class called "AddDrop" which consists of class variables and get and set methods.
 * @author Ameen
 * @author Waqas
 * @author Ashiq
 * @author Will
 * @author Reuben
 * @since 2017-04-01
 * @version 1.0
 *
 */
public class AddDrop {
	
	/**
	 * The course code for this AddDrop
	 */
	private String CourseCode;
	
	/**
	 * The index for each course
	 */
	private int Index;
	
	/**
	 * The matriculation id of a student
	 */
	private String MatricNum;
	
	/**
	 * A List of matriculation id's
	 */
	private List<String> list;

	/**
	 * Creates a new AddDrop object given the coursecode,index and matriculation number of a student
	 * 
	 * 
	 * @param CourseCode  CourseCode to Add/Drop courses
	 * @param index       A single index from courseCode
	 * @param matricNum   Matriculation Number of student enrolled in this course
	 */
	public AddDrop(String CourseCode, int index, String matricNum) {
		this.CourseCode = CourseCode;
		this.Index = index;
		this.MatricNum = matricNum;

	}
	
	
	/**
	 * Creates a new AddDrop object given the course code,index and a list of matriculation numbers enrolled in a particular course
	 * @param CourseCode Takes in the course code of a particular course
	 * @param index Takes in the index of a particular course E.G: CE2003_1
	 * @param matricNum Takes in a list of student matriculation numbers enrolled to particular course
	 */
	public AddDrop(String CourseCode, int index, List<String> matricNum) {
		
		
		this.CourseCode = CourseCode;
		this.Index = index;
		list = new ArrayList<String>();
		
		for(int i=0;i<matricNum.size();i++)
		{
			list.add(matricNum.get(i));
		}

	}

	/**
	 * 
	 * @return The course code
	 */
	public String getCourseCode() {
		return CourseCode;
	}
	
	/**
	 * 
	 * @param courseCode sets the course code
	 */
	public void setCourseCode(String courseCode) {
		CourseCode = courseCode;
	}
	
	/**
	 * 
	 * @return the index of a particular course
	 */
	public int getIndex() {
		return Index;
	}
	
	/**
	 * 
	 * @param index set the index of a particular course
	 */
	public void setIndex(int index) {
		Index = index;
	}

	/**
	 * 
	 * @return the matriculation number of a student
	 */
	public String getMatricNum() {
		return MatricNum;
	}
	
	/**
	 * 
	 * @param matricNum sets the matriculation number of a student
	 */
	public void setMatricNum(String matricNum) {
		MatricNum = matricNum;
	}


	/**
	 * 
	 * @return a list of matriculation numbers
	 */
	public List<String> getList() {
		return list;
	}
	
	/**
	 * 
	 * @param list Takes in a list of matriculation numbers
	 */
	public void setList(List<String> list) {
		this.list.add(list.get(0));
	}


}

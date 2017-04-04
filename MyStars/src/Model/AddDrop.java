package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import Model.Admin;
import Model.Course;
import Model.Student;

public class AddDrop {

	private String CourseCode;
	private int Index;
	private String MatricNum;
	private List<String> list;

	public AddDrop(String CourseCode, int index, String matricNum) {
		this.CourseCode = CourseCode;
		this.Index = index;
		this.MatricNum = matricNum;

	}

	public AddDrop(String CourseCode, int index, List<String> matricNum) {
		
		
		this.CourseCode = CourseCode;
		this.Index = index;
		list = new ArrayList<String>();
		
		for(int i=0;i<matricNum.size();i++)
		{
			list.add(matricNum.get(i));
		}

	}

	public AddDrop() {
		// TODO Auto-generated constructor stub
	}

	public String getCourseCode() {
		return CourseCode;
	}

	public void setCourseCode(String courseCode) {
		CourseCode = courseCode;
	}

	public int getIndex() {
		return Index;
	}

	public void setIndex(int index) {
		Index = index;
	}

	public String getMatricNum() {
		return MatricNum;
	}

	public void setMatricNum(String matricNum) {
		MatricNum = matricNum;
	}



	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list.add(list.get(0));
	}

	public void sendEmail() {

	}
	public void checkClash() {

	}

}

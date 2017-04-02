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
	private String[] matricNum;
	
	
	public AddDrop(String CourseCode,int index,String matricNum ){
		this.CourseCode=CourseCode;
		this.Index=index;
		this.MatricNum=matricNum;
		
	}
	
	

	public AddDrop(String CourseCode,int index,String[] matricNum ){
		this.CourseCode=CourseCode;
		this.Index=index;
		this.matricNum =matricNum;
		//System.out.println(this.matricNum);
		for(int x=0;x<matricNum.length;x++){
			this.matricNum[x]=matricNum[x];
		//System.out.println(this.matricNum[x]);
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

	public void setmatricNumArray(String matricNum) {
		this.matricNum[this.matricNum.length]=matricNum; //Adds the MatricNum to the lst Position of the array
	
	}
	public String[] getMatricNumArray() {
	return this.matricNum;
	}
	
	
	
	public void sendEmail()
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
	
	
	
	
	
	

}

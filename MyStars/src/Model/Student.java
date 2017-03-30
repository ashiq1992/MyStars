package Model;

public class Student extends Person{
	
	private String matriculationNumber;
	
	public Student()
	{
		
	}
	public Student(String gender, String name, String nationality, String userId, String password,String matriculationNumber) {
		super(gender, name, nationality, userId, password);
		this.matriculationNumber=matriculationNumber;
	}

	

	public String getMatriculationNumber() {
		return matriculationNumber;
	}

	public void setMatriculationNumber(String matriculationNumber) {
		this.matriculationNumber = matriculationNumber;
	}
	
	public String checkCourseRegistered(){
		return null;
	}
	
	public String printCourseRegistered(){
		return null;
	}

}
package Model;

public class Student extends Person{
	
	public Student(String gender, String name, String nationality, String userId, String password) {
		super(gender, name, nationality, userId, password);
		// TODO Auto-generated constructor stub
	}

	private String matriculationNumber;

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
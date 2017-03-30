package Model;

public class Student extends Person{
	//Properties
	private String matriculationNumber;
	
	
	//Constructor
	public Student()
	{
		
	}
	public Student(String matriculationNumber,String name,String userId,String password,String nationality,String gender) {
		super(gender, name, nationality, userId, password);
		this.matriculationNumber=matriculationNumber;
	}

	
	//Methods
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
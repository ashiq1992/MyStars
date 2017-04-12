package Model;
/**
 * 
 * An entity class,student object is created
 * 
 * @author Ameen
 * @author Waqas
 * @author Ashiq
 * @author Will
 * @author Reuben
 * @version 1.0
 * @since 2017-04-01
 */
public class Student extends Person{
	
	/**
	 * MatriculationNumber of the Student
	 */
	private String matriculationNumber;
	
	/**
	 * 
	 * An Empty Constructor
	 */
	public Student()
	{
		
	}
	
	/**
	 * Creates a student based on the following fields,
	 * and inherits attributs from the Parent class Person
	 * 
	 * @param matriculationNumber
	 * @param name
	 * @param userId
	 * @param password
	 * @param nationality
	 * @param gender
	 */
	public Student(String matriculationNumber,String name,String userId,String password,String nationality,String gender) {
		super(gender, name, nationality, userId, password);
		this.matriculationNumber=matriculationNumber;
	}

	
	/**
	 * Gets the matriculationNumber of the student
	 * @return
	 */
	public String getMatriculationNumber() {
		return matriculationNumber;
	}
	
	/**
	 * 
	 * Sets the matriculation number of the student
	 * @param matriculationNumber
	 */
	public void setMatriculationNumber(String matriculationNumber) {
		this.matriculationNumber = matriculationNumber;
	}
	
}
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
	 * @param matriculationNumber of the student
	 * @param name of the person
	 * @param userId of the user
	 * @param password of the user
	 * @param nationality of the user
	 * @param gender of the user
	 */
	public Student(String matriculationNumber,String name,String userId,String password,String nationality,String gender) {
		super(gender, name, nationality, userId, password);
		this.matriculationNumber=matriculationNumber;
	}

	
	/**
	 * Gets the matriculationNumber of the student
	 * @return the matriculation number as a string
	 */
	public String getMatriculationNumber() {
		return matriculationNumber;
	}
	
	/**
	 * 
	 * Sets the matriculation number of the student
	 * @param matriculationNumber of a student as a String
	 */
	public void setMatriculationNumber(String matriculationNumber) {
		this.matriculationNumber = matriculationNumber;
	}
	
}
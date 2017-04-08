package Model;

/**
 * An Entity class called Person which is a super class,
 * A Person can be a student or an Admin
 * @author Ameen
 * @since 2017-04-01
 * @version 1.0
 */
public class Person {

	/**
	 *  The gender of the person	
	 */
	private String gender;
	
	/**
	 * The name of the person
	 */
	private String name;
	
	/**
	 * The nationality of the person
	 */
	private String nationality;
	
	/**
	 * The userId of the person
	 */
	private String userId;
	
	/**
	 * The password of the person
	 */
	private String password;
	
	/**
	 * An Empty person constructor
	 */
	public Person()
	{
		
	}
	
	/**
	 * The is constructor creates a person object given the following,
	 * 
	 * 
	 * @param gender --> The gender of the Person
	 * @param name --> The name of the Person
	 * @param nationality --> The nationality of the Person
	 * @param userId --> The userId of the person which is used to login into the system
	 * @param password --> The password of the person in order to user the Stars System
	 */
	public Person(String gender,String name,String nationality,String userId,String password)
	{
		this.gender=gender;
		this.name=name;
		this.nationality=nationality;
		this.userId=userId;
		this.password=password;
	}
	
	/**
	 * 
	 * 
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	
	/**
	 * 
	 * @param gender ->>Takes in a gender.Input is either M/F
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
	 * 
	 * @return ->>Returns the name of the person
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name sets the name of the person
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return the nationality of the person
	 */
	public String getNationality() {
		return nationality;
	}
	
	/**
	 * Sets the nationality of person.E.g: Singaporean and etc
	 * @param nationality
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	/**
	 * Gets the userID of a person
	 * @return
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * Sets the userId of the person
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * Gets the password of the person
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password of the person
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}

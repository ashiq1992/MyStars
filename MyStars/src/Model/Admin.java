package Model;

/**
 * An entity class called Admin that inherits its variables from a Person class
 * 
 * @author Ameen
 * @author Waqas
 * @author Ashiq
 * @author Will
 * @author Reuben
 * @since 2017-04-01
 * @version 1.0
 */
public class Admin extends Person {
	
	/**
	 * This constructor takes in variables from the Person class & Admin class and sets the values accordingly
	 * 
	 * @param gender Gender of the admin
	 * @param name   the full name of the admin
	 * @param nationality  the nationality of the admin
	 * @param userId  the userId of admin
	 * @param password the password of admin
	 */
	public Admin(String gender, String name, String nationality, String userId, String password) {
		super(gender, name, nationality, userId, password);

	}

}

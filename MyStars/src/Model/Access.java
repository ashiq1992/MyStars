package Model;
/**
 * An Entity class called "Access" which consists of class variables and get and set methods. 
 * @author Ameen
 * @author Waqas
 * @author Ashiq
 * @author Will
 * @author Reuben
 * @since 2017-04-01
 * @version 1.0
 *
 */
public class Access {
	/**
	 * The start date to access the Stars Planner System
	 */
	private String startDate;
	
	
	/**
	 * The end date to access the Stars Planner System
	 */
	private String endDate;
	
	
	/**
	 * The end date to access the StarsPlanner System
	 */
	
	private String startTime;
	
	/**
	 * The end date to access the StarsPlanner System
	 */
	private String endTime;
	
	/**
	 * An empty constructor
	 */
	public Access()
	{
		
	}
	
	/**
	 * Create a new Access object with the given startTime,endTime,startDate,endDate
	 * 
	 * 
	 * @param startTime: The start time to access the stars system (HH:MM)
	 * @param endTime: The end time to access the stars system in the format ( HH:MM)
	 * @param startDate: The start date to access the stars system in the format (DD/MM/YYYY)
	 * @param endDate: The end date to access the stars system in the format(DD/MM/YYYY)
	 * 
	 */
	public Access(String startTime,String endTime,String startDate,String endDate)
	{
		this.startTime=startTime;
		this.endTime=endTime;
		this.startDate=startDate;
		this.endDate=endDate;
	}

	/**
	 * 
	 * 
	 * @return the start date as a String in the format DD/MM/YYYY
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * 
	 * 
	 * @param startDate in the format DD/MM/YYYY
	 * 
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * 
	 * 
	 * @return the end date in the format DD/MM/YYYY
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 
	 * @param endDate Takes in the input of endDate in the format DD/MM/YYYY
	 * 
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
	/**
	 * 
	 * @return startTime as a string in the format (HH:MM)
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * 
	 * @param startTime as a String in the format (HH:MM)
	 *
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * 
	 * @return the endTime as a String in the format (HH:MM)
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * 
	 * @param endTime,The end time to access the stars system
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}

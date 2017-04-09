package Control;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.Access;
/**
 * An AccessController class to check whether the student has access to the Stars Planner system
 * 
 * @author Ameen
 * @author Ashiq
 * @author Will
 * @author Reuben
 * @author Waqas
 * @since 2017-04-01
 * @version 1.0
 */
public class AccessController {
	
	/**
	 * Separator which is used to break elements read from a text file
	 */
	public static final String SEPARATOR = "|";
	
	
	/**
	 * Declaration of an access object
	 */
	private Access access=null;
	
	/**
	 * Declaration of a course controller object
	 */
	private CourseController course;
	
	/**
	 * A static variable format declared in the format "HH:MM"
	 */
	final static DateFormat dateFormat = new SimpleDateFormat("HH:mm");
	
	
		/**
		 * A method that writes content onto a text file
		 * 
		 * @param fileName --> The filename where the data should be written onto
		 * @param data --> Takes in a List of data to be written to the text file
		 * @throws IOException --> Exception when there is a problem writing data on the file
		 */
	  public static void write(String fileName, List data) throws IOException  {
	    PrintWriter out = new PrintWriter(new FileWriter(fileName));

	    try {
			for (int i =0; i < data.size() ; i++) {
	      		out.println((String)data.get(i));
			}
	    }
	    finally {
	      out.close();
	    }
	  }

	  /**
	   * A method that retrieves data given the file name of a text file
	   * 
	   * @param fileName --> The file name of the text file where the contents should be read
	   * @return --> Returns a List of data read from a particular text file
	   * @throws IOException --> Exception thrown if there is problems reading from the file
	   */
	  public static List read(String fileName) throws IOException {
		List data = new ArrayList() ;
	    Scanner scanner = new Scanner(new FileInputStream(fileName));
	    try {
	      while (scanner.hasNextLine()){
	        data.add(scanner.nextLine());
	      }
	    }
	    finally{
	      scanner.close();
	    }
	    return data;
	  }
	
	  /**
	   * A method which is used by the Admin to set the the time & date for Students to access the Stars System
	   * 
	   * @return -->Returns a boolean which is true when the Time & Date has been set successfully and false if otherwise
	   */
	public boolean accessPeriod()
	
	{
		course = new CourseController();
		Scanner sc = new Scanner(System.in);
		boolean state=false;
		
		boolean startTimeResult=false;
		String startTime=null;
		while(startTimeResult==false)
		{
			System.out.println("Please enter the start time in the format HH:MM ");
			startTime=sc.next();
			
			String ip = "";
			String pattern = "^([01]?\\d|2[0-3]):([0-5]?\\d)$";
			ip = startTime;
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(ip);
			if (m.find()) {
				startTime = ip;
				startTimeResult=true;
			} else {
				System.out.println("Invalid Start Time,Please try again!");
				startTimeResult=false;
			}
		}
		
		
		boolean endTimeResult=false;
		String endTime=null;
		while(endTimeResult==false)
		{
			System.out.println("Please enter the end time in the format HH:MM: ");
			endTime=sc.next();
			
			String ip = "";
			String pattern = "^([01]?\\d|2[0-3]):([0-5]?\\d)$";
			ip = endTime;
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(ip);
			if (m.find()) {
				endTime = ip;
				endTimeResult=true;
			} else {
				System.out.println("Invalid End Time,Please try again!");
				endTimeResult=false;
			}
		}
		
		boolean startDateResult=false;
		String startDate=null;
		while(startDateResult==false)
		{
			
			System.out.println("Please enter the start date : ");
			startDate=sc.next();
			startDateResult=course.validateDate(startDate);
		}
		
		boolean endDateResult=false;
		String endDate=null;
		while(endDateResult==false)
		{
			
			System.out.println("Please enter the end date : ");
			endDate=sc.next();
			endDateResult=course.validateDate(endDate);
		}
		
				
		

		
				
		List acc=new ArrayList();
		access=new Access(startTime,endTime,startDate,endDate);
		StringBuilder st = new StringBuilder();
		st.append(startTime);
		st.append(SEPARATOR);
		st.append(endTime);
		st.append(SEPARATOR);
		st.append(startDate);
		st.append(SEPARATOR);
		st.append(endDate);
		acc.add(st.toString());
				
		try {
			write("DataBase/AccessPeriod.txt",acc);
			state=true;
		} catch (IOException e) {
			e.printStackTrace();
			state=false;
		}
		
		return state;
	}
	
	/**
	 * A method that calls the read() method to read all the content and processes it to store in an access object and returns it as an ArrayList
	 * 
	 * @return --> returns a Single ArrayList of Access object
	 * @throws IOException --> Passes the exception thrown to the method that calls this method in the event where there is problems reading the file
	 */
	public ArrayList readAccess() throws IOException
	{
		
		ArrayList access1 = (ArrayList) read("DataBase/AccessPeriod.txt");
		ArrayList data= new ArrayList();
		
		String st = (String) access1.get(0);
		StringTokenizer star = new StringTokenizer(st, SEPARATOR);
		
		String startTime=star.nextToken().trim();
		String endTime=star.nextToken().trim();
		String startDate=star.nextToken().trim();
		String endDate=star.nextToken().trim();
		
		access= new Access(startTime,endTime,startDate,endDate);
		data.add(access);
		return data;
	}
	
	/**
	 * A method which takes in parameters of the current date & current time of the student logging in and checks against the text file
	 * to check if the Student is allowed to access or not.
	 * 
	 * @param currentDate --> The current date based on the students systems clock
	 * @param currentTime --> The current time based on the students systems clock
	 * @return --> A boolean which is either true or false.True means the student has access to the system and false means otherwise.
	 * @throws ParseException --> An exception is thrown when the method is unable to read the current date and current time in a particular format
	 */
	public boolean checkStudentAccess(Date currentDate,Date currentTime) throws ParseException
	{
		boolean state=false;
		List getStudentAccess= new ArrayList();
		try {
			SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
			getStudentAccess= readAccess();
			
			access=(Access) getStudentAccess.get(0);
			
			Date startTime = parser.parse(access.getStartTime());
			Date endTime = parser.parse(access.getEndTime());
			
			String startDate=access.getStartDate();
			String endDate=access.getEndDate();
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat df1 = new SimpleDateFormat("HH:MM");
			
			Date sD =df.parse(startDate);
			Date sD1 =df.parse(endDate);
			 String newDateString = df.format(sD);
		     
			
			if(currentDate.after(sD)&& currentDate.before(sD1))
			{
				if(currentTime.getHours()>=startTime.getHours() && currentTime.getHours()<=endTime.getHours())
				{
					state=true;
				}
				
				else
				{
					state=false;
				}
				
			}
			
			else
			{
				state=false;
			}
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			state=false;
		}
		
		return state;
	}
	
}

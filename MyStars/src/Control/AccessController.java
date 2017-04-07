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

public class AccessController {
	public static final String SEPARATOR = "|";
	private Access access=null;
	private CourseController course;
	final static DateFormat dateFormat = new SimpleDateFormat("HH:mm");
	
	
	 /** Write fixed content to the given file. */
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

	  /** Read the contents of the given file. */
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
				System.out.println("Time" + m.group(0));
				System.out.println("hh " + m.group(1));
				System.out.println("mm " + m.group(2));
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
				System.out.println("Time" + m.group(0));
				System.out.println("hh " + m.group(1));
				System.out.println("mm " + m.group(2));
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
			write("src/AccessPeriod.txt",acc);
			state=true;
		} catch (IOException e) {
			e.printStackTrace();
			state=false;
		}
		
		return state;
	}
	
	public ArrayList readAccess() throws IOException
	{
		
		ArrayList access1 = (ArrayList) read("src/AccessPeriod.txt");
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
	
//	public static void main(String args[])
//	{
//		
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		LocalDate localDate = LocalDate.now();
//		String currentDate=dtf.format(localDate);
//		Date cDate=new Date();
//		cDate.parse(currentDate);
//
//		
//		DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
//		DateFormat format1 = new SimpleDateFormat("HH:mm");
//		Calendar now = Calendar.getInstance();
//		
//		Date date=null;
//		Date date1=new Date();
//		try {
//			date = format.parse(dtf.format(localDate));
//			String test2=dateFormat.format(now.getTime());
//			date1=format1.parse(test2);
//
//		} catch (ParseException e) {
//	
//			e.printStackTrace();
//		}
//
//		AccessController a1 = new AccessController();
//		try {
//			boolean result=a1.checkStudentAccess(cDate,date1);
//			System.out.println(result);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
	
}

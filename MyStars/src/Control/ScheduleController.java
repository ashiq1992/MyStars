package Control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.Course;
import Model.Schedule;

public class ScheduleController {
	AddDropController check=new AddDropController();
	// Properties
		public static final String SEPARATOR = "|";
		public static final String INDEX_SEPARATOR = "_";
		public static final String SCHEDULE_SEPARATOR=",";
		
		

		
	
	
	public void saveSchedule(List schedule,String courseCode){
		File f = new File("src/schedule/"+courseCode+".txt");//creates the file in the folder
		//coursecode_index|type|day|venue|starttime|endTime|
		//index
		//type
		//day
		//starttime
		//endtime
		
		String index;
		List tempList = new ArrayList();// to store students data

		for (int i = 0; i < schedule.size(); i++) {
			Schedule scheduleNew = (Schedule) schedule.get(i);
			StringBuilder st = new StringBuilder();
		st.append(scheduleNew.getCourseCode().trim());
		st.append(INDEX_SEPARATOR);
		index=scheduleNew.getIndex()+"";
		st.append(index.trim());
		st.append(SEPARATOR);
		st.append(scheduleNew.getType());
		st.append(SEPARATOR);
		st.append(scheduleNew.getDay());
		st.append(SEPARATOR);
		st.append(scheduleNew.getVenue());
		st.append(SEPARATOR);
		st.append(scheduleNew.getStartTime());
		st.append(SEPARATOR);
		st.append(scheduleNew.getEndTime());
		st.append(SEPARATOR);
		
			tempList.add(st.toString());
		}
		
		
		try {
			write("src/schedule/"+courseCode+".txt",tempList);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public ArrayList readSchedule(String courseCode){
		
		ArrayList sch = new ArrayList();// to store Admins data
		
		
		try {
			ArrayList stringArray = (ArrayList) read("src/schedule/"+courseCode+".txt");
			for(int x=0;x<stringArray.size();x++){
				
			String st = (String) stringArray.get(x);
			// get individual 'fields' of the string separated by SEPARATOR
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);
			
			String code = star.nextToken("_").trim();
			StringBuilder modifyIndex = new StringBuilder(code);
			courseCode = modifyIndex.deleteCharAt(0).toString();
			String index = star.nextToken("|").trim();
			StringBuilder modifyVacancy = new StringBuilder(index);
			index = modifyVacancy.deleteCharAt(0).toString();
			String type = star.nextToken().trim();
			String day = star.nextToken().trim();
			String venue = star.nextToken().trim();
			String startTime = star.nextToken().trim();
			String endTime = star.nextToken().trim();
			
			Schedule S1=new Schedule(code,Integer.parseInt(index),type,day,venue,startTime,endTime);
			sch.add(S1);
			
			}
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
		
		return sch;
		
	}
	
	public 
	
	
	/** Read the contents of the given file. */
	public static List read(String fileName) throws IOException {
		List data = new ArrayList();
		Scanner scanner = new Scanner(new FileInputStream(fileName));
		try {
			while (scanner.hasNextLine()) {
				data.add(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}
		return data;
	}

	
	public static void write(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName, true));//create a new file 
		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}
	
	
	
	
	public boolean clashTimeValidate(String newcourseCode,int index){
		ArrayList oldData;
		ArrayList check;
		
		try {
			=check.readAllCourseAndStudent("src/courseAndStudent.txt");
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
//	public static void main(String [] args){
//		ScheduleController n=new ScheduleController();
//		ArrayList test;
//		test=n.readSchedule("ce3005");
//		for(int x=0;x<test.size();x++){
//			Schedule s=(Schedule)test.get(0);
//			System.out.println(s.getCourseCode()+" "+s.getIndex()+" "+s.getType()+" "+s.getDay()+" "+s.getStartTime()+" "+s.getEndTime());;
//			
//			
//			
//		}
//		
//		
//	}
//	
	}


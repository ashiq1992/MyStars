package Control;

import java.io.File;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.Course;
import Model.Schedule;

public class ScheduleController {
	
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
		
		
		List tempList = new ArrayList();// to store students data

		for (int i = 0; i < schedule.size(); i++) {
			Schedule scheduleNew = (Schedule) schedule.get(i);
			StringBuilder st = new StringBuilder();
		st.append(scheduleNew.getCourseCode().trim());
		st.append(INDEX_SEPARATOR);
		st.append(scheduleNew.getIndex().trim());
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
	
	
	
	}


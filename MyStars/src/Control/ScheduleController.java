package Control;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import Model.Course;
import Model.Schedule;

public class ScheduleController {
	
	// Properties
		public static final String SEPARATOR = "|";
		public static final String INDEX_SEPARATOR = "_";
		public static final String SCHEDULE_SEPARATOR=",";
	
	private CourseController Cc=new CourseController();
	
	
	public void saveSchedule(List schedule){
		List tempList = new ArrayList();// to store students data

		for (int i = 0; i < schedule.size(); i++) {
			Schedule scheduleNew = (Schedule) schedule.get(i);
			StringBuilder st = new StringBuilder();
		st.append(scheduleNew.getCourseCode().trim());
		st.append(INDEX_SEPARATOR);
		st.append(scheduleNew.getIndex().trim());
		st.append(SEPARATOR);
		st.append()
		
		
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

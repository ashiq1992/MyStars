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

import Model.AddDrop;
import Model.Course;
import Model.Schedule;

public class ScheduleController {
	/**
	 * Instantiated a FileManager object to read and write data to the text files.
	 */
	public static FileManager manage =new FileManager();
	
	/**
	 * Instantiated a AddDropController object to facilitate in Adding and dropping of courses
	 */
	AddDropController check = new AddDropController();
	
	/**
	 * A separator which is used to separate the elements from a text file
	 */
	public static final String SEPARATOR = "|";
	
	/**
	 * An index separator which is used to separate the elements from a text file
	 */
	public static final String INDEX_SEPARATOR = "_";
	
	/**
	 * A schedule separator which is used to separate the elements from a text file
	 */
	public static final String SCHEDULE_SEPARATOR = ",";
	
	/**
	 * A method which build a schedule and writes it to a file
	 * 
	 * @param schedule which a List of schedule to be written
	 * @param courseCode of a particular course
	 */
	public void saveSchedule(List schedule, String courseCode) {
		File f = new File("DataBase/schedule/" + courseCode + ".txt");

		String index;
		List tempList = new ArrayList();// to store students data

		for (int i = 0; i < schedule.size(); i++) {
			Schedule scheduleNew = (Schedule) schedule.get(i);
			StringBuilder st = new StringBuilder();
			st.append(scheduleNew.getCourseCode().trim());
			st.append(INDEX_SEPARATOR);
			index = scheduleNew.getIndex() + "";
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
			manage.write("DataBase/schedule/" + courseCode + ".txt", tempList);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * A method to read a schedule
	 * 
	 * @param courseCode of a particular course
	 * @return an Arraylist of schedule
	 */
	public ArrayList readSchedule(String courseCode) {

		ArrayList sch = new ArrayList();// to store Admins data

		try {
			ArrayList stringArray = (ArrayList)manage.read("DataBase/schedule/" + courseCode + ".txt");
			for (int x = 0; x < stringArray.size(); x++) {

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

				Schedule S1 = new Schedule(code, Integer.parseInt(index), type, day, venue, startTime, endTime);
				sch.add(S1);
				

			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return sch;

	}

	/**
	 * A method to checks for clashes
	 * 
	 * @param newCourseCode of a particular course
	 * @param newIndex of a particular course and a specific index
	 * @param matricNum of a student
	 * @return True if a clash exists.False if there is no clash.
	 */
	public boolean clashcheck(String newCourseCode, int newIndex, String matricNum) {
		boolean clash = false;
		boolean interrupt=false;
		CourseController CC=new CourseController();
		List checkCourse = new ArrayList();
		List timeTable;
		List newTimeTable;
		List match = new ArrayList();
		int courseindex;
		List courses = new ArrayList();
		List index = new ArrayList();
		String starts, ends, newStarts, newEnds;
		int start, end, newStart, newEnd;
		int durationOld, durationNew;
		List day = new ArrayList();
		List startTime = new ArrayList();
		List endTime = new ArrayList();
		try {
			checkCourse=CC.readAllCourse("DataBase/courses.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int q=0;q<checkCourse.size();q++){
			Course c=(Course)checkCourse.get(q);
			if(c.getCourseCode().toLowerCase().equals(newCourseCode.toLowerCase())){
				interrupt=true;
		
			}
		}
		
		

		courses = check.returnCourseRegistered(matricNum);
		if (courses.isEmpty() == false && interrupt==true) {
		
			/* Rtrive all the data for timetable of the student */
			for (int x = 0; x < courses.size(); x += 2) {

				courseindex = Integer.parseInt(courses.get(x + 1).toString());
				timeTable = this.readSchedule(courses.get(x).toString());

				for (int k = 0; k < timeTable.size(); k++) {

					Schedule s = (Schedule) timeTable.get(k);
					if (s.getCourseCode().toLowerCase().equals(courses.get(x).toString().toLowerCase()) && (s.getIndex() == courseindex)) {
						day.add(s.getDay());
						startTime.add(s.getStartTime());
						endTime.add(s.getEndTime());
//						System.out.println(
//							s.getDay()+	s.getCourseCode() + " " + s.getIndex() + " " + startTime.get(k) + " " + endTime.get(k));
					}

				}

			}

			newTimeTable = this.readSchedule(newCourseCode);
			/*TO get ther schedule only by the index stated*/
			for(int p=0;p<newTimeTable.size();p++){
				Schedule TT=(Schedule)newTimeTable.get(p);
				if(TT.getCourseCode().toLowerCase().equals(newCourseCode.toLowerCase())&&TT.getIndex()==newIndex){
					match.add(TT);
				}
			}
			/*end of check                                      */
			
			
			for (int k = 0; k < day.size(); k++) {
				starts = startTime.get(k).toString();
				starts = starts.substring(0, 2);
				start = Integer.parseInt(starts);
				ends = endTime.get(k).toString();
				ends = ends.substring(0, 2);
				end = Integer.parseInt(ends);
				durationOld = end - start;// get the duration of the session
				
				
				
				for (int x = 0; x < match.size(); x++) {
					Schedule s = (Schedule) match.get(x);
				
					if (s.getDay().equals(day.get(k).toString())) {
					
						newStarts = s.getStartTime();
						newStarts = newStarts.substring(0, 2);
						newStart = Integer.parseInt(newStarts);
						newEnds = s.getEndTime();
						newEnds = newEnds.substring(0, 2);
						newEnd = Integer.parseInt(newEnds);
						durationNew = newEnd - newStart;

						// condition for checking for clashes

						if (newStart >= start) {
							//System.out.println(newStart + " " + start);
							if (newStart > (start + durationOld)) {
								clash = false;// success
								//System.out.println(newStart + " " + start + " test 5");
							} else {
								//System.out.println(newStart + " " + start + " test 6");
								clash = true;// clash
								break;
							}
						} else {
							//System.out.println(newStart + " " + start + " test 7");
							if ((newStart + durationNew) < start) {
								clash = false;// success
								//System.out.println(newStart + " " + start + " test 8");
							} else {
								//System.out.println(newStart + " " + start + " test 9");
								clash = true;// clash
								break;
							}
						}

					}
					
					

				}

			}
		}

	
		return clash;

	}

	/**
	 * A method to display schedules given a course code
	 * 
	 * @param courseCode of a particular courses
	 */
	public void displaySchedule(String courseCode){
		boolean status=false;
		List match=new ArrayList();
		CourseController n=new CourseController();
		try {
			 match=n.readAllCourse("Database/courses.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List timeTable=new ArrayList();
	for(int l=0;l<match.size();l++){
		Course MATCH=(Course)match.get(l);
		if(MATCH.getCourseCode().toLowerCase().equals(courseCode.toLowerCase())){
			status=true;
		}
	}
	if(status==true){
		timeTable = this.readSchedule(courseCode);
			System.out.println("************************************************************************************************");
		for(int p=0;p<timeTable.size();p++){
			Schedule TT=(Schedule)timeTable.get(p);
			System.out.println("Index: "+TT.getIndex());
			if(TT.getCourseCode().toLowerCase().equals(courseCode)){
			System.out.println(TT.getType()+" Day: "+TT.getDay()+" Venue: "+TT.getVenue()+" StartTime: "+TT.getEndTime());
			}
			System.out.println("************************************************************************************************");
		}
		
		
	}
		
		/*end of check                                      */
		
	}

}

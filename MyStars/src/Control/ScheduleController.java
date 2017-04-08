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
	AddDropController check = new AddDropController();
	// Properties
	public static final String SEPARATOR = "|";
	public static final String INDEX_SEPARATOR = "_";
	public static final String SCHEDULE_SEPARATOR = ",";

	public void saveSchedule(List schedule, String courseCode) {
		File f = new File("dataBase/schedule/" + courseCode + ".txt");// creates
																		// the
																		// file
																		// in
																		// the
																		// folder
		// coursecode_index|type|day|venue|starttime|endTime|
		// index
		// type
		// day
		// starttime
		// endtime

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
			write("DataBase/schedule/" + courseCode + ".txt", tempList);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public ArrayList readSchedule(String courseCode) {

		ArrayList sch = new ArrayList();// to store Admins data

		try {
			ArrayList stringArray = (ArrayList) read("DataBase/schedule/" + courseCode + ".txt");
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

	/** Read the contents of the given file. */
	public List read(String fileName) throws IOException {
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
		PrintWriter out = new PrintWriter(new FileWriter(fileName, true));// create
																			// a
																			// new
																			// file
		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}

	public boolean clashcheck(String newCourseCode, int newIndex, String matricNum) {
		boolean clash = false;
		List timeTable;
		List newTimeTable;
		int courseindex;
		List courses = new ArrayList();
		List index = new ArrayList();
		String starts, ends, newStarts, newEnds;
		int start, end, newStart, newEnd;
		int durationOld, durationNew;
		List day = new ArrayList();
		List startTime = new ArrayList();
		List endTime = new ArrayList();

		courses = check.returnCourseRegistered(matricNum);
		if (courses.isEmpty() == false) {
			/* Rtrive all the data for timetable of the student */
			for (int x = 0; x < courses.size(); x += 2) {

				courseindex = Integer.parseInt(courses.get(x + 1).toString());
				timeTable = this.readSchedule(courses.get(x).toString());

				for (int k = 0; k < timeTable.size(); k++) {

					Schedule s = (Schedule) timeTable.get(k);
					if (s.getCourseCode().toLowerCase().equals(courses.get(x)) && (s.getIndex() == courseindex)) {
						day.add(s.getDay());
						startTime.add(s.getStartTime());
						endTime.add(s.getEndTime());
//						System.out.println(
//								s.getCourseCode() + " " + s.getIndex() + " " + startTime.get(k) + " " + endTime.get(k));
					}

				}

			}

			newTimeTable = this.readSchedule(newCourseCode);
			for (int k = 0; k < day.size(); k++) {
				starts = startTime.get(k).toString();
				starts = starts.substring(0, 2);
				start = Integer.parseInt(starts);
				ends = endTime.get(k).toString();
				ends = ends.substring(0, 2);
				end = Integer.parseInt(ends);
				durationOld = end - start;// get the duration of the session

				for (int x = 0; x < newTimeTable.size(); x++) {
					Schedule s = (Schedule) newTimeTable.get(x);
					if (s.getDay().toLowerCase().equals(day.get(k).toString().toLowerCase())) {
						newStarts = s.getStartTime();
						newStarts = newStarts.substring(0, 2);
						newStart = Integer.parseInt(newStarts);
						newEnds = s.getEndTime();
						newEnds = newEnds.substring(0, 2);
						newEnd = Integer.parseInt(newEnds);
						durationNew = newEnd - newStart;

						// condition for checking for clashes

						if (newStart > start) {
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
					
					else{
						clash=false;
						
					}

				}

			}
		}

		else {
			clash = false;
		}
		return clash;

	}

	// public static void main(String [] args){
	// ScheduleController n=new ScheduleController();
	// boolean check;
	// ArrayList test;
	// test=n.readSchedule("ce3005");
	// for(int x=0;x<test.size();x++){
	// Schedule s=(Schedule)test.get(0);
	// System.out.println(s.getCourseCode()+" "+s.getIndex()+" "+s.getType()+"
	// "+s.getDay()+" "+s.getStartTime()+" "+s.getEndTime());;
	//
	//
	//
	// }

	// check=n.clashcheck("ce3005", 1, "u163");
	// if(check){
	// System.out.println(" clash");
	// }
	// else{
	// System.out.println("no Clash");
	// }
	// }
	//
}

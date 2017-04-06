package Model;

import java.util.List;

public class Schedule {

	private String Venue;
	private String day;
	private String type;
	private int index;
	private String courseCode;
	private String startTime, endTime;

	public Schedule(String courseCode, int index, String type, String day, String venue, String startTime,
			String endTime) {
		this.courseCode = courseCode;
		this.day = day;
		this.type = type;
		this.index = index;
		this.Venue = venue;
		this.startTime = startTime;
		this.endTime = endTime;

	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getVenue() {
		return Venue;
	}

	public void setVenue(String venue) {
		Venue = venue;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		type = type;
	}

}

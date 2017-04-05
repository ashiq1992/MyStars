package Model;

import java.util.List;

public class Schedule {

	private String Venue;
	private String day;
	private String type;
	private int index;
	private String courseCode;
	private String time;
	
	
	public Schedule(String courseCode,int index,String type,String day,String venue,String time){
		this.courseCode=courseCode;
		this.day=day;
		this.type=type;
		this.index=index;
		this.Venue=venue;
		this.time=time;
		
		
	}
	
	
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
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

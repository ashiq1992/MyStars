package Model;

public class Access {
	
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	
	
	public Access()
	{
		
	}
	
	
	public Access(String startTime,String endTime,String startDate,String endDate)
	{
		this.startTime=startTime;
		this.endTime=endTime;
		this.startDate=startDate;
		this.endDate=endDate;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	
	
	
	
	
	

}

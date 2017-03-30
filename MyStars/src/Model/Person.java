package Model;

public class Person {

	private String gender;
	private String name;
	private String nationality;
	private String userId;
	private String password;
	
	public Person()
	{
		
	}
	
	public Person(String gender,String name,String nationality,String userId,String password)
	{
		this.gender=gender;
		this.name=name;
		this.nationality=nationality;
		this.userId=userId;
		this.password=password;
	}
	
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}

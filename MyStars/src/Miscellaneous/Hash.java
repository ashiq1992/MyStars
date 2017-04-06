package Miscellaneous;
import java.io.Console;
import java.security.MessageDigest;

public class Hash {
	
	public static String hashString(String password, String salt, String pepper) throws Exception{
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((password + salt + pepper).getBytes());
 
        byte byteData[] = md.digest();
        
        StringBuffer hexString = new StringBuffer();
        
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
        
        return hexString.toString();
	}
	
	
	 public static void passwordExample() {        
	        Console console = System.console();
	        if (console == null) {
	            System.out.println("Couldn't get Console instance");
	            System.exit(0);
	        }

	        console.printf("Testing password%n");
	        char passwordArray[] = console.readPassword("Enter your secret password: ");
	        console.printf("Password entered was: %s%n", new String(passwordArray));

	    }
	
	public static void main(String args[])
	{
//		try {
//			System.out.println(hashString("test","hello","bye"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		passwordExample();
	}
}
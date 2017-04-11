package Miscellaneous;
import java.io.Console;
import java.security.MessageDigest;

/**
 * A class to Hash clear text 
 * @author Ameen
 * @author Ashiq
 * @author Will
 * @author Reuben
 * @author Waqas
 * @since 2017-04-01
 * @version 1.0
 *
 */
public class Hash {
	
	
	/**
	 * A method to hash the a clear text to Hash
	 * 
	 * @param password the input to be hashed
	 * @param salt to add another input to be hashed together with the password
	 * @return a String of hash content
	 * @throws Exception is thrown if inputs are not passed in as String inputs
	 */
	public static String hashString(String password, String salt) throws Exception{
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((password + salt ).getBytes());
 
        byte byteData[] = md.digest();
        
        StringBuffer hexString = new StringBuffer();
        
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
        
        return hexString.toString();
	}
	


}
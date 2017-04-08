package Miscellaneous;
import java.io.Console;
import java.security.MessageDigest;

public class Hash {
	
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
	
	

	
//	public static void main(String args[])
//	{
//		try {
//			System.out.println(hashString("123456","N1234567B"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
}
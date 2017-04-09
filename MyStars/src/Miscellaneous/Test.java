package Miscellaneous;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	
	public void validateMatricNum(String txt) {
		//String txt="U1234567K";

	    String re1="(U)";	// Any Single Character 1
	    String re2="(\\d)";	// Any Single Digit 1
	    String re3="(\\d)";	// Any Single Digit 2
	    String re4="(\\d)";	// Any Single Digit 3
	    String re5="(\\d)";	// Any Single Digit 4
	    String re6="(\\d)";	// Any Single Digit 5
	    String re7="(\\d)";	// Any Single Digit 6
	    String re8="(\\d)";	// Any Single Digit 7
	    String re9="([A-Z])";	// Any Single Word Character (Not Whitespace) 1

	    Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7+re8+re9, Pattern.DOTALL);
	    Matcher m = p.matcher(txt);
	    if (m.find())
	    {
	    	
	    	System.out.println("valid");
	    	
//	        String c1=m.group(1);
//	        String d1=m.group(2);
//	        String d2=m.group(3);
//	        String d3=m.group(4);
//	        String d4=m.group(5);
//	        String d5=m.group(6);
//	        String d6=m.group(7);
//	        String d7=m.group(8);
//	        String w1=m.group(9);
//	        System.out.print("("+c1.toString()+")"+"("+d1.toString()+")"+"("+d2.toString()+")"+"("+d3.toString()+")"+"("+d4.toString()+")"+"("+d5.toString()+")"+"("+d6.toString()+")"+"("+d7.toString()+")"+"("+w1.toString()+")"+"\n");
	    }
	    
	    else
	    {
	    	
	    	System.out.println("invalid");
	    }
	}
	
	public static void main(String args[])
	{
		Test t1 = new Test();
		t1.validateMatricNum("Z1634567K");
	}

}

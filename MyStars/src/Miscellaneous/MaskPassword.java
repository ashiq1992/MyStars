package Miscellaneous;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * A class to mask the password when entered into the console
 * @author Ameen
 * @author Ashiq
 * @author Will
 * @author Reuben
 * @author Waqas
 * @since 2017-04-01
 * @version 1.0
 *
 */
public class MaskPassword {
	/**
	 * This method reads the password entered and at the same time masks the password
	 * from the console
	 * 
	 * @param prompt refers to the prompt of the message such as "Enter Password"
	 * @return the password the user entered in clear text
	 */
   public static String readPassword (String prompt) {
      EraserThread et = new EraserThread(prompt);
      Thread mask = new Thread(et);
      mask.start();

      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      String password = "";

      try {
          password = in.readLine();
      } catch (IOException ioe) {
          ioe.printStackTrace();
      }
      et.stopMasking();
      return password;
   }
}   

/**
 * A class which converts the clear text input entered on the console to asterix in realtime
 * 
 */
class EraserThread implements Runnable {
	/**
	 * A boolean variable
	 */
   private boolean stop;
   
   /**
    * To prompt a message to the user 
    * @param prompt
    */
   public EraserThread(String prompt) {
       System.out.print(prompt);
   }
   
   /**
    * A method to run the code that turns clear text to asterix realtime
    */
   public void run () {
      while (!stop){
         System.out.print("\010*");
         try {
            Thread.currentThread().sleep(1);
         } catch(InterruptedException ie) {
            ie.printStackTrace();
         }
      }
   }
   
   /**
    * To stop masking of the text
    */
   public void stopMasking() {
      this.stop = true;
   }
}
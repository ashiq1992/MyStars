package Control;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import Model.Admin;
import Model.Student;

/**
 * A File manager class which handles the way data is written to the file
 * @author Ameen
 * @author Ashiq
 * @author Will
 * @author Reuben
 * @author Waqas
 * @since 2017-04-01
 * @version 1.0
  */
public class FileManager {

	/**
	 * Declared an AddDrop Controller for adding and dropping of courses
	 */
	private AddDropController addDrop;
	
	/**
	 * Declared an Admin Object;
	 */
	private Admin admin;
	
	/**
	 * Declared a Student object
	 */
	private Student student;
	
	

	/** 
	 * A method that retrieves data given the file name of a text file
	 * 
	 * @param fileName  The file name of the text file where the contents should be read
	 * @return  Returns a List of data read from a particular text file
	 * @throws IOException  Exception thrown if there is problems reading from the file
	 */
	
	public static List read(String fileName) throws IOException {
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
	
	
	/**
	 * A method that writes content onto a text file
	 * 
	 * @param fileName  The filename where the data should be written onto
	 * @param data  Takes in a List of data to be written to the text file
	 * @throws IOException  Exception when there is a problem writing data on the file
	 */
	public static void write(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName, true));

		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}
	
	
	/**
	 * A method to clear all contents of a file before writing new contents on it
	 * 
	 * @param fileName the name of the file where the contents should be written on
	 * @param data which takes in a List of data
	 * @throws IOException throws when files contents are unable to written onto the file
	 */
	public static void writeNew(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName));

		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}
	

}

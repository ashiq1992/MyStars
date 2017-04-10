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

public class FileManager {

	
	private AddDropController a1;
	private Admin ad1;
	private Student s1;
	
	

	/** 
	 * A method that retrieves data given the file name of a text file
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
		PrintWriter out = new PrintWriter(new FileWriter(fileName, true));// true
																			// to
																			// ensure
																			// the
																			// previous
																			// data
																			// is
																			// not
																			// deleted

		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}
	
	
	
	public static void writeNew(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName));// clears
																	// the data
																	// in the
																	// file
																	// before
																	// writing
																	// it back
																	// to the
																	// file

		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}
	

}

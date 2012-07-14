package net.plaidypus.deadreckoning.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.newdawn.slick.util.LogSystem;

public class FileSaveLogSystem implements LogSystem {
	/** The output stream for dumping the log out on */
	public static PrintStream out = System.out;
	
	public static PrintWriter fileWriter;
	public static String fileName;
	
	public FileSaveLogSystem(String outDest){
		try {
			fileWriter = new PrintWriter( new FileWriter(new File(outDest)));
			fileName = outDest;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Log an error
	 * 
	 * @param message The message describing the error
	 * @param e The exception causing the error
	 */
	public void error(String message, Throwable e) {
		error(message);
		error(e);
	}

	/**
	 * Log an error
	 * 
	 * @param e The exception causing the error
	 */
	public void error(Throwable e) {
		StringWriter s = new StringWriter();
		PrintWriter r = new PrintWriter(s);
		e.printStackTrace(r);
		sendMessage(s.toString());
	}

	/**
	 * Log an error
	 * 
	 * @param message The message describing the error
	 */
	public void error(String message) {
		sendMessage(new Date()+" ERROR: " +message);
	}

	/**
	 * Log a warning
	 * 
	 * @param message The message describing the warning
	 */
	public void warn(String message) {
		sendMessage(new Date()+" WARN: " +message);
	}

	/**
	 * Log an information message
	 * 
	 * @param message The message describing the infomation
	 */
	public  void info(String message) {
		sendMessage(new Date()+" INFO: " +message);
	}

	/**
	 * Log a debug message
	 * 
	 * @param message The message describing the debug
	 */
	public void debug(String message) {
		sendMessage(new Date()+" DEBUG: " +message);
	}

	/**
	 * Log a warning with an exception that caused it
	 * 
	 * @param message The message describing the warning
	 * @param e The cause of the warning
	 */
	public void warn(String message, Throwable e) {
		warn(message);
		error(e);
	}
	
	private void sendMessage(String message){
		out.println(message);
		fileWriter.println(message);
	}
	
	public static void closeWriter(){
		fileWriter.close();
	}

}

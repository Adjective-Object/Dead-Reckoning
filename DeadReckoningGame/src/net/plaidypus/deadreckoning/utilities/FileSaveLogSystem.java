package net.plaidypus.deadreckoning.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.newdawn.slick.util.LogSystem;

public class FileSaveLogSystem implements LogSystem {
	/** The output stream for dumping the log out on */
	public static PrintStream out = System.out;
	
	public static PrintWriter fileWriter;
	public static String fileName;
	
	public static boolean printLog;
	
	String currentColor = "";
	
	static int 
		COLOR_STD = 0,
		COLOR_RED = 1,
		COLOR_BLUE = 2,
		COLOR_ORANGE = 3;
	
	public FileSaveLogSystem(String outDest){
		try {
			fileWriter = new PrintWriter( new FileWriter(new File(outDest)));
			fileName = outDest;
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileWriter.println("{\\rtf1\\ansi\\ansicpg1252\\cocoartf1038\\cocoasubrtf360");// header of RTF files
		fileWriter.println("{\\fonttbl\\f0\\fnil\\fcharset0 AndaleMono;}");
		
		fileWriter.print("{\\colortbl;"); 			//begin color table
		fileWriter.print("\\red255\\green0\\blue0;");		//Color 1 - red
		fileWriter.print("\\red0\\green0\\blue160;");		//Color 2 - blue
		fileWriter.print("\\red255\\green150\\blue0;");		//Color 3 - orange
		fileWriter.println("}");						//end color table
		
		fileWriter.println("\\f0");
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
		error(e.getMessage());
		StringWriter s = new StringWriter();
		PrintWriter r = new PrintWriter(s);
		e.printStackTrace(r);
		sendMessage(COLOR_RED,s.toString());
	}

	/**
	 * Log an error
	 * 
	 * @param message The message describing the error
	 */
	public void error(String message) {
		sendMessage(COLOR_RED,message);
	}

	/**
	 * Log a warning
	 * 
	 * @param message The message describing the warning
	 */
	public void warn(String message) {
		sendMessage(COLOR_ORANGE,message);
	}

	/**
	 * Log an information message
	 * 
	 * @param message The message describing the infomation
	 */
	public  void info(String message) {
		sendMessage(COLOR_STD,message);
	}

	/**
	 * Log a debug message
	 * 
	 * @param message The message describing the debug
	 */
	public void debug(String message) {
		sendMessage(COLOR_BLUE,message);
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
	
	private void sendMessage(int colorCode,String message){
		if(printLog){
			out.println(message);//TODO ANSI colors in console - not supported by eclipse, so...
		}
		fileWriter.print("\\cf"+colorCode+" ");
		fileWriter.println(message);
		fileWriter.println("\\");
	}
	
	public static void closeWriter(){
		fileWriter.println("}");
		fileWriter.close();
	}

}

package brennan.brennan.robotlogger.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import brennan.brennan.robotlogger.RobotLogger;
import brennan.brennan.robotlogger.TryUSB;
import brennan.brennan.robotlogger.config.Config;
/**
 * RobotLogger (c) 2016 by Brennan Macaig
 *
 * RobotLogger is licensed under a
 * Creative Commons Attribution-ShareAlike 4.0 International License.

 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by-sa/4.0/>. 
 *
 */
public class LogToFile {
	
	/*
	 * Filled this variable with junk, so that if filePath has been reset then it will exist, otherwise it won't (and won't throw errors!)
	 */
	public static String filePath = "asdlfhiy3kjadi";
	
	
	public static void executeCommand(String msg, String level) {
		if (RobotLogger.programWasQuit == true) {
			System.out.println("RobotLogger was safely quit. Ignoring request...");
			return;
		} else {
			File f = new File(filePath);
			if (f.exists() && (!f.isDirectory())) {
				// File exists
				writeToLog(msg, level);
			} else if (!(f.exists() && (!f.isDirectory()))) {
				// File doesn't exist
				createLogFile();
				writeToLog(msg, level);
			} else {
				RobotLogger.exitClean("HIGH", "LogToFile.executeCommand()[19]; File is a directory! Please clean filesystem.");
			}
		}
	}
	private static String buildLogName() {
		if (Config.usbBOOL == true) {
			// Attempt to find the USB.
			if (TryUSB.tryUSB() == true) {
				return "/U/" + Config.filePath.toString() + Config.prefix.toString() + "-" + getCurrentTime().toString() + ".log";
			} else {
				if(Config.exitBOOL == true) {
					RobotLogger.exitClean("HIGH", "Could not load USB, and safe-exit is on! Quitting!");
					return "";
				} else {
					// Resort to default path
					return "" + Config.filePath.toString() + Config.prefix.toString() + "-" + getCurrentTime().toString() + ".log";
				}
			}
		} else {
			return "" + Config.filePath.toString() + Config.prefix.toString() + "-" + getCurrentTime().toString() + ".log";
		}
	}
	
	private static void createLogFile() {
		filePath = buildLogName();
		PrintWriter writer;
		try {
			File file = new File(filePath);
			file.getParentFile().mkdirs();
			writer = new PrintWriter(filePath, "UTF-8");
			writer.print("\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			RobotLogger.exitClean("HIGH", "FileNotFound exception, line 81");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			RobotLogger.exitClean("HIGH", "UnsupportedEncodingException, line 84");
			e.printStackTrace();
		}
		

		
	}
	
	private static String buildMessage(String message, String level) {
		return "[" + getCurrentTime() + "] [" + level + "] " + message + "\n";
	}
	
	private static void writeToLog(String write, String level) {
		try {
			// BUGFIX: Changed path so that it's actually correct.
		    Files.write(Paths.get(buildLogName()), buildMessage(write, level).getBytes(), StandardOpenOption.APPEND);
		    System.out.println(buildMessage(write, level));
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
			RobotLogger.exitClean("HIGH", "IOException, line 101");
			e.printStackTrace();
		}
	}
	
	private static String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date date = new Date();
		return dateFormat.format(date).toString();
	}
}

package com.concordrobotics.log.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.concordrobotics.log.RobotLogger;
public class Config {
	public static String usb = "";
	public static boolean usbBOOL = false;
	public static String filePath = "";
	public static String prefix = "";
	public static String exitOp = "";
	public static boolean exitBOOL = true;
	
	public static void getConfig() {
		// Test if the file exists
		// TODO: Make this *NOT* a direct path.
		File f = new File("/home/lvuser/robotlogger.config");
		if (!(f.exists() && !f.isDirectory())) {
			// File does NOT exist, create it!
			createConfig();
			loadConfig();
			parseVariables();
		} else if (f.exists() && !f.isDirectory()) {
			// File does exist, just load it.
			loadConfig();
			parseVariables();
		} else {
			// Something else, exit the program!
			RobotLogger.exitClean("HIGH", "Config.getConfig() [22] --> could not access file 'robotlogger.config'.");
		}
	}
	
	public static void loadConfig() {
		// Grab the variables from the config and save them somewhere safe.
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			// TODO: Make this not an absolute path.
			input = new FileInputStream("/home/lvuser/robotlogger.config");
			
			prop.load(input);
			
			usb = prop.getProperty("usb-storage");
			filePath = prop.getProperty("filepath");
			prefix = prop.getProperty("file-prefix");
			exitOp = prop.getProperty("exit-without-usb");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void createConfig() {
		// No config file was detected, lets create one.
		Properties prop = new Properties();
		OutputStream output = null;

		try {
			// TODO: Make this not an absolute path.
			output = new FileOutputStream("/home/lvuser/robotlogger.config");

			// set the properties value
			prop.setProperty("usb-storage", "true");
			prop.setProperty("filepath", "logs/");
			prop.setProperty("file-prefix", "RobotRun");
			prop.setProperty("exit-without-usb", "true");
			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}
	
	public static void parseVariables() {
		// Take the strings and turn any of them into non-strings that shouldn't be strings (I.E. true or false to boolean)
		if (usb.equalsIgnoreCase("false")) {
			usbBOOL = false;
		} else if (usb.equalsIgnoreCase("true")) {
			usbBOOL = true;
		} else {
			// Could not cast, will go with safer option.
			usbBOOL = false;
		}
		
		if (exitOp.equalsIgnoreCase("true")) {
			exitBOOL = true;
		} else if (exitOp.equalsIgnoreCase("false")) {
			exitBOOL = false;
		} else {
			// Could not cast, will go with safer option
			exitBOOL = true;
		}
	}
	
	/**
	 * This method is _NEVER_ called internally.
	 * You can call it manually from your code.
	 * Realistically, this does the same thing as manually deleting the config file.
	 * It does however prove useful for...
	 * 1. Avoiding FTP into the RIO and wanting a clean config.
	 * 2. Cleaning the config incase this code malfunctions (I.E. causing robot to crash with no USB, and USB set to enabled-unsafe).
	 */
	public static void resetConfig() {
		try {
			File file = new File("/home/lvuser/robotlogger.config");
			file.delete();
			createConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

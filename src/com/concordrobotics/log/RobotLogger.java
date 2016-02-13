package com.concordrobotics.log;

import com.concordrobotics.command.LogToFile;
import com.concordrobotics.log.config.Config;

import edu.wpi.first.wpilibj.DriverStation;
public class RobotLogger {
	
	public static boolean programWasQuit = false;
	
	public static void init() {
		Config.getConfig(); // Get the config file
		
	}
	
	public static void log(String msg, String level) {
		LogToFile.executeCommand(msg, level);
	}
	
	public static void exitClean(String errorLevel, String message) {
		// Safely exit the program
		programWasQuit = true; // Alert all files that the program was quit (clean or no) so that code does not crash. 
		DriverStation.reportError("This is a notice of CLEAN-EXIT from RobotLogger. RobotLogger provided the following:\n LVL" + errorLevel + ", RSN:" + message, false);
		System.err.println("This is a notice of CLEAN-EXIT. Code provided following exit reason: \n LVL: " + errorLevel + ", RSN: " + message);
		
	}
}

package brennan.brennan.robotlogger;

import brennan.brennan.robotlogger.command.LogToFile;
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
public class RobotLogger {
	
	public static boolean programWasQuit = false;
	
	public void init() {
		Config.getConfig(); // Get the config file
		
	}
	
	public void log(String msg, String level) {
		LogToFile.executeCommand(msg, level);
	}
	// This has to be static because I'm really lazy.
	public static void exitClean(String errorLevel, String message) {
		// Safely exit the program
		programWasQuit = true; // Alert all files that the program was quit (clean or no) so that code does not crash. 
		System.err.println("This is a notice of CLEAN-EXIT. Code provided following exit reason: \n LVL: " + errorLevel + ", RSN: " + message);
	}
}

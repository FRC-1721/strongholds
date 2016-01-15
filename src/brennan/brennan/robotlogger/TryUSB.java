package brennan.brennan.robotlogger;

import java.io.File;

/**
 * RobotLogger (c) 2016 by Brennan Macaig
 *
 * RobotLogger is licensed under a
 * Creative Commons Attribution-ShareAlike 4.0 International License.

 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by-sa/4.0/>. 
 *
 */
public class TryUSB {

	/**
	 * Method that tests to see if a USB-Drive has been mounted to the RoboRIO
	 * By default drives are mounted as /U/, so we'll write to /U/.
	 */
	
	public static boolean tryUSB() {
		File f = new File("/U/");
		if (f.exists() && f.isDirectory()) {
			// /U/ exists, meaning there is a drive plugged in!
			return true;
		} else {
			return false;
		}
	}
}

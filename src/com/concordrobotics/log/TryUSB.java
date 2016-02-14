package com.concordrobotics.log;

import java.io.File;
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

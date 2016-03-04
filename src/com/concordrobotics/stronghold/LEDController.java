package com.concordrobotics.stronghold;

public class LEDController {

	/**
	 * Send data to Arduino to manipulate LEDs.
	 * @author Brennan
	 */
	public static void sendLED(String pattern) {
		
		char[] send = pattern.toCharArray();
		byte[] data = new byte[send.length];
		
		for ( int i = 0; i < send.length; i++ ) {
			data[i] = (byte) send[i];	
		}
		
		RobotMap.wire.transaction(data, data.length, null, 0);
	}
	
	
}

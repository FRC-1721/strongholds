package com.concordrobotics.stronghold;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	/**
     * DRIVE TRAIN VARIABLES
     */
	public static final int spLeftPort = 0;
	public static final int spRightPort = 1;
	
	/**
	 * SHOOTER VARIABLES
	 */
	public static final int spShootLP = 2;
	public static final int spShootLR = 3;
	public static final int spShootAP = 4;
	public static final int spShootKP = 5;
	
	/**
	 * MOTORS, MOTOR CONTROLLERS, ETC.
	 */
	// dtLeft, dtRight = drivetrain. 
	// shootL, shootR = shooter left/right CIMs.
	// shootA = shooter angle.
	public static VictorSP dtLeft, dtRight, shootL, shootR, shootA;
	public static Servo shootK;
	
	/**
	 * Joysticks, Input, and Buttons.
	 */
	public static final int jLeftPort = 1;
	public static final int jRightPort = 2;
}

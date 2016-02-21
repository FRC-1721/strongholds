package com.concordrobotics.stronghold;

import com.concordrobotics.stronghold.subsystems.*;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Encoder;
import com.kauailabs.navx.frc.AHRS;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	/**
	 * MOTORS, MOTOR CONTROLLERS, ETC.
	 *  
	 * dtLeft, dtRight = drivetrain.
	 * shootL, shootR = shooter left/right CIMs.
	 * shootA = shooter angle
	 */
	public static VictorSP dtLeft, dtRight, shootL, shootR, shootA;
	public static Servo shootK;
	public static Encoder dtLeftEnc, dtRightEnc;

	/**
     * DRIVE TRAIN VARIABLES
     */
	public static final int spLeftPort = 0;
	public static final int spRightPort = 1;
	public static final int dtLeftEncPortA = 0;
	public static final int dtLeftEncPortB = 1;
	public static final int dtRightEncPortA = 2;
	public static final int dtRightEncPortB = 3;
	public static final boolean dtLeftEncReversed = false;
	public static final boolean dtRightEncReversed = false;	
	public static double dtP = 0.0, dtI = 0.01, dtD = 0.0, dtF = 0.0;
	public static CustomPIDController dtLeftController;
	public static CustomPIDController dtRightController;	
	
	/**
	 * NAVX Gyro & PID
	 * 
	 */
	public static AHRS navx;
	public static double navP = 0.06, navI = 0.0, navD = 0.0, navF = 0.0;

	/**
	 * SHOOTER VARIABLES
	 */
	public static final int spShootLP = 2;
	public static final int spShootRP = 3;
	public static final int spShootAP = 4;
	public static final int spShootKP = 5;
	
	public static final double sSpinupTime = .75; // Spin-up time in seconds.
	/**
	 *Joysticks, Input, and Buttons.
	 */
	public static final int jLeftPort = 1;
	public static final int jRightPort = 2;
	public static final int jOpPort = 3;

	/**
	 * BUTTON MAPPING
	 */
	public static final int jTrigger = 1;
}

package com.concordrobotics.stronghold;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//#################################
	// MOTORS, SERVOS, ENCODERS, GYRO
	//#################################
	/** VictorSP */
	public static VictorSP dtLeft, dtRight, shootL, shootR, shootA;
	/** Servo */
	public static Servo shootK;
	/** Encoders */
	public static Encoder dtLeftEnc, dtRightEnc, shootEnc;

	//##############
	// ROBOT VISION
	//##############
	public static CameraServer camera;
	
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
	public static double dtP = 0.0, dtI = 0.04, dtD = 0.01, dtF = 0.0;
	public static CustomPIDController dtLeftController;
	public static CustomPIDController dtRightController;	
	
	/**
	 * NAVX Gyro & PID
	 * 
	 */
	public static AHRS navx;
	public static double navP = 0.1, navI = 0.01, navD = 0.05, navF = 0.0;

	/**
	 * SHOOTER VARIABLES
	 */
	public static final int spShootL = 2;
	public static final int spShootR = 3;
	public static final int spShootA = 4;
	public static final int kickerP = 5;
	
	public static final int encShootA = 4;
	public static final int encShootB = 5;
	
	public static final double shooterP = 0.1;
	public static final double shooterI = 0.002;
	public static final double shooterD = 0.001;
	
	public static final double spinUp = .75;
	public static final double pitchUpVolts = .3;  // NOTE: This is reversed!
	public static final double pitchDownVolts = -.4; // NOTE: This is reversed!
	public static final double suckLVolts = .3;
	public static final double suckRVolts = -.3;
	
	/**
	 *Joysticks, Input, and Buttons.
	 */
	public static final int jLeftPort = 1;
	public static final int jRightPort = 2;
	public static final int jOpPort = 3;
}
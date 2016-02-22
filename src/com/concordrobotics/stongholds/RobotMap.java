package com.concordrobotics.stongholds;

import com.concordrobotics.strongholds.subsystems.DriveTrain;
import com.concordrobotics.strongholds.subsystems.Shooter;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;

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
	
	//#############
	// DRIVE TRAIN
	//#############
	public static final int spLeftP = 0;
	public static final int spRightP = 1;
	public static final int encLeftA = 0;
	public static final int encLeftB = 1;
	public static final int encRightA = 2;
	public static final int encRightB = 3;
	public static final boolean encLeftInvert = false;
	public static final boolean encRightInvert = false;
	public static RobotDrive drive;
	
	//############
	// SUBSYSTEMS 
	//############
	public static DriveTrain driveTrain;
	public static Shooter shooter;
	public static OI oi;
	
	//###################
	// SHOOTER VARIABLES
	//###################
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
	
	//###########################
	// JOYSTICKS, USER INTERFACE
	//###########################
	public static final int jLeftPort = 1;
	public static final int jRightPort = 2;
	public static final int jOpPort = 3;
	
}

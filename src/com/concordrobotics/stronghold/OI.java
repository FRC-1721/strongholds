package com.concordrobotics.stronghold;

import com.concordrobotics.stronghold.commands.*;
import com.concordrobotics.stronghold.subsystems.DriveTrain.GyroMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
public class OI {
	
	public static Joystick jLeft;
	public static Joystick jRight;
	public static Joystick jOperator;
	
	// Shooter controls
	public static JoystickButton sucker;
	public static JoystickButton angleUp;
	public static JoystickButton angleDown;
	public static JoystickButton shooter;
	public static JoystickButton setAngle25;
	public static JoystickButton lowGoal;
	
	// Drive controls
    public static JoystickButton driveReverseOnButton;
    public static JoystickButton driveReverseOffButton;
    public static JoystickButton enableGyroRateButton;
    public static JoystickButton disableGyroRateButton;
    public static JoystickButton enableDrivePIDButton;
    public static JoystickButton disableDrivePIDButton;
    public static JoystickButton disablePIDShooter;
    
    public OI() {
    	jLeft = new Joystick(RobotMap.jLeftPort);
    	jRight = new Joystick(RobotMap.jRightPort);
    	jOperator = new Joystick(RobotMap.jOpPort);
    	
    	//Shooter commands
    	angleUp = new JoystickButton(jOperator, 5);
		angleDown = new JoystickButton(jOperator, 7);
		
		sucker = new JoystickButton(jOperator, 6);
		shooter = new JoystickButton(jOperator, 8);
		
		setAngle25 = new JoystickButton(jOperator, 4);
		lowGoal = new JoystickButton(jOperator, 2);
		disablePIDShooter = new JoystickButton(jOperator, 3);
		
		//####################
		// COMMAND ACTIVATION 
		//####################
		angleUp.whileHeld(new PitchUpCommand());
		angleDown.whileHeld(new PitchDownCommand());
		angleUp.whenReleased(new PitchReleaseCommand());
		angleDown.whenReleased(new PitchReleaseCommand());
		sucker.whileHeld(new SuckBallCommand());
		sucker.whenReleased(new ReleaseThrowerCommand());
		disablePIDShooter.whenPressed(new SetPitchToZero());
		
		
		shooter.whenPressed(new ThrowBallCommand()); //MULTITHREAD THIS.  I WILL FIX YOUR BRACES AFTER
		setAngle25.whenPressed(new SetPitchAngle(70));
		lowGoal.whenPressed(new SetPitchAngle(25));
    	
    	// Drive commands
    	driveReverseOnButton = new JoystickButton(jLeft, 11);
    	driveReverseOnButton.whenPressed(new SetDriveReversed(true));
    	driveReverseOffButton = new JoystickButton(jLeft, 12);
    	driveReverseOffButton.whenPressed(new SetDriveReversed(false));  	
    	enableGyroRateButton = new JoystickButton(jLeft, 10);
    	enableGyroRateButton.whenPressed(new SetDriveGyroMode(GyroMode.rate));
    	disableGyroRateButton = new JoystickButton(jLeft, 9);
    	disableGyroRateButton.whenPressed(new SetDriveGyroMode(GyroMode.off)); 
    	enableDrivePIDButton = new JoystickButton(jLeft, 8);
    	enableDrivePIDButton.whenPressed(new EnableDrivePIDCommand());
    	disableDrivePIDButton = new JoystickButton(jLeft, 7);
    	disableDrivePIDButton.whenPressed(new DisableDrivePIDCommand());
    }
}

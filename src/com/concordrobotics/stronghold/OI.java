package com.concordrobotics.stronghold;

import com.concordrobotics.stronghold.commands.*;

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
    public static JoystickButton enableDrivePIDButton;
    public static JoystickButton disableDrivePIDButton;
    public static JoystickButton enableDriveHeadingLockButton;
    public static JoystickButton disableDriveHeadingLockButton;
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
		angleUp.whenPressed(new PitchUpCommand());
		angleDown.whenPressed(new PitchDownCommand());
		angleUp.whenReleased(new PitchReleaseCommand());
		angleDown.whenReleased(new PitchReleaseCommand());
		sucker.whileHeld(new SuckBallCommand());
		sucker.whenReleased(new ReleaseThrowerCommand());
		disablePIDShooter.whenPressed(new SetPitchToZero());
		
		
		shooter.whenPressed(new ThrowBallCommand()); //MULTITHREAD THIS.  I WILL FIX YOUR BRACES AFTER
		setAngle25.whenPressed(new SetPitchAngle(65));
    	
    	// Drive commands
    	enableDrivePIDButton = new JoystickButton(jLeft, 7);
    	enableDrivePIDButton.whenPressed(new EnableDrivePIDCommand());
    	disableDrivePIDButton = new JoystickButton(jLeft, 11);
    	disableDrivePIDButton.whenPressed(new DisableDrivePIDCommand());   	
    	enableDriveHeadingLockButton = new JoystickButton(jLeft, 8);
    	enableDriveHeadingLockButton.whenPressed(new EnableDriveHeadingLock());
    	disableDriveHeadingLockButton = new JoystickButton(jLeft, 12);
    	disableDriveHeadingLockButton.whenPressed(new DisableDriveHeadingLock());    	

    }
}

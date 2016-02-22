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
	public static JoystickButton setAngle10;
	
	// Drive controls
    public static JoystickButton enableDrivePIDButton;
    public static JoystickButton disableDrivePIDButton;
    public static JoystickButton enableDriveHeadingLockButton;
    public static JoystickButton disableDriveHeadingLockButton;
    
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
		setAngle10 = new JoystickButton(jOperator, 2);
		
		
		//####################
		// COMMAND ACTIVATION 
		//####################
		angleUp.whenPressed(new PitchUpCommand());
		angleDown.whenPressed(new PitchDownCommand());
		angleUp.whenReleased(new PitchReleaseCommand());
		angleDown.whenReleased(new PitchReleaseCommand());
		sucker.whenPressed(new SuckBallCommand());
		sucker.whenReleased(new ReleaseThrowerCommand());
		shooter.whenPressed(new ThrowBallCommand());
		setAngle25.whenPressed(new SetPitchAngle(25));
		setAngle10.whenPressed(new SetPitchAngle(10));
    	
    	// Drive commands
    	enableDrivePIDButton = new JoystickButton(jLeft, 2);
    	enableDrivePIDButton.whenPressed(new EnableDrivePIDCommand());
    	disableDrivePIDButton = new JoystickButton(jLeft, 3);
    	disableDrivePIDButton.whenPressed(new DisableDrivePIDCommand());   	
    	enableDriveHeadingLockButton = new JoystickButton(jLeft, 4);
    	enableDriveHeadingLockButton.whenPressed(new EnableDriveHeadingLock());
    	disableDriveHeadingLockButton = new JoystickButton(jLeft, 5);
    	disableDriveHeadingLockButton.whenPressed(new DisableDriveHeadingLock());    	

    }

}

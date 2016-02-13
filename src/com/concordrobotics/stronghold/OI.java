package com.concordrobotics.stronghold;

import com.concordrobotics.stronghold.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
public class OI {
    public final Joystick jLeft;
    public final Joystick jRight;
    public final Joystick operator;
    public static JoystickButton trig;
    public static JoystickButton enableDrivePIDButton;
    public static JoystickButton disableDrivePIDButton;
    public static JoystickButton enableDriveHeadingLockButton;
    public static JoystickButton disableDriveHeadingLockButton;
    
    public OI() {
    	jLeft = new Joystick(RobotMap.jLeftPort);
    	jRight = new Joystick(RobotMap.jRightPort);
    	operator = new Joystick(RobotMap.jOpPort);
    	trig = new JoystickButton(jLeft, 1);
    	trig.whenPressed(new ShootCommand());
    	enableDrivePIDButton = new JoystickButton(jLeft, 2);
    	enableDrivePIDButton.whenPressed(new EnableDrivePIDCommand());
    	disableDrivePIDButton = new JoystickButton(jLeft, 3);
    	disableDrivePIDButton.whenPressed(new DisableDrivePIDCommand());   	
    	enableDriveHeadingLockButton = new JoystickButton(jLeft, 4);
    	enableDriveHeadingLockButton.whenPressed(new EnableDriveHeadingLock());
    	disableDriveHeadingLockButton = new JoystickButton(jLeft, 5);
    	disableDriveHeadingLockButton.whenPressed(new DisableDriveHeadingLock());    	

    }

    public JoystickButton getBtn(int joyid, int button)
    {
    	switch (joyid) {
    	case RobotMap.jLeftPort:
    		return new JoystickButton(jLeft, button);
    	case RobotMap.jRightPort:
    		return new JoystickButton(jRight, button);
    	case RobotMap.jOpPort:
    		return new JoystickButton(operator, button);
    	default:
    		return null;
    	}
    }
}

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
    public static JoystickButton enableEasyControlLeft;
    public static JoystickButton enableEasyControlRight;
    public static JoystickButton disableEasyControlLeft;
    public static JoystickButton disableEasyControlRight;
    public static JoystickButton sucker;
    public static JoystickButton shooter;
    
    
    public OI() {
    	jLeft = new Joystick(RobotMap.jLeftPort);
    	jRight = new Joystick(RobotMap.jRightPort);
    	operator = new Joystick(RobotMap.jOpPort);
    	
    	enableEasyControlLeft = new JoystickButton(jLeft, 12);
    	enableEasyControlRight = new JoystickButton(jRight, 12);
    	enableEasyControlRight.whenPressed(new EnableEasyControl());
    	enableEasyControlLeft.whenPressed(new EnableEasyControl());
    	disableEasyControlLeft = new JoystickButton(jLeft, 11);
    	disableEasyControlRight = new JoystickButton(jRight, 11);
    	disableEasyControlLeft.whenPressed(new DisableEasyControl());
    	disableEasyControlRight.whenPressed(new DisableEasyControl());    	
    	enableDrivePIDButton = new JoystickButton(jLeft, 2);
    	enableDrivePIDButton.whenPressed(new EnableDrivePIDCommand());
    	disableDrivePIDButton = new JoystickButton(jLeft, 3);
    	disableDrivePIDButton.whenPressed(new DisableDrivePIDCommand());   	
    	enableDriveHeadingLockButton = new JoystickButton(jLeft, 4);
    	enableDriveHeadingLockButton.whenPressed(new EnableDriveHeadingLock());
    	disableDriveHeadingLockButton = new JoystickButton(jLeft, 5);
    	disableDriveHeadingLockButton.whenPressed(new DisableDriveHeadingLock());    	

    	sucker = new JoystickButton(operator, 6);
    	shooter = new JoystickButton(operator, 8);
    	sucker.whileHeld(new SuckCommand());
    	shooter.whenPressed(new ShootCommand());
    	
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

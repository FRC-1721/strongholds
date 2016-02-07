package com.concordrobotics.stronghold.subsystems;


import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.commands.DriveStop;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
/** 
 * We don't want to touch this yet.
 * @author Zach
 * 
 */
public class DriveTrain extends Subsystem {
	
	/* REQUIRED METHOD */
	/* !!! UNUSED !!! */
	public enum DriveMode {
		tankMode, arcadeMode
	}

	protected DriveMode mode = DriveMode.tankMode;
	
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveStop());
	}
	
	public void jInput(Joystick left, Joystick right) {
		switch (mode) {
		case tankMode:
			// TANK
			RobotMap.robotDrive.tankDrive(left, right);
			Timer.delay(.01);
			break;
		case arcadeMode:
			// ARCADE
			// TODO: Grab this code from zachary.
			break;
		default:
			break;
		}
	}
	
	public void stop() {
		switch (mode) {
		case tankMode:
			RobotMap.robotDrive.drive(0, 0);
			break;
		case arcadeMode:
			RobotMap.robotDrive.drive(0, 0);
			break;
		default:
			break;
		}
	}
	
	public void setDriveMode(DriveMode dMode) {
		mode = dMode;
	}
}

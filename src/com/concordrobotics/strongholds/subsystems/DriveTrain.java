package com.concordrobotics.strongholds.subsystems;

import com.concordrobotics.stongholds.RobotMap;
import com.concordrobotics.strongholds.commands.DriveCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

	protected void initDefaultCommand() {
	}

	/**
	 * Take joystick input to make the drive train move about in TANK drive.
	 * @param left - H.I.D. Joystick LEFT
	 * @param right - H.I.D. Joystick RIGHT
	 */
	public void jInput(Joystick left, Joystick right) {
		RobotMap.drive.tankDrive(left, right);
	}
	
	/**
	 * Stop the drive train from moving after command is done.
	 * @author Brennan
	 */
	public void stopDT() {
		RobotMap.drive.tankDrive(0, 0);
	}
	
}

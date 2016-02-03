package com.concordrobotics.stronghold.subsystems;

import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	
	/* REQUIRED METHOD */
	/* !!! UNUSED !!! */
	protected void initDefaultCommand() {}
	
	public void jInput(Joystick left, Joystick right, int mode) {
		switch (mode) {
		case 1:
			// TANK
			RobotMap.tank.tankDrive(left, right);
			Timer.delay(.01);
			break;
		case 2:
			// ARCADE
			// TODO: Grab this code from zachary.
			break;
		default:
			break;
		}
	}
	
	public void stop(int mode) {
		switch (mode) {
		case 1:
			RobotMap.tank.drive(0, 0);
			break;
		case 2:
			RobotMap.arcade.drive(0, 0);
			break;
		default:
			break;
		}
	}
}

package com.concordrobotics.stronghold.subsystems;


import com.concordrobotics.stronghold.CustomRobotDrive;
import com.concordrobotics.stronghold.commands.DriveInTeleop;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/** 
 * We don't want to touch this yet.
 * @author Zach
 * 
 */
public class DriveTrain extends Subsystem {
	
	
	protected static CustomRobotDrive m_robotDrive;
	/* REQUIRED METHOD */
	/* !!! UNUSED !!! */
	public enum DriveMode {
		tankMode, arcadeMode
	}

	protected DriveMode mode = DriveMode.tankMode;
	
	public DriveTrain(CustomRobotDrive robotDrive) {
		super("DriveTrain");
		m_robotDrive = robotDrive;
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveInTeleop());
	}
	
	public void jInput(Joystick left, Joystick right) {
		switch (mode) {
		case tankMode:
			// TANK
			m_robotDrive.tankDrive(left, right);
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
			m_robotDrive.drive(0, 0);
			break;
		case arcadeMode:
			m_robotDrive.drive(0, 0);
			break;
		default:
			break;
		}
	}
	
	public void setDriveMode(DriveMode dMode) {
		mode = dMode;
	}
	
	public void updateSmartDashboard () {
		if (mode == DriveMode.tankMode) {
			SmartDashboard.putString("DriveTrain/mode", "tank");
		} else {
			SmartDashboard.putString("DriveTrain/mode", "arcade");
		}
		m_robotDrive.updateSmartDashboard();
	}
}

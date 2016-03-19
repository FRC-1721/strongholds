package com.concordrobotics.stronghold.subsystems;


import com.concordrobotics.stronghold.CustomRobotDrive;
import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.commands.DriveInTeleop;
import com.concordrobotics.stronghold.CustomPIDController;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSourceType;
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
	CustomPIDController distanceController;
	
	/* REQUIRED METHOD */
	/* !!! UNUSED !!! */
	public enum DriveMode {
		tankMode, arcadeMode, distanceMode
	}
	public enum GyroMode {
		off, rate, heading
	}
	protected boolean m_reversed = false;
	protected DriveMode mode = DriveMode.tankMode;
	protected GyroMode gyroMode = GyroMode.off;
	protected NavxController navController;
	
	public DriveTrain(CustomRobotDrive robotDrive) {
		super("DriveTrain");
		m_robotDrive = robotDrive;
		navController = Robot.navController;
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveInTeleop());
	}
	public GyroMode getGyroMode () {
		return gyroMode;
	}
	
	public double getDistance() {
    	double leftDistance = RobotMap.dtLeftEnc.getDistance();
    	double rightDistance = RobotMap.dtRightEnc.getDistance();
    	if (RobotMap.leftEncoderDisabled) {
    		leftDistance = rightDistance;
    	} else if (RobotMap.rightEncoderDisabled) {
    		rightDistance = leftDistance;
    	}
    	double avgDist = 0.5*(leftDistance + rightDistance);
    	return avgDist;
	}
	
	public void setGyroMode (GyroMode gMode) {
		if (gyroMode != gMode) {
			gyroMode = gMode;
			if (gyroMode == GyroMode.off) {
				navController.disable();
			} else if (gyroMode == GyroMode.heading) {
				navController.setPIDSourceType(PIDSourceType.kDisplacement);
				CustomPIDController gyroController = navController.getPIDController();
				gyroController.setPID(RobotMap.navP, RobotMap.navI, RobotMap.navD, RobotMap.navF);
			} else {
				navController.setPIDSourceType(PIDSourceType.kRate);
				CustomPIDController gyroController = navController.getPIDController();
				gyroController.setPID(RobotMap.navRateP, RobotMap.navRateI, RobotMap.navRateD, RobotMap.navRateF);
			}
		}
	}
	
	public void jInput(Joystick left, Joystick right) {
		switch (mode) {
		case tankMode:
			m_robotDrive.tankDrive(left, right);
			break;
		case arcadeMode:
			m_robotDrive.arcadeDrive(left);
			break;
		default:
			break;
		}
	}
	
	public boolean setReversed (boolean reversed) {
		m_robotDrive.setReversed(reversed);
		if (m_reversed != reversed) {
			m_reversed = reversed;
			return true;
		} else {
			return false;
		}
	}
	
	
	public void rawDrive(double leftValue, double rightValue) {
		switch (mode) {
		case distanceMode:
			double drivePower = Robot.distanceDrivePID.getPIDOutput();
			m_robotDrive.tankDrive(drivePower,drivePower);
			SmartDashboard.putNumber("DistanceRawDrive", drivePower);
			break;
		default:
			m_robotDrive.tankDrive(leftValue, rightValue);
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
			SmartDashboard.putString("DriveTrainMode", "tank");
		} else if (mode==DriveMode.arcadeMode) {
			SmartDashboard.putString("DriveTrainMode", "arcade");
		} else {
			SmartDashboard.putString("DriveTrainMode", "distance");
			Robot.distanceDrivePID.updateSmartDashboard();
		}
		m_robotDrive.updateSmartDashboard();
	}
}

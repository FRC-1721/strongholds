package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.subsystems.DriveTrain.GyroMode;
import edu.wpi.first.wpilibj.command.Command;

public class TurnAbsolute extends Command{
	double m_targetHeading;
	static int kToleranceIterations = 1;
	boolean checkOnTarget = true;
	
	public TurnAbsolute(double heading, int tolIter) {
		requires(Robot.driveTrain);
		m_targetHeading = heading;
		if (tolIter == 0) checkOnTarget = false;
		if (kToleranceIterations <= 1) kToleranceIterations = 2;
		kToleranceIterations = tolIter;
	}
	protected void initialize() { 
		Robot.driveTrain.setGyroMode(GyroMode.heading);
		Robot.navController.setSetpoint(m_targetHeading - RobotMap.yawOffset);
		Robot.navController.setAbsoluteTolerance(5.0);
		Robot.navController.setToleranceBuffer(kToleranceIterations);
	}
	
	protected void execute() { 
		Robot.driveTrain.rawDrive(0, 0); 
		}
	// Just set to run tank.
	
	protected void end() { 
		Robot.driveTrain.stop(); 
		} // Just set to tank.
	
	protected void interrupted() { end(); }
	
	/* Unused, required methods. Pfffft */
	protected boolean isFinished() {
		if (checkOnTarget) {
			return Robot.navController.onTargetDuringTime();
		} else {
			return true;
		}
	}
	
}

package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.Robot;
import edu.wpi.first.wpilibj.command.Command;
import com.concordrobotics.stronghold.subsystems.DriveTrain.GyroMode;

public class TurnRelative extends Command{
	double m_targetHeading;
	double m_turnAngle;
	protected int kToleranceIterations = 1;
	
	public TurnRelative(double turnAngle, int tolIter) {
		requires(Robot.driveTrain);
		kToleranceIterations = tolIter;
		if (kToleranceIterations <= 1) kToleranceIterations = 2;
		m_turnAngle =  turnAngle;
	}
	
	protected void initialize() { 
		m_targetHeading = RobotMap.navx.getYaw() + m_turnAngle;
		Robot.driveTrain.setGyroMode(GyroMode.heading);
		Robot.navController.setSetpointRelative(m_turnAngle);
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
		//return finished;
		return Robot.navController.onTargetDuringTime();
	}
	
}

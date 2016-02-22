package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.Robot;
import edu.wpi.first.wpilibj.command.Command;
public class TurnRelative extends Command{
	double m_targetHeading;
	double m_turnAngle;
	static int kToleranceIterations = 10;
	public TurnRelative(double turnAngle) {
		requires(Robot.driveTrain);
		m_turnAngle =  turnAngle;
	}
	protected void initialize() { 
		m_targetHeading = RobotMap.navx.getYaw() + m_turnAngle;
		Robot.navController.reset();
		Robot.robotDrive.enableHeadingLock();
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
		return Robot.navController.onTargetDuringTime(0.5);
	}
	
}

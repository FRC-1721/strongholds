package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class TurnAbsolute extends Command{
	double m_targetHeading;
	static int kToleranceIterations = 10;
	public TurnAbsolute(double heading) {
		requires(Robot.driveTrain);
		m_targetHeading = heading;
	}
	protected void initialize() { 
		Robot.navController.reset();
		Robot.robotDrive.enableHeadingLock();
		Robot.navController.setSetpoint(m_targetHeading);
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
		return Robot.navController.onTarget();
	}
	
}

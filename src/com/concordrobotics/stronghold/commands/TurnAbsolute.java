package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.subsystems.DriveTrain.GyroMode;
import edu.wpi.first.wpilibj.command.Command;

public class TurnAbsolute extends Command{
	double m_targetHeading;
	static int kToleranceIterations = 1;
	public TurnAbsolute(double heading, int tolIter) {
		requires(Robot.driveTrain);
		m_targetHeading = heading;
		kToleranceIterations = tolIter;
	}
	protected void initialize() { 
		Robot.navController.reset();
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
		return Robot.navController.onTargetDuringTime();
	}
	
}

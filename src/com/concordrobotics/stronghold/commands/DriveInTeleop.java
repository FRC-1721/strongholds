package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.CustomRobotDrive;
import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveInTeleop extends Command{

	public DriveInTeleop() {
		requires(Robot.driveTrain);
	}

	protected void execute() { Robot.driveTrain.jInput(Robot.oi.jLeft, Robot.oi.jRight); } // Just set to run tank.
	protected void end() { Robot.driveTrain.stop(); } // Just set to tank.
	protected void interrupted() { end(); }
	
	/* Unused, required methods. Pfffft */
	protected void initialize() { }
	protected boolean isFinished() {return false;}
	
}

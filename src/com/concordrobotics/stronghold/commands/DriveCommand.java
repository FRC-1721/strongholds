package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCommand extends Command{

	public DriveCommand() {
		requires(RobotMap.driveTrain); 
	}
	protected void execute() { RobotMap.driveTrain.jInput(RobotMap.oi.jLeft, RobotMap.oi.jRight, 1); } // Just set to run tank.
	protected void end() { RobotMap.driveTrain.stop(1); } // Just set to tank.
	protected void interrupted() { end(); }
	
	/* Unused, required methods. Pfffft */
	protected void initialize() {}
	protected boolean isFinished() {return false;}
	
}

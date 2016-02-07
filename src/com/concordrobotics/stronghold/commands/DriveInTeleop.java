package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.CustomRobotDrive;
import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class DriveInTeleop extends Command{

	public DriveInTeleop() {
		requires(RobotMap.robotDrive); 
	}
	private void requires(CustomRobotDrive tank) {
		// TODO Auto-generated method stub
		
	}
	protected void execute() { RobotMap.driveTrain.jInput(RobotMap.oi.jLeft, RobotMap.oi.jRight); } // Just set to run tank.
	protected void end() { RobotMap.driveTrain.stop(); } // Just set to tank.
	protected void interrupted() { end(); }
	
	/* Unused, required methods. Pfffft */
	protected void initialize() {}
	protected boolean isFinished() {return false;}
	
}

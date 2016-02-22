package com.concordrobotics.strongholds.commands;

import com.concordrobotics.stonghold.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCommand extends Command {

	

	protected void execute() {
		RobotMap.driveTrain.jInput(RobotMap.oi.jLeft, RobotMap.oi.jRight);
	}

	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {
		RobotMap.driveTrain.stopDT();
	}
	
	protected void interrupted() {end();}
	protected void initialize() {
	}
}
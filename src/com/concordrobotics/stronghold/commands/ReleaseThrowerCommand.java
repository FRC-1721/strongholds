package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stonghold.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class ReleaseThrowerCommand extends Command {

	protected void execute() {
		RobotMap.shooter.throwerRelease();
	}

	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {}
	protected void interrupted() {}
	protected void initialize() {}
}

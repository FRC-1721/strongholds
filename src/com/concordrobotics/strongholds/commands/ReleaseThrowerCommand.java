package com.concordrobotics.strongholds.commands;

import com.concordrobotics.stongholds.RobotMap;

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

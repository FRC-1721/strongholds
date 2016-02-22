package com.concordrobotics.strongholds.commands;

import com.concordrobotics.stonghold.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class SuckBallCommand extends Command {

	protected void execute() {
		RobotMap.shooter.suck();
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {}
	protected void interrupted() {}
	protected void initialize() {}
	
}

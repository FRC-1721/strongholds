package com.concordrobotics.strongholds.commands;

import com.concordrobotics.stongholds.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class ThrowBallCommand extends Command {
	
	private boolean finished = false;
	
	protected void execute() {
		RobotMap.shooter.shoot();
		finished = true;
	}
	protected boolean isFinished() {
		return finished;
	}
	
	protected void end() {}
	protected void interrupted() {}
	protected void initialize() {	}
}

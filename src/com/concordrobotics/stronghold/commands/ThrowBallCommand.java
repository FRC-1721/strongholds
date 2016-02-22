package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ThrowBallCommand extends Command {
	
	private boolean finished = false;
	
	protected void execute() {
		Robot.shooter.shoot();
		finished = true;
	}
	protected boolean isFinished() {
		return finished;
	}
	
	protected void end() {}
	protected void interrupted() {}
	protected void initialize() {	}
}

package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ThrowBallCommand extends Command {
	
	private boolean finished = false;
	
	protected void execute() {
		finished = Robot.shooter.waitLoop();
	}
	protected boolean isFinished() {
		return finished;
	}
	
	protected void end() {
		Robot.shooter.throwerRelease();
	}
	protected void interrupted() {}
	protected void initialize() {	
		Robot.shooter.shoot();
		}
}

package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ThrowBallCommand extends Command {
	
	private boolean finished = false;
	private boolean shotStarted = false;
	
	protected void execute() {
		if(! shotStarted) {
			Robot.shooter.shoot();
			shotStarted = true;
		}
		finished = Robot.shooter.waitLoop();
	}
	protected boolean isFinished() {
		return finished;
	}
	
	protected void end() {
		Robot.shooter.throwerRelease();
	}
	protected void interrupted() {end();}
	protected void initialize() {	
		shotStarted = false;
		finished = false;
		}
}

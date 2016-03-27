package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SuckBallCommand extends Command {

	protected void execute() {
		Robot.shooter.waitLoop();
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {Robot.shooter.throwerRelease();}
	protected void interrupted() { end();}
	protected void initialize() {
		Robot.shooter.suck();
		}
	
}

package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PitchDownCommand extends Command {

	public PitchDownCommand() {
		requires(Robot.shooter);
	}
	protected void initialize() {
		Robot.shooter.disable();
	}
	protected void execute() {
		Robot.shooter.pitch(false); // Pitch down (negative)
	}

	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {}
	protected void interrupted() {}

	
}

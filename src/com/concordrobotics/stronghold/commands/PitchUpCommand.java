package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PitchUpCommand extends Command {

	protected void execute() {
		Robot.shooter.pitch(true); // Pitch up (positive)
	}

	protected boolean isFinished() {
		return true;
	}
	protected void end() {}
	protected void interrupted() {}
	protected void initialize() {}
}

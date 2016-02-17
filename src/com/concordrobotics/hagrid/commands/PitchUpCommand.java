package com.concordrobotics.hagrid.commands;

import com.concordrobotics.hagrid.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class PitchUpCommand extends Command {

	protected void execute() {
		RobotMap.shooter.pitch(true); // Pitch up (positive)
	}

	protected boolean isFinished() {
		return true;
	}
	protected void end() {}
	protected void interrupted() {}
	protected void initialize() {}
}

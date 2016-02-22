package com.concordrobotics.strongholds.commands;

import com.concordrobotics.stongholds.RobotMap;

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

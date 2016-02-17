package com.concordrobotics.hagrid.commands;

import com.concordrobotics.hagrid.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class PitchReleaseCommand extends Command {

	protected void execute() {
		RobotMap.shooter.pitchRelease(); // Zero out the motors
	}

	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {}
	protected void interrupted() {}
	protected void initialize() {}
}

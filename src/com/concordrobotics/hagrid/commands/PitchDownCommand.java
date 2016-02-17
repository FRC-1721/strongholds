package com.concordrobotics.hagrid.commands;

import com.concordrobotics.hagrid.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class PitchDownCommand extends Command {

	protected void execute() {
		RobotMap.shooter.pitch(false); // Pitch down (negative)
	}

	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {}
	protected void interrupted() {}
	protected void initialize() {}
	
}

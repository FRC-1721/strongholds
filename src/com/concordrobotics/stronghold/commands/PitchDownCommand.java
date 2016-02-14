package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Pitch down command
 * Command to pitch the shooter downwards.
 * After the command is executed, the angler is released (stopped)
 * @author Brennan
 */

public class PitchDownCommand extends Command {

private boolean finished = false;
	
	public PitchDownCommand() {
		requires(RobotMap.shooter);
	}
	
	protected void execute() {
		RobotMap.shooter.pitchDown();
		finished = true;
	}

	protected void end() {
		RobotMap.shooter.pitchRelease();
	}

	protected boolean isFinished() {
		return finished;
	}
	
	protected void initialize() {}
	protected void interrupted() {}
	
}

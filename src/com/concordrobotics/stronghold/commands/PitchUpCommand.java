package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to pitch the shooter up.
 * After the command is executed, the motors are released (turned off)
 * 
 * @author Brennan
 */
public class PitchUpCommand extends Command {
	
	private boolean finished = false;
	
	public PitchUpCommand() {
		requires(RobotMap.shooter);
	}
	
	protected void execute() {
		RobotMap.shooter.pitchUp();
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

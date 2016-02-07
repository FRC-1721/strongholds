package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class SuckCommand extends Command{

	public SuckCommand() {
		requires(RobotMap.shooter); 
	}
	protected void execute() { RobotMap.shooter.suck(); }
	protected void end() { RobotMap.shooter.release(); } //Fill in if motors are not stopped in suck method
	protected void interrupted() { end(); }
	
	/* Unused, required methods. Pfffft */
	protected void initialize() {}
	protected boolean isFinished() {return false;}
	
}

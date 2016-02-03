package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class ShootCommand extends Command{

	public ShootCommand() {
		requires(RobotMap.shooter); 
	}
	protected void execute() { RobotMap.shooter.shoot(); }
	protected void end() {} //Fill in if motors are not stopped in shoot method
	protected void interrupted() { end(); }
	
	/* Unused, required methods. Pfffft */
	protected void initialize() {}
	protected boolean isFinished() {return false;}
	
}

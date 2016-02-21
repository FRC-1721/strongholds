package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class ShootCommand extends Command{

	public ShootCommand() {
		requires(Robot.shooter); 
	}
	protected void execute() { Robot.shooter.shoot(); }
	protected void end() {} //Fill in if motors are not stopped in shoot method
	protected void interrupted() { end(); }
	
	/* Unused, required methods. Pfffft */
	protected void initialize() {}
	protected boolean isFinished() {
		if (Shooter.shoot == true) {
			return true;
		} else {
			return false;
		}
	}
	
}

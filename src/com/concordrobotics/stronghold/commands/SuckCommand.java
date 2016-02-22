package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;


/**
 * The command to intake the ball into the shooter area and ball holder.
 * Executes the Shooter class.
 * <p>Please take note:</p>
 * <p>This command shouldd *only* happen while a button is being held down.
 * To end this command, activate the end() method after releasing said button.
 * @author Eli, Brennan
 */
public class SuckCommand extends Command{

	public SuckCommand() {
		requires(Robot.shooter); 
	}
	protected void execute() { Robot.shooter.suck(); }
	protected void end() { Robot.shooter.release(); } //Fill in if motors are not stopped in suck method
	protected void interrupted() { end(); }
	
	protected void initialize() {}
	
	protected boolean isFinished() {
		if (Shooter.suck == true) {
			return true;
		} else {
			return false;
		}
	}
	
}

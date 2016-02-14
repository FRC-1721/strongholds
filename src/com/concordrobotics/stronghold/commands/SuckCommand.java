package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.RobotMap;
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
	
	private boolean sucker = false;
	
	public SuckCommand() {
		requires(RobotMap.shooter); 
	}
	protected void execute() { 
		RobotMap.shooter.suck(); 
		sucker = true;
	}
	protected void end() {} //Fill in if motors are not stopped in suck method
	protected void interrupted() { end(); }
	
	protected void initialize() {}
	
	protected boolean isFinished() {
		return sucker;
	}
	
}

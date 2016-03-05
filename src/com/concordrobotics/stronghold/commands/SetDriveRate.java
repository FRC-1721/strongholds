package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetDriveRate extends Command {

	protected boolean complete = false;
	double m_driveRate;
	
    public SetDriveRate(double rate) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_driveRate = rate;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.robotDrive.setDriveRate(m_driveRate);
    	complete = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return complete;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

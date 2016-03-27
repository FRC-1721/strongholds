package com.concordrobotics.stronghold.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.subsystems.DriveTrain.GyroMode;
/**
 *
 */
public class SetDriveGyroMode extends Command {

	protected boolean complete = false;
	protected GyroMode gyroMode;
	
    public SetDriveGyroMode(GyroMode gMode) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	gyroMode = gMode;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	complete = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.setGyroMode(gyroMode);
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

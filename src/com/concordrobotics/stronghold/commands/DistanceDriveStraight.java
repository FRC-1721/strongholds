package com.concordrobotics.stronghold.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.subsystems.DriveTrain;
/**
 *
 */
public class DistanceDriveStraight extends Command {
	double m_distance;
	static int kToleranceIterations = 10;
    public DistanceDriveStraight(double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	m_distance=distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.distanceDrivePID.enable();
    	Robot.distanceDrivePID.setSetpointRelative(m_distance);
    	Robot.driveTrain.setDriveMode(DriveTrain.DriveMode.distanceMode);
    	Robot.distanceDrivePID.setOutputRange(-1, 1.0);
		Robot.navController.setToleranceBuffer(kToleranceIterations);
		Robot.navController.setAbsoluteTolerance(1.0);	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.rawDrive(0.0, 0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       return Robot.distanceDrivePID.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.distanceDrivePID.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.distanceDrivePID.disable();
    }
}

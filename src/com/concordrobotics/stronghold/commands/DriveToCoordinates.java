package com.concordrobotics.stronghold.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.subsystems.DriveTrain;
/**
 *
 */
public class DriveToCoordinates extends Command {
	protected double targetX;
	protected double targetY;
	protected double heading;
	protected double headingErr;
	protected double distance;
	protected static final double kRad2Deg = 57.3;
	protected static final double kDistTol = 1.0;
	protected static final double angleTol = 15.0;
	static int kToleranceIterations = 20;
	
    public DriveToCoordinates(double x, double y) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	targetX = x;
    	targetY = y;
    }

    protected void getDistanceAndHeading() {
    	double delX = targetX - Robot.positionEstimator.getDisplacementX();
    	double delY = targetY - Robot.positionEstimator.getDisplacementY();
    	heading = Math.atan2(delY, delX)*kRad2Deg;
    	distance = Math.sqrt(delX*delX + delY*delY);
    }
    
    protected void setSetpoints() {  	
    	Robot.navController.setSetpoint(heading);
    	double headingErr = Math.abs(RobotMap.navx.getYaw() - heading);
    	if (headingErr < angleTol) {
    		Robot.distanceDrivePID.setSetpointRelative(distance);
    	}
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.distanceDrivePID.enable();
    	Robot.navController.enable();
    	setSetpoints();
    	Robot.driveTrain.setDriveMode(DriveTrain.DriveMode.distanceMode);
    	Robot.distanceDrivePID.setOutputRange(-0.5, 0.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	getDistanceAndHeading();
    	setSetpoints();
    	Robot.driveTrain.rawDrive(0.0, 0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
      if ( distance < kDistTol ) {
    	   return true;
       } else {
    	   return false;
       } 
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.distanceDrivePID.disable();
    	Robot.navController.disable();
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

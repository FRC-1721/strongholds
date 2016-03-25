package com.concordrobotics.stronghold.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.subsystems.DriveTrain;
import com.concordrobotics.stronghold.subsystems.DriveTrain.DriveMode;
import com.concordrobotics.stronghold.subsystems.DriveTrain.GyroMode;
/**
 *
 */
public class DriveToCoordinates extends Command {
	protected double targetX;
	protected double targetY;
	protected double heading;
	protected double headingErr;
	protected double distance;
	protected double m_startDistance;
	protected double mSpeed;
	protected static final double kRad2Deg = 57.3;
	protected static final double kDistTol = 1.0;
	protected static final double angleTol = 5.0;
	static int kToleranceIterations = 5;
	protected boolean onHeading = false;
	
    public DriveToCoordinates(double x, double y, double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	mSpeed = speed;
    	targetX = x;
    	targetY = y;
    }
    
    
    protected void getDistanceAndHeading() {

    	double delX = targetX - Robot.positionEstimator.getDisplacementX();
    	double delY = targetY - Robot.positionEstimator.getDisplacementY();
    	heading = Math.atan2(delY, delX)*kRad2Deg;
    	distance = Math.sqrt(delX*delX + delY*delY);
    	if (mSpeed < 0 ) {
    		// Driving backwards
    		heading = heading + 180.0;
    		distance = -distance;
    	}
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	onHeading = false;
    	getDistanceAndHeading();
    	Robot.driveTrain.setGyroMode(GyroMode.heading);
    	Robot.navController.setSetpoint(heading);
    	Robot.navController.setAbsoluteTolerance(angleTol);
    	Robot.navController.setToleranceBuffer(kToleranceIterations);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (! onHeading) {
    		Robot.driveTrain.rawDrive(0.0, 0.0);
    		if (Robot.navController.onTargetDuringTime()) {
    			onHeading = true;
    			m_startDistance = Robot.driveTrain.getDistance();
    			//Robot.driveTrain.setDriveMode(DriveTrain.DriveMode.distanceMode);
    	    	//Robot.distanceDrivePID.setAbsoluteTolerance(kDistTol);
    	    	//Robot.distanceDrivePID.setToleranceBuffer(kToleranceIterations);
    	    	//Robot.distanceDrivePID.setOutputRange(-mSpeed, mSpeed);
    			//Robot.distanceDrivePID.setSetpointRelative(distance);
    		}
    	} 
    	if (onHeading) {
    		Robot.driveTrain.rawDrive(mSpeed,mSpeed);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
      if (onHeading) {
      	if (distance > 0) {
    		if (Robot.driveTrain.getDistance() > m_startDistance + distance) {
    			return true;
    		}
    	} else {
    		if (Robot.driveTrain.getDistance() < m_startDistance + distance) {
    			return true;
    		}
    	}
    	return false;
      } else {
    	  return false;
      }
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.setDriveMode(RobotMap.teleopDriveMode);
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

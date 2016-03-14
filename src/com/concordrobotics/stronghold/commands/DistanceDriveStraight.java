package com.concordrobotics.stronghold.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.subsystems.DriveTrain;
/**
 *
 */
public class DistanceDriveStraight extends Command {
	double m_distance;
	static int kToleranceIterations = 1;
	protected double mSpeed = 0.0;
	protected double m_startDistance;
    public DistanceDriveStraight(double distance, double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	mSpeed = speed;
    	m_distance=distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.distanceDrivePID.enable();
    	Robot.distanceDrivePID.setSetpointRelative(m_distance);
    	//Robot.driveTrain.setDriveMode(DriveTrain.DriveMode.distanceMode);
    	Robot.distanceDrivePID.setOutputRange(-Math.abs(mSpeed), Math.abs(mSpeed));
		Robot.distanceDrivePID.setToleranceBuffer(kToleranceIterations);
		Robot.distanceDrivePID.setAbsoluteTolerance(1.0);
		m_startDistance = RobotMap.dtRightEnc.getDistance();
		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.rawDrive(mSpeed,mSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (m_distance > 0) {
    		if (RobotMap.dtRightEnc.getDistance() > m_startDistance + m_distance) {
    			return true;
    		}
    	} else {
    		if (RobotMap.dtRightEnc.getDistance() < m_startDistance + m_distance) {
    			return true;
    		}
    	}
    	return false;
      /*

    	   return true;
       } else {
    	   return false;
       } */
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.distanceDrivePID.disable();
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.distanceDrivePID.disable();
    	Robot.driveTrain.stop();
    }
}

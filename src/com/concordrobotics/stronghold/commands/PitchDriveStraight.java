package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;
/**
 *
 */
public class PitchDriveStraight extends DistanceDriveStraight {
	double m_pitch;
	static int kToleranceIterations = 10;
    public PitchDriveStraight(double distance, double pitch) {
    	super(distance);
    	m_pitch = pitch;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (Robot.distanceDrivePID.onTarget()) return true;
        if( m_pitch > 0) {
        	if (RobotMap.navx.getPitch() > m_pitch) return true;
        } else {
        	if (RobotMap.navx.getPitch() < m_pitch) return true;
        }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.stop();
    }
}

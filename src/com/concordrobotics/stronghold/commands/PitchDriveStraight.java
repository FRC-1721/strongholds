package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;
/**
 *
 */
public class PitchDriveStraight extends DistanceDriveStraight {
	double m_pitch;
	static int kToleranceIterations = 10;
    public PitchDriveStraight(double distance, double pitch, boolean useGyro) {
    	super(distance, useGyro);
    	m_pitch = pitch;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (Robot.distanceDrivePID.onTargetDuringTime()) return true;
        if( m_pitch > 0) {
        	if (RobotMap.navx.getRoll() >= m_pitch) return true;
        } else {
        	if (RobotMap.navx.getRoll() <= m_pitch) return true;
        }
        return false;
    }

}

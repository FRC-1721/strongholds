package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.Timer;

public class DistanceDriveStraightPing extends DistanceDriveStraight {

	public Timer pingTimer;
	public double pingMin;
	public double pingTime;
	public double deltaPing = 1.0;
	public boolean onTarget = false;
	public DistanceDriveStraightPing(double distance, double speed, double pingMin, double pingTime) {
		super(distance, speed);
		this.pingMin = pingMin;
		this.pingTime = pingTime;
		pingTimer = new Timer();
		pingTimer.start();
		// TODO Auto-generated constructor stub
	}
	protected void initialize() {
		super.initialize();
		onTarget = false;
	}
	   // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	deltaPing = 1.0;
    	if (mSpeed < 0) {
    		deltaPing = RobotMap.backPing.getDistance() - pingMin;
    		if (deltaPing < 0) {
    			if (!onTarget) {
    				onTarget = true;
    				pingTimer.reset();
    			}
    			deltaPing =Math.min(0.0,deltaPing);
    			deltaPing = Math.max(deltaPing, 1.0);
    			
    		} else {
    			onTarget = false;
    		}
    	}
    	Robot.driveTrain.rawDrive(deltaPing*mSpeed,deltaPing*mSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (onTarget && (pingTimer.get() > pingTime)) {
    		return true;
    	} else {
    		return super.isFinished();
    	}
      /*

    	   return true;
       } else {
    	   return false;
       } */
    }
}

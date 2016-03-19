package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLowBar extends CommandGroup {
    
    public  AutoLowBar() {

    }
    
    public void initialize() {
    	if (RobotMap.useDrivePIDinAuto) {
    		addSequential(new EnableDrivePIDCommand());
    	}
    	addSequential(new SetCurrentYaw(180));
    	//addParallel(new SetPitchAngle(5));
    	addSequential(new TurnRelative(0.0, 1));
    	addSequential(new DistanceDriveStraight(-10.0,-0.7));
    	addParallel(new DriveToCoordinates(RobotMap.xAutoShootPosition, RobotMap.yAutoShootPosition, 0.8));
    	addParallel(new SetPitchAngle(RobotMap.autoShootAngle));
    	
    	addSequential(new TurnAbsolute(RobotMap.autoDriveAngle, 3));
    	addSequential(new ThrowBallCommand());
    	addParallel(new SetPitchAngle(5.0));
    	addSequential(new DriveToCoordinates(Robot.getStationX(1), 10.0, 0.8) );
    }
}
